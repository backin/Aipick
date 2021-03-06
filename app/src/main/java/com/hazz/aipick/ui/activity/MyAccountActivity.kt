package com.hazz.aipick.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.support.design.widget.BottomSheetDialog
import com.hazz.aipick.R
import com.hazz.aipick.base.BaseActivity
import com.hazz.aipick.ui.fragment.*
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.hazz.aipick.mvp.contract.CollectionContract
import com.hazz.aipick.mvp.contract.WaletContract
import com.hazz.aipick.mvp.model.bean.BindCoinHouse
import com.hazz.aipick.mvp.model.bean.ChooseTime
import com.hazz.aipick.mvp.model.bean.Collection
import com.hazz.aipick.mvp.model.bean.MyAccount
import com.hazz.aipick.mvp.model.bean.UserInfo
import com.hazz.aipick.mvp.presenter.AccountPresenter
import com.hazz.aipick.mvp.presenter.CollectionPresenter
import com.hazz.aipick.ui.adapter.CoinAdapter
import com.hazz.aipick.ui.adapter.CoinBiduiAdapter
import com.hazz.aipick.utils.SPUtil
import com.hazz.aipick.utils.ToastUtils
import com.hazz.aipick.widget.RecyclerViewSpacesItemDecoration
import kotlinx.android.synthetic.main.activity_mine_set.*
import kotlinx.android.synthetic.main.activity_my_account.*
import kotlinx.android.synthetic.main.activity_my_account.iv_avatar
import kotlinx.android.synthetic.main.dialog_coin.view.*


class MyAccountActivity : BaseActivity(), WaletContract.myaccountView, CollectionContract.collectionView {


    override fun getCollection(msg: List<Collection>) {

    }

    override fun addCollectionSucceed(msg: String) {
        ToastUtils.showToast(this,msg)
    }

    override fun setFollow(msg: String) {
        if (!mMyAccount!!.is_following) {
            tv_yiguanzhu.visibility = View.VISIBLE
            tv_guanzhu.visibility = View.GONE
        }else{
            tv_yiguanzhu.visibility = View.GONE
            tv_guanzhu.visibility = View.VISIBLE
        }
        ToastUtils.showToast(this,msg)
    }

    override fun getPrice(msg: ChooseTime) {

    }

    override fun coinList(msg: BindCoinHouse) {
        mCoinAdapter!!.setNewData(msg.exchanges)
        mBindCoinHouse=msg.symbols
    }


    override fun myaccount(msg: MyAccount) {
        if (!TextUtils.isEmpty(msg.avatar)) {
            Glide.with(this).load(msg.avatar)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(iv_avatar)
        }
        coin=msg.coins
        mMyAccount = msg
        currentName = msg.nickname
        username.text = msg.nickname
        if (msg.is_following) {
            tv_yiguanzhu.visibility = View.VISIBLE
            tv_guanzhu.visibility = View.GONE
        }
        tv_fans.text = msg.fans.toString()
        tv_guanzhu_num.text = msg.follow.toString()
        tv_renqi.text = msg.pageview.toString()
        tv_shouyi_rate.text = msg.gain_rate
        tv_follow_incoming.text = msg.follow.toString()
        tv_all_incoming.text = msg.total.toString()
        tv_ten.text = msg.pullback
        tv_shouyi.text = msg.self
    }


    override fun layoutId(): Int = R.layout.activity_my_account

    override fun initData() {
        id = intent.getStringExtra("id")
       if(intent.getStringExtra("role")!=null){
           role=intent.getStringExtra("role")
       }
        Log.d("junjun", role)
        val obj = SPUtil.getObj("userinfo", UserInfo::class.java)
        if(obj.uid==id){
            rl.visibility=View.GONE
        }
        mAccountPresenter.myAccount(id)
    }


