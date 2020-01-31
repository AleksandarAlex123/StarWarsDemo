package rs.alexandar.startwars.data

import rs.alexandar.startwars.model.Planet
import rs.alexandar.startwars.model.Resident
import java.util.*

object PlanetData {

    var planet: Planet? = null

    var residentList: MutableList<Resident> =
        ArrayList()
        set(residentList) {
            residentList.clear()
            field = residentList
        }
}