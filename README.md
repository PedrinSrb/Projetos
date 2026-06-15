# Guia de Times 

## Descrição

O Guia de Times é um aplicativo Android desenvolvido em Kotlin que permite consultar informações sobre clubes de futebol e ligas esportivas utilizando a API-Football. O usuário pode pesquisar pelo nome de um time ou liga e visualizar informações relevantes de forma organizada e intuitiva.

## API utilizada

* Nome da API: API-Football (API Sports)
* Endpoint utilizado:

  * `/teams?search={nome}`
  * `/leagues?search={nome}`
  * `/standings?league={id}&season={ano}`

* Exemplo de URL consultada:

  * `https://v3.football.api-sports.io/teams?search=Flamengo`
  * `https://v3.football.api-sports.io/leagues?search=Premier League`
* Principais dados retornados:

  * Nome do time
  * País
  * Fundação
  * Estádio
  * Capacidade
  * Escudo do time
  * Nome da liga
  * Tipo da competição
  * Classificação da liga

## Funcionalidades

* Entrada de dados pelo usuário
* Pesquisa de times por nome
* Pesquisa de ligas por nome
* Validação de campo vazio
* Consulta a uma API pública
* Exibição das informações retornadas
* Exibição de escudos e logos
* Tratamento básico de erro
* Tratamento de falhas de conexão

## Tecnologias utilizadas

* Kotlin
* Android Studio
* XML
* Retrofit
* Gson Converter
* OkHttp
* Glide
* API-Football

## Permissões utilizadas

O aplicativo utiliza a permissão INTERNET para realizar requisições à API pública.

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Como executar o projeto

1. Clonar este repositório.
2. Abrir o projeto no Android Studio.
3. Aguardar a sincronização do Gradle.
4. Executar o aplicativo em um emulador ou dispositivo Android.
5. Informar o nome de um time ou liga.
6. Clicar no botão "Buscar".

Prints do aplicativo


Tela Inicial

  
  
  <img width="393" height="852" alt="Screenshot_62" src="https://github.com/user-attachments/assets/4e4151ba-2353-47c6-bdd0-05711bc1215f" />


Consulta de Time


  
  <img width="391" height="847" alt="Screenshot_63" src="https://github.com/user-attachments/assets/12da175a-f68b-4b98-b996-d77cd508910a" />


Consulta de Liga



   <img width="376" height="862" alt="Screenshot_64" src="https://github.com/user-attachments/assets/738e5e00-48e5-443e-bc3d-1d0d0d7d5592" />


## Autor
Pedro Henrique De Olivera Cadiz


