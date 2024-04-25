package com.nbc.messenger

import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nbc.messenger.data.DataSource
import com.nbc.messenger.model.My
class ViewPagerAdapter (fa: Fragment, myData: My): FragmentStateAdapter(fa){
    val fragments = listOf(MainFragment(), TestFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MainFragment()
            else -> TestFragment()
        }

    }
}