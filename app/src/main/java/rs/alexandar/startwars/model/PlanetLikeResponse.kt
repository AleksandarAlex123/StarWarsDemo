package rs.alexandar.startwars.model

class PlanetLikeResponse {
    var likes: String? = null
    var planet_id: String? = null

    override fun toString(): String {
        return "PlanetLikeResponse [likes  = $likes, planet_id = $planet_id]"
    }
}