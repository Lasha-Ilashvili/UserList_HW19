package com.example.userlist_hw19.presentation.user_list.fragment

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.userlist_hw19.data.user_list.resources.UserListResult
import com.example.userlist_hw19.databinding.FragmentUserListBinding
import com.example.userlist_hw19.presentation.base.BaseFragment
import com.example.userlist_hw19.presentation.user_list.adapter.UserListItemAdapter
import com.example.userlist_hw19.presentation.user_list.event.UserListEvent
import com.example.userlist_hw19.presentation.user_list.view_model.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : BaseFragment<FragmentUserListBinding>(FragmentUserListBinding::inflate) {

    private val userListViewModel: UserListViewModel by viewModels()

    private lateinit var adapter: UserListItemAdapter

    override fun setUp() {
        userListViewModel.onEvent(UserListEvent.SetUpUserListEvent)
    }

    override fun setListeners() {
        binding.btnRemove.setOnClickListener {
            userListViewModel.onEvent(UserListEvent.RemoveUserEvent)
        }
    }

    override fun setRecycler() {
        adapter = UserListItemAdapter().apply {
            itemOnClick = { position, isSelected ->
                userListViewModel.onEvent(UserListEvent.SendClickEvent(position, isSelected))
            }
//            itemOnLongClick = {
//                userListViewModel.onEvent(UserListEvent.SendLongClickEvent())
//            }
        }
        binding.rvUserList.adapter = adapter
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userListViewModel.userListState.collect { userListState ->
                    when (userListState) {
                        is UserListResult.Success -> {
                            adapter.submitList(userListState.userList)
                        }

                        is UserListResult.Error -> {
                            Toast.makeText(
                                requireContext(),
                                userListState.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is UserListResult.Loading -> {
                            binding.pbUserList.visibility =
                                if (userListState.isLoading)
                                    VISIBLE
                                else
                                    GONE
                        }

                        is UserListResult.OnClick -> {
                            if (userListState.count == 0) {

                                binding.btnRemove.visibility = GONE

                                findNavController().navigate(
                                    UserListFragmentDirections.actionUserListFragmentToUserPageFragment(
                                        id = adapter.currentList[userListState.position].id
                                    )
                                )
                            } else {
                                adapter.notifyItemChanged(userListState.position)
                                binding.btnRemove.visibility = VISIBLE
                            }
                        }

                        is UserListResult.OnLongClick -> {
                            adapter.notifyItemChanged(userListState.position)

                            adapter.itemSelected = userListState.count != 0
                            binding.btnRemove.visibility =
                                if (adapter.itemSelected) VISIBLE else GONE
                        }

                        is UserListResult.ItemRemoved -> {
                            adapter.itemSelected = !adapter.itemSelected
                            adapter.submitList(userListState.value)
                            binding.btnRemove.visibility = GONE
                        }
                    }
                }
            }
        }
    }
}