
package com.nbc.messenger

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.nbc.messenger.data.DataSource
import com.nbc.messenger.databinding.FragmentMyPageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

//private lateinit var callback: OnBackPressedCallback

/**
 * A simple [Fragment] subclass.
 * Use the [MyPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
//    private val binding by lazy { FragmentMyPageBinding.inflate(layoutInflater) }
    private var _binding: FragmentMyPageBinding? = null //
    private val binding get() = _binding!!

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                // 뒤로가기 클릭시 동작하는 로직
//                requireActivity().supportFragmentManager.beginTransaction()
//                    .setCustomAnimations(0, androidx.appcompat.R.anim.abc_fade_in)
//                    .remove(this@MyPageFragment)
//                    .commit()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return binding.root
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.tabMyPage.text = param1
        val my = DataSource.getMyData()
//        binding.ivMyProfile.setImageResource(my.profileImage)
        binding.tvName.text = my.name
        binding.tvUserContent.text = my.nickname
        binding.tvPhoneContent.text = my.phoneNumber
        binding.tvEmailContent.text = my.email
//        binding.tvGroupContent.getList
//        requireActivity().onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                // 뒤로 가기 시 실행되는 코드
//                requireActivity().supportFragmentManager.beginTransaction().remove(this@MyPageFragment).commit()
//            }
//        })
//    }

        // [2] FirstFragment -> SecondFragment
        // 2-1.replaec로 fragment2로 교체하며, SecondFragment로 가보자.
//        binding.tabMyPage.setOnClickListener{
////            val dataToSend = "Hello Fragment2! \n From Fragment1"
////            val fragment2 = MyPageFragment.newInstance(dataToSend)
//            val fragment2 = MyPageFragment
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.frameLayout, fragment2)
//                .addToBackStack(null)
//                .commit()
//        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            MyPageFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
        fun newInstance(param1: String) =
        // [1] Activity -> FirstFragment
        // [2] FirstFragment -> SecondFragment
            // 두 방법 모두 아래 코드로 구현 가능하다.
            MyPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}