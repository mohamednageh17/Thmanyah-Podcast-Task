package com.example.domain.network

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val networkStatus: Flow<NetworkStatus>
    val isConnected: Boolean
}

sealed class NetworkStatus {
    data object Connected : NetworkStatus()
    data object Disconnected : NetworkStatus()
    data object Unknown : NetworkStatus()
}
