package com.gremoryyx.kerjasama

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class RegisteredJobAdapter(private val registeredJobList: ArrayList<RegisteredJobData>) : RecyclerView.Adapter<RegisteredJobAdapter.RegisteredJobViewHolder>() {

    private var onCancelButtonClickListener: ((RegisteredJobData) -> Unit)? = null

    fun setOnCancelButtonClickListenerLambda(listener: (RegisteredJobData) -> Unit) {
        onCancelButtonClickListener = listener
    }

    fun updateJobList(newJobList: ArrayList<RegisteredJobData>) {
        registeredJobList.clear()
        registeredJobList.addAll(newJobList)
        notifyDataSetChanged()
    }

    fun setJobList(newList: List<RegisteredJobData>) {
        registeredJobList.clear()
        registeredJobList.addAll(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisteredJobViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.register_item, parent, false)
        return RegisteredJobViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RegisteredJobViewHolder, position: Int) {
        val currentItem = registeredJobList[position]
        holder.bind(currentItem)
        holder.cancelButton.setOnClickListener {
            onCancelButtonClickListener?.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return registeredJobList.size
    }

    inner class RegisteredJobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cancelButton = itemView.findViewById<Button>(R.id.cancelButton)

        fun bind(registeredJob: RegisteredJobData) {
            itemView.findViewById<ImageView>(R.id.jobImageView)
                .setImageBitmap(registeredJob.jobImage)
            itemView.findViewById<TextView>(R.id.jobNameTextView).text = registeredJob.jobName
            itemView.findViewById<TextView>(R.id.companyNameTextView).text =
                registeredJob.companyName
            itemView.findViewById<TextView>(R.id.jobTypeTextView).text = registeredJob.jobType
            itemView.findViewById<TextView>(R.id.locationTextView).text = registeredJob.location
            itemView.findViewById<TextView>(R.id.durationTextView).text = registeredJob.duration
            itemView.findViewById<TextView>(R.id.salaryTextView).text = registeredJob.salary
            itemView.findViewById<TextView>(R.id.jobDescriptionContentTextView).text =
                registeredJob.jobDescription
        }
    }
}