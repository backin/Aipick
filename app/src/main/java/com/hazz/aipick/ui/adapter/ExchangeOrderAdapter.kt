package com.hazz.aipick.ui.adapter


import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.aipick.R
import com.hazz.aipick.mvp.model.bean.Message
import com.hazz.aipick.mvp.model.bean.TixianRecord
import com.hazz.aipick.ui.activity.MineSubscribeActivity
import com.hazz.aipick.ui.activity.SubscribeDescActivity

class ExchangeOrderAdapter(layoutResId: Int, data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {


    lateinit var onConfirm: (View, Int) -> Unit
    override fun convert(helper: BaseViewHolder, item: String) {

//        helper.setText(R.id.mTvNoticeTitle,item.title)
//        helper.setText(R.id.mTvTime,item.create_at)
    }
}
