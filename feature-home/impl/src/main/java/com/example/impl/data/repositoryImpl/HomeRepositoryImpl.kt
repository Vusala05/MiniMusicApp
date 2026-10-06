package com.example.impl.data.repositoryImpl

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.api.repository.HomeRepository
import com.example.api.response.AlbumResponseDO
import com.example.api.response.ArtistResponseDO
import com.example.api.response.TrackResponseDO
import com.example.core_data.feauture.GlobalNetworkHandler
import com.example.core_data.model.ResultWrapper
import com.example.core_data.model.apiCallingHandler
import com.example.core_data.model.handleResultWrapper
import com.example.impl.data.dataSource.DataSource
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
   val dataSource: DataSource,
   val globalNetworkHandler: GlobalNetworkHandler
) : HomeRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAlbums(tag:String?,offset: Int): ResultWrapper<List<AlbumResponseDO>> {
        return handleResultWrapper(result = apiCallingHandler(globalNetworkHandler = globalNetworkHandler,
            apiCalling ={ dataSource.getAlbums(tag = tag, offset = offset) } ), transform = { result ->
             result?.map { it.toUiModel()}.orEmpty()
            })

    }

    override suspend fun getTracks(tag:String?,offset: Int): ResultWrapper<List<TrackResponseDO>> {
        return handleResultWrapper(result = apiCallingHandler(globalNetworkHandler = globalNetworkHandler,
            apiCalling ={ dataSource.getSongs(tags = tag, offset = offset) } ), transform = { result ->
            result?.map { it.toUiModel()}.orEmpty()
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getArtist(offset: Int): ResultWrapper<List<ArtistResponseDO>> {
        return handleResultWrapper(result = apiCallingHandler(globalNetworkHandler = globalNetworkHandler,
            apiCalling ={ dataSource.getArtists(offset = offset) } ), transform = { result ->
            result?.map { it.toUiModel()}.orEmpty()
        })

    }

    override suspend fun getTrackById(id: String): ResultWrapper<TrackResponseDO> {
        return handleResultWrapper(result = apiCallingHandler(globalNetworkHandler = globalNetworkHandler,
            apiCalling ={ dataSource.getSongById(id = id) } ), transform = { result ->
            result?.firstOrNull()?.toUiModel() ?: TrackResponseDO("","",0,"","","","",emptyList())
        })
    }

    override suspend fun getAlbumTracks(albumId: String, offset : Int): ResultWrapper<List<TrackResponseDO>> {
        return handleResultWrapper(result = apiCallingHandler(globalNetworkHandler = globalNetworkHandler,
            apiCalling ={ dataSource.getAlbumTracks(albumId = albumId, offset = offset) } ), transform = { result ->
            result?.map { it.toUiModel() }.orEmpty()
        })
    }
}