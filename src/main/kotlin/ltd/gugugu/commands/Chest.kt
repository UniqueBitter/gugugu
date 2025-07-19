package ltd.gugugu.commands

import ltd.gugugu.data.platt
import ltd.gugugu.ui.Chest
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand

@CommandHeader("chest", permission = "panling.admin.chest")
object Chest {
    @CommandBody(permission = "panling.admin.chest")
    val chest = mainCommand {
        execute<Player> { sender, context, argument ->
            sender.persistentDataContainer.set(platt.chestb, PersistentDataType.BOOLEAN,false)
            sender.persistentDataContainer.set(platt.chestc, PersistentDataType.BOOLEAN,false)
        }
    }
}