package id.bts.devin.alfagift_test.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import id.bts.devin.alfagift_test.R
import id.bts.devin.alfagift_test.databinding.ActivityMainBinding
import id.bts.devin.alfagift_test.presentation.ui.details.DetailFragmentArgs

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavController()
        setupToolbar()
    }

    private fun setupNavController() {
        val navHostFragment: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.toolbarMain, navController)
    }

    private fun setupToolbar() {
        binding.toolbarMain.apply {
            navController.addOnDestinationChangedListener { _, destination, arguments ->
                when(destination.id) {
                    R.id.homeFragment -> setupHomeToolbar()
                    R.id.detailFragment -> setupDetailToolbar(arguments)
                }
            }
        }
    }

    private fun setupHomeToolbar() {
        binding.toolbarMain.apply {
            title = "What do you want to watch?"
        }
    }

    private fun setupDetailToolbar(arguments: Bundle?) {
        if (arguments == null) return
        val args = DetailFragmentArgs.fromBundle(arguments)
        binding.toolbarMain.apply {
            title = args.movieName
            setNavigationIcon(R.drawable.ic_nav_back)
        }
    }
}