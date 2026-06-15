package com.example.estatisticasdefutebolapp.model

data class LeagueResponse(
    val response: List<LeagueData>
)

data class LeagueData(
    val league: League,
    val country: Country,
    val seasons: List<Season>
)

data class League(
    val id: Int,
    val name: String,
    val type: String,
    val logo: String
)

data class Country(
    val name: String
)

data class Season(
    val year: Int,
    val current: Boolean
)