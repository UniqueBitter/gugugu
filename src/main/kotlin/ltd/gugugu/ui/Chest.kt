package ltd.gugugu.ui

import ltd.gugugu.data.Database
import ltd.gugugu.data.platt
import ltd.gugugu.util.ItemCache
import ltd.gugugu.util.ItemEdit
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.persistence.PersistentDataType
import taboolib.library.xseries.XMaterial.BRICK
import taboolib.library.xseries.XMaterial.CHEST
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Chest
import taboolib.platform.util.buildItem
import java.util.*

object Chest {
    val playerChest = mutableMapOf<UUID, MutableMap<Int, Inventory>>()
    val setList = mutableListOf<ChestData>()

    data class ChestData(
        val name: String,
        val id: Char,
        val chestId: Int,
    )

    fun chestMenu(player: Player) {
        val data = player.persistentDataContainer
        player.openMenu<Chest>("§6 储物空间") {
            val uuid = player.uniqueId
            map(
                "#########",
                "#       #",
                "#a  b  c#",
                "#       #",
                "#########"
            )
            set('#', ItemCache.bar) {
                isCancelled = true
            }
            set('a', buildItem(CHEST) {
                name = "§b日月袋"
            }) {
                openChest(player, 21, "§b星辰袋")
            }

            if (data.get(platt.chestb, PersistentDataType.BOOLEAN) == true) {
                set('b', buildItem(CHEST) {
                    name = "§b星辰袋"
                }) {
                    openChest(player, 22, "§b星辰袋")
                }
            } else {
                set('b', buildItem(BRICK) {
                    name = "§a点我购买"
                    lore.add("§7需要64通宝")
                }) {
                    if (ItemEdit.takeItem(player, "panling:vantone_tick", 64)) {
                        player.sendMessage("§a解锁成功！")
                        data.set(platt.chestb, PersistentDataType.BOOLEAN, true)
                        chestMenu(player)
                    } else {
                        player.sendMessage("§c需要64通宝，当前不足！")
                    }
                }
            }

            if (data.get(platt.chestc, PersistentDataType.BOOLEAN) == true) {
                set('c', buildItem(CHEST) {
                    name = "§b乾坤袋"
                }) {
                    openChest(player, 23, "§b星辰袋")
                }
            } else {
                set('c', buildItem(BRICK) {
                    name = "§a点我购买"
                    lore.add("§7需要128通宝")
                }) {
                    if (ItemEdit.takeItem(player, "panling:vantone_tick", 128)) {
                        player.sendMessage("§a解锁成功！")
                        data.set(platt.chestc, PersistentDataType.BOOLEAN, true)
                        chestMenu(player)
                    } else {
                        player.sendMessage("§c需要128通宝，当前不足！")
                    }
                }
            }
        }
    }

    fun openChest(player: Player, chestId: Int, chestName: String) {
        // 获取玩家的唯一标识符
        val uuid = player.uniqueId
        // 如果玩家还没有仓库记录，为其创建一个新的 Map
        if (playerChest[uuid] == null) {
            playerChest[uuid] = mutableMapOf()
        }
        // 尝试获取玩家指定编号的仓库
        var inventory = playerChest[uuid]?.get(chestId)
        // 如果指定编号的仓库不存在，则创建一个新的
        if (inventory == null) {
            inventory = Bukkit.createInventory(null, 27, chestName);
            // 从数据库获取该玩家特定仓库的物品数据
            val packData = Database.INSTANCE.getPackData(uuid, chestId)
            // 将数据库中的物品放入新创建的物品栏
            packData.data.forEach { (key, value) ->
                inventory.setItem(key, value)
            }
            // 将新创建的物品栏保存到玩家的仓库记录中
            playerChest[uuid]!![chestId] = inventory
        }
        // 为玩家打开仓库界面
        player.openInventory(inventory)
    }
}