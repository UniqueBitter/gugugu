package ltd.gugugu.util.itemedit

import ltd.gugugu.util.ItemCache
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Basic

object LoreSet {
    fun open(player: Player,item:ItemStack) {
        player.openMenu<Basic>("§b 装备lore") {
            map(
                "####E####",
                "1 2 3 4 5",
                "         ",
                "6 7 8 9 a",
                "U########",
            )
            set('#', ItemCache.bar.clone())
            set('U', ItemCache.lastPage.clone()) {
                Option.open(player)
            }
            set('E', item) {
                isCancelled = true
            }

        }
    }
}