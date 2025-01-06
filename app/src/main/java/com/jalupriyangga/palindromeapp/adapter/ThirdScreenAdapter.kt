package com.jalupriyangga.palindromeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.jalupriyangga.palindromeapp.R
import com.jalupriyangga.palindromeapp.UserResponse
import com.jalupriyangga.palindromeapp.viewmodel.UserViewModel
import com.squareup.picasso.Picasso

class ThirdScreenAdapter(
    private val context: Context,
    private val datalist: ArrayList<UserResponse.Data>,
    private val viewModel: UserViewModel // Gunakan ViewModel dari Fragment
) : RecyclerView.Adapter<ThirdScreenAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_user_view, parent, false)
        return UserViewHolder(itemView)
    }

    override fun getItemCount(): Int = datalist.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = datalist[position]
        holder.tvNama.text = "${user.first_name} ${user.last_name}"
        holder.tvEmail.text = user.email
        holder.rlUser.setOnClickListener {
            // Perbarui ViewModel dengan nama user
            viewModel.selectedUserName.value = "${user.first_name} ${user.last_name}"
            Toast.makeText(context, "User ${user.first_name} ${user.last_name} clicked", Toast.LENGTH_SHORT).show()
        }
        Picasso.get()
            .load(user.avatar)
            .placeholder(R.drawable.ic_photo)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.imgUser)
    }

    fun setData(data: List<UserResponse.Data>) {
        datalist.clear()
        datalist.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        datalist.clear() // Clear the data list
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgUser: ImageView = view.findViewById(R.id.userImageView)
        val tvNama: TextView = view.findViewById(R.id.userNameTextView)
        val tvEmail: TextView = view.findViewById(R.id.emailTextView)
        val rlUser: View = view.findViewById(R.id.itemUser)
    }
}
