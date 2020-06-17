class Main(
    private val input: Input,
    private val output: Output
) {

    fun run() {

        val twitter = getTwitterFactory()

        val list = input.read()

        val hololiverIdList = list.map {
            it.twitterScreenName
        }

        val hololiveMenbers = twitter.lookupUsers(*hololiverIdList.toTypedArray())

        val outputHololiverList = list.mapIndexed { index, hololiver ->
            OutputHololiver(
                hololiver.id,
                hololiver.name,
                hololiver.twitterScreenName,
                hololiveMenbers[index].profileImageURL.replaceBiggerSizeUrl().toHttps(),
                hololiver.generation,
                hololiver.basicInfo,
                hololiver.channelId,
                hololiver.fanArtHashTag
            )
        }

        output.write(outputHololiverList)
    }
}

fun main() {
    Main(InputImpl(), OutputImpl()).run()
}