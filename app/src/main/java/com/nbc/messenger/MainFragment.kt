package com.nbc.messenger

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nbc.messenger.data.DataSource
import com.nbc.messenger.data.MemoryStorage
import com.nbc.messenger.databinding.FragmentMainBinding
//import com.nbc.messenger.databinding.FragmentMainBinding
import com.nbc.messenger.model.User
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

val channelId = "one-channel"
val channelName = "My Channel One"
private val NOTIFICATION_ID = 1000
const val ACTION_NOTIFICATION_CLICKED = "action_notification_clicked"

//var intentItem: Intent? = null

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var isGrid = false
    private var isLike = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
//        if (intentItem != null) {
//            handleNotificationClick(intentItem)
//            Toast.makeText(context,"@@@@",Toast.LENGTH_LONG).show()
//        }
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        binding.ivTypeChange.setOnClickListener {
            isGrid = !isGrid
            setUpRecyclerView()
        }

        // 알림 클릭을 위한 브로드캐스트 리시버 등록
        val filter = IntentFilter(ACTION_NOTIFICATION_CLICKED)
        requireActivity().registerReceiver(
            notificationClickReceiver, filter,
            Context.RECEIVER_NOT_EXPORTED
        )


    }


    private fun setUpRecyclerView() {
        if (!isGrid) {
            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            val recyclerView = binding.recyclerView
            recyclerView.addItemDecoration(decoration)
            recyclerView.layoutManager = LinearLayoutManager(context)
            val adapter = MyAdapter(MemoryStorage.users, isGrid) { position ->
                // 클릭 리스너 처리
                val user = MemoryStorage.users[position]
                user.isLike = !user.isLike // isLike 토글
                Toast.makeText(
                    context,
                    "${MemoryStorage.users[position].isLike} ss",
                    Toast.LENGTH_SHORT
                ).show()

                recyclerView.adapter?.notifyItemChanged(position) // UI 업데이트
            }

            adapter.setItemClickListener(object : MyAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val user = MemoryStorage.users[position]
                    val detailFragment = DetailFragment.newInstance(user)  // 매개변수 추가
                    val transaction =
                        requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.main, detailFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onItemLongClick(position: Int) {
                    val temp = adapter.getItem(position).name

                    val item = DataSource.searchByName(temp)!!
                    showNumberSelectionDialog(context!!) { number ->
                        createNotificationChannel(item, number)
                        // 선택한 숫자에 대한 추가 작업 수행
                        item.isChecked = false // 알림을 보냈으므로 isChecked를 false로 변경
                        Toast.makeText(
                            context,
                            "${item.name} // ${item.isChecked}",
                            Toast.LENGTH_LONG
                        ).show()
                        adapter.notifyDataSetChanged()
                    }

                }
            })


            recyclerView.adapter = adapter
        } else {
            val likedList = DataSource.getUsers().filter { it.isLike }
            likedList?.let { users ->
                val recyclerView = binding.recyclerView
                val adapter = MyAdapter(users, isGrid) { position ->
                    val user = DataSource.getUsers()[position]

                    user.isLike = !user.isLike // isLike 토글
                    recyclerView.adapter?.notifyItemChanged(position) // UI 업데이트
                }
                binding.recyclerView.adapter = adapter
                val layoutManager = GridLayoutManager(requireContext(), 3)
                binding.recyclerView.layoutManager = layoutManager

                adapter.setItemClickListener(object : MyAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        val user = MemoryStorage.users[position]
                        val detailFragment = DetailFragment.newInstance(user)  // 매개변수 추가
                        val transaction =
                            requireActivity().supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.main, detailFragment)
                        transaction.addToBackStack(null)
                        transaction.commit()
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    override fun onItemLongClick(position: Int) {
                        val temp = adapter.getItem(position).name

                        val item = DataSource.searchByName(temp)!!
                        showNumberSelectionDialog(context!!) { number ->
                            createNotificationChannel(item, number)
                            // 선택한 숫자에 대한 추가 작업 수행
                            item.isChecked = false // 알림을 보냈으므로 isChecked를 false로 변경
                            Toast.makeText(
                                context,
                                "${item.name} // ${item.isChecked}",
                                Toast.LENGTH_LONG
                            ).show()
                            adapter.notifyDataSetChanged()
                        }

                    }
                })
            }
        }


    }

    fun showNumberSelectionDialog(context: Context, onNumberSelected: (Int) -> Unit) {
        val numbers = arrayOf("1", "2", "3")

        AlertDialog.Builder(context)
            .setTitle("몇 분 뒤에 문자를 보낼까요?")
            .setItems(numbers) { _, which ->
                val selectedNumber = numbers[which].toInt()
                onNumberSelected(selectedNumber)
            }
            .show()

    }


    @SuppressLint("NotifyDataSetChanged")
    private fun handleNotificationClick(item: User) {


        val temp = DataSource.searchByName(item.name)
        // 아이템이 null이 아니면 isChecked 값을 변경
        temp?.isChecked = true // 예시: isChecked 값을 true로 변경

        Toast.makeText(context, "dididididdi ${temp?.isChecked}", Toast.LENGTH_LONG).show()

        // 여기에서 해당 아이템을 찾아서 isChecked 값을 변경하는 로직을 구현
        // 예를 들어, MemoryStorage.users에서 해당 아이템을 찾고 isChecked 값을 변경

        // 이후 UI 업데이트 등 필요한 작업 수행
        // 예를 들어, RecyclerView의 어댑터에게 변경 사항을 알려 UI를 업데이트
        binding.recyclerView.adapter?.notifyDataSetChanged()

    }

    // 알림 클릭을 처리하는 함수
    private fun handleNotification(intent: Intent?) {
        // 알림을 클릭했을 때 실행되는 동작을 처리
        intent?.let {
            val item = it.getParcelableExtra<User>("item")
            Toast.makeText(requireContext(),"${item?.isChecked} handleNotification 1",Toast.LENGTH_LONG).show()
            item?.let {
                handleNotificationClick(it)
            }
        }
    }
    override fun onStart() {
        super.onStart()
        Toast.makeText(context,"onstart",Toast.LENGTH_LONG).show()
        // 알림 클릭을 처리
        handleNotification(arguments?.getParcelable("item"))
    }


    // 액티비티가 활성화되는 시점에 채널을 생성하는 함수
    private fun createNotificationChannel(item: User, delayMinutes: Int) {

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, delayMinutes)


        val manager =
            requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder

        //채널 만드는 게 8.0 이상 부터니까 버전체크
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 26 버전 이상

            // 추가한 코드... 사용자 권한 요청 부분. 설정으로 안내함
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (!NotificationManagerCompat.from(requireContext()).areNotificationsEnabled()) {
                    // 알림 권한이 없다면, 사용자에게 권한 요청
                    // Setting 는 android.provider 로 선택하기
                    val intent22 = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                        putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
                    }
                    startActivity(intent22)
                }
            }

            //채널을 하나 만든다. NotificationChannel로.
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                // 채널에 다양한 정보 설정
                description = "My Channel One Description"

                // 빨간색으로 1, 2 뜨는거...?
                setShowBadge(true)

                //알람 울리게. 링톤
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

                //오디오 들어가 있는거... 이건 기본 소리니까 바꾸고 싶으면 mp3 다운받아 바꾸기
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()

                //사운드에 오디오 넣기
                setSound(uri, audioAttributes)

                //진동 넣을건지
                enableVibration(true)
            }
            // 채널을 manager를 통해 NotificationManager에 등록
            manager.createNotificationChannel(channel)

            // 채널을 이용하여 builder 생성, builder 통해 채널 아이디 넣어줌
            builder = NotificationCompat.Builder(requireContext(), channelId)

        } else {
            // 26 버전(8.0) 이하라면
            builder = NotificationCompat.Builder(requireContext())
        }


        // 알림 클릭 시 MainActivity로 돌아가기 위한 Intent 설정
        var intentItem = Intent(context, MainActivity::class.java).apply {
//            action = ACTION_NOTIFICATION_CLICKED
            putExtra("item", item)
        }
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                intentItem,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )


