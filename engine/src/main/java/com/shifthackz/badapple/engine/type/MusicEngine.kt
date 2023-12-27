package com.shifthackz.badapple.engine.type

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import com.shifthackz.badapple.engine.Engine
import com.shifthackz.badapple.engine.SequenceState

class MusicEngine : Engine {
    private val player = MediaPlayer()
    private var descriptor: AssetFileDescriptor? = null

    private var prepared = false
    private var awaitingPlayback = false

    init {
        player.setOnCompletionListener {
            prepared = false
        }
        player.setOnPreparedListener {
            prepared = true
            if (awaitingPlayback) {
                player.start()
                awaitingPlayback = false
            }
        }
    }

    fun load(descriptor: AssetFileDescriptor) {
        this.descriptor = descriptor
        prepare()
    }

    override fun pause() {
        player.pause()
    }

    override fun play() {
        if (prepared) {
            player.start()
        } else {
            player.prepare()
            awaitingPlayback = true
        }
    }

    override fun stop() {
        player.stop()
        prepared = false
    }

    override fun toggle() {
        if (player.isPlaying) pause()
        else play()
    }

    private fun prepare() {
        descriptor?.let {
            player.setDataSource(
                it.fileDescriptor,
                it.startOffset,
                it.length,
            )
            player.prepare()
        }
    }
}
