package ltd.gugugu.npc.npcedit

import org.bukkit.Bukkit
import java.util.*

object NpcSummon {
    val recipeItem = HashMap<UUID, RecipeCache>()
    val type = HashMap<UUID, Int>()
    val job = HashMap<UUID, Int>()
    private val cool = mutableListOf<UUID>()
    val team = Bukkit.getScoreboardManager().mainScoreboard.getTeam("villager")!!

}