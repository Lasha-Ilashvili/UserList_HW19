package com.example.userlist_hw19.presentation.user_list.fragment

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.userlist_hw19.data.user_list.resources.UserListState
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
        userListViewModel.onEvent(UserListEvent.SetUpUserList)
    }

    override fun setRecycler() {
        adapter = UserListItemAdapter().apply {
            itemOnClick = { userListViewModel.onEvent(UserListEvent.SendClickEvent(it)) }
        }
        binding.rvUserList.adapter = adapter
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userListViewModel.userListState.collect { userListState ->
                    when (userListState) {
                        is UserListState.Success -> {
                            adapter.submitList(userListState.userList)
                        }

                        is UserListState.Error -> {
                            Toast.makeText(
                                requireContext(),
                                userListState.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is UserListState.Loading -> {
                            binding.pbUserList.visibility =
                                if (userListState.isLoading)
                                    View.VISIBLE
                                else
                                    View.GONE
                        }

                        is UserListState.OnClick -> {
                            findNavController().navigate(
                                UserListFragmentDirections.actionUserListFragmentToUserPageFragment(
                                    id = userListState.id
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}