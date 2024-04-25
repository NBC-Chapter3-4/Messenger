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
import com.nbc.messenger.createNotificationChannel
import com.nbc.messenger.data.DataSource
import com.nbc.messenger.databinding.FragmentContactListBinding
import com.nbc.messenger.model.User
import com.nbc.messenger.showNumberSelectionDialog
import com.nbc.messenger.ui.add_contact.AddContactDialog
import com.nbc.messenger.ui.detail.DetailFragment

class ContactListFragment : Fragment() {

    private var isGrid = false
    private var isLike = false

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    private val adapter = MyAdapter { position ->
        // 클릭 리스너 처리
        val user = DataSource.getUsers()[position]
        user.isLike = !user.isLike // isLike 토글
        Toast.makeText(
            context,
            "${DataSource.getUsers()[position].isLike} ss",
            Toast.LENGTH_SHORT
        ).show()

        binding.recyclerView.adapter?.notifyItemChanged(position) // UI 업데이트
    }

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

        binding.fabMain.setOnClickListener { clickFAB() }
    }

    private fun clickFAB() {
        AddContactDialog {
            DataSource.addUser(it)
            adapter.notifyItemChanged(DataSource.getUsers().lastIndex)
        }
            .show(childFragmentManager, AddContactDialog.TAG)
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.adapter = adapter

        adapter.registerItemClickListener(
            object : MainRecyclerViewClickListener {
                override fun onItemClick(user: User) {
                    val detailFragment = DetailFragment.newInstance(user)  // 매개변수 추가
                    val transaction =
                        requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.main, detailFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }

                override fun onItemLongClick(user: User) {
                    showNumberSelectionDialog(context!!) { number ->
                        context?.createNotificationChannel(user, number)
                        adapter.updateItems { last ->
                            val index = last.indexOf(user)
                            DataSource.updateIsChecked(last[index], false)
                            last.apply {
                                get(index).isChecked = false
                            }
                        }
                    }
                }
            }
        )

        if (!isGrid) {
            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            binding.recyclerView.addItemDecoration(decoration)

            binding.recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            adapter.changeLayout(MyRecyclerViewLayout.LINEAR)
            adapter.updateItems(DataSource.getUsers())
        } else {
            val layoutManager = GridLayoutManager(requireContext(), 3)
            binding.recyclerView.layoutManager = layoutManager
            adapter.changeLayout(MyRecyclerViewLayout.GRID)

            val likedList = DataSource.getUsers().filter { it.isLike }.ifEmpty { null }
            likedList?.let { users ->
                adapter.updateItems(users)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.unregisterItemClickListener()
        _binding = null
    }
}