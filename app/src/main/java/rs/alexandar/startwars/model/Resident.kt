package rs.alexandar.startwars.model

class Resident {
    var homeworld: String? = null
    var eye_color: String? = null
    var gender: String? = null
    var skin_color: String? = null
    var edited: String? = null
    var created: String? = null
    var image_url: String? = null
    var mass: String? = null
    var name: String? = null
    var height: String? = null
    var hair_color: String? = null
    var birth_year: String? = null

    override fun toString(): String {
        return "Resident [homeworld = $homeworld, eye_color = $eye_color, gender = $gender, skin_color = $skin_color, edited = $edited, created = $created, image_url = $image_url, mass = $mass, name = $name, height = $height, hair_color = $hair_color, birth_year = $birth_year]"
    }
}