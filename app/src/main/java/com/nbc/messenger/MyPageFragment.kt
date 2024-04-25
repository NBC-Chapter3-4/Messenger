package com.nbc.messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.nbc.messenger.data.DataSource
import com.nbc.messenger.databinding.FragmentMyPageBinding
import com.nbc.messenger.model.ProfileImage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // 1. binding : 둘 중 뭐가 다르고 좋은거지 모르겠다.
//    private val binding by lazy { FragmentMyPageBinding.inflate(layoutInflater) }
    private var _binding: FragmentMyPageBinding? = null //
    private val binding get() = _binding!!

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
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2. 데이터 연결하여 뷰에 표시하는 곳, 아래 바인딩은 메인이랑 연결되면 지워질듯?
        val my = DataSource.getMyData()
        when (my.profileImage) {
            ProfileImage.DefaultImage -> binding.ivMyProfile.setImageResource(R.drawable.ic_profile_default)
            // 프로필 이미지를 지정하지 않은 경우(개발자)
            is ProfileImage.ResourceImage -> binding.ivMyProfile.setImageResource(my.profileImage.id)
            // id값으로 custom(사용자)
        }
        binding.tvName.text = my.nickname
        binding.tvUserContent.text = my.name
        binding.tvPhoneContent.text = my.phoneNumber
        binding.tvEmailContent.text = my.email

        // 2. 커스텀 다이어 로그
        // Activity에서 할때는 onCreate()에서 하고 Fragment에서 구현할때는 onViewCreated에서 해야한다.
        binding.linearClicked.setOnClickListener {

//            val builder = AlertDialog.Builder(this)
            // Fragment에서는 requireActivity()를 사용하여 Activity의 Context를 가져옴
//            val builder = AlertDialog.Builder(requireActivity())

            // dialogFragment로 구현한 것이 아니라 이 부분이 꼭 필요하다.
            val dialogView = layoutInflater.inflate(R.layout.my_page_dialog, null)
//            builder.setView(v1)

            val alertDialog = AlertDialog.Builder(requireActivity())
                .setView(dialogView)
                .setTitle("회원 정보 수정")
                .setIcon(R.drawable.ic_brand_logo)
                .setPositiveButton("확인", null)
                .setNegativeButton("취소", null)
                .create() // AlertDialog를 생성
            alertDialog?.window?.setBackgroundDrawableResource(R.drawable.add_contact_dialog_background)
//            alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            // 위는 전에 만들던 border 처리 관련 코드인데, 민준님의 dialog border 쓰게되어서 필요가 없어짐
            alertDialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            // dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE) 이코드를 삽입 하지 않아도 모서리는 둥글게 나오지만,
            // android version 4.4 이하에서는 blue line이 다이얼로그 상단에 나타난다고 하니 꼭 넣어주자.
            // drawable은 원래 여기에 없던 코드인데, lister 시작 전에 미리 세팅해줘야하기 떄문에 여기에 두어야 한다.
            // border 설정을 위해 필요한건데, onCreate도 안되고 여기서 구현되는 것 같다. Fragment 한정인듯함
            alertDialog.show() // AlertDialog를 보여줌

            // 리스너는 아이템클릭 누르기 전까지는 실행이 안된다
            // edittext와 확인 및 취소 버튼 클릭 리스너 설정
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                // 변수에 dialog layout에 있는 edittext의 id를 주어야 하는데, fragment textview의 id를 주고 있어서 수정했다.
                val userContent = dialogView.findViewById<EditText>(R.id.edit_name)
                val phoneContent = dialogView.findViewById<EditText>(R.id.edit_phone)
                val emailContent = dialogView.findViewById<EditText>(R.id.edit_email)

                // 사용자가 입력한 값을 Fragment 내의 View에 바인딩
                binding.tvUserContent.text = userContent.text
                binding.tvPhoneContent.text = phoneContent.text
                binding.tvEmailContent.text = emailContent.text

                alertDialog.dismiss() // AlertDialog를 닫음
            }

            // 취소 버튼 클릭 리스너 설정
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
                alertDialog.dismiss() // AlertDialog를 닫음
            }
        }

        // 3. 메인 액티비티에 연결되었을때 참고할 코드
        // [1] Activity -> FirstFragment
        // [2] FirstFragment -> SecondFragment
        // 2-2. 두 방법 모두 아래 코드로 구현 가능하다. [끝]
//        binding.tvFrag2Text.text = param1
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

    override fun onDestroyView() {
        super.onDestroyView()
        // Binding 객체 해제
        _binding = null
    }
}