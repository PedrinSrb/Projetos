package com.example.estatisticasdefutebolapp.model

data class StandingsResponse(
    val response: List<StandingWrapper>
)

data class StandingWrapper(
    val league: StandingLeague
)

data class StandingLeague(
    val standings: List<List<Standing>>
)

data class Standing(
    val rank: Int,
    val points: Int,
    val team: StandingTeam
)

data class StandingTeam(
    val id: Int,
    val name: String,
    val logo: String
)