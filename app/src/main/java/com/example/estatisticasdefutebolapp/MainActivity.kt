package com.example.estatisticasdefutebolapp


import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.estatisticasdefutebolapp.api.RetrofitClient
import com.example.estatisticasdefutebolapp.model.TeamResponse
import com.example.estatisticasdefutebolapp.model.LeagueResponse
import com.example.estatisticasdefutebolapp.model.StandingsResponse


class MainActivity : AppCompatActivity() {

    private lateinit var edtTime: EditText
    private lateinit var btnBuscar: Button
    private lateinit var txtResultado: TextView
    private lateinit var imgEscudo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("API_TEST", BuildConfig.API_FOOTBALL_KEY)

        edtTime = findViewById(R.id.edtTime)
        btnBuscar = findViewById(R.id.btnBuscar)
        txtResultado = findViewById(R.id.txtResultado)
        imgEscudo = findViewById(R.id.imgEscudo)

        btnBuscar.setOnClickListener {

            val nomeTime = edtTime.text.toString().trim()

            if (nomeTime.isEmpty()) {
                Toast.makeText(
                    this,
                    "Digite um time!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            buscarTime(nomeTime)
        }
    }

    private fun buscarTime(nome: String) {

        RetrofitClient.instance
            .getTeam(nome)
            .enqueue(object : Callback<TeamResponse> {

                override fun onResponse(
                    call: Call<TeamResponse>,
                    response: Response<TeamResponse>
                ) {

                    if (response.isSuccessful) {

                        val dados =
                            response.body()?.response?.firstOrNull()

                        if (dados != null) {
                            Glide.with(this@MainActivity)
                                .load(dados.team.logo)
                                .into(imgEscudo)


                            txtResultado.text =
                                """
                                Nome: ${dados.team.name}
                                
                                País: ${dados.team.country}
                                
                                Fundação: ${dados.team.founded}
                                
                                Estádio: ${dados.venue.name}
                                
                                Capacidade: ${dados.venue.capacity}
                                """.trimIndent()

                        } else {
                            buscarLiga(nome)
                        }

                    } else {

                        txtResultado.text =
                            "Time ou liga não encontrado."
                    }
                }

                override fun onFailure(
                    call: Call<TeamResponse>,
                    t: Throwable
                ) {

                    txtResultado.text =
                        "Falha de conexão: ${t.message}"
                }
            })
    }

    private fun buscarLiga(nome: String) {

        RetrofitClient.instance
            .buscarLiga(nome)
            .enqueue(object : Callback<LeagueResponse> {

                override fun onResponse(
                    call: Call<LeagueResponse>,
                    response: Response<LeagueResponse>
                ) {

                    if (response.isSuccessful) {

                        val liga =
                            response.body()?.response?.firstOrNull()

                        if (liga != null) {

                            Glide.with(this@MainActivity)
                                .load(liga.league.logo)
                                .into(imgEscudo)


                                txtResultado.text =
                                """ 📊 Informações
    
                                Liga: ${liga.league.name}
    
                                País: ${liga.country.name}
    
                                Tipo: ${liga.league.type}
    
                                ID: ${liga.league.id}
    
                                Carregando tabela...
                                """.trimIndent()

                            buscarUltimaTemporada(liga.league.id)


                        } else {

                            txtResultado.text =
                                "Nenhum time ou liga encontrado."
                        }

                    } else {

                        txtResultado.text =
                            "Erro: ${response.code()}"
                    }
                }

                override fun onFailure(
                    call: Call<LeagueResponse>,
                    t: Throwable
                ) {

                    txtResultado.text =
                        "Falha: ${t.message}"
                }
            })
    }
    private fun buscarTabelaLiga(leagueId: Int, season: Int) {
        Log.d("TABELA", "Liga ID: $leagueId")

        RetrofitClient.instance
            .getStandings(leagueId, season)
            .enqueue(object : Callback<StandingsResponse> {

                override fun onResponse(
                    call: Call<StandingsResponse>,
                    response: Response<StandingsResponse>
                ) {

                    if(response.isSuccessful){

                        val tabela =
                            response.body()
                                ?.response
                                ?.firstOrNull()
                                ?.league
                                ?.standings
                                ?.firstOrNull()

                        if(tabela != null){

                            val texto = StringBuilder()

                            tabela.forEach {

                                texto.append(
                                    "${it.rank}º - ${it.team.name} (${it.points} pts)\n"
                                )
                            }

                            txtResultado.text =
                                texto.toString()
                        }
                    }
                }

                override fun onFailure(
                    call: Call<StandingsResponse>,
                    t: Throwable
                ) {

                    txtResultado.text =
                        t.message
                }
            })
    }

    private fun buscarUltimaTemporada(leagueId: Int) {

        RetrofitClient.instance
            .getLeagueInfo(leagueId)
            .enqueue(object : Callback<LeagueResponse> {

                override fun onResponse(
                    call: Call<LeagueResponse>,
                    response: Response<LeagueResponse>
                ) {

                    if (response.isSuccessful) {

                        val liga =
                            response.body()?.response?.firstOrNull()

                        if (liga != null) {

                            val ultimaComTabela = liga.seasons
                                .map { season -> season.year }
                                .sortedDescending()

                            buscarTabelaLigaTentandoTemporadas(leagueId, ultimaComTabela)

                            Log.d(
                                "TEMPORADA",
                                "Última temporada: $ultimaComTabela"
                            )



                        }
                    }
                }

                override fun onFailure(
                    call: Call<LeagueResponse>,
                    t: Throwable
                ) {

                    Log.d(
                        "TEMPORADA",
                        "Erro: ${t.message}"
                    )
                }
            })
    }
    private fun buscarTabelaLigaTentandoTemporadas(
        leagueId: Int,
        temporadas: List<Int>,
        index: Int = 0
    ) {
        if (index >= temporadas.size) {
            txtResultado.text = "Nenhuma tabela encontrada para essa liga."
            return
        }

        val season = temporadas[index]

        Log.d("TABELA", "Tentando temporada: $season")

        RetrofitClient.instance
            .getStandings(leagueId, season)
            .enqueue(object : Callback<StandingsResponse> {

                override fun onResponse(
                    call: Call<StandingsResponse>,
                    response: Response<StandingsResponse>
                ) {
                    val tabela = response.body()
                        ?.response
                        ?.firstOrNull()
                        ?.league
                        ?.standings
                        ?.firstOrNull()

                    if (tabela != null && tabela.isNotEmpty()) {
                        val texto = StringBuilder()

                        texto.append("📊 Tabela - Temporada $season\n\n")

                        tabela.forEach {
                            texto.append(
                                "${it.rank}º - ${it.team.name} (${it.points} pts)\n"
                            )
                        }

                        txtResultado.text = texto.toString()
                    } else {
                        buscarTabelaLigaTentandoTemporadas(
                            leagueId,
                            temporadas,
                            index + 1
                        )
                    }
                }

                override fun onFailure(
                    call: Call<StandingsResponse>,
                    t: Throwable
                ) {
                    txtResultado.text = "Erro: ${t.message}"
                }
            })
    }
}

