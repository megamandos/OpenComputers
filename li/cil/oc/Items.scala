package li.cil.oc

import cpw.mods.fml.common.registry.GameRegistry
import li.cil.oc.common.item
import net.minecraft.block.Block
import net.minecraft.item.{Item, ItemStack}
import net.minecraftforge.oredict.OreDictionary

object Items {
  var multi: item.Delegator = _

  // ----------------------------------------------------------------------- //
  // Tools
  var analyzer: item.Analyzer = _
  var terminal: item.Terminal = _

  // ----------------------------------------------------------------------- //
  // Memory
  var ram1, ram2, ram3, ram4, ram5: item.Memory = _

  // ----------------------------------------------------------------------- //
  // Storage
  var floppyDisk: item.FloppyDisk = _
  var hdd1, hdd2, hdd3: item.HardDiskDrive = _
  // Servers
  var server: item.Server = _

  // ----------------------------------------------------------------------- //
  // Cards
  var abstractBus: item.AbstractBusCard = _
  var gpu1, gpu2, gpu3: item.GraphicsCard = _
  var lan: item.NetworkCard = _
  var rs: item.RedstoneCard = _
  var wlan: item.WirelessNetworkCard = _

  // ----------------------------------------------------------------------- //
  // Upgrades
  var upgradeCrafting: item.UpgradeCrafting = _
  var upgradeGenerator: item.UpgradeGenerator = _
  var upgradeNavigation: item.UpgradeNavigation = _
  var upgradeSign: item.UpgradeSign = _
  var upgradeSolarGenerator: item.UpgradeSolarGenerator = _

  // ----------------------------------------------------------------------- //
  // Crafting
  var ironNugget: item.IronNugget = _
  var cuttingWire: item.CuttingWire = _
  var acid: item.Acid = _
  var disk: item.Disk = _

  var buttonGroup: item.ButtonGroup = _
  var arrowKeys: item.ArrowKeys = _
  var numPad: item.NumPad = _

  var transistor: item.Transistor = _
  var chip1, chip2, chip3: item.Microchip = _
  var alu: item.ALU = _
  var cpu0, cpu1, cpu2: item.CPU = _
  var cu: item.ControlUnit = _

  var rawCircuitBoard: item.RawCircuitBoard = _
  var circuitBoard: item.CircuitBoard = _
  var pcb: item.PrintedCircuitBoard = _
  var card: item.CardBase = _

