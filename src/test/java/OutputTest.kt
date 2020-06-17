import org.junit.Test

class OutputTest {

    private val output: Output = OutputImpl()

    @Test
    fun successOutput() {

        val list = mutableListOf(
            OutputHololiver(
                1, "ときのそら", "tokinosora",
                "http://example.com", mutableListOf("0"), "基本情報", "channelid", "fanart"
            )
        )

        output.write(list)
    }
}