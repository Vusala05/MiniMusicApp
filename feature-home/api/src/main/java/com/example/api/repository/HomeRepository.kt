package com.example.api.repository

import com.example.api.response.AlbumResponseDO
import com.example.api.response.ArtistResponseDO
import com.example.core_domain.model.TrackResponseDO
import com.example.core_domain.model.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getAlbums(tag:String?, offset: Int) : ResultWrapper<List<AlbumResponseDO>>
    suspend fun getTracks(tag : String?,offset: Int) : ResultWrapper<List<TrackResponseDO>>
    suspend fun getArtist(offset: Int) : ResultWrapper<List<ArtistResponseDO>>
    suspend fun getTrackById(id : String) : ResultWrapper<TrackResponseDO>
    suspend fun getAlbumTracks(albumId : String,offset : Int) : ResultWrapper<List<TrackResponseDO>>
    suspend fun getSearchedTracks(offset: Int,search : String) : ResultWrapper<List<TrackResponseDO>>
}