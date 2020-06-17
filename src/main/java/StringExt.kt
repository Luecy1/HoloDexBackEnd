fun String.replaceBiggerSizeUrl(): String {
    return this.replace("normal", "400x400")
}

fun String.toHttps(): String {
    return if (!this.startsWith("https://")) {
        this.replace("http://", "https://")
    } else {
        this
    }
}