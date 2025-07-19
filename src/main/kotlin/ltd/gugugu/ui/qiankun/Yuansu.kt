package ltd.gugugu.ui.qiankun

import org.bukkit.entity.Player
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Basic

object Yuansu {
    fun openMenu(player: Player) {
        player.openMenu<Basic>("§b 乾坤袋") {
            map(
                "#########",
                "# A B C #",
                "#       #",
                "# D E F #",
                "#########"
            )
        }
    }
}