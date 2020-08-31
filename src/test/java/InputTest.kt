import com.google.common.truth.Truth.assertThat
import org.junit.Test

class InputTest {

    private val input: Input = InputImpl()

    @Test
    fun successRead() {

        val list = input.read()

        assertThat(list.first().name).isEqualTo("ときのそら")
    }
}