package com.example.f1.ui.telemetry

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.net.InetSocketAddress

class NewSessionViewModel: ViewModel() {
    data class ViewState (
        val receivingData: Boolean = false
    )

    private val _viewState = MutableStateFlow<ViewState>(ViewState())
    val viewState = _viewState.asStateFlow()

    private val server: BoundDatagramSocket = aSocket(ActorSelectorManager(Dispatchers.IO)).udp().bind(
        InetSocketAddress("10.0.2.15", 8888)
    )

    init {
        viewModelScope.launch {
            datagramFlow().collect { value ->
                Log.d("UDP", "Got value: $value")
                _viewState.update { it.copy(receivingData = true) }
            }
        }
    }

    private fun datagramFlow(): Flow<Datagram> = flow {
        while(true) {
            val input = server.incoming.receive()
            // val input = server.incoming.receive()
            Log.d("UDP", input.address.toString())
            val read = input.packet.readText(4)
            Log.d("UDP", read)
            emit(input)
        }
    }

    override fun onCleared() {
        server.close()
        viewModelScope.cancel()

        super.onCleared()
    }
}