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
        val outputHololiverList = hololiverFromMasterData.map { (screenName, data) ->

            val oldData = restoreData[screenName]!!

            if (!hololiverFromTwitter.contains(screenName)) {
                // if can not fetch twitter api
                id++
                return@map oldData
            }

            val hololiver = hololiverFromTwitter[screenName]!!

            id++
            return@map OutputHololiver(
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