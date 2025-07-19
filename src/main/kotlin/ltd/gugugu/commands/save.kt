package ltd.gugugu.commands


import ltd.gugugu.Main.console
import ltd.gugugu.data.StoreAPI
import ltd.gugugu.ui.Chest
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand

@CommandHeader("save", permission = "panling.admin")
object save {
    @CommandBody(permission = "panling.admin.save")
    val save = mainCommand {
        execute<Player> { sender, context, argument ->
            Chest.playerChest.forEach {
                val value = it.value
                val uuid = it.key
                value.forEach {
                    StoreAPI.save(uuid, it.key, it.value)
                    sender.sendMessage("§a${it.key}")
                    sender.sendMessage("§a${it.value}")
                }
            }
        }
    }
}