package com.example.impl.data.dataSource

import com.example.core_data.BuildConfig
import com.example.core_data.model.BaseResponse
import com.example.impl.data.response.AlbumResponse
import com.example.impl.data.response.ArtistResult
import com.example.impl.data.response.TrackResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface

DataSource {

    @GET("albums/musicinfo")
        suspend fun getAlbums(
        @Query("client_id") clientId: String = BuildConfig.CLIENT_ID,
        @Query("tag") tag: String? = null,
        @Query("type") type: String = "album",
        @Query("order") order: String = "popularity_month",
        @Query("limit") limit: Int = 15,
        @Query("offset") offset: Int,
        @Query("format") format: String = "json"
        ): Response<BaseResponse<List<AlbumResponse>>>

    @GET("tracks")
    suspend fun getSongs(
        @Query("client_id") clientId: String = BuildConfig.CLIENT_ID,
        @Query("tags") tags: String? = null,
        @Query("order") order: String = "popularity_month",
        @Query("groupby") groupBy: String = "artist_id",
        @Query("limit") limit: Int = 15,
        @Query("offset") offset: Int,
        @Query("format") format: String = "json"
    ): Response<BaseResponse<List<TrackResult>>>

    @GET("tracks")
    suspend fun getSongById(
        @Query("client_id") clientId: String = BuildConfig.CLIENT_ID,
        @Query("id") id: String,
        @Query("include") include: String = "musicinfo",
        @Query("format") format: String = "json"
    ): Response<BaseResponse<List<TrackResult>>>

    @GET("albums/tracks")
    suspend fun getAlbumTracks(
        @Query("client_id") clientId: String = BuildConfig.CLIENT_ID,
        @Query("id") albumId: String,
        @Query("imagesize") imageSize: Int = 300,
        @Query("format") format: String = "json",
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 15
    ): Response<BaseResponse<List<TrackResult>>>

    @GET("artists")
    suspend fun getArtists(
        @Query("client_id") clientId: String = BuildConfig.CLIENT_ID,
        @Query("order") order: String = "popularity_week",
        @Query("limit") limit: Int = 15,
        @Query("offset") offset: Int,
        @Query("format") format: String = "json"
    ): Response<BaseResponse<List<ArtistResult>>>



}