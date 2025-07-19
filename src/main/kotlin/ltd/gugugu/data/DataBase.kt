package ltd.gugugu.data

import java.util.*
import java.util.concurrent.ConcurrentHashMap

abstract class Database {

    companion object {
        val INSTANCE by lazy {
            DatabaseSQL()
        }
    }
    val cache = ConcurrentHashMap<UUID, PlayerData>()
}