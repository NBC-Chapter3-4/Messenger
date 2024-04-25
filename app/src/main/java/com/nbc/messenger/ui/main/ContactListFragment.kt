package com.nbc.messenger.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nbc.messenger.R
import com.nbc.messenger.data.DataSource
import com.nbc.messenger.databinding.FragmentContactListBinding
import com.nbc.messenger.ui.add_contact.AddContactDialog
import com.nbc.messenger.ui.detail.DetailFragment

class ContactListFragment : Fragment() {

    private var isGrid = false
    private var isLike = false

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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
            val adapter = MyAdapter(isGrid) { position ->
                // 클릭 리스너 처리
                val user = DataSource.getUsers()[position]
                user.isLike = !user.isLike // isLike 토글
                Toast.makeText(
                    context,
                    "${DataSource.getUsers()[position].isLike} ss",
                    Toast.LENGTH_SHORT
                ).show()

                recyclerView.adapter?.notifyItemChanged(position) // UI 업데이트
            }

            adapter.setItemClickListener(
                object : MyAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        val user = DataSource.getUsers()[position]
                        val detailFragment = DetailFragment.newInstance(user)  // 매개변수 추가
                        val transaction =
                            requireActivity().supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.main, detailFragment)
                        transaction.addToBackStack(null)
                        transaction.commit()
                    }
                }
            )

            recyclerView.adapter = adapter

            binding.fabMain.setOnClickListener {
                AddContactDialog {
                    DataSource.addUser(it)
                    adapter.notifyItemChanged(DataSource.getUsers().lastIndex)
                }
                    .show(childFragmentManager, AddContactDialog.TAG)
            }

        } else {
            val likedList = DataSource.getUsers().filter { it.isLike }
            likedList.let { users ->
                val recyclerView = binding.recyclerView
                val adapter = MyAdapter(isGrid) { position ->
                    val user = DataSource.getUsers()[position]

                    user.isLike = !user.isLike // isLike 토글
                    recyclerView.adapter?.notifyItemChanged(position) // UI 업데이트
                }
                binding.recyclerView.adapter = adapter
                val layoutManager = GridLayoutManager(requireContext(), 3)
                binding.recyclerView.layoutManager = layoutManager

                adapter.setItemClickListener(
                    object : MyAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val user = DataSource.getUsers()[position]
                            val detailFragment = DetailFragment.newInstance(user)  // 매개변수 추가
                            val transaction =
                                requireActivity().supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.main, detailFragment)
                            transaction.addToBackStack(null)
                            transaction.commit()
                        }
                    }
                )

                binding.fabMain.setOnClickListener {
                    AddContactDialog {
                        DataSource.addUser(it)
                        adapter.notifyItemChanged(DataSource.getUsers().lastIndex)
                    }
                        .show(childFragmentManager, AddContactDialog.TAG)
                }

                // 다이얼로그 클릭리스너

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
}