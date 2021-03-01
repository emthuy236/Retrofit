package Retrofit2

import retrofit2.create

class APIUtils {
    companion object{
       var  base_url:String = "http://192.168.1.7/quanlysinhvien/"
        fun Getdata():DataClient{
            return RetrofitClient.getClient(base_url).create()
        }
    }


}