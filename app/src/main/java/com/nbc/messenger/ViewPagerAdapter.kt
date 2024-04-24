package com.nbc.messenger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nbc.messenger.data.DataSource
import com.nbc.messenger.model.My

class ViewPagerAdapter (fa: Fragment, myData: My): FragmentStateAdapter(fa){
    val fragments = listOf(ContactListFragment(), MyPageFragment())
    val myData = DataSource.getMyData()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ContactListFragment()
            else -> MyPageFragment()
        }

    }
}
