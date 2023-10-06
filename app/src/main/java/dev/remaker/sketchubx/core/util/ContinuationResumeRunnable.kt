package dev.remaker.sketchubx.core.util

import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

class ContinuationResumeRunnable(
    private val continuation: Continuation<Unit>
) : Runnable {

    override fun run() {
        continuation.resume(Unit)
    }
}
