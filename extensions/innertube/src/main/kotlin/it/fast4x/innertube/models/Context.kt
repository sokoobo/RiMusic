package it.fast4x.innertube.models

import io.ktor.http.headers
import it.fast4x.innertube.Innertube
import it.fast4x.innertube.utils.InnertubePreferences
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

        val USER_AGENT = InnertubePreferences.preference?.p33 ?: ""
        val USER_AGENT1 = InnertubePreferences.preference?.p32 ?: ""

        val REFERER1 = InnertubePreferences.preference?.p34 ?: ""
        val REFERER2 = InnertubePreferences.preference?.p35 ?: ""

        val cname = InnertubePreferences.preference?.p18 ?: ""
        val cver = InnertubePreferences.preference?.p19 ?: ""
        val cplatform = InnertubePreferences.preference?.p20 ?: ""
        val cxname = InnertubePreferences.preference?.p21 ?: ""


        val DefaultWeb = Context(
            client = Client(
                clientName = cname,
                clientVersion = cver,
                platform = cplatform,
                userAgent = USER_AGENT,
                referer = REFERER1,
                visitorData = Innertube.visitorData,
                xClientName = cxname.toIntOrNull()
            )
        )


        //val hl = if (LocalePreferences.preference?.useLocale == true) LocalePreferences.preference!!.hl else ""
        val hl = LocalePreferences.preference?.hl
        //val gl = LocalePreferences.preference?.gl


        val DefaultWebWithLocale = DefaultWeb.copy(
            client = DefaultWeb.client.copy(hl = hl)
        )

        val cname2 = InnertubePreferences.preference?.p22 ?: ""
        val cver2 = InnertubePreferences.preference?.p23 ?: ""


        val DefaultWeb2 = Context(
            client = Client(
                clientName = cname2,
                clientVersion = cver2,
                userAgent = USER_AGENT,
            )
        )

        val DefaultWeb2WithLocale = DefaultWeb2.copy(
            client = DefaultWeb2.client.copy(hl = hl)
        )

        val cname3 = InnertubePreferences.preference?.p24 ?: ""
        val cver3 = InnertubePreferences.preference?.p25 ?: ""
        val dmake = InnertubePreferences.preference?.p26 ?: ""
        val dmodel = InnertubePreferences.preference?.p27 ?: ""
        val osname = InnertubePreferences.preference?.p28 ?: ""
        val osversion = InnertubePreferences.preference?.p29 ?: ""
        val accept = InnertubePreferences.preference?.p30 ?: ""
        val cxname3 = InnertubePreferences.preference?.p31 ?: ""

        val DefaultWeb3 = Context(
            client = Client(
                clientName = cname3,
                clientVersion = cver3,
                deviceMake = dmake,
                deviceModel = dmodel,
                osName = osname,
                osVersion = osversion,
                acceptHeader = accept,
                userAgent = USER_AGENT1,
                xClientName = cxname3.toIntOrNull()
            )
        )

    }
}
