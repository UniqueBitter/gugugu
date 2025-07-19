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
            }) {
                setname(player, item)
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
            }) {
                setcustomModel(player, item)
            }
        }
    }

    private fun setname(player: Player, itemStack: ItemStack) {
        player.closeInventory()
        val uuid = player.uniqueId
        player.sendMessage("§a请输入要设置的名字")
        loreSetMarker[uuid] = Pair(1, itemStack)
    }

    private fun addLore(player: Player, itemStack: ItemStack) {
        player.closeInventory()
        val uuid = player.uniqueId
        player.sendMessage("§a请输入要添加的装备描述")
        loreSetMarker[uuid] = Pair(2, itemStack)
    }

    private fun removeLore(player: Player, itemStack: ItemStack) {
        val meta = itemStack.itemMeta
        if (meta != null && meta.hasLore() && meta.lore() != null && meta.lore()!!.isNotEmpty()) {
            val currentLore = meta.lore()!!.toMutableList()
            currentLore.removeAt(currentLore.size - 1)
            meta.lore(currentLore)
            itemStack.itemMeta = meta
            player.inventory.setItemInMainHand(itemStack)
            player.sendMessage("§a已删除最后一行装备描述")
        } else {
            player.sendMessage("§c该装备没有描述可以删除")
        }
        player.closeInventory()
    }

    private fun setid(player: Player, itemStack: ItemStack) {
        player.closeInventory()
        val uuid = player.uniqueId
        player.sendMessage("§a请输入要设置id")
        loreSetMarker[uuid] = Pair(3, itemStack)
    }

    private fun setcustomModel(player: Player, itemStack: ItemStack) {
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
            when (type) {
                1 -> {
                    // 设置名字
                    val meta = item.itemMeta
                    meta.displayName(Component.text(chat))
                    item.itemMeta = meta
                    loreSetMarker.remove(uuid)
                    player.inventory.setItemInMainHand(item)
                    player.sendMessage("§a已成功设置装备名称为 $chat")
                }

                2 -> {
                    // 添加装备描述
                    val meta = item.itemMeta
                    val currentLore = if (meta.hasLore() && meta.lore() != null) {
                        meta.lore()!!.toMutableList()
                    } else {
                        mutableListOf<Component>()
                    }
                    currentLore.add(Component.text(chat))
                    meta.lore(currentLore)
                    item.itemMeta = meta
                    loreSetMarker.remove(uuid)
                    player.inventory.setItemInMainHand(item)
                    player.sendMessage("§a已成功添加装备描述: $chat")
                }

                3 -> {
                    // 设置id
                    item.getItemTag().set("id", chat)
                    loreSetMarker.remove(uuid)
                    player.inventory.setItemInMainHand(item)
                    player.sendMessage("§a已成功设置装备id为 {id:$chat}")
                }

                4 -> {
                    // 设置自定义模型
                    try {
                        val modelData = chat.toInt()
                        val meta = item.itemMeta
                        meta.setCustomModelData(modelData)
                        item.itemMeta = meta
                        loreSetMarker.remove(uuid)
                        player.inventory.setItemInMainHand(item)
                        player.sendMessage("§a已成功设置装备CustomModelData为 $modelData")
                    } catch (e: NumberFormatException) {
                        player.sendMessage("§c请输入一个有效的数字")
                        loreSetMarker.remove(uuid)
                    }
                }
            }
        }

    }
}