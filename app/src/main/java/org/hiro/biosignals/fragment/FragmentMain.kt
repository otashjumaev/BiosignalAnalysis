package org.hiro.biosignals.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.hiro.biosignals.R
import org.hiro.biosignals.databinding.FragmentMainBinding

class FragmentMain : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val pages = arrayOf<Fragment>(
        HomeFragment(),
        BarChartFragment()
    )
    private var activePage = -1
    private var path: String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        path = arguments?.getString("FILE_PATH")
        initBottomNavBar()
        closeKeyboard()
        chooseFragment(0, arguments)
    }

    private fun initBottomNavBar() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            val b = Bundle()
            b.putString("FILE_PATH", path)
            when (it.itemId) {
                R.id.homePage -> {
                    chooseFragment(0, b)
                }
                R.id.barChart -> {
                    chooseFragment(1, b)
                }
            }
            true
        }
    }

    private fun chooseFragment(i: Int, bundle: Bundle? = null) {
        if (activePage != i) {
            activePage = i
            bundle?.let {
                pages[i].arguments = bundle
            }
            childFragmentManager
                .beginTransaction()
                .replace(R.id.container, pages[i])
                .commit()
        }
    }


}