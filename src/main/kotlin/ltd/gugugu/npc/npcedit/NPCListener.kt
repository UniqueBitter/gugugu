package ltd.gugugu.npc.npcedit

import ltd.gugugu.npc.npcedit.NpcSummon.recipeItem
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Villager
import org.bukkit.event.player.PlayerInteractEntityEvent
import taboolib.common.platform.event.SubscribeEvent
import java.util.*


object NPCListener {
    private val noClick = listOf("§a收取物", "§b售出物", "§b上一页", "§b下一页", "§b其他设置")

    //@SubscribeEvent
    fun check(event: PlayerInteractEntityEvent) {
        val entiy = event.rightClicked
        val player = event.player
        if (!player.isOp) return
        val tool = player.inventory.itemInMainHand.type
        if (!player.isSneaking || tool != Material.WOODEN_HOE) return
        event.isCancelled = true
        if (entiy.type != EntityType.VILLAGER) { return }
        val npc = entiy as? Villager ?:return
        val recipeUi = Bukkit.createInventory(null, 54, "§a 听雨交易")
        val uuid = player.uniqueId
        if (recipeItem[uuid] == null) recipeItem[uuid] = RecipeCache(1, recipeUi, mutableMapOf())

    }
}