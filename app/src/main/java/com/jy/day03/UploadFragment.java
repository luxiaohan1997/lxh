package com.jy.day03;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;
import static com.jy.day03.ApiService.mUrl;

public class UploadFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ImageView mIm;
    /**
     * 上传图片
     */
    private Button mBt;
    private String url=" http://yun918.cn/study/public/file_upload.php";
    private File mFile=new File("storage/emulated/0/");
    private Uri uri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upload_fragment, null);
        initView(view);
        checkPremisstion();
        return view;
    }

    private void checkPremisstion() {
        //检查 sdcard读权限
        int i = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (i != PackageManager.PERMISSION_GRANTED) {//没有权限，申请权限
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
        }
      /*  //检查 拍照权限
        int j = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (j != PackageManager.PERMISSION_GRANTED) {//没有权限，申请权限
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, 200);
        }*/
    }

    private void initView(View view) {
        mIm = (ImageView) view.findViewById(R.id.im);
        mBt = (Button) view.findViewById(R.id.bt);
        mBt.setOnClickListener(this);
        mIm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt:
                okUpload(mFile);
                break;
            case R.id.im:
                initImage();
                break;
        }
    }

    private void okUpload(File _file) {
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/octet-stream");
        if (_file.exists()) {
            RequestBody requestBody = RequestBody.create(mediaType, _file);

            MultipartBody multipartBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", _file.getName(), requestBody)
                    .addFormDataPart("key", "uploads")//上传文件的保存文件夹
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(multipartBody)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("tag", "onFailure: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();
                    Log.i("tag", "onResponse: " + str);
                }
            });
        }
    }


    private void initImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        //隐式跳转打开相册，上面的都是参数，选取完照片后，会回调onActivityResult 进行处理  请求码是200
        startActivityForResult(intent,200);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==200){
            if (resultCode==RESULT_OK){
                Uri imageUri = data.getData();//得到浏览的图片的uir路径
                //处理uri,7.0以后的fileProvider 把URI 以content provider 方式
                // 对外提供的解析方法,,得到浏览的相册的图片
                File file = getFileFromUri(imageUri, getActivity());

                if (file.exists()){
                    okUpload(file);//把浏览的图片进行上传
                }
            }
        }
    }

    private File getFileFromUri(Uri imageUri, Context context) {
        if (imageUri == null) {
            return null;
        }
        switch (imageUri.getScheme()) {//uri.getScheme()得到返回的照片的内容形式
            case "content"://sdk7.0之后是 内容提供者的处理
                return getFileFromContentUri(imageUri, context);
            case "file": //sdk7.0之前是 file的简单处理
                return new File(imageUri.getPath());//uri.getPath()得到浏览的相册中的图片的路径，直接实例化file对象使用
            default:
                return null;
        }
    }

    private File getFileFromContentUri(Uri imageUri, Context context) {
        if (imageUri == null) {
            return null;
        }
        File file = null;
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        //内容解析者,查询系统资源
        ContentResolver contentResolver = context.getContentResolver();
        //  content://xxxxxxx
        Cursor cursor = contentResolver.query(imageUri, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();//取出第一条就是浏览的图片，只有一条，不需要while循环
            //文件的路径
            filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();

            if (!TextUtils.isEmpty(filePath)) {
                file = new File(filePath);//通过读取的路径创建file文件对象，返回并上传
            }
        }
        return file;
    }
}
