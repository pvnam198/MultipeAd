package com.ads

class AdInitializationManagerImpl(
    private val initializers: List<AdInitializer>
) : AdInitializationManager {

    override fun initialize() {
        initializers.forEach { it.initialize() }
    }

}