  def init() {
    multi = new item.Delegator(Settings.get.itemId)

    GameRegistry.registerItem(multi, Settings.namespace + "item")

    analyzer = new item.Analyzer(multi)

    ram1 = new item.Memory(multi, 0)
    ram2 = new item.Memory(multi, 1)
    ram3 = new item.Memory(multi, 2)

    floppyDisk = new item.FloppyDisk(multi)
    hdd1 = new item.HardDiskDrive(multi, 0)
    hdd2 = new item.HardDiskDrive(multi, 1)
    hdd3 = new item.HardDiskDrive(multi, 2)

    gpu1 = new item.GraphicsCard(multi, 0)
    gpu2 = new item.GraphicsCard(multi, 1)
    gpu3 = new item.GraphicsCard(multi, 2)
    lan = new item.NetworkCard(multi)
    rs = new item.RedstoneCard(multi)
    wlan = new item.WirelessNetworkCard(multi)

    upgradeCrafting = new item.UpgradeCrafting(multi)
    upgradeGenerator = new item.UpgradeGenerator(multi)

    ironNugget = new item.IronNugget(multi)
    cuttingWire = new item.CuttingWire(multi)
    acid = new item.Acid(multi)
    disk = new item.Disk(multi)

    buttonGroup = new item.ButtonGroup(multi)
    arrowKeys = new item.ArrowKeys(multi)
    numPad = new item.NumPad(multi)

    transistor = new item.Transistor(multi)
    chip1 = new item.Microchip(multi, 0)
    chip2 = new item.Microchip(multi, 1)
    chip3 = new item.Microchip(multi, 2)
    alu = new item.ALU(multi)
    cu = new item.ControlUnit(multi)
    cpu0 = new item.CPU(multi, 0)

    rawCircuitBoard = new item.RawCircuitBoard(multi)
    circuitBoard = new item.CircuitBoard(multi)
    pcb = new item.PrintedCircuitBoard(multi)
    card = new item.CardBase(multi)

    // v1.1.0
    upgradeSolarGenerator = new item.UpgradeSolarGenerator(multi)
    upgradeSign = new item.UpgradeSign(multi)
    upgradeNavigation = new item.UpgradeNavigation(multi)

    ram4 = new item.Memory(multi, 3)
    ram5 = new item.Memory(multi, 4)

    abstractBus = new item.AbstractBusCard(multi)

    // v1.2.0
    server = new item.Server(multi)
    terminal = new item.Terminal(multi)
    cpu1 = new item.CPU(multi, 1)
    cpu2 = new item.CPU(multi, 2)

    // ----------------------------------------------------------------------- //

    registerExclusive("craftingPiston", new ItemStack(Block.pistonBase), new ItemStack(Block.pistonStickyBase))
    registerExclusive("nuggetGold", new ItemStack(Item.goldNugget))
    registerExclusive("nuggetIron", ironNugget.createItemStack())
    register("oc:craftingCircuitBoardRaw", rawCircuitBoard.createItemStack())
    register("oc:craftingCircuitBoard", circuitBoard.createItemStack())
    register("oc:craftingCircuitBoardPrinted", pcb.createItemStack())
    register("oc:craftingCard", card.createItemStack())
    register("oc:craftingWire", cuttingWire.createItemStack())
    register("oc:circuitBasic", chip1.createItemStack())
    register("oc:circuitAdvanced", chip2.createItemStack())
    register("oc:circuitElite", chip3.createItemStack())
    register("oc:craftingTransistor", transistor.createItemStack())
    register("oc:craftingCU", cu.createItemStack())
    register("oc:craftingALU", alu.createItemStack())
    register("oc:craftingCPU", cpu0.createItemStack())
    register("oc:craftingCPUTier1", cpu0.createItemStack())
    register("oc:craftingCPUTier2", cpu1.createItemStack())
    register("oc:craftingCPUTier3", cpu2.createItemStack())
    register("oc:componentCardRedstone", rs.createItemStack())
    register("oc:componentCardLan", lan.createItemStack())
    register("oc:craftingGPUBasic", gpu1.createItemStack())
    register("oc:craftingGPUAdvanced", gpu2.createItemStack())
    register("oc:craftingGPUElite", gpu3.createItemStack())
    register("oc:craftingRAMBasic", ram1.createItemStack())
    register("oc:craftingRAMAdvanced", ram2.createItemStack())
    register("oc:craftingRAMElite", ram3.createItemStack())
    register("oc:craftingRAMElite2", ram4.createItemStack())
    register("oc:craftingRAMElite3", ram5.createItemStack())
    register("oc:craftingHDDBasic", hdd1.createItemStack())
    register("oc:craftingHDDAdvanced", hdd2.createItemStack())
    register("oc:craftingHDDElite", hdd3.createItemStack())
    register("oc:craftingButtonGroup", buttonGroup.createItemStack())
    register("oc:craftingArrowKey", arrowKeys.createItemStack())
    register("oc:craftingNumPad", numPad.createItemStack())
    register("oc:craftingDisk", disk.createItemStack())
    register("oc:craftingAcid", acid.createItemStack())
    register("oc:craftingGenerator", upgradeGenerator.createItemStack())
  }

  def register(name: String, item: ItemStack) {
    if (!OreDictionary.getOres(name).contains(item)) {
      OreDictionary.registerOre(name, item)
    }
  }

  def registerExclusive(name: String, items: ItemStack*) {
    if (OreDictionary.getOres(name).isEmpty) {
      for (item <- items) {
        OreDictionary.registerOre(name, item)
      }
    }
  }
}