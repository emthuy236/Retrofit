package Retrofit2

import android.telecom.Call
import com.example.retrofit.Sinhvien
import okhttp3.MultipartBody
import retrofit2.http.*

interface DataClient {
    @Multipart
    @POST("uploadhinh.php")
    fun Uploadphoto(@Part photo: MultipartBody.Part?): retrofit2.Call<String?>?

    @FormUrlEncoded
    @POST("insert.php")
    fun inSert(@Field("taikhoan") taikhoan:String
               ,@Field("matkhau") matkhau:String
               ,@Field("hinhanh") hinhanh:String):retrofit2.Call<String?>?

    @FormUrlEncoded
    @POST("login.php")
    fun LoginData(@Field("user") user:String,
                  @Field("pass") password:String): retrofit2.Call<List<Sinhvien>>

    @GET("delete.php")
    fun Deletedata(@Query("id") id:String,
                   @Query("hinhanh") hinhanh:String):retrofit2.Call<String>

}