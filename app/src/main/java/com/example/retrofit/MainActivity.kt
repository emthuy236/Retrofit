package com.example.retrofit

import Retrofit2.APIUtils
import Retrofit2.DataClient
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var taikhoan: String = ""
    var matkhau: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btndangki.setOnClickListener {
            var intent: Intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
        btndangnhap.setOnClickListener {
            taikhoan = edtaccount.text.toString().trim()
            matkhau = edtpassword.text.toString().trim()
            if (taikhoan.length > 0 && matkhau.length > 0) {
                var data: DataClient = APIUtils.Getdata()
                var calldata: retrofit2.Call<List<Sinhvien>> = data.LoginData(taikhoan, matkhau)
                calldata.enqueue(object: Callback<List<Sinhvien>>{
                    override fun onFailure(call: Call<List<Sinhvien>>, t: Throwable) {
                        Toast.makeText(this@MainActivity,"Không tồn tại tk này !!",Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<List<Sinhvien>>,
                        response: Response<List<Sinhvien>>
                    ) {
                        var arraysinhvien:ArrayList<Sinhvien> = response.body() as ArrayList<Sinhvien>
                        if (arraysinhvien.size > 0){
                            var inten:Intent = Intent(this@MainActivity,Thongtinnguoidung::class.java)
                            inten.putExtra("mangsinhvien",arraysinhvien)
                            startActivity(inten)
                        }
                    }
                })

            }
        }
    }
}