package com.example.api.useCases

import com.example.service.domain.controller.MusicController
import javax.inject.Inject

class ControlDetailBottomComponentsUseCase @Inject constructor(
    val musicController: MusicController
) {
    operator fun invoke(controlBottomComponents: ControlBottomComponents){
        when(controlBottomComponents){
            is ControlBottomComponents.ToggleNext -> musicController.next()
            is ControlBottomComponents.TogglePrevious -> musicController.previous()
            is ControlBottomComponents.TogglePlayPause -> musicController.togglePlayPause()
            is ControlBottomComponents.ToggleRepeatMode -> musicController.toggleRepeatMode()
            is ControlBottomComponents.SeekTo -> musicController.seekTo(controlBottomComponents.position)
            is ControlBottomComponents.SetShuffleEnabled -> musicController.setShuffleEnabled(controlBottomComponents.enable)
        }
    }
}

sealed interface ControlBottomComponents{
    data object ToggleNext : ControlBottomComponents
    data object TogglePrevious : ControlBottomComponents
    data object TogglePlayPause : ControlBottomComponents
    data object ToggleRepeatMode : ControlBottomComponents
    data class SeekTo( val position : Long) : ControlBottomComponents
    data class SetShuffleEnabled(val enable: Boolean) : ControlBottomComponents
}

