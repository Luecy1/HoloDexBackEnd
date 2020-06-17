import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonWriter
import okio.Buffer
import java.io.IOException

fun toPretty(adapter: JsonAdapter<List<OutputHololiver>>, member: List<OutputHololiver>): String {
    val buffer = Buffer()

    val prettyPrintWriter = JsonWriter.of(buffer)
    prettyPrintWriter.indent = "  "

    try {
        adapter.toJson(prettyPrintWriter, member)
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return buffer.readUtf8()
}