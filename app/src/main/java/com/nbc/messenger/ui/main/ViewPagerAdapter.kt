package com.nbc.messenger.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nbc.messenger.ui.my.MyPageFragment

class ViewPagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
    private val fragments = listOf(ContactListFragment(), MyPageFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ContactListFragment()
            else -> MyPageFragment()
        }

    }
}
