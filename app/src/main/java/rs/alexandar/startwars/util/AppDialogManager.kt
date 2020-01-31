package rs.alexandar.startwars.util

import android.app.Activity
import android.app.Dialog
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import rs.alexandar.startwars.R
import rs.alexandar.startwars.model.Resident

object AppDialogManager {
    var RESIDENT_DIALOW_SHOWN = false
    fun showResidentDetailDialog(resident: Resident, activity: Activity?) {
        if (RESIDENT_DIALOW_SHOWN) {
            return
        }
        val dialog =
            Dialog(activity!!, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.setContentView(R.layout.resident_full_screen_dialog)
        val residentImage =
            dialog.findViewById<ImageView>(R.id.residentImage)
        Glide.with(activity)
            .load(resident.image_url)
            .error(R.drawable.error_placeholder)
            .into(residentImage)
        val nameValue = dialog.findViewById<TextView>(R.id.nameValue)
        nameValue.text = resident.name
        val heightValue = dialog.findViewById<TextView>(R.id.heightValue)
        heightValue.text = resident.height
        val massValue = dialog.findViewById<TextView>(R.id.massValue)
        massValue.text = resident.mass
        val hairColorValue = dialog.findViewById<TextView>(R.id.hairColorValue)
        hairColorValue.text = resident.hair_color
        val skinColorValue = dialog.findViewById<TextView>(R.id.skinColorValue)
        skinColorValue.text = resident.skin_color
        val eyeColorValue = dialog.findViewById<TextView>(R.id.eyeColorValue)
        eyeColorValue.text = resident.eye_color
        val birthYearValue = dialog.findViewById<TextView>(R.id.birthYearValue)
        birthYearValue.text = resident.birth_year
        val genderValue = dialog.findViewById<TextView>(R.id.genderValue)
        genderValue.text = resident.gender
        val homeworldValue = dialog.findViewById<TextView>(R.id.homeworldValue)
        homeworldValue.text = resident.homeworld
        val createdValue = dialog.findViewById<TextView>(R.id.createdValue)
        createdValue.text = resident.created
        val editedValue = dialog.findViewById<TextView>(R.id.editedValue)
        editedValue.text = resident.edited
        val buttonCancel = dialog.findViewById<TextView>(R.id.buttonCancel)
        buttonCancel.setOnClickListener {
            RESIDENT_DIALOW_SHOWN = false
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        RESIDENT_DIALOW_SHOWN = true
        dialog.show()
    }

    fun showFullScreenPlanetImageDialog(
        activity: Activity?,
        imageUrl: String?
    ) {
        val dialog =
            Dialog(activity!!, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.setContentView(R.layout.planet_full_screen_dialog)
        val biggerPlaneImage =
            dialog.findViewById<ImageView>(R.id.biggerPlaneImage)
        Glide.with(activity)
            .load(imageUrl)
            .into(biggerPlaneImage)
        val buttonCancel =
            dialog.findViewById<Button>(R.id.buttonCancel)
        buttonCancel.setOnClickListener { dialog.dismiss() }
        dialog.setCancelable(false)
        dialog.show()
    }
}