package ltd.gugugu.data

import org.bukkit.inventory.ItemStack

object TradeAPI {
    val trades = HashMap<String, MutableMap<Int, ItemStack?>>()
}