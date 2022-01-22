package com.saveo.ui

import com.saveo.R
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import com.google.android.material.navigation.NavigationView
import com.saveo.utils.BaseActivity
import com.saveo.utils.checkNull
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : BaseActivity(), NavController.OnDestinationChangedListener,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var currentDestination: NavDestination
    private lateinit var navController: NavController
    private val hostId = R.id.mainNav
    var toggle: ActionBarDrawerToggle? = null
    private var mToolBarNavigationListenerIsRegistered: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(hostId)
        val navMainFragment = supportFragmentManager.findFragmentById(hostId) as NavHostFragment?
        navController = navMainFragment?.navController!!
        appBarConfiguration = AppBarConfiguration(navController.graph)

        initNavView()
    }

    private fun initNavView() {
        toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbarMain,
            R.string.open_drawer_description,
            R.string.close_drawer_description
        )
        setSupportActionBar(toolbarMain)
        drawer_layout.addDrawerListener(toggle!!)
        toggle?.syncState()
        side_nav_view.setNavigationItemSelectedListener(this)
    }

    private fun enableViews(enable: Boolean) {
        if (enable) {
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            // Remove hamburger
            toggle?.isDrawerIndicatorEnabled = false
            // Show back button
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            if (!mToolBarNavigationListenerIsRegistered) {
                toggle?.toolbarNavigationClickListener = View.OnClickListener { onBackPressed() }
                mToolBarNavigationListenerIsRegistered = true
            }
            toolbarMain.navigationIcon =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_back_white, null)
        } else {
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            // Remove back button
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            // Show hamburger
            toggle?.isDrawerIndicatorEnabled = true
            toggle?.toolbarNavigationClickListener = null
            mToolBarNavigationListenerIsRegistered = false
        }
    }


    override fun onStart() {
        super.onStart()
        navController.addOnDestinationChangedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!navController.navigateUp(appBarConfiguration)) {
            onBackPressed()
        }
        return navController.navigateUp(appBarConfiguration)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        currentDestination = destination
        currentDestination.label?.let { setToolbar(it) }
        when (currentDestination.id) {
            R.id.moviesFragment -> enableViews(false)
            else -> enableViews(true)
        }
    }

    private fun setToolbar(it: CharSequence) {
        supportActionBar?.apply {
            title = it.toString().checkNull()
            setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle?.onOptionsItemSelected(item)!!) {
            return true
        }
        return item.onNavDestinationSelected(findNavController(hostId)) || super.onOptionsItemSelected(
            item
        )
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else
            super.onBackPressed()
    }

}