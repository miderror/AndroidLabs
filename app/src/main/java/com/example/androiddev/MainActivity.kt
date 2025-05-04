package com.example.androiddev

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androiddev.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG_ACTIVITY = "ACTIVITY_FRAGMENT_TAG"
        private const val TAG_PROFILE = "PROFILE_FRAGMENT_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ActivityFragment(), TAG_ACTIVITY)
                .commit()
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_activity -> switchToFragment(TAG_ACTIVITY, TAG_PROFILE)
                R.id.nav_profile -> switchToFragment(TAG_PROFILE, TAG_ACTIVITY)
                else -> throw IllegalArgumentException("Invalid itemId")
            }
            true
        }
    }

    private fun switchToFragment(tagToShow: String, tagToHide: String) {
        val fragmentToShow = supportFragmentManager.findFragmentByTag(tagToShow)
        val fragmentToHide = supportFragmentManager.findFragmentByTag(tagToHide)

        supportFragmentManager.beginTransaction().apply {
            if (fragmentToHide != null) {
                hide(fragmentToHide)
            }

            if (fragmentToShow == null) {
                val newFragment = when (tagToShow) {
                    TAG_PROFILE -> ProfileFragment.newInstance()
                    TAG_ACTIVITY -> ActivityFragment.newInstance()
                    else -> throw IllegalArgumentException("Invalid TAG")
                }
                add(R.id.fragment_container, newFragment, tagToShow)
            } else {
                show(fragmentToShow)
            }

            commit()
        }
    }
}