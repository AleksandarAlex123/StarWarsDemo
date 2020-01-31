package rs.alexandar.startwars.view

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import rs.alexandar.startwars.R
import rs.alexandar.startwars.dagger.ViewModelFactory
import rs.alexandar.startwars.util.AppDialogManager
import rs.alexandar.startwars.util.RecyclerItemClickListener
import rs.alexandar.startwars.view.adapter.ResidentRecyclerViewAdapter
import rs.alexandar.startwars.viewmodel.ResidentActivityViewModel
import java.util.*
import javax.inject.Inject

class ResidentActivity : BasicActivity(), HasActivityInjector {
    @kotlin.jvm.JvmField
    @Inject
    var viewModelFactory: ViewModelFactory? = null
    private var residentActivityViewModel: ResidentActivityViewModel? = null
    private var residents: Array<String>? = null
    private var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resident)
        initialiseViewAndViewModel()
    }

    override fun initialiseViewAndViewModel() {
        recyclerView = findViewById(R.id.residentsRecyclerView)
        residentActivityViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(
                ResidentActivityViewModel::class.java
            )
        getResidentUrl()
    }

   private fun getResidentUrl() {
        residents = residentActivityViewModel!!.residentsUrl
        if (residents == null) {
            residentActivityViewModel!!.planets
                ?.observe(this, Observer { planet ->
                    residents = planet?.residents
                    initData()
                })
        } else {
            initData()
        }
    }

    private fun initData() {
        recyclerView!!.adapter = ResidentRecyclerViewAdapter(
            applicationContext, residents
        )
        recyclerView!!.addOnItemTouchListener(
            RecyclerItemClickListener(applicationContext, recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        residentActivityViewModel!!.getResidentData(residents!![position])!!.observe(
                            this@ResidentActivity,
                            Observer { resident ->
                                resident?.let {
                                    AppDialogManager.showResidentDetailDialog(
                                        it,
                                        this@ResidentActivity
                                    )
                                }
                            })
                    }

                    override fun onLongItemClick(view: View?, position: Int) {}
                })
        )
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return null
    }
}