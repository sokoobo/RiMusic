package it.fast4x.innertube.models

import io.ktor.http.headers
import it.fast4x.innertube.Innertube
import it.fast4x.innertube.utils.LocalePreferences
import kotlinx.serialization.Serializable

@Serializable
data class Context(
    val client: Client,
    val thirdParty: ThirdParty? = null,
    private val request: Request = Request(),
    val user: User? = User()
) {

    @Serializable
    data class Client(
        val clientName: String,
        val clientVersion: String,
        val platform: String? = null,
        val hl: String? = "en",
        val gl: String? = "US",
        val visitorData: String? = null,
        val androidSdkVersion: Int? = null,
        val userAgent: String? = null,
        val referer: String? = null,
        val deviceMake: String? = null,
        val deviceModel: String? = null,
        val osName: String? = null,
        val osVersion: String? = null,
        val acceptHeader: String? = null,
        val xClientName: Int? = null,
    )

    @Serializable
    data class ThirdParty(
        val embedUrl: String,
    )

    @Serializable
    data class User(
        val lockedSafetyMode: Boolean = false
    )

    @Serializable
    data class Request(
        val internalExperimentFlags: Array<String> = emptyArray(),
        val useSsl: Boolean = true,
    )

    fun apply() {
        client.userAgent

        headers {
            client.referer?.let { append("Referer", it) }
            append("X-Youtube-Bootstrap-Logged-In", "false")
            append("X-YouTube-Client-Name", client.clientName)
            append("X-YouTube-Client-Version", client.clientVersion)
        }
    }



    companion object {

        const val USER_AGENT_WEB = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36 Edg/132.0.0.0,gzip(gfe)"
        const val USER_AGENT_ANDROID = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Mobile Safari/537.36"
        const val USER_AGENT_ANDROID_MUSIC = "com.google.android.youtube/19.29.1  (Linux; U; Android 11) gzip"
        const val USER_AGENT_PLAYSTATION = "Mozilla/5.0 (PlayStation; PlayStation 4/12.00) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.4 Safari/605.1.15"
        const val USER_AGENT_DESKTOP = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36"
        const val USER_AGENT_IOS = "com.google.ios.youtube/20.03.02 (iPhone16,2; U; CPU iOS 18_2_1 like Mac OS X;)"

        const val REFERER_YOUTUBE_MUSIC = "https://music.youtube.com/"
        const val REFERER_YOUTUBE = "https://www.youtube.com/"

        val DefaultWeb = Context(
            client = Client(
                clientName = "WEB_REMIX",
                clientVersion = "1.20250122.01.00",
                platform = "DESKTOP",
                userAgent = USER_AGENT_WEB,
                referer = REFERER_YOUTUBE_MUSIC,
                visitorData = Innertube.visitorData,
                xClientName = 67
            )
        )


        //val hl = if (LocalePreferences.preference?.useLocale == true) LocalePreferences.preference!!.hl else ""
        val hl = LocalePreferences.preference?.hl
        //val gl = LocalePreferences.preference?.gl


        val DefaultWebWithLocale = DefaultWeb.copy(
            client = DefaultWeb.client.copy(hl = hl)
        )

        val DefaultWeb2 = Context(
            client = Client(
                clientName = "WEB",
                clientVersion = "2.2021111",
                userAgent = USER_AGENT_WEB,
            )
        )

        val DefaultWeb2WithLocale = DefaultWeb2.copy(
            client = DefaultWeb2.client.copy(hl = hl)
        )

        val DefaultWebCreator = Context(
            client = Client(
                clientName = "WEB_CREATOR",
                clientVersion = "1.20240918.03.00",
                userAgent = USER_AGENT_WEB,
                referer = REFERER_YOUTUBE_MUSIC,
            )
        )



        val DefaultAndroid = Context(
            client = Client(
                clientName = "ANDROID_MUSIC",
                clientVersion = "7.31.51",
                androidSdkVersion = 31,
                platform = "MOBILE",
                userAgent = USER_AGENT_ANDROID_MUSIC,
                referer = REFERER_YOUTUBE_MUSIC,
                visitorData = Innertube.visitorData,
                xClientName = 21
            )
        )

        val DefaultIOS = Context(
            client = Client(
                clientName = "IOS",
                clientVersion = "20.03.02",
                deviceMake = "Apple",
                deviceModel = "iPhone16,2",
                osName = "iOS",
                osVersion = "18.2.1.22C161",
                acceptHeader = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8",
                userAgent = USER_AGENT_IOS,
                xClientName = 5
            )
        )

        val DefaultTVEmbedded = Context(
            client = Client(
                clientName = "TVHTML5_SIMPLY_EMBEDDED_PLAYER",
                clientVersion = "2.0",
                xClientName = 85,
                platform = "TV",
                userAgent = USER_AGENT_PLAYSTATION,
            )
        )

    }
}
