package com.example.speedtest.data

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

//репозиторий который получает данные сети
class GetSpeedNetworkRepository @Inject constructor(
    context: Context
) {

    private val connectManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val networkCapabilities = connectManager.getNetworkCapabilities(connectManager.activeNetwork)

    fun getSpeed(): SpeedResult{

        val downSpeed = networkCapabilities?.let { it.linkDownstreamBandwidthKbps/1000 }
        val upSpeed = networkCapabilities?.let { it.linkUpstreamBandwidthKbps/1000 }
        return SpeedResult(downSpeed = downSpeed, upSpeed = upSpeed)
    }
}