    private var mLastFragment: Fragment? = null
    private var mAccountPresenter: AccountPresenter = AccountPresenter(this)
    private var mCollectionPresenter: CollectionPresenter = CollectionPresenter(this)
    private var id = ""
    private var mCoinAdapter: CoinAdapter? = null
    private var mCoinBiduiAdapter: CoinBiduiAdapter? = null
    private var currentName = ""
    private var role = ""
    private var coin=""
    private var mMyAccount: MyAccount? = null
    private var mBindCoinHouse:MutableList<BindCoinHouse.SymbolsBean>?= mutableListOf()
    private var current:BindCoinHouse.ExchangesBean?=null
    private var baseCoin:BindCoinHouse.SymbolsBean?=null
    @SuppressLint("SetTextI18n")
    override fun initView() {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl, TransactionAnalysisFragment()).commitAllowingStateLoss()
        rg.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                R.id.rb1 -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fl, TransactionAnalysisFragment()).commitAllowingStateLoss()
                }
                R.id.rb2 -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fl, OrderFragment.getInstance(id)).commitAllowingStateLoss()


                }
                R.id.rb3 -> {

                }
            }


        }

        iv_back.setOnClickListener {
            finish()
        }

        tv_yiguanzhu.setOnClickListener {
            mAccountPresenter.attentionCancle(id)
        }
        tv_guanzhu.setOnClickListener {
            mAccountPresenter.attention(id)
        }
        iv_collection.setOnClickListener {
            val split = coin.split(",")
            if(split!=null){
                mCollectionPresenter.addCollection("sentiment",id,split[0],split[1])
            }

        }
    }


    override fun start() {
        tv_suscribe.setOnClickListener {

            // startActivity(Intent(this,PayActivity::class.java).putExtra("id",id).putExtra("price","0.01"))
            val bottomSheetDialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.dialog_coin, null)
            mAccountPresenter.coinList(id)
            view.recycleview1.layoutManager = GridLayoutManager(this, 4)


            mCoinAdapter = CoinAdapter(R.layout.item_text, null)
            view.recycleview1.adapter = mCoinAdapter
            val stringIntegerHashMap: HashMap<String, Int>? = HashMap()
            stringIntegerHashMap?.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, 15)//右间距
            view.recycleview1.addItemDecoration(RecyclerViewSpacesItemDecoration(stringIntegerHashMap))
            mCoinAdapter!!.bindToRecyclerView(view.recycleview1)
            mCoinAdapter!!.setEmptyView(R.layout.empty_view_coin)
            mCoinAdapter!!.emptyView.setOnClickListener {

                startActivity(Intent(this, CoinHouseActivity::class.java))
                bottomSheetDialog!!.dismiss()
            }

            view.tv_sure.setOnClickListener {

                showNextBottom()
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog!!.setContentView(view)
            bottomSheetDialog.show()
        }

    }

    private fun showNextBottom() {
        // startActivity(Intent(this,PayActivity::class.java).putExtra("id",id).putExtra("price","0.01"))
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_coin, null)
        mAccountPresenter.coinList(id)
        view.recycleview1.layoutManager = GridLayoutManager(this, 4)
        view.tv_title.text=getString(R.string.choose_bidui)
        view.tv_sure.text=getString(R.string.confirm)
        mCoinBiduiAdapter = CoinBiduiAdapter(R.layout.item_text, mBindCoinHouse)
        view.recycleview1.adapter = mCoinBiduiAdapter
        val stringIntegerHashMap: HashMap<String, Int>? = HashMap()
        stringIntegerHashMap?.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, 15)//右间距
        view.recycleview1.addItemDecoration(RecyclerViewSpacesItemDecoration(stringIntegerHashMap))
        view.tv_sure.setOnClickListener {

            if(mBindCoinHouse!=null){
                baseCoin = mBindCoinHouse!![mCoinAdapter!!.getCurr()]
                startActivity(Intent(this, SettingFollowedActivity::class.java).putExtra("id", id).putExtra("price", "0.01")
                        .putExtra("bean", current).putExtra("name", currentName)
                        .putExtra("SymbolsBean",baseCoin)
                        .putExtra("role", role)
                )
                bottomSheetDialog.dismiss()

            }else{
                ToastUtils.showToast(this,"请选择投放平台")
                return@setOnClickListener
            }

        }

        bottomSheetDialog!!.setContentView(view)
        bottomSheetDialog.show()
    }



}
