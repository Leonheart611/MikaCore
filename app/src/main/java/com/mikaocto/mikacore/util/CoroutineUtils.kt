package com.mikaocto.mikacore.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun io(block: suspend CoroutineScope.() -> Unit) {
    withContext(Dispatchers.IO) {
        block.invoke(this)
    }
}

suspend fun ui(block: suspend CoroutineScope.() -> Unit) {
    withContext(Dispatchers.Main) {
        block.invoke(this)
    }
}

suspend fun default(block: suspend CoroutineScope.() -> Unit) {
    withContext(Dispatchers.Default) {
        block.invoke(this)
    }
}