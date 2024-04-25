//package com.nbc.messenger
//
//import android.annotation.SuppressLint
//import android.app.Dialog
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Context.NOTIFICATION_SERVICE
//import android.content.Intent
//import android.content.IntentFilter
//import android.graphics.BitmapFactory
//import android.media.AudioAttributes
//import android.media.RingtoneManager
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.provider.ContactsContract.Data
//import android.provider.Settings
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.annotation.RequiresApi
//import androidx.appcompat.app.AlertDialog
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import androidx.fragment.app.DialogFragment
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.DividerItemDecoration
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.nbc.messenger.data.DataSource
//import com.nbc.messenger.data.MemoryStorage
//import com.nbc.messenger.databinding.FragmentMainBinding
////import com.nbc.messenger.databinding.FragmentMainBinding
//import com.nbc.messenger.model.User
//import com.nbc.messenger.ui.detail.DetailFragment
//import com.nbc.messenger.ui.main.MyAdapter
//import java.util.Calendar
//
//val channelId = "one-channel"
//val channelName = "My Channel One"
//private val NOTIFICATION_ID = 1000
//const val ACTION_NOTIFICATION_CLICKED = "action_notification_clicked"
//
//class MainFragment : Fragment() {
//
//    private var isGrid = false
//    private var isLike = false
//
//    private var _binding: FragmentMainBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        // Inflate the layout for this fragment
//        _binding = FragmentMainBinding.inflate(inflater, container, false)
//        return binding.root
//
//    }
//
//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    @SuppressLint("NotifyDataSetChanged")
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        setUpRecyclerView()
//
//        binding.ivTypeChange.setOnClickListener {
//            isGrid = !isGrid
//            setUpRecyclerView()
//        }
//
//        // 알림 클릭을 위한 브로드캐스트 리시버 등록
//        val filter = IntentFilter(ACTION_NOTIFICATION_CLICKED)
//        requireActivity().registerReceiver(
//            notificationClickReceiver, filter,
//            Context.RECEIVER_NOT_EXPORTED
//        )
//
//
//    }
//
//    private val adapter = MyAdapter { position ->
//        // 클릭 리스너 처리
//        val user = MemoryStorage.users[position]
//        user.isLike = !user.isLike // isLike 토글
//        Toast.makeText(
//            context,
//            "${MemoryStorage.users[position].isLike} ss",
//            Toast.LENGTH_SHORT
//        ).show()
//
//        recyclerView.adapter?.notifyItemChanged(position) // UI 업데이트
//    }
//
//
//    private fun setUpRecyclerView() {
//        if (!isGrid) {
//            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
//            val recyclerView = binding.recyclerView
//            recyclerView.addItemDecoration(decoration)
//            recyclerView.layoutManager = LinearLayoutManager(context)
//
//            adapter.setItemClickListener(object : MyAdapter.onItemClickListener {
//                override fun onItemClick(position: Int) {
//                    val user = MemoryStorage.users[position]
//                    val detailFragment = DetailFragment.newInstance(user)  // 매개변수 추가
//                    val transaction =
//                        requireActivity().supportFragmentManager.beginTransaction()
//                    transaction.replace(R.id.main, detailFragment)
//                    transaction.addToBackStack(null)
//                    transaction.commit()
//                }
//
//                @SuppressLint("NotifyDataSetChanged")
//                override fun onItemLongClick(position: Int) {
//                    val temp = adapter.getItem(position).name
//
//                    val item = DataSource.searchByName(temp)!!
//                    showNumberSelectionDialog(context!!) { number ->
//                        context?.createNotificationChannel(item, number)
//                        // 선택한 숫자에 대한 추가 작업 수행
//                        item.isChecked = false // 알림을 보냈으므로 isChecked를 false로 변경
//                        Toast.makeText(
//                            context,
//                            "${item.name} // ${item.isChecked}",
//                            Toast.LENGTH_LONG
//                        ).show()
//                        adapter.notifyDataSetChanged()
//                    }
//
//                }
//            })
//
//
//            recyclerView.adapter = adapter
//        } else {
//            val likedList = DataSource.getUsers().filter { it.isLike }
//            likedList?.let { users ->
//                val recyclerView = binding.recyclerView
//                val adapter = MyAdapter(users, isGrid) { position ->
//                    val user = DataSource.getUsers()[position]
//
//                    user.isLike = !user.isLike // isLike 토글
//                    recyclerView.adapter?.notifyItemChanged(position) // UI 업데이트
//                }
//                binding.recyclerView.adapter = adapter
//                val layoutManager = GridLayoutManager(requireContext(), 3)
//                binding.recyclerView.layoutManager = layoutManager
//
//                adapter.setItemClickListener(object : MyAdapter.onItemClickListener {
//                    override fun onItemClick(position: Int) {
//                        val user = MemoryStorage.users[position]
//                        val detailFragment = DetailFragment.newInstance(user)  // 매개변수 추가
//                        val transaction =
//                            requireActivity().supportFragmentManager.beginTransaction()
//                        transaction.replace(R.id.main, detailFragment)
//                        transaction.addToBackStack(null)
//                        transaction.commit()
//                    }
//
//                    @SuppressLint("NotifyDataSetChanged")
//                    override fun onItemLongClick(position: Int) {
//                        val temp = adapter.getItem(position).name
//
//                        val item = DataSource.searchByName(temp)!!
//                        showNumberSelectionDialog(context!!) { number ->
//                            context?.createNotificationChannel(item, number)
//                            // 선택한 숫자에 대한 추가 작업 수행
//                            item.isChecked = false // 알림을 보냈으므로 isChecked를 false로 변경
//                            Toast.makeText(
//                                context,
//                                "${item.name} // ${item.isChecked}",
//                                Toast.LENGTH_LONG
//                            ).show()
//                            adapter.notifyDataSetChanged()
//                        }
//
//                    }
//                })
//            }
//        }
//
//
//    }
//
//    fun showNumberSelectionDialog(context: Context, onNumberSelected: (Int) -> Unit) {
//        val numbers = arrayOf("1", "2", "3")
//
//        AlertDialog.Builder(context)
//            .setTitle("몇 분 뒤에 문자를 보낼까요?")
//            .setItems(numbers) { _, which ->
//                val selectedNumber = numbers[which].toInt()
//                onNumberSelected(selectedNumber)
//            }
//            .show()
//
//    }
//
//
//    @SuppressLint("NotifyDataSetChanged")
//    private fun handleNotificationClick(item: User) {
//
//
//        val temp = DataSource.searchByName(item.name)
//        // 아이템이 null이 아니면 isChecked 값을 변경
//        temp?.isChecked = true // 예시: isChecked 값을 true로 변경
//
//        Toast.makeText(context, "dididididdi ${temp?.isChecked}", Toast.LENGTH_LONG).show()
//
//        // 여기에서 해당 아이템을 찾아서 isChecked 값을 변경하는 로직을 구현
//        // 예를 들어, MemoryStorage.users에서 해당 아이템을 찾고 isChecked 값을 변경
//
//        // 이후 UI 업데이트 등 필요한 작업 수행
//        // 예를 들어, RecyclerView의 어댑터에게 변경 사항을 알려 UI를 업데이트
//        binding.recyclerView.adapter?.notifyDataSetChanged()
//
//    }
//
//    // 알림 클릭을 처리하는 함수
//    private fun handleNotification(intent: Intent?) {
//        // 알림을 클릭했을 때 실행되는 동작을 처리
//        intent?.let {
//            val item = it.getParcelableExtra<User>("item")
//            Toast.makeText(requireContext(),"${item?.isChecked} handleNotification 1",Toast.LENGTH_LONG).show()
//            item?.let {
//                handleNotificationClick(it)
//            }
//        }
//    }
//    override fun onStart() {
//        super.onStart()
//        Toast.makeText(context,"onstart",Toast.LENGTH_LONG).show()
//        // 알림 클릭을 처리
//        handleNotification(arguments?.getParcelable("item"))
//    }
//
//    companion object {
//        const val ACTION_NOTIFICATION_CLICKED = "your.package.name.ACTION_NOTIFICATION_CLICKED"
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//
//        // 프래그먼트가 더 이상 보이지 않을 때 브로드캐스트 리시버 등록 해제
//        requireActivity().unregisterReceiver(notificationClickReceiver)
//    }
//
//    // 알림 클릭을 처리하는 브로드캐스트 리시버
//    private val notificationClickReceiver = object : BroadcastReceiver() {
//
//        @SuppressLint("NotifyDataSetChanged")
//        override fun onReceive(context: Context?, intent: Intent?) {
//            Toast.makeText(requireContext(), "come?", Toast.LENGTH_LONG).show()
//            intent?.let {
//                if (it.action == ACTION_NOTIFICATION_CLICKED) {
//                    val temp = it.getParcelableExtra<User>("item")
//                    val item = DataSource.searchByName(temp!!.name)
//                    item?.let { user ->
//                        // 아이템이 null이 아니라면 isChecked 값을 변경
//                        user.isChecked = true // 예시: isChecked 값을 true로 변경
//                        Toast.makeText(
//                            requireContext(),
//                            "${user.isChecked} 바뀌었나?",
//                            Toast.LENGTH_LONG
//                        ).show()
//
//                        // 여기에서 해당 아이템을 찾아서 isChecked 값을 변경하는 로직을 구현
//                        // 예를 들어, MemoryStorage.users에서 해당 아이템을 찾고 isChecked 값을 변경
//
//                        // 이후 UI 업데이트 등 필요한 작업 수행
//                        // 예를 들어, RecyclerView의 어댑터에게 변경 사항을 알려 UI를 업데이트
//                        binding.recyclerView.adapter?.notifyDataSetChanged()
//                    }
//                }
//            }
//        }
//    }
//
//}
