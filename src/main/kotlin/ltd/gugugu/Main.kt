package ltd.gugugu

import ltd.gugugu.data.StoreAPI
import ltd.gugugu.ui.Chest
import org.bukkit.Bukkit
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.submit
import taboolib.common.platform.function.submitAsync


object Main : Plugin() {
    val console = Bukkit.getConsoleSender()
    val onlinePlayer = Bukkit.getOnlinePlayers()
    val plugin = this
    val world = Bukkit.getWorld("world")

    override fun onEnable() {
        console.sendMessage(
            """
        §9 UB提示你：顾菇谷插件加载完成
        """
        )
        minsave()
    }

    override fun onDisable() {
        for (player in Bukkit.getOnlinePlayers()) {
            player.closeInventory()
        }
        Chest.playerChest.forEach { //遍历uuid对应的map
            val value = it.value
            val uuid = it.key
            value.forEach {
                StoreAPI.save(uuid, it.key, it.value)
            }

        }
    }

    private fun minsave() {
        //异步执行
        submit(period = 20 * 60) {
            submitAsync {
                Chest.playerChest.forEach {
                    val value = it.value
                    val uuid = it.key
                    value.forEach {
                        StoreAPI.save(uuid, it.key, it.value)
                    }
                }

            }
        }
    }
}