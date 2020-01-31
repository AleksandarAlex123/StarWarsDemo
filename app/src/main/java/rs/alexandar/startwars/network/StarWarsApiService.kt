package rs.alexandar.startwars.network

enum class StarWarsApiService {
    INSTANCE;

    val value: StartWarsApi

    init {
        value =
            HttpClient.INSTANCE.retrofit.create(
                StartWarsApi::class.java
            )
    }
}