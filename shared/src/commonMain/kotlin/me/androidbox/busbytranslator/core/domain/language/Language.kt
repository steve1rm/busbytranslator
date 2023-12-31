package me.androidbox.busbytranslator.core.domain.language

enum class Language(
    val languageCode: String,
    val languageName: String
) {
    ENGLISH("en", "English"),
    ARABIC("ar", "Arabic"),
    AZERBAIJANI("az", "Azerbaijani"),
    CHINESE("zh", "Chinese"),
    CZECH("cs", "Czech"),
    DANISH("da", "Danish"),
    DUTCH("nl", "Dutch"),
    FINNISH("fi", "Finnish"),
    FRENCH("fr", "French"),
    GERMAN("de", "German"),
    GREEK("el", "Greek"),
    HEBREW("he", "Hebrew"),
    HINDI("hi", "Hindi"),
    HUNGARIAN("hu", "Hungarian"),
    INDONESIAN("id", "Indonesian"),
    IRISH("ga", "Irish"),
    ITALIAN("it", "Italian"),
    JAPANESE("ja", "Japanese"),
    KOREAN("ko", "Korean"),
    PERSIAN("fa", "Persian"),
    POLISH("pl", "Polish"),
    PORTUGUESE("pt", "Portuguese"),
    RUSSIAN("ru", "Russian"),
    SLOVAK("sk", "Slovak"),
    SPANISH("es", "Spanish"),
    SWEDISH("sv", "Swedish"),
    TURKISH("tr", "Turkish"),
    THAILAND("th", "Thai"),
    UKRAINIAN("uk", "Ukrainian");

    companion object {
        fun byCode(languageCode: String): Language {
            return entries.find { language ->
                language.languageCode == languageCode
            } ?: throw IllegalArgumentException("Cannot find language code or code is invalid")
        }
    }
}