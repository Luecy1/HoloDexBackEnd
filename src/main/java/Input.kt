import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.Okio
import java.io.File
import java.net.URL

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
    /**
     * Read from MasterData.json
     */
    fun read(): List<InputHololiver>

    /**
     * Restore last Data
     */
    fun restoreData(): List<OutputHololiver>
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

    override fun restoreData(): List<OutputHololiver> {
        val type = Types.newParameterizedType(List::class.java, OutputHololiver::class.java)

        val adapter = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build().adapter<List<OutputHololiver>>(type)

        val file = File("public/members.json")

        if (!file.exists()) {
            return listOf()
        }

        val publishedData = URL("https://luecy1.github.io/HoloDexBackEnd/members.json").readText()

        val restoreList = adapter.fromJson(publishedData)

        return restoreList ?: listOf()
    }
}