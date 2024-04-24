package com.nbc.messenger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.nbc.messenger.data.DataSource
import com.nbc.messenger.databinding.FragmentViewPagerBinding
import com.nbc.messenger.model.My


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ViewPagerFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)


        val myData = DataSource.getMyData()
        val viewPagerAdapter = ViewPagerAdapter(this, myData)

        binding.viewPager2.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tapMain, binding.viewPager2) { tab, position ->
            when(position) {
                0 -> {
                    tab.setIcon(R.drawable.people2)
                    tab.text = "연락처" }
                1 -> tab.setIcon(R.drawable.heart2)
            }
        }.attach()

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(myData: My) : MyPageFragment {
            val fragment = MyPageFragment()
            val args = Bundle()
            args.putParcelable("myDataKey", myData)
            fragment.arguments = args
            return fragment
        }
    }
}
