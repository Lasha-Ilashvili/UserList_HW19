package com.example.userlist_hw19.presentation.user_page.fragment

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.userlist_hw19.data.user_page.resources.UserPageState
import com.example.userlist_hw19.databinding.FragmentUserPageBinding
import com.example.userlist_hw19.presentation.base.BaseFragment
import com.example.userlist_hw19.presentation.user_page.event.UserPageEvent
import com.example.userlist_hw19.presentation.user_page.view_model.UserPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserPageFragment : BaseFragment<FragmentUserPageBinding>(FragmentUserPageBinding::inflate) {

    private val userPageViewModel: UserPageViewModel by viewModels()

    private val navArgs: UserPageFragmentArgs by navArgs()


    override fun setUp() {
        userPageViewModel.onEvent(UserPageEvent.SetUpUserPage(navArgs.id))
    }


    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userPageViewModel.userPageState.collect { userPageState ->
                    when (userPageState) {
                        is UserPageState.Success -> {
                            with(binding) {
                                tvEmail.text = userPageState.user.email
                                tvFirstname.text = userPageState.user.firstName
                                tvLastname.text = userPageState.user.lastName

                                Glide.with(requireContext())
                                    .load(userPageState.user.avatar)
                                    .into(ivAvatar)
                            }
                        }

                        is UserPageState.Error -> {
                            Toast.makeText(
                                requireContext(),
                                userPageState.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is UserPageState.Loading -> {
                            binding.pbUserPage.visibility =
                                if (userPageState.isLoading)
                                    View.VISIBLE
                                else
                                    View.GONE
                        }

                        is UserPageState.OnClick -> {
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }
}