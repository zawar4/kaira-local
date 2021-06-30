package ai.kaira.app.home

import ai.kaira.app.R
import ai.kaira.app.SharedMainViewModel
import ai.kaira.app.databinding.ActivityMainBinding
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK) {
            data?.let { intnt ->
                sharedMainViewModel.refreshMyFinancialFragment()
            }
        }
    }

    lateinit var sharedMainViewModel : SharedMainViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        sharedMainViewModel = ViewModelProvider(this).get(SharedMainViewModel::class.java)
        val bottomNavView: BottomNavigationView = binding.navView
        bottomNavView.setOnNavigationItemReselectedListener {

        }
        bottomNavView.itemIconTintList = null
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        bottomNavView.setupWithNavController(navController)
    }
}