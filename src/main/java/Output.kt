import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File

data class OutputHololiver(
    val id: Int,
    val name: String,
    val twitterScreenName: String?,
    val imageUrl: String,
    val generation: List<String>,
    val basicInfo: String? = null,
    val channelId: String? = null,
    val fanArtHashTag: String? = null
)

interface Output {
    fun write(output: List<OutputHololiver>)
}

class OutputImpl : Output {
    override fun write(output: List<OutputHololiver>) {

        val type = Types.newParameterizedType(List::class.java, OutputHololiver::class.java)

        val adapter = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build().adapter<List<OutputHololiver>>(type)

        File("public/members.json").writeText(toPretty(adapter, output))
    }
}