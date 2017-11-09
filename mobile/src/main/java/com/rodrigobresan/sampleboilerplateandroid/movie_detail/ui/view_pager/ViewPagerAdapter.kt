package com.rodrigobresan.sampleboilerplateandroid.movie_detail.ui.view_pager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(private val fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragmentList = mutableListOf<Fragment>()
    private val titleList = mutableListOf<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titleList[position]
    }

}