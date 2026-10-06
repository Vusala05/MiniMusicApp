package com.example.api.repository

import com.example.api.response.AlbumResponseDO
import com.example.api.response.ArtistResponseDO
import com.example.api.response.TrackResponseDO
import com.example.core_data.model.ResultWrapper

interface HomeRepository {

    suspend fun getAlbums(tag:String?, offset: Int) : ResultWrapper<List<AlbumResponseDO>>
    suspend fun getTracks(tag : String?,offset: Int) : ResultWrapper<List<TrackResponseDO>>
    suspend fun getArtist(offset: Int) : ResultWrapper<List<ArtistResponseDO>>
    suspend fun getTrackById(id : String) : ResultWrapper<TrackResponseDO>
    suspend fun getAlbumTracks(albumId : String,offset : Int) : ResultWrapper<List<TrackResponseDO>>


}