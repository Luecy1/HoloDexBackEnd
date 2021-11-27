class Main(
    private val input: Input,
    private val output: Output
) {

    fun run() {

        val twitter = getTwitterFactory()

        val hololiverListFromMasterData = input.read()
        val hololiverFromMasterData = hololiverListFromMasterData.map {
            it.twitterScreenName to it
        }.toMap()

        // restore data
        val restoreData = input.restoreData().map {
            it.twitterScreenName to it
        }.toMap()

        // for twitter api
        val hololiverIdList = hololiverListFromMasterData.map {
            it.twitterScreenName
        }

        val hololiverFromTwitter = twitter.lookupUsers(*hololiverIdList.toTypedArray()).map {
            it.screenName to it
        }.toMap()

        // merge twitter and master
        var id = 0
        val outputHololiverList = hololiverFromMasterData.mapNotNull { (screenName, data) ->

            if (!hololiverFromTwitter.contains(screenName)) {
                // if can not fetch twitter api, use old data
                id++
                return@mapNotNull restoreData[screenName] ?: run {
                    println("screenName:$screenName is unavailable twitter and not exist old data")
                    null
                }
            }

            val hololiver = hololiverFromTwitter[screenName]!!

            id++
            return@mapNotNull OutputHololiver(
                id,
                data.name,
                data.twitterScreenName,
                hololiver.profileImageURL.replaceBiggerSizeUrl().toHttps(),
                data.generation,
                data.basicInfo,
                data.channelId,
                data.fanArtHashTag,
            )
        }.toList()

        output.write(outputHololiverList)
    }
}

fun main() {
    Main(InputImpl(), OutputImpl()).run()
}