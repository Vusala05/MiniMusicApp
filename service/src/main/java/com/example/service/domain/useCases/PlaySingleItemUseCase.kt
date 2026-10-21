package com.example.service.domain.useCases

import com.example.core_domain.model.PlayableItem
import com.example.service.domain.controller.MusicController
import javax.inject.Inject

class PlaySingleItemUseCase @Inject constructor(
    val musicController: MusicController
) {
    operator fun invoke(item : PlayableItem){
        musicController.playSingleItem(item)
    }
}