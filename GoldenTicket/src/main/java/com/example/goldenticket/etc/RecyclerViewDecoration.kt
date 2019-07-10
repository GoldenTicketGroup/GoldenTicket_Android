package com.example.goldenticket.etc

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewDecoration : RecyclerView.ItemDecoration() {

//    var divideLeft: Int = 0
//    constructor(divideLeft : Int) {
//        this.divideLeft = divideLeft
//    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if(position%2!=0){
            outRect.left=30
        }else{
            outRect.right=30
        }
    }
}