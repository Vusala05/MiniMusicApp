package com.example.service.service

import android.content.ComponentName
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.ui.compose.material3.Player
import com.example.core_data.util.ButtonState
import com.example.core_data.util.RepeatMode
import com.example.service.controller.MusicController
import com.example.service.model.PlayableItem
import com.example.service.model.PlaybackState
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.map

@Singleton
class MediaSessionController @Inject constructor(
    @ApplicationContext private val context: Context
) : MusicController, Player.Listener {

    private val _playbackState = MutableStateFlow(PlaybackState())
    override val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    var progressJob : Job?=null
    var controllerFuture: ListenableFuture<MediaController>? = null
    var controller : MediaController?=null
    var pendingControllerActions = mutableListOf<(MediaController )-> Unit> ()

    override fun bind() {
        if (controllerFuture != null) return
        val token = SessionToken(context, ComponentName(context, MusicService::class.java))
        val future = MediaController.Builder(context, token).buildAsync()
        controllerFuture = future
        future.addListener({
            if (controllerFuture !== future) return@addListener
            try {
                val currentController = future.get()
                controller = currentController
                controller?.addListener(this)
                pendingControllerActions.forEach{it(currentController)}
                pendingControllerActions.clear()
                getCurrentStateToInitialize(currentController)
            } catch (e: Exception) {
                pendingControllerActions.clear()
                controllerFuture = null
            }
        }, ContextCompat.getMainExecutor(context))
    }

    override fun unbind() {
        stopProgressLoop()
        controller?.removeListener(this)
        controllerFuture?.let { MediaController.releaseFuture(it) }
        controller = null
        controllerFuture = null
        pendingControllerActions.clear()
    }

    override fun onEvents(player: Player, events: Player.Events) {
      getCurrentStateToInitialize(player,events)
    }

    private fun getCurrentStateToInitialize(player : Player, events: Player.Events?=null){
        updateState(player,events)
        if(player.isPlaying) startProgressLoop() else stopProgressLoop()
    }

    fun withController(block : (MediaController) -> Unit) {
        val c = controller
        c?.let {
            block(c)
        } ?: pendingControllerActions.add(block)
    }


    private fun startProgressLoop() {
        if (progressJob?.isActive == true) return
        val currentController = controller
        progressJob = scope.launch {
            while (isActive) {
               currentController?.let {
                   _playbackState.update { it.copy(currentPositionMs = currentController.currentPosition.toInt()) }
               }
                delay(500L)
                }


            }
        }

    private fun stopProgressLoop() {
        progressJob?.cancel()
        progressJob = null
    }

    override fun <T : PlayableItem> appendToQueue(data: List<T>) {
        val mediaItems = data.toMediaItems()
        withController {
            if(it.mediaItemCount > 0) it.addMediaItems(mediaItems)
        }

    }

    override fun <T : PlayableItem> playQueue(
        data: List<T>,
        startIndex: Int
    ) {
        val mediaItems = data.toMediaItems()
       withController {
           it.setMediaItems(mediaItems,startIndex,C.TIME_UNSET)
           it.prepare()
           it.play()
       }

    }

    override fun <T : PlayableItem> setQueue(
        data: List<T>,
        startIndex: Int
    ) {
        val mediaItems = data.toMediaItems()
        withController {
            it.setMediaItems(mediaItems,startIndex,C.TIME_UNSET)
            it.prepare()
        }    }

    override fun seekToIndex(index: Int) {
         withController {
            it.seekToDefaultPosition(index)
             it.prepare()
        }
    }


    override fun playAtIndex(index: Int) = withController { c ->
        if(c.playbackState == Player.STATE_IDLE) c.prepare()
    c.seekToDefaultPosition(index)
    c.play()
}


     override fun togglePlayPause() = withController {
             when{
                 it.playbackState == Player.STATE_IDLE -> {
                     it.prepare()
                     it.play()
                 }
                 it.playbackState == Player.STATE_ENDED -> {
                     it.seekToDefaultPosition()
                     it.play()
                 }
                 it.playWhenReady -> it.pause()
                 else -> it.play()
             }
         }


    override fun next() = withController { if(it.hasNextMediaItem()) {
        if(it.playbackState == Player.STATE_IDLE)  it.prepare()
        it.seekToNextMediaItem()
        it.play()
    }}


    override fun previous() = withController {it.seekToPrevious() }

    override fun seekTo(positionMs: Long) = withController { it.seekTo(positionMs) }

    override fun setShuffleEnabled(enabled: Boolean) = withController { it.shuffleModeEnabled = enabled }


    override fun toggleRepeatMode() {
        withController {
            it.repeatMode = when (it.repeatMode) {
                Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ALL
                Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_ONE
                else -> Player.REPEAT_MODE_OFF
            }
        }

    }



    private fun updateState(player: Player, events: Player.Events? = null) {
        val btnState = when {
            player.playbackState == Player.STATE_BUFFERING && player.playWhenReady ->
               ButtonState.LOADING
            player.isPlaying -> ButtonState.PAUSE
            else ->ButtonState.PLAY
        }

        _playbackState.update { old ->
            val queue = if (events == null || events.contains(Player.EVENT_TIMELINE_CHANGED)) {
                List(player.mediaItemCount) { player.getMediaItemAt(it) }
            } else old.queue

            old.copy(
                queue = queue,
                currentIndex = if (player.mediaItemCount == 0) -1 else player.currentMediaItemIndex,
                isPlaying = player.isPlaying,
                currentItem = player.currentMediaItem,
                currentQueueItemsId = queue,
                currentPositionMs = player.currentPosition.coerceAtLeast(0L).toInt(),
                totalDurationMs = player.duration.coerceAtLeast(0L).toInt(),
                hasNext = player.hasNextMediaItem(),
                hasPrevious = player.hasPreviousMediaItem(),
                isShuffleOn = player.shuffleModeEnabled,
                repeatMode = when(player.repeatMode){
                    Player.REPEAT_MODE_ALL -> RepeatMode.REPEAT_MODE_ALL
                    Player.REPEAT_MODE_ONE -> RepeatMode.REPEAT_MODE_ONE
                    else -> RepeatMode.REPEAT_MODE_OFF

                },
                btnState = btnState,
                errorMessage = player.playerError?.toMessage()
            )
        }
    }

    private fun PlaybackException.toMessage(): String = when (errorCode) {
        PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED,
        PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT,
        PlaybackException.ERROR_CODE_TIMEOUT -> "Connect to internet"

        PlaybackException.ERROR_CODE_IO_BAD_HTTP_STATUS,
        PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND -> "Track is unavailable"

        PlaybackException.ERROR_CODE_DECODING_FAILED,
        PlaybackException.ERROR_CODE_PARSING_CONTAINER_MALFORMED -> "This track can't be played"

        else -> "Playback error"
    }


    private fun<T : PlayableItem> List<T>.toMediaItems(): List<MediaItem> = this.map{ item ->
        MediaItem.Builder()
            .setMediaId(item.id)
            .setUri(item.audio)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setArtist(item.artistName)
                    .setTitle(item.name)
                    .setArtworkUri(item.image.toUri())
                    .build()
            )
            .build()

    }

}
