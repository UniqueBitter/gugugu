package ltd.gugugu.npc.npcedit

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class RecipeCache(
    var page: Int, val inv: Inventory,
    val data: MutableMap<Int, ItemStack?> = mutableMapOf()) {

    fun savePageItems(page: Int, inv: Inventory, data: MutableMap<Int, ItemStack?>) {
        var index = 0
        val pageSlot = (page - 1) * 54
        for (i in (0 + pageSlot)..(44 + pageSlot)) {
            val item = inv.getItem(index)
            index++
            if (item?.type == Material.GRAY_STAINED_GLASS_PANE) continue
            if (item == null) {
                data.remove(i)
            } else {
                data[i] = item
            }
        }
    }

    fun setPageItems(page: Int, inv: Inventory, data: MutableMap<Int, ItemStack?>) {
        var index = 0
        val pageSlot = (page - 1) * 54
        for (i in (0 + pageSlot)..(44 + pageSlot)) {
            val item = data[i]
            inv.setItem(index, item)
            index++
        }
    }
}