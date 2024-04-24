package com.nbc.messenger

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nbc.messenger.data.DataSource
import com.nbc.messenger.data.MemoryStorage
import com.nbc.messenger.databinding.FragmentContactListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactListFragment : Fragment() {

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


    }

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        binding.ivTypeChange.setOnClickListener {
            isGrid = !isGrid
            setUpRecyclerView()
        }

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
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.main, detailFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }
            )


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
                })

                // 다이얼로그 클릭리스너
//                binding.fabMain.setOnClickListener {
//                    AddContactDialog().show(childFragmentManager, AddContactdialog.TAG)
//                }

//                adapter.itemLongClick = object : MyAdapter.onItemLongClick {
//                    override fun onLongClick(view: View, position: Int): Boolean {
//                        val longClickedItem = adapter.getItem(position)
//
//                        setDialog(requireContext(), longClickedItem)
//                        return true
//                    }
//
//                }

            }


        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
            ContactListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}