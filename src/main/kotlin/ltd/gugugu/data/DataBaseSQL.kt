package ltd.gugugu.data

import org.bukkit.inventory.ItemStack
import taboolib.common.io.newFile
import taboolib.common.platform.function.getDataFolder
import taboolib.module.database.ColumnTypeSQLite
import taboolib.module.database.Table
import taboolib.module.database.getHost
import ygub123.main.database.util.base64ToPackData
import ygub123.main.database.util.toBase64
import java.util.*

class DatabaseSQL : Database() {
    val host = newFile(getDataFolder(), "packdata.db").getHost() //获取数据库连接地址
    val name = "dbcstore"

    val tablePack = Table("${name}_data", host) {
        add { id() }
        add("owner") {
            type(ColumnTypeSQLite.TEXT, 64)
        }
        add("pack") {
            type(ColumnTypeSQLite.TEXT, 64)
        }
        add("value") {
            type(ColumnTypeSQLite.TEXT)
        }
    }

    val dataSource = host.createDataSource()

    init {
        tablePack.createTable(dataSource)
    }

    fun getPackData(uuid: UUID, packId: Int): PackData {
        val result = cache.computeIfAbsent(uuid) { //一个PlayerData
            PlayerData(tablePack.select(dataSource) {
                where("owner" eq uuid.toString())
                rows("value", "pack")
            }.map {
                PackData(getInt("pack"), getString("value").base64ToPackData())
            }.toMutableList())
        }
        return result.data.find {
            it.name == packId
        } ?: PlayerData.createPackData(result, PackData(packId))
    }
    fun setPackData(uuid: UUID, id: Int, inventory: MutableMap<Int, ItemStack?>) {
        if (tablePack.find(dataSource) { where("owner" eq uuid.toString() and ("pack" eq id)) }) {
            tablePack.update(dataSource) {
                where("owner" eq uuid.toString() and ("pack" eq id))
                set("value", inventory.toBase64())
            }
        } else {
            tablePack.insert(dataSource, "owner", "pack", "value") {
                value(uuid.toString(), id, inventory.toBase64())
            }
        }
    }
}