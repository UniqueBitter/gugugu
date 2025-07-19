package ltd.gugugu.data

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import taboolib.module.nms.getItemTag

// 储存仓库名以及仓库中的物品数据
class PackData(val name: Int, val data: MutableMap<Int, ItemStack?> = mutableMapOf()) {

    fun open(player: Player, id: Int) {
        player.closeInventory() //先关闭当前背包
    }

    fun getPageItems(page: Int, data: MutableMap<Int, ItemStack?>): MutableMap<Int, ItemStack> {
        val items = mutableMapOf<Int, ItemStack>()
        for (i in 0..53) {
            val item = data[i] ?: continue //获取data中该格子的物品
            items[i] = item //把items物品设置为data中的物品
        }
        return items //返回items列表，也就是一个背包
    }

    fun setPageItems(page: Int, inventory: Inventory, data: MutableMap<Int, ItemStack?>) {
        var index = 0
        for (i in 0..inventory.size-1) {
            val item = inventory.getItem(index) //获取每个格子的物品
            val tag = item?.getItemTag() //获取物品tag
            if (tag != null) {
                if (tag.getDeep("pack.type") != null) {
                    continue //不保存锁位置用的物品
                }
            }
            if (item == null) { //如果物品不存在，删除data中的物品
                data.remove(i)
            } else { //否则同步物品
                data[i] = item
            }
            index += 1
        }
    }

}