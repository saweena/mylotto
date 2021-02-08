package com.maicakapo.thailotto2021_1.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.maicakapo.thailotto2021_1.R
import kotlinx.android.synthetic.main.itemview_cardview_detail_lottery.view.*

class LotteryCardView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        View.inflate(context, R.layout.itemview_cardview_detail_lottery,this)
    }

    fun setTitle(text: String) {
        tv_title?.text = text
    }

    fun setPrize(text: String) {
        tv_prize?.text = text
    }

    fun setData(text: String) {
        tv_content?.text = text
    }

    fun setContentTextSize(size: Float){
        tv_content?.textSize = size
    }

}