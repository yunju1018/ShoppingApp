package org.techtown.shoppingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import okhttp3.internal.notify
import org.techtown.shoppingapp.adapters.ShipmentListAdapter
import org.techtown.shoppingapp.databinding.ActivityMyShipmentInfoBinding
import org.techtown.shoppingapp.datas.BasicResponse
import org.techtown.shoppingapp.datas.UserAllAddressData
import org.techtown.shoppingapp.fragments.MyShipmentAddDialog
import org.techtown.shoppingapp.interfaces.SelectShipmentInfoListener
import org.techtown.shoppingapp.interfaces.ShipmentAddListener
import org.techtown.shoppingapp.interfaces.ShipmentDeletedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyShipmentInfoActivity : BaseActivity(), ShipmentDeletedListener, ShipmentAddListener, SelectShipmentInfoListener {

    lateinit var binding : ActivityMyShipmentInfoBinding
    lateinit var mShipListAdapter : ShipmentListAdapter
    var mList : ArrayList<UserAllAddressData> = arrayListOf()

    val dialog = MyShipmentAddDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_shipment_info)

        setupEvents()
        getShipmentInfo()

    }

    override fun setupEvents() {
        binding.btnExit.setOnClickListener {
            finish()
        }

        binding.btnNewAddress.setOnClickListener {
            dialog.setShipmentListener(this)
            dialog.show(supportFragmentManager, "CustomDialog2")
        }

    }

    override fun setValues() {

        mShipListAdapter = ShipmentListAdapter(mList, this, this )
        binding.myShipmentRecyclerView.adapter = mShipListAdapter
        binding.myShipmentRecyclerView.layoutManager = LinearLayoutManager(mContext)


    }

    fun getShipmentInfo(){
        apiList.getRequestShipmentInfo().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!

                    mList.clear()
                    mList.addAll(br.data.user_all_address)

                    setValues()


                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })
    }



    override fun onDeletedShipment(id: Int) {

        apiList.DeletedShipmentInfo(id).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                Toast.makeText(mContext, "배송지가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                getShipmentInfo()
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("yj", "배송지삭제 ${t.message}")
            }

        })
    }

    override fun shipmentAdd() {
        getShipmentInfo()
        dialog.dismiss()
    }

    override fun onSelectedShipmentList(shipmentData: UserAllAddressData) {

        val resultIntent = Intent()
        resultIntent.putExtra("shipmentInfo", shipmentData)
        setResult(RESULT_OK, resultIntent)
        finish()

    }


}