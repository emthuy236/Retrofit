package com.example.retrofit

import Retrofit2.APIUtils
import Retrofit2.DataClient
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.telecom.Call
import android.util.Log
import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File
import java.io.InputStream

class Register : AppCompatActivity() {
  var request_code_img:Int = 123
    var realpath:String = ""
    var taikhoan:String = ""
    var matkhau:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        TedPermission.with(this@Register)
            .setPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {

                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

                }
            })
            .check()

        imgregister.setOnClickListener {
            var intent:Intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            startActivityForResult(intent,request_code_img)
        }
        btnxacnhan.setOnClickListener {
            taikhoan = edttaikhoan.text.toString()
            matkhau = edtmatkhau.text.toString()
            if (taikhoan.length > 0 && matkhau.length > 0) {
                var file:File = File(realpath)
                var file_path:String = file.absolutePath
                var mangtenfile:Array<String> = file_path.split("\\.").toTypedArray()
//            file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1]
//            Log.d("BBB",file_path)
                var requestboy:RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
                var body:MultipartBody.Part = MultipartBody.Part.createFormData("upload_file",file_path,requestboy)
                var dataclient:DataClient = APIUtils.Getdata()
                var callback: retrofit2.Call<String?>? = dataclient.Uploadphoto(body)
                callback!!.enqueue(object : retrofit2.Callback<String?> {
                    override fun onFailure(call: retrofit2.Call<String?>, t: Throwable) {
                        Log.d("BBB",t.message)
                    }

                    override fun onResponse(call: retrofit2.Call<String?>, response: Response<String?>) {
                        if (response != null){
                            var message: String? = response.body()
                          //  Log.d("BBB",message)
                            if (message!!.length >0){
                                var insertdata:DataClient = APIUtils.Getdata()
                                var call:retrofit2.Call<String?>? = insertdata.inSert(taikhoan,matkhau,APIUtils.base_url + "image/" + message)
                                call!!.enqueue(object : retrofit2.Callback<String?> {
                                    override fun onFailure(call: retrofit2.Call<String?>, t: Throwable) {
                                        Log.d("AAA",t.message)
                                    }

                                    override fun onResponse(call: retrofit2.Call<String?>, response: Response<String?>) {
                                        var result:String? = response.body()
                                        if (result!!.equals("success")){
                                           // Log.d("CCC","lỗi truy cập")
                                            Toast.makeText(this@Register,"Thêm thành công",Toast.LENGTH_SHORT).show()
                                            finish()
                                        }
                                    }
                                })
                            }
                        }
                    }
                })


            }else{
                Toast.makeText(this,"Hãy nhập đủ thông tin",Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == request_code_img && resultCode == Activity.RESULT_OK && data != null){
                var uri: Uri? = data.data
            realpath = getRealPathFromURI(uri).toString()
            var inputstream: InputStream? = contentResolver.openInputStream(uri!!)
            var bitmap:Bitmap = BitmapFactory.decodeStream(inputstream)
            imgregister.setImageBitmap(bitmap)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    fun getRealPathFromURI(contentUri: Uri?): String? {
        var path: String? = null
        val proj = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor = contentResolver.query(contentUri!!, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            path = cursor.getString(column_index)
        }
        cursor.close()
        return path
    }
}
