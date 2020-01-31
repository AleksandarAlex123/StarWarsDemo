package rs.alexandar.startwars.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class Planet {
    var edited: String? = null
    var created: String? = null
    var image_url: String? = null
    var climate: String? = null
    var rotation_period: String? = null
    var population: String? = null
    var orbital_period: String? = null
    var surface_water: String? = null
    var diameter: String? = null
    var gravity: String? = null
    var name: String? = null
    lateinit var residents: Array<String>
    var terrain: String? = null
    var likes: String? = null

    override fun toString(): String {
        return "Planet [edited = $edited, created = $created, image_url = $image_url, climate = $climate, rotation_period = $rotation_period, population = $population, orbital_period = $orbital_period, surface_water = $surface_water, diameter = $diameter, gravity = $gravity, name = $name, residents = $residents, terrain = $terrain, likes = $likes]"
    }

    companion object {
        @kotlin.jvm.JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }
}