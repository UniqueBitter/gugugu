package ltd.gugugu.ui.qiankun

import ltd.gugugu.util.ItemCache
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Basic
import taboolib.platform.util.buildItem

object MainUi {
    fun openMenu(player: Player) {
        val data = player.persistentDataContainer
        val uuid = player.uniqueId
        val scores = player.scoreboard
        player.openMenu<Basic>("§b 乾坤袋") {
            map(
                "#########",
                "# A B C #",
                "#       #",
                "# D E F #",
                "#########"
            )
            set('#', ItemCache.bar) { isCancelled = true }
            set('A', buildItem(XMaterial.PAPER){
                name = "§b万通宝符"
                lore.add("§f点我打开")
                lore.add("§f万通宝符存取界面")
                hideAll()
            })
            set('B', buildItem(XMaterial.IRON_INGOT){
                name = "§b钱币"
                lore.add("§f点我打开")
                lore.add("§f钱币存取界面")
                hideAll()
            })
            set('C', buildItem(XMaterial.EMERALD){
                name = "§b元素"
                lore.add("§f点我打开")
                lore.add("§f元素存取界面")
                hideAll()
            })
            set('D', buildItem(XMaterial.CHEST){
                name = "§e§l清点库存"
                lore.add("§f点我查看")
                lore.add("§f乾坤袋库存数量")
                hideAll()
            })
            set('E', buildItem(XMaterial.ENDER_CHEST){
                name = "§6§l自动存储"
                lore.add("§f点我打开")
                lore.add("§f自动存储模式")
                hideAll()
            })
            set('F', buildItem(XMaterial.EMERALD){
                name = "§3§l炼丹师精炼模式"
                lore.add("§f点我打开")
                lore.add("§f炼丹师精炼元素模式")
                customModelData = 2
                hideAll()
            })
        }
    }
}