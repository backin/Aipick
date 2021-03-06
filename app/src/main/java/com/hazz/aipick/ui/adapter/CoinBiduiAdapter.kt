package com.hazz.aipick.ui.adapter


import android.content.Intent
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hazz.aipick.R
import com.hazz.aipick.mvp.model.bean.BindCoinHouse
import com.hazz.aipick.mvp.model.bean.Home
import com.hazz.aipick.ui.activity.MyAccountActivity
import com.hazz.aipick.ui.activity.RebotCategryActivity
import kotlinx.android.synthetic.main.item_home.view.*


class CoinBiduiAdapter(layoutResId: Int, data: List<BindCoinHouse.SymbolsBean>?) : BaseQuickAdapter<BindCoinHouse.SymbolsBean, BaseViewHolder>(layoutResId, data) {

    private var currentTitle: BindCoinHouse.SymbolsBean? =null

    private var isfirst: Boolean = false
    private var curr: Int = 0

    override fun convert(helper: BaseViewHolder, item: BindCoinHouse.SymbolsBean) {
        //  helper.setText(R.id.tv_name, "")

        helper.setText(R.id.tv_name, item.base_coin+"/"+item.quote_coin)

        if (curr == helper.adapterPosition) {
            currentTitle=item
            helper.getView<RelativeLayout>(R.id.rl_tv).setBackgroundResource(R.drawable.bg_blue_solid_5dp_coner)
        } else {
            helper.getView<RelativeLayout>(R.id.rl_tv).setBackgroundResource(R.drawable.bg_shaixuan_coner)
        }
        helper.itemView.setOnClickListener {
            currentTitle = item
            isfirst = true
            curr = helper.adapterPosition
            notifyDataSetChanged()

        }

    }

    fun getCurrent(): BindCoinHouse.SymbolsBean {
        return currentTitle!!

    }

    fun getCurr(): Int {
        return curr

    }
}
