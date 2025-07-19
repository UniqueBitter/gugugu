package ltd.gugugu.listeners

import net.minecraft.network.chat.PlayerChatMessage
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent

object playerMessage {//CN_Sakuro
    @SubscribeEvent
    fun join(event:PlayerJoinEvent){
        val player =  event.player
        if (player.name == "CN_Sakuro"){
            event.joinMessage = "§1昔日相聚，今朝重逢"
        }
    }
    @SubscribeEvent
    fun quit(event:PlayerQuitEvent){
        val player =  event.player
        if (player.name == "CN_Sakuro"){
            event.quitMessage = "§6叹人生，最难欢聚易离别"
        }
    }
}