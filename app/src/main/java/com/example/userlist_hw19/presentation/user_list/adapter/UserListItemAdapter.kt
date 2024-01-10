package com.example.userlist_hw19.presentation.user_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userlist_hw19.R
import com.example.userlist_hw19.databinding.UserListItemBinding
import com.example.userlist_hw19.presentation.model.User

class UserListItemAdapter :
    ListAdapter<User, UserListItemAdapter.UserListItemViewHolder>(UserListDiffUtil) {

    object UserListDiffUtil : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    var itemOnClick: ((Int, Boolean) -> Unit)? = null

    var itemOnLongClick: ((Int, Boolean) -> Unit)? = null

    var itemSelected = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        return UserListItemViewHolder(
            UserListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind()
    }

    inner class UserListItemViewHolder(private val binding: UserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        init {
            binding.root.setOnClickListener {
                itemOnClick?.invoke(adapterPosition, currentList[adapterPosition].isSelected)
            }

            binding.root.setOnLongClickListener {
                itemOnClick?.invoke(adapterPosition, currentList[adapterPosition].isSelected)

                true
            }
        }

        fun bind() {
            with(binding) {
                val user = currentList[adapterPosition]

                tvEmail.text = user.email
                tvFirstname.text = user.firstName
                tvLastname.text = user.lastName

                Glide.with(itemView.context)
                    .load(user.avatar)
                    .into(ivAvatar)

                itemView.setBackgroundColor(
                    itemView.context.getColor(
                        if (user.isSelected)
                            R.color.light_gray
                        else
                            R.color.black
                    )
                )
            }
        }
    }
}