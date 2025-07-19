package ltd.gugugu.listeners

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
            event.quitMessage = "§d叹人生，最难欢聚易离别"
        }
    }
}