package eu.hxreborn.phdp.util

import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RootUtils {
    private val commands =
        listOf(
            "killall com.android.systemui",
            "pkill -f com.android.systemui",
            "kill -9 $(pidof com.android.systemui)",
        )

    private var _shell: Shell? = null

    private val shell: Shell
        get() = _shell ?: Shell.Builder.create().build().also { _shell = it }

    suspend fun isRootAvailable(): Boolean =
        withContext(Dispatchers.IO) {
            ArrayList<String>().also {
                shell.newJob().add("whoami").to(it).exec()
            }.firstOrNull() == "root"
        }.also {
            if (!it) {
                _shell?.close()
                _shell = null
            }
        }

    suspend fun restartSystemUI(): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                var lastErr: String? = null
                for (cmd in commands) {
                    val res = shell.newJob().add(cmd).exec()
                    if (res.isSuccess) return@runCatching
                    lastErr = (res.err + res.out).joinToString().ifBlank { "exit ${res.code}" }
                }
                error(lastErr ?: "All methods failed")
            }
        }
}
