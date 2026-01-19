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

    private var shellInstance: Shell? = null

    private val shell: Shell
        get() =
            shellInstance ?: Shell.Builder
                .create()
                .build()
                .also { shellInstance = it }

    suspend fun isRootAvailable(): Boolean =
        withContext(Dispatchers.IO) {
            ArrayList<String>()
                .also {
                    shell
                        .newJob()
                        .add("whoami")
                        .to(it)
                        .exec()
                }.firstOrNull() == "root"
        }.also {
            if (!it) {
                shellInstance?.close()
                shellInstance = null
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
