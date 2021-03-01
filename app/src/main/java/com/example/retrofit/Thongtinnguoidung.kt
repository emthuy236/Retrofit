package com.example.retrofit

import Retrofit2.APIUtils
import Retrofit2.DataClient
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_thongtinnguoidung.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Thongtinnguoidung : AppCompatActivity() {
    var sinhvien:ArrayList<Sinhvien>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thongtinnguoidung)
        init()
        btndelete.setOnClickListener {
            var namefolder:String? = sinhvien!!.get(0).hinhanh
            namefolder = namefolder!!.substring(namefolder!!.lastIndexOf("/"))
            var dataclie:DataClient = APIUtils.Getdata()
            var call:Call<String>? = dataclie.Deletedata(sinhvien!!.get(0).id.toString(),namefolder)
            call!!.enqueue(object : Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {

                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    var ketqua: String? = response.body()
                    if (ketqua.equals("ok")){
                        Toast.makeText(this@Thongtinnguoidung,"Xóa thành công!",Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this@Thongtinnguoidung,"Thất bại!",Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }

    private fun init() {
        var intent:Intent = getIntent()
         sinhvien = intent.getParcelableArrayListExtra("mangsinhvien")
        txtuser.setText(sinhvien!!.get(0).user)
        txtpass.setText(sinhvien!!.get(0).password)
        Picasso.with(this).load(sinhvien!!.get(0).hinhanh).into(imglogin)
    }
}
