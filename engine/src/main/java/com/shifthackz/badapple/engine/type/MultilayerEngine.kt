package com.shifthackz.badapple.engine.type

import com.shifthackz.badapple.engine.Engine

class MultilayerEngine(
    private val engines: Set<Engine>,
) : Engine {

    init {
        require(engines.isNotEmpty()) {
            "engines can not be empty"
        }
    }

    override fun pause() = engines.forEach(Engine::pause)

    override fun play() = engines.forEach(Engine::play)

    override fun stop() = engines.forEach(Engine::stop)

    override fun toggle() = engines.forEach(Engine::toggle)
}
