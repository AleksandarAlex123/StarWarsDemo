package rs.alexandar.startwars.network

import io.reactivex.Single
import retrofit2.http.*
import rs.alexandar.startwars.model.Planet
import rs.alexandar.startwars.model.PlanetLikeRequest
import rs.alexandar.startwars.model.PlanetLikeResponse
import rs.alexandar.startwars.model.Resident

interface StartWarsApi {
    @get:GET("planets/10")
    @get:Headers("Content-Type: application/json")
    val kaminoPlanets: Single<Planet?>?

    @Headers("Content-Type: application/json")
    @POST("planets/10/like")
    fun setPlanetLike(@Body planetLikeRequest: PlanetLikeRequest?): Single<PlanetLikeResponse?>?

    @Headers("Content-Type: application/json")
    @GET
    fun getResident(@Url url: String?): Single<Resident?>?
}