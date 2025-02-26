package it.fast4x.environment.utils

object YoutubePreferences {
    var preference: YoutubePreferenceItem? = null
}

data class YoutubePreferenceItem(
    var cookie: String?,
    var visitordata: String?,
    var dataSyncId: String?,
    var dnsOverHttps: String?
)