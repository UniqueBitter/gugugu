package ltd.gugugu.util.itemedit

import ltd.gugugu.util.ItemCache
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.event.SubscribeEvent
import taboolib.library.xseries.XMaterial
import taboolib.module.chat.colored
import taboolib.module.nms.getItemTag
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Basic
import taboolib.platform.util.buildItem
import java.util.*

object Option {
    val page = HashMap<UUID, Int>(5)
    val loreSetMarker = HashMap<UUID, Pair<Int, ItemStack>>()
    fun open(player: Player) {
        player.openMenu<Basic>("§b 物品管理") {
            val item = player.inventory.itemInMainHand.clone()
            map(
                "####U####",
                "#       #",
                "#a b c d#",
                "#       #",
                "#########",
            )
            set('#', ItemCache.bar)
            set('U', item) {
                isCancelled = true
                player.closeInventory()
            }
            set('a', buildItem(Material.NAME_TAG) {
                name = "§b修改名字"
            }){
                setname(player,item)
            }
            set('b', buildItem(XMaterial.FEATHER) {
                name = "§8装备描述"
                lore.add("§7左键点击添加装备描述")
                lore.add("§7右键点击删除一行装备描述")
            }) {
                if (clickEvent().isLeftClick) {
                    isCancelled = true
                } else {
                    isCancelled = true
                }
            }
            set('c', buildItem(Material.NAUTILUS_SHELL) {
                name = "§b设置id"
            })
            set('d', buildItem(Material.PRISMARINE_SHARD) {
                name = "§b设置自定义模型"
            }){
                setcustomModel(player,item)
            }
        }
    }

    fun setname(player: Player, itemStack: ItemStack) {
        player.closeInventory()
        val uuid = player.uniqueId
        player.sendMessage("§a请输入要设置的名字")
        loreSetMarker[uuid] = Pair(1, itemStack)
    }

    fun setid(player: Player, itemStack: ItemStack) {
        player.closeInventory()
        val uuid = player.uniqueId
        player.sendMessage("§a请输入要设置id")
        loreSetMarker[uuid] = Pair(3, itemStack)
    }
    fun setcustomModel(player: Player, itemStack: ItemStack) {
        player.closeInventory()
        val uuid = player.uniqueId
        player.sendMessage("§a请输入要设置的自定义模型")
        loreSetMarker[uuid] = Pair(4, itemStack)
    }

    @SubscribeEvent
    fun Itemset(event: AsyncPlayerChatEvent) {
        val player = event.player
        val uuid = player.uniqueId
        val chat = event.message.colored()
        if (uuid in loreSetMarker) {
            event.isCancelled = true
            val type = loreSetMarker[uuid]!!.first
            val item = loreSetMarker[uuid]!!.second
            if (type == 1) {
                val meta = item.itemMeta
                meta.displayName(Component.text(chat))
                item.itemMeta = meta
                loreSetMarker.remove(uuid)
                player.inventory.setItemInMainHand(item)
                player.sendMessage("§a已成功设置装备名称为 $chat")
                return
            }
            if (type == 3) {
                item.getItemTag().set("id", chat)
                player.inventory.setItemInMainHand(item)
                player.sendMessage("§a已成功设置装备id为 {id:$chat}")
            }

            if (type == 4) {
                try {
                    chat.toInt()
                } catch (e: NumberFormatException) {
                    player.sendMessage("§c请输入一个数字")
                    loreSetMarker.remove(uuid)
                    return
                }
                val meta = item.itemMeta
                meta.setCustomModelData(chat.toInt())
                item.itemMeta = meta
                loreSetMarker.remove(uuid)
                player.inventory.setItemInMainHand(item)
                player.sendMessage("§a已成功设置装备CustomModelData")
                return
            }
        }
    }

}