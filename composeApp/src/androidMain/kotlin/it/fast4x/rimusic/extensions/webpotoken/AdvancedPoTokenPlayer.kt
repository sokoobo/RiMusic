package it.fast4x.rimusic.extensions.webpotoken

import io.ktor.client.call.body
import it.fast4x.innertube.Innertube.playerWithWebPoToken
import it.fast4x.innertube.models.PlayerResponse
import it.fast4x.innertube.models.bodies.PlayerBody
import it.fast4x.rimusic.utils.getSignatureTimestampOrNull

suspend fun advancedPoTokenPlayer(body: PlayerBody): Result<Triple<String?, PlayerResponse?, String?>> = runCatching{

    val maxRetries = 2
    var retryCount = 0
    var loop = true
    var response: PlayerResponse? = null

    val poTokenGenerator = PoTokenGenerator()
    val signatureTimestamp = getSignatureTimestampOrNull(body.videoId)
    val (webPlayerPot, webStreamingPot) = poTokenGenerator.getWebClientPoToken(body.videoId)?.let {
        Pair(it.playerRequestPoToken, it.streamingDataPoToken)
    } ?: Pair(null, null)

    while (loop == true) {

        response = playerWithWebPoToken(
            body.videoId,
            body.playlistId,
            signatureTimestamp,
            webPlayerPot
        ).body<PlayerResponse>()

        println("advancedPoTokenPlayer webStreamingPot: $webStreamingPot webPlayerPot: $webPlayerPot signatureTimestamp: $signatureTimestamp")


        if (response.playabilityStatus?.status == "OK" || retryCount >= maxRetries)
            loop = false

        println("advancedPoTokenPlayer retryCount: $retryCount")
        retryCount++
    }



    return@runCatching Triple(null, response, webStreamingPot)

}