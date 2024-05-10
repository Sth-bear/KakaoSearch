package com.example.searchcollector.activities.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // 아이템의 위치 가져오기
        val column = position % spanCount // 아이템이 속한 열 구하기

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount // 왼쪽 여백 설정
            outRect.right = (column + 1) * spacing / spanCount // 오른쪽 여백 설정

        } else {
            outRect.left = column * spacing / spanCount // 왼쪽 여백 설정
            outRect.right = spacing - (column + 1) * spacing / spanCount // 오른쪽 여백 설정
        }
    }
}