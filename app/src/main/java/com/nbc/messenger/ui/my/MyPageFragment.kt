package com.nbc.messenger.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nbc.messenger.R
import com.nbc.messenger.data.DataSource
import com.nbc.messenger.databinding.FragmentMyPageBinding
import com.nbc.messenger.model.ProfileImage

class MyPageFragment : Fragment() {

    // 1. 둘 중 뭐가 다르고 좋은거지
//    private val binding by lazy { FragmentMyPageBinding.inflate(layoutInflater) }
    private var _binding: FragmentMyPageBinding? = null //
    private val binding get() = _binding!!


    // 2. back button 이벤트 처리시 해보려고 했던 것
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val my = DataSource.getMyData()
        when (my.profileImage) {
            ProfileImage.DefaultImage -> binding.ivMyProfile.setImageResource(R.drawable.ic_profile_default)
            // 프로필 이미지를 지정하지 않은 경우(개발자)
            is ProfileImage.ResourceImage -> binding.ivMyProfile.setImageResource(my.profileImage.id)
            // id값으로 custom(사용자)
        }
        binding.tvName.text = my.name
        binding.tvUserContent.text = my.nickname
        binding.tvPhoneContent.text = my.phoneNumber
        binding.tvEmailContent.text = my.email

        // 2-2. 이것도 bactbutton 구현 시도했던 부분
//        requireActivity().onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                // 뒤로 가기 시 실행되는 코드
//                requireActivity().supportFragmentManager.beginTransaction().remove(this@MyPageFragment).commit()
//            }
//        })
//    }

        // 3. 메인 액티비티에 연결되었을때 참고할 코드
        // [1] Activity -> FirstFragment
        // [2] FirstFragment -> SecondFragment
        // 2-2. 두 방법 모두 아래 코드로 구현 가능하다. [끝]
//        binding.tvFrag2Text.text = param1
    }
}