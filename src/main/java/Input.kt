import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.Okio

data class InputHololiver(
    val id: Int?,
    val name: String,
    val twitterScreenName: String,
    val generation: List<String>,
    val basicInfo: String,
    val channelId: String,
    val fanArtHashTag: String
)

interface Input {
    fun read(): List<InputHololiver>
}

class InputImpl : Input {
    override fun read(): List<InputHololiver> {
        val type = Types.newParameterizedType(List::class.java, InputHololiver::class.java)

        val adapter = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build().adapter<List<InputHololiver>>(type)

        val bufferedInputStream = this.javaClass.classLoader.getResourceAsStream("MasterData.json").buffered()
        val bufferedSource = Okio.buffer(Okio.source(bufferedInputStream))
        val json = adapter.fromJson(bufferedSource)

        return json!!
    }
}