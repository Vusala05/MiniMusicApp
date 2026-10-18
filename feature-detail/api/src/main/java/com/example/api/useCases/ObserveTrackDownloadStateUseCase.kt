package com.example.api.useCases

import com.example.core_sync.domain.repository.DownloadRepository
import com.example.core_domain.model.UiDownloadingState
import com.example.core_sync.domain.feature.DownloadController
import com.example.storage.data.model.DownloadingState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ObserveDownloadStatusUseCase @Inject constructor(
    private val downloadRepository: DownloadRepository,
    private val downloadController: DownloadController
) {
    operator fun invoke(id: String): Flow<UiDownloadingState> =
        combine(
            downloadRepository.observeCurrentItem(id),
            downloadController.observeProgress(id)
        ) { entity, progress ->
            when (entity?.state) {
                DownloadingState.DOWNLOADING -> UiDownloadingState.InProgress(progress)
                DownloadingState.QUEUED -> UiDownloadingState.InProgress(null)
                DownloadingState.DOWNLOADED_SUCCESSFULLY -> UiDownloadingState.Completed
                else -> UiDownloadingState.Idle
            }
        }
}
