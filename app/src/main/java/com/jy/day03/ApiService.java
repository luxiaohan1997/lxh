package com.jy.day03;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    public String  URL="https://www.wanandroid.com/";

    public static String mUrl = "http://yun918.cn/";

    @GET("project/list/1/json?cid=294")
    Observable<AetBean> getdataurl();

//    @POST("study/public/file_upload.php")
//    @Multipart
        //key是一个参数，服务器用来创建保存文件的文件夹
//    Observable<UploadResultBean> uploadFile(@Part MultipartBody.Part part, @Part("key") RequestBody key);

}
