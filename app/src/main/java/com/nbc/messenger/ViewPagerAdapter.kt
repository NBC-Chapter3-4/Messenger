package com.nbc.messenger

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

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
