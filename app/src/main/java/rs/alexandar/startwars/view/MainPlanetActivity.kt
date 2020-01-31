package rs.alexandar.startwars.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import rs.alexandar.startwars.R
import rs.alexandar.startwars.dagger.ViewModelFactory
import rs.alexandar.startwars.data.PlanetData
import rs.alexandar.startwars.databinding.ActivityMainPlanetsBinding
import rs.alexandar.startwars.model.PlanetLikeRequest
import rs.alexandar.startwars.util.AppDialogManager
import rs.alexandar.startwars.util.Constants
import rs.alexandar.startwars.util.DeviceDataManager
import rs.alexandar.startwars.util.StarWarsPreferencesManager
import rs.alexandar.startwars.viewmodel.MainPlanetActivityViewModel
import javax.inject.Inject

class MainPlanetActivity : BasicActivity(), View.OnClickListener, HasActivityInjector {
    @kotlin.jvm.JvmField
    @Inject
    var viewModelFactory: ViewModelFactory? = null
    private var mainPlanetActivityViewModel: MainPlanetActivityViewModel? = null
    private var activityMainPlanetsBinding: ActivityMainPlanetsBinding? = null
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initialiseViewAndViewModel()
    }

    override fun initialiseViewAndViewModel() {
        activityMainPlanetsBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_planets)
        activityMainPlanetsBinding!!.planetThumbnail.setOnClickListener(this@MainPlanetActivity)
        activityMainPlanetsBinding!!.residentListButton.setOnClickListener(this)
        activityMainPlanetsBinding!!.likeImage.setOnClickListener(this)
        mainPlanetActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainPlanetActivityViewModel::class.java)
        getPlanetData()
    }

    private fun getPlanetData() {
        mainPlanetActivityViewModel!!.planets?.observe(this, Observer { planet -> activityMainPlanetsBinding!!.planet = planet
                imageUrl = planet?.image_url
            })
    }

    override fun onClick(view: View) {
        if (view.id == R.id.planetThumbnail) {
            AppDialogManager.showFullScreenPlanetImageDialog(this@MainPlanetActivity, imageUrl)
        } else if (view.id == R.id.likeImage) {
            if (USER_LIKED) {
                Toast.makeText(
                    applicationContext,
                    R.string.you_already_liked_this,
                    Toast.LENGTH_LONG
                ).show()
                return
            }
            mainPlanetActivityViewModel!!.getUser(DeviceDataManager.getDeviceId(this@MainPlanetActivity))
                ?.observe(
                    this@MainPlanetActivity,
                    Observer { user ->
                        if (user == null) {
                            val planetLikeRequest = PlanetLikeRequest()
                            // This is hard coded because getPlanet API does not contain this information
                            planetLikeRequest.planet_id = "10"
                            mainPlanetActivityViewModel!!.createUser(
                                DeviceDataManager.getDeviceId(this@MainPlanetActivity), true, planetLikeRequest
                            )
                            PlanetData.planet?.likes?.let { Integer.valueOf(it) }?.plus(1)?.let {
                                StarWarsPreferencesManager.getInstance(this@MainPlanetActivity)
                                    ?.saveData(
                                        Constants.LIKES_NUMBER_KEY,
                                        it
                                    )
                            }
                            USER_LIKED = true
                            return@Observer
                        } else {
                            USER_LIKED = true
                            Toast.makeText(applicationContext, R.string.you_already_liked_this, Toast.LENGTH_LONG).show()
                        }
                    })
        } else {
            startActivity(Intent(this@MainPlanetActivity, ResidentActivity::class.java))
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        mainPlanetActivityViewModel!!.clear()
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return null
    }

    companion object {
        private var USER_LIKED = false
    }
}