// 알림의 기본 정보... 빌더의 옵션들
        builder.run {
            setSmallIcon(R.drawable.ic_profile_default)
            //알람 발생 시각 : 현재 시각
            setWhen(calendar.timeInMillis)
            setContentTitle("${item.name} 에게서 문자 수신")
            setContentText("카페 가는중!!")
            //setPriority(NotificationCompat.PRIORITY_DEFAULT)
            setAutoCancel(true)
            setContentIntent(pendingIntent) // 알림 클릭 시 PendingIntent 설정

            //addAction(R.mipmap.ic_launcher, "Action", pendingIntent)
        }


//알림 실행... permission 추가하기!
        val notificationManager =
            requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(11, builder.build())

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        const val ACTION_NOTIFICATION_CLICKED = "your.package.name.ACTION_NOTIFICATION_CLICKED"
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // 프래그먼트가 더 이상 보이지 않을 때 브로드캐스트 리시버 등록 해제
        requireActivity().unregisterReceiver(notificationClickReceiver)
    }

    // 알림 클릭을 처리하는 브로드캐스트 리시버
    private val notificationClickReceiver = object : BroadcastReceiver() {

        @SuppressLint("NotifyDataSetChanged")
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(requireContext(), "come?", Toast.LENGTH_LONG).show()
            intent?.let {
                if (it.action == ACTION_NOTIFICATION_CLICKED) {
                    val temp = it.getParcelableExtra<User>("item")
                    val item = DataSource.searchByName(temp!!.name)
                    item?.let { user ->
                        // 아이템이 null이 아니라면 isChecked 값을 변경
                        user.isChecked = true // 예시: isChecked 값을 true로 변경
                        Toast.makeText(
                            requireContext(),
                            "${user.isChecked} 바뀌었나?",
                            Toast.LENGTH_LONG
                        ).show()

                        // 여기에서 해당 아이템을 찾아서 isChecked 값을 변경하는 로직을 구현
                        // 예를 들어, MemoryStorage.users에서 해당 아이템을 찾고 isChecked 값을 변경

                        // 이후 UI 업데이트 등 필요한 작업 수행
                        // 예를 들어, RecyclerView의 어댑터에게 변경 사항을 알려 UI를 업데이트
                        binding.recyclerView.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }

}
