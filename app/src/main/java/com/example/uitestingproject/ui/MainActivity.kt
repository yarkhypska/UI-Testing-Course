package com.example.uitestingproject.ui

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.uitestingproject.UiTestingApplication
import com.example.uitestingproject.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            NetworkConnectionStateHolder.updateNetworkAvailability(true)
        }

        override fun onLost(network: Network) {
            NetworkConnectionStateHolder.updateNetworkAvailability(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as UiTestingApplication).applicationGraph.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeInternetConnection()
    }

    private fun observeInternetConnection() {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val connectivityManager =
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
        checkCurrentNetworkState(connectivityManager)
    }

    private fun checkCurrentNetworkState(connectivityManager: ConnectivityManager) {
        try {
            NetworkConnectionStateHolder.updateNetworkAvailability(
                connectivityManager.activeNetwork?.let {
                    connectivityManager.getNetworkCapabilities(it)?.let { capabilities ->
                        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                    }
                } ?: false
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val connectivityManager =
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}