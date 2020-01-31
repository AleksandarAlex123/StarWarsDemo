package rs.alexandar.startwars.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BasicActivity : AppCompatActivity() {
    /*   This Activity we will use of course if we want expand our project
         For now it doesen't have too much methods
   */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected abstract fun initialiseViewAndViewModel()
}