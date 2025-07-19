package ygub123.main.database.util

import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.util.*

//序列化背包的map对象
fun MutableMap<Int, ItemStack?>.toBase64(): String {
    ByteArrayOutputStream().use { byteArrayOutputStream ->
        BukkitObjectOutputStream(byteArrayOutputStream).use { bukkitObjectOutputStream ->
            bukkitObjectOutputStream.writeObject(this)
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun String.base64ToPackData(): MutableMap<Int, ItemStack?> {
    ByteArrayInputStream(Base64.getDecoder().decode(this)).use { byteArrayInputStream ->
        BukkitObjectInputStream(byteArrayInputStream).use { bukkitObjectInputStream ->
            return bukkitObjectInputStream.readObject() as MutableMap<Int, ItemStack?>
        }
    }
}

//序列化物品
fun String.base64ToItem(): ItemStack {
    ByteArrayInputStream(Base64.getDecoder().decode(this)).use { byteArrayInputStream ->
        BukkitObjectInputStream(byteArrayInputStream).use { bukkitObjectInputStream ->
            return bukkitObjectInputStream.readObject() as ItemStack
        }
    }
}

fun ItemStack.itemToBase64(): String {
    ByteArrayOutputStream().use { byteArrayOutputStream ->
        BukkitObjectOutputStream(byteArrayOutputStream).use { bukkitObjectOutputStream ->
            bukkitObjectOutputStream.writeObject(this)
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
        }
    }
}

//序列化属性map
fun MutableMap<String, Number>.attToBase64(): String {
    ByteArrayOutputStream().use { byteArrayOutputStream ->
        BukkitObjectOutputStream(byteArrayOutputStream).use { bukkitObjectOutputStream ->
            bukkitObjectOutputStream.writeObject(this)
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun String.base64ToAtt(): HashMap<String, Number> {
    ByteArrayInputStream(Base64.getDecoder().decode(this)).use { byteArrayInputStream ->
        BukkitObjectInputStream(byteArrayInputStream).use { bukkitObjectInputStream ->
            return bukkitObjectInputStream.readObject() as HashMap<String, Number>
        }
    }
}

//序列化路径点
@Suppress("UNCHECKED_CAST")
fun String.base64ToPoint(): Pair<UUID, Location> {
    ByteArrayInputStream(Base64.getDecoder().decode(this)).use { byteArrayInputStream ->
        BukkitObjectInputStream(byteArrayInputStream).use { bukkitObjectInputStream ->
            return bukkitObjectInputStream.readObject() as Pair<UUID, Location>
        }
    }
}

fun Pair<UUID, Location>.pointToBase64(): String {
    ByteArrayOutputStream().use { byteArrayOutputStream ->
        BukkitObjectOutputStream(byteArrayOutputStream).use { bukkitObjectOutputStream ->
            bukkitObjectOutputStream.writeObject(this)
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
        }
    }
}

//序列化IntArray
fun IntArray.toBase64(): String {
    ByteArrayOutputStream().use { byteArrayOutputStream ->
        BukkitObjectOutputStream(byteArrayOutputStream).use { bukkitObjectOutputStream ->
            bukkitObjectOutputStream.writeObject(this)
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
        }
    }
}

fun String.base64ToIntArray(): IntArray {
    ByteArrayInputStream(Base64.getDecoder().decode(this)).use { byteArrayInputStream ->
        BukkitObjectInputStream(byteArrayInputStream).use { bukkitObjectInputStream ->
            return bukkitObjectInputStream.readObject() as IntArray
        }
    }
}

//序列化Location
fun Location.toBase64(): String {
    ByteArrayOutputStream().use { byteArrayOutputStream ->
        BukkitObjectOutputStream(byteArrayOutputStream).use { bukkitObjectOutputStream ->
            bukkitObjectOutputStream.writeObject(this)
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
        }
    }
}

fun String.base64ToLoc(): Location {
    ByteArrayInputStream(Base64.getDecoder().decode(this)).use { byteArrayInputStream ->
        BukkitObjectInputStream(byteArrayInputStream).use { bukkitObjectInputStream ->
            return bukkitObjectInputStream.readObject() as Location
        }
    }
}

//序列化date
fun LocalDateTime.dateToBase64(): String {
    ByteArrayOutputStream().use { byteArrayOutputStream ->
        BukkitObjectOutputStream(byteArrayOutputStream).use { bukkitObjectOutputStream ->
            bukkitObjectOutputStream.writeObject(this)
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
        }
    }
}

fun String.base64ToDate(): LocalDateTime {
    ByteArrayInputStream(Base64.getDecoder().decode(this)).use { byteArrayInputStream ->
        BukkitObjectInputStream(byteArrayInputStream).use { bukkitObjectInputStream ->
            return bukkitObjectInputStream.readObject() as LocalDateTime
        }
    }
}

//序列化物品列表
fun ArrayList<ItemStack>.itemListToBase64(): String {
    ByteArrayOutputStream().use { byteArrayOutputStream ->
        BukkitObjectOutputStream(byteArrayOutputStream).use { bukkitObjectOutputStream ->
            bukkitObjectOutputStream.writeObject(this)
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun String.base64ToItemList(): ArrayList<ItemStack> {
    ByteArrayInputStream(Base64.getDecoder().decode(this)).use { byteArrayInputStream ->
        BukkitObjectInputStream(byteArrayInputStream).use { bukkitObjectInputStream ->
            return bukkitObjectInputStream.readObject() as ArrayList<ItemStack>
        }
    }
}