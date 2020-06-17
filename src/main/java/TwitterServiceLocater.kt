import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

fun getEnv(key: String): String {
    return System.getenv(key) ?: ""
}

val isCi = (getEnv("CI") == "true")

fun getTwitterFactory(): Twitter {

    if (isCi) {

        val consumerKey = getEnv("OAUTH_CONSUMERKEY")
        val consumerSecret = getEnv("OAUTH_CONSUMERSECRET")
        val accessToken = getEnv("OAUTH_ACCESSTOKEN")
        val accessTokenSecret = getEnv("OAUTH_ACCESSTOKENSECRET")

        val configuration = ConfigurationBuilder()
            .setOAuthConsumerKey(consumerKey)
            .setOAuthConsumerSecret(consumerSecret)
            .setOAuthAccessToken(accessToken)
            .setOAuthAccessTokenSecret(accessTokenSecret)
            .build()

        return TwitterFactory(configuration).instance
    } else {
        return TwitterFactory.getSingleton()
    }
}