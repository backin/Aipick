package com.hazz.aipick.ui.adapter


import android.content.Intent
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.aipick.R
import com.hazz.aipick.mvp.model.bean.MySubscribe
import com.hazz.aipick.ui.activity.MineSubscribeActivity
import com.hazz.aipick.ui.activity.SubscribeDescActivity
import kotlinx.android.synthetic.main.item_mysubscribe.view.*

class SubscribeAdapter(layoutResId: Int, data: List<MySubscribe>?) : BaseQuickAdapter<MySubscribe, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: MySubscribe) {

        helper.setText(R.id.tv_name, item.subee_nickname)
        val view = helper.getView<ImageView>(R.id.iv)
        Glide.with(mContext).load(item.subee_avatar)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(view)

        helper.setText(R.id.tv_time, item.create_at)

        helper.setText(R.id.tv_price, item.price)

        when (item.status) {
            "unpaid" -> helper.setText(R.id.tv_state, mContext.getString(R.string.unpaid))
            "paid" -> helper.setText(R.id.tv_state, mContext.getString(R.string.paid))
            "cancel" -> helper.setText(R.id.tv_state, mContext.getString(R.string.cancel))
        }
        helper.itemView.setOnClickListener {
            mContext.startActivity(Intent(mContext, SubscribeDescActivity::class.java).putExtra("subId", item.sub_id))
        }
    }
}
