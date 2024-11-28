package org.gerhard.kmp_sandbox_demo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform