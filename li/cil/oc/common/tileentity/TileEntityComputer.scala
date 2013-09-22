package li.cil.oc.common.tileentity

import java.util.concurrent.atomic.AtomicBoolean

import li.cil.oc.api.INetworkMessage
import li.cil.oc.client.{ PacketSender => ClientPacketSender }
import li.cil.oc.client.computer.{ Computer => ClientComputer }
import li.cil.oc.server.{ PacketSender => ServerPacketSender }
import li.cil.oc.server.computer.{ Computer => ServerComputer }
import li.cil.oc.server.computer.IComputerEnvironment
import li.cil.oc.server.computer.NetworkNode
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound

class TileEntityComputer(isClient: Boolean) extends TileEntityRotatable with IComputerEnvironment with ItemComponentProxy with BlockComponentProxy {
  def this() = this(false)

  protected val computer =
    if (isClient) new ClientComputer(this)
    else new ServerComputer(this)

  private val hasChanged = new AtomicBoolean(true)

  private var isRunning = computer.isRunning

  // ----------------------------------------------------------------------- //
  // NetworkNode
  // ----------------------------------------------------------------------- //

  override def receive(message: INetworkMessage) = message.getData match {
    case Array() if message.getName == "network.connect" =>
      computer.signal("component_added", message.getSource.getAddress)
    case Array() if message.getName == "network.disconnect" =>
      computer.signal("component_removed", message.getSource.getAddress)
    case Array(oldAddress: Integer) if message.getName == "network.reconnect" =>
      computer.signal("component_updated", message.getSource.getAddress, oldAddress)
    case Array(name: String, args @ _*) if message.getName == "signal" =>
      computer.signal(name, args: _*)
    case _ => // Ignore message.
  }

  override def onAddressChange = computer.signal("address_change", address)

  // ----------------------------------------------------------------------- //
  // General
  // ----------------------------------------------------------------------- //

  def turnOn() = computer.start()

  def turnOff() = computer.stop()

  def isOn = computer.isRunning

  def isOn_=(value: Boolean) = {
    computer.asInstanceOf[ClientComputer].isRunning = value
    worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord)
  }

  override def readFromNBT(nbt: NBTTagCompound) = {
    super.readFromNBT(nbt)
    computer.readFromNBT(nbt.getCompoundTag("computer"))
    readBlocksFromNBT(nbt.getCompoundTag("blocks"))
    readItemsFromNBT(nbt.getCompoundTag("items"))
  }

  override def writeToNBT(nbt: NBTTagCompound) = {
    super.writeToNBT(nbt)

    val computerNbt = new NBTTagCompound
    computer.writeToNBT(computerNbt)
    nbt.setCompoundTag("computer", computerNbt)

    val blocksNbt = new NBTTagCompound
    writeBlocksToNBT(blocksNbt)
    nbt.setCompoundTag("blocks", blocksNbt)

    val itemsNbt = new NBTTagCompound
    writeItemsToNBT(itemsNbt)
    nbt.setCompoundTag("items", itemsNbt)
  }

  override def updateEntity() = {
    computer.update()
    if (hasChanged.get) {
      worldObj.updateTileEntityChunkAndDoNothing(
        xCoord, yCoord, zCoord, this)
      if (isRunning != computer.isRunning) {
        isRunning = computer.isRunning
        ServerPacketSender.sendComputerState(this, isRunning)
      }
    }
  }

  override def validate() = {
    super.validate()
    if (worldObj.isRemote)
      ClientPacketSender.sendComputerStateRequest(this)
  }

  // ----------------------------------------------------------------------- //
  // Interfaces and updating
  // ----------------------------------------------------------------------- //

  def onNeighborBlockChange(blockId: Int) =
    (0 to 5).foreach(checkBlockChanged(_))

  def isUseableByPlayer(entityplayer: EntityPlayer) =
    world.getBlockTileEntity(xCoord, yCoord, zCoord) == this &&
      entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64

  def world = worldObj

  def coordinates = (xCoord, yCoord, zCoord)

  def driver(id: Int) = itemDriver(id).orElse(blockDriver(id))

  def component(id: Int) = itemComponent(id).orElse(blockComponent(id))

  def markAsChanged() = hasChanged.set(true)
}