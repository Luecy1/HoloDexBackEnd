import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okio.Okio

data class HololiveMember(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val generation: List<String>,
    val basicInfo: String? = null,
    val channelId: String? = null,
    val fanArtHashTag: String? = null
)

data class InputHololiver(
    val id: Int,
    val name: String,
    val generation: List<String>,
    val basicInfo: String?,
    val channelId: String?,
    val fanArtHashTag: String?
)


class JsonRead {
    operator fun invoke() {
        val type = Types.newParameterizedType(List::class.java, InputHololiver::class.java)

        val adapter = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build().adapter<List<HololiveMember>>(type)

        val bufferedInputStream = this.javaClass.classLoader.getResourceAsStream("MasterData.json").buffered()
        val bufferedSource = Okio.buffer(Okio.source(bufferedInputStream))
        val json = adapter.fromJson(bufferedSource)

        println(json)
    }
}

fun main() {
    JsonRead()()
}