package com.example.api.useCases

import com.example.core_data.feauture.DownloadRepository
import com.example.core_data.model.DownloadingState
import com.example.core_data.util.DownloadingStatus
import com.example.core_sync.domain.DownloadController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ObserveDownloadStatusUseCase @Inject constructor(
    private val downloadRepository: DownloadRepository,
    private val downloadController: DownloadController
) {
    operator fun invoke(id: String): Flow<DownloadingStatus> =
        combine(
            downloadRepository.observeCurrentItem(id),
            downloadController.observeProgress(id)
        ) { entity, progress ->
            when (entity?.state) {
                DownloadingState.DOWNLOADING -> DownloadingStatus.InProgress(progress)
                DownloadingState.QUEUED -> DownloadingStatus.InProgress(null)
                DownloadingState.DOWNLOADED_SUCCESSFULLY -> DownloadingStatus.Completed
                else -> DownloadingStatus.Idle
            }
        }
}
