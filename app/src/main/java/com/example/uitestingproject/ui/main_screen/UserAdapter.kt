package com.example.uitestingproject.ui.main_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.uitestingproject.data.user.local.UserInListEntity
import com.example.uitestingproject.databinding.ItemUserBinding
import com.squareup.picasso.Picasso

class UserAdapter(
    diffCallback: DiffUtil.ItemCallback<UserInListEntity>,
    val onItemClick: OnItemClick,
) : PagingDataAdapter<UserInListEntity, UserAdapter.UserViewHolder>(diffCallback) {

    inner class UserViewHolder(private val itemUserBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {

        fun bind(userInList: UserInListEntity) {
            with(itemUserBinding) {
                userName.text = userInList.login
                val target = MyTarget(userAvatar, progressBar = avatarProgressBar)
                userAvatar.setImageDrawable(null)
                userAvatar.tag = target
                Picasso.get().load(userInList.imageUrl)
                    .into(target)
                root.setOnClickListener {
                    onItemClick.onClick(userInList)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    object UserComparator : DiffUtil.ItemCallback<UserInListEntity>() {
        override fun areItemsTheSame(oldItem: UserInListEntity, newItem: UserInListEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserInListEntity, newItem: UserInListEntity): Boolean {
            return oldItem == newItem
        }
    }

    fun interface OnItemClick {
        fun onClick(userInList: UserInListEntity)
    }
}