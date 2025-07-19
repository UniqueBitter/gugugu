package ltd.gugugu.data


class PlayerData(val data: MutableList<PackData>) {

    companion object {
        fun createPackData(playerData: PlayerData, packData: PackData): PackData {
            playerData.data.add(packData)
            return packData
        }
    }
}