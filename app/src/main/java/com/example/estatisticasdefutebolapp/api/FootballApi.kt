package com.example.estatisticasdefutebolapp.api

import com.example.estatisticasdefutebolapp.model.StandingsResponse
import com.example.estatisticasdefutebolapp.model.LeagueResponse
import com.example.estatisticasdefutebolapp.model.TeamResponse
import retrofit2.Call
import retrofit2.http.GET

import retrofit2.http.Query

interface FootballApi {

    @GET("teams")
    fun getTeam(
        @Query("search") teamName: String
    ): Call<TeamResponse>

    @GET("leagues")
    fun buscarLiga(
        @Query("search") nomeLiga: String
    ): Call<LeagueResponse>

    @GET("leagues")
    fun getLeagueInfo(
        @Query("id") leagueId: Int
    ): Call<LeagueResponse>
    @GET("standings")
    fun getStandings(
        @Query("league") leagueId: Int,
        @Query("season") season: Int
    ): Call<StandingsResponse>

}