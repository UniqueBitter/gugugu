package ltd.gugugu.listeners


import ltd.gugugu.ui.Chest
import ltd.gugugu.ui.qiankun.MainUi
import ltd.gugugu.util.ItemEdit.checkItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
import taboolib.module.nms.getItemTag
import taboolib.module.nms.itemTagReader
import taboolib.module.ui.Menu
import taboolib.platform.util.buildItem
import taboolib.platform.util.hasName
import taboolib.platform.util.isRightClick
import taboolib.platform.util.title
import java.util.*
import javax.swing.text.View
import kotlin.collections.HashSet

object PlayerEvent {
    private val noClick = HashSet<String>()

    init {
        noClick.addAll(
            listOf(
                "§6 储物空间",
                "§b 乾坤袋"
            )
        )
    }
    private val farmList = HashMap<UUID, Int>()
    //耕地保护
    @SubscribeEvent
    fun farmProtect(event: PlayerInteractEvent) {
        if (event.action == Action.PHYSICAL && event.clickedBlock?.type == Material.FARMLAND) {
            event.isCancelled = true
            val player = event.player
            val uuid = player.uniqueId
            player.sendMessage("§7请爱护农民伯伯辛苦劳作的成果哦")
            if (farmList[uuid] == null) {
                farmList[uuid] = 1
                submit(delay = 60 * 20) {
                    farmList.remove(uuid)
                }
            } else {
                farmList[uuid] = farmList[uuid]!! + 1
                if (farmList[uuid]!! >= 10) {
                    player.kickPlayer("§c还踩，给你一脚")
                    farmList[uuid] = farmList[uuid]!! - 3
                }
            }
        }
    }

    val air = Material.AIR
    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun clickUi(event: InventoryClickEvent) {
        if (event.view.title in noClick || event.view.title.endsWith('§')) {
            event.isCancelled = true
            return
        }
        if (event.currentItem?.type != air && event.clickedInventory?.holder !is Player && event.currentItem?.displayName == " ")
            event.isCancelled = true
        val player = event.whoClicked as Player
        val slot = event.slot
        val inventory = event.clickedInventory ?: return
        val uuid = player.uniqueId
    }

    @SubscribeEvent
    fun menu(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item ?:return
        item.itemTagReader {
            val itemId = getString("id", "")
            if ((itemId == "panling:cangku_box" || itemId == "panling:canku_box") && event.isRightClick()) {
                event.isCancelled = true
                Chest.chestMenu(player)
            }
            if ((itemId == "dlc:qiankun_dai_plus") && event.isRightClick()) {
                event.isCancelled = true
                MainUi.openMenu(player)
            }
        }
    }
    val noitemlist = listOf(
        "panling:cangku_box",
        "panling:canku_box",
        "panling:instance3_0",
        "panling:instance3_1",
    )
    val noinvList = listOf(
        "§b星辰袋",
        "§b日月袋",
        "§b乾坤袋",
    )
    @SubscribeEvent
    fun itemProtect(event: InventoryClickEvent){
        val player = event.whoClicked as Player

        if (event.view.title !in noinvList) {
            return
        }
        val inv = player.inventory
        val item = event.currentItem?:return

        if (item.type == Material.AIR) {
            return
        }
        if (event.inventory.holder !is Player) {
            for (id in noitemlist) {
                if (checkItem(item, id)) {
                    event.isCancelled = true
                    player.sendMessage("§c这个物品不能放入这里面！")
                    return
                }
            }
        }
    }

    @SubscribeEvent
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as Player
        val inventory = event.inventory
        if (event.view.title !in noinvList) {
            return
        }
        val itemsToRemove = mutableListOf<Int>()
        for (i in 0 until inventory.size-1) {
            val item = inventory.getItem(i) ?: continue
            if (item.type == Material.AIR) {
                continue
            }
            for (id in noitemlist) {
                if (checkItem(item, id)) {
                    itemsToRemove.add(i)
                    break
                }
            }
        }
        if (itemsToRemove.isNotEmpty()) {
            for (slot in itemsToRemove) {
                val item = inventory.getItem(slot) ?: continue
                inventory.setItem(slot, null)
                player.world.dropItemNaturally(player.location, item)
            }
            player.sendMessage("§c检测到不允许的物品，已自动弹出！")
        }
    }
}