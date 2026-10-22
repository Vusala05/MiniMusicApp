package com.example.impl.data.repositoryImpl

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.api.repository.HomeRepository
import com.example.api.response.AlbumResponseDO
import com.example.api.response.ArtistResponseDO
import com.example.core_domain.model.TrackResponseDO
import com.example.core_data.handler.apiCallingHandler
import com.example.core_domain.handler.GlobalNetworkHandler
import com.example.core_domain.model.ResultWrapper
import com.example.core_domain.model.toTransform
import com.example.impl.data.dataSource.DataSource
import javax.inject.Inject
import kotlin.collections.map
import kotlin.collections.orEmpty

class HomeRepositoryImpl @Inject constructor(
   val dataSource: DataSource,
   val globalNetworkHandler: GlobalNetworkHandler
) : HomeRepository {
    @RequiresApi(Build.VERSION_CODES.O)

    override suspend fun getAlbums(tag:String?,offset: Int): ResultWrapper<List<AlbumResponseDO>> {
        return apiCallingHandler(globalNetworkHandler = globalNetworkHandler,
            apiCalling ={ dataSource.getAlbums(tag = tag, offset = offset) }).toTransform { result ->
            result?.map { it.toUiModel() }.orEmpty()
         }

    }

    override suspend fun getTracks(tag:String?,offset: Int): ResultWrapper<List<TrackResponseDO>> {
        return  apiCallingHandler(globalNetworkHandler = globalNetworkHandler,
            apiCalling ={ dataSource.getSongs(tags = tag, offset = offset) } ).toTransform { result->
            result?.map { it.toUiModel() }.orEmpty()
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getArtist(offset: Int): ResultWrapper<List<ArtistResponseDO>> {
        return apiCallingHandler(globalNetworkHandler = globalNetworkHandler,
            apiCalling ={ dataSource.getArtists(offset = offset) }).toTransform { result ->
            result?.map { it.toUiModel() }.orEmpty()
        }

    }

    override suspend fun getTrackById(id: String): ResultWrapper<TrackResponseDO> {
        Log.d("ApiCalll", "getTrackById called, id=$id", Throwable("caller trace"))
        return apiCallingHandler(globalNetworkHandler = globalNetworkHandler,
            apiCalling ={ dataSource.getSongById(id = id) }).toTransform { result->
                result?.firstOrNull()?.toUiModel() ?:TrackResponseDO("","",0,"","","","","",false)
        }
    }

    override suspend fun getAlbumTracks(albumId: String, offset : Int): ResultWrapper<List<TrackResponseDO>> {
        return apiCallingHandler(globalNetworkHandler = globalNetworkHandler,
            apiCalling ={ dataSource.getAlbumTracks(albumId = albumId, offset = offset) } ).toTransform { result ->
            result?.map { it.toUiModel() }.orEmpty()
            }
    }

    override suspend fun getSearchedTracks(
        offset: Int,
        search: String
    ): ResultWrapper<List<TrackResponseDO>> {
        return apiCallingHandler(globalNetworkHandler = globalNetworkHandler,
            apiCalling ={ dataSource.observeSearchedTracks(search =search, offset = offset) } ).toTransform { result ->
            result?.map { it.toUiModel() }.orEmpty()
            }
    }
}