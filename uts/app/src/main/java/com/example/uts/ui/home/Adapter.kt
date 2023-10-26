package com.example.uts.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.uts.R
import com.example.uts.databinding.UserListBinding
import com.example.uts.userHelper.User

class Adapter (private var users: MutableList<User>, private val fragment: Fragment) : RecyclerView.Adapter<Adapter.ListViewHolder>(){

    private var userListOriginal: MutableList<User> = users

    private var onClickListener: OnClickListener?=null

    private fun filterList(filterList: MutableList<User>){
        users = filterList
        notifyDataSetChanged()
    }

    fun filter(text: String){
        val filteredList = mutableListOf<User>()
        val emptyList = mutableListOf<User>()

        for (item in userListOriginal){
            val stringName = "${item.firstName} ${item.lastName}"
            if (stringName.contains(text, ignoreCase = true)){
                filteredList.add(item)
            }
        }
        if (filteredList.isNotEmpty()){
            filterList(filteredList)
        }else{
            filterList(emptyList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            UserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun addUser(newUsers: User) {
        users.add(newUsers)
        notifyItemInserted(users.lastIndex)
    }

    private fun clear() {
        users.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = users[position]

        Glide.with(holder.itemView.context).load(user.image).apply(RequestOptions().override(64,64)).into(holder.tvImage)

        holder.tvName.text = fragment.getString(R.string.user_name, user.firstName, user.lastName)
        holder.tvEmail.text=user.email

        holder.itemView.setOnClickListener {
            if (onClickListener!=null){
                onClickListener!!.onClick(position, user)
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    interface OnClickListener{
        fun onClick(position: Int, model: User)
    }

    inner class ListViewHolder(binding: UserListBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvName
        val tvImage = binding.tvImage
        val tvEmail = binding.tvEmail

    }
}