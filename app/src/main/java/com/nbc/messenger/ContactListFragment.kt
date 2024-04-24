package com.nbc.messenger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
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

    private var isGrid = false
    private var isLike = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        val recyclerView = binding.recyclerView
        recyclerView.addItemDecoration(decoration)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = MyAdapter(DataSource.getUsers(), isGrid) { position ->

            val user = MemoryStorage.users[position]
            user.isLike = !user.isLike // isLike 토글
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
        })

        // 연락처 추가 클릭 리스너
//        binding.fabMain.setOnClickListener {
//            AddContactDialog().show(childFragmentManager, AddContactDialog.TAG)
//        }

        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

    }

//    private fun onPressedBackButton(){
//        onBackPressedDispatcher.addCallback(this, callback)
//    }

    private fun showDialog(){}
}