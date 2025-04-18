package com.ads.initializer

class AdInitializationManagerImpl(
    private val initializers: List<AdInitializer>
) : AdInitializationManager {

    override fun initialize() {
        initializers.forEach { it.initialize() }
    }

}