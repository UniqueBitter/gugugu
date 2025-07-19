package ltd.gugugu.data


import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

object DbcStoreAPI {

    fun open(player: Player, data: PackData, id: Int) {
        data.open(player, id)
    }

    fun save(uuid: UUID, id: Int, inventory: Inventory) {
        val itemList: MutableMap<Int, ItemStack?> = HashMap<Int, ItemStack?>()
        for (i in 0..inventory.size-1) { //把背包里的物品一一放进map里
            itemList[i] = inventory.getItem(i)
        }
        Database.INSTANCE.setPackData(uuid, id, itemList)
    }
}