package com.example.goldenticket.Adapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.goldenticket.Data.QuestionData
import com.example.goldenticket.R
import com.github.aakira.expandablelayout.ExpandableLayout
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter
import com.github.aakira.expandablelayout.ExpandableLinearLayout

class QuestionRecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<QuestionData>): RecyclerView.Adapter<QuestionRecyclerViewAdapter.Holder>() {
    private val expandState = SparseBooleanArray()

    init {
        for (i in dataList.indices) {
            expandState.append(i, false)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_question, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = dataList[position].title
        holder.content.text = dataList[position].content

        holder.setIsRecyclable(false)
        holder.expandableLayout.setInRecyclerView(true)
        holder.expandableLayout.isExpanded = expandState.get(position)
        holder.expandableLayout.setListener(object : ExpandableLayoutListenerAdapter() {
            override fun onPreOpen() {
                holder.btn.isSelected = true
                expandState.put(position, true)
            }

            override fun onPreClose() {
                holder.btn.isSelected = false
                expandState.put(position, false)
            }
        })
        holder.container.setOnClickListener(View.OnClickListener {
            onClickButton(holder.expandableLayout)
        })
    }

    private fun onClickButton(expandableLayout: ExpandableLayout) {
        expandableLayout.toggle()
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var container = itemView.findViewById(R.id.rl_rv_item_notice_container) as RelativeLayout
        var title = itemView.findViewById(R.id.txt_rv_item_notice_title) as TextView
        //var date = itemView.findViewById(R.id.txt_rv_item_notice_date) as TextView
        var content = itemView.findViewById(R.id.txt_rv_item_notice_content) as TextView
        var btn = itemView.findViewById(R.id.img_rv_item_btn) as ImageView
        var expandableLayout = itemView.findViewById(R.id.expandable_ll) as ExpandableLinearLayout
    }
}