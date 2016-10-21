package com.example.administrator.mvp_beautylistview.util;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/9.
 */
public class OkHttpUtil {
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient(); //静态常量
    //1.获取Request对象
    private static Request getRequestFromUrl(String urlString) {
        //得到Request对象
        Request request = new Request.Builder()
                .url(urlString)
                .build();
        return request;
    }
    //2.获取Response对象
    private static Response getResponseFromUrl(String urlString) throws IOException {
        //要想得到Response先要有一个request对象
        Request request = getRequestFromUrl(urlString);
        //调用newCall()方法：意思就是：一个请求返回一个响应对象
        Response response = OK_HTTP_CLIENT.newCall(request).execute();
        return response;
    }
    //3.获取ResponseBody对象
    private static ResponseBody getResponseBodyFromUrl(String urlString) throws IOException {
        Response response = getResponseFromUrl(urlString);
        //一次request请求 服务器不一定要响应（只有statusCode>=200 <300时才成功）
        if (response.isSuccessful()) {
            return response.body();
        }
        return null;
    }
    //4.通过网络请求获取服务器端发来的字符串（从responseBody中获取服务端返回的数据）
    public static String loadStringFromUrl(String urlString) throws IOException {
        ResponseBody responseBody = getResponseBodyFromUrl(urlString);
        if (responseBody != null) {
            return responseBody.string();    //将body里的东西转换为字符串
        }
        return null;
    }
    //通过网络请求返回字节数组
    public static byte[] loadByteFromUrl(String urlString) throws IOException {
        ResponseBody responseBody = getResponseBodyFromUrl(urlString);
        if (responseBody != null) {
            return responseBody.bytes();
        }
        return null;
    }
    //通过网络请求返回输入流
    public static InputStream loadStreamFromUrl(String urlString) throws IOException {
        ResponseBody responseBody = getResponseBodyFromUrl(urlString);
        if (responseBody != null) {
            return responseBody.byteStream();
        }
        return null;
    }

    /**
     *  Get高级用法
     */
    /**
     * 开启一个异步线程 通过实现回调方法 实现
     * @param urlString
     * @param callback
     */
    public static void loadDataByNewThread(String urlString, Callback callback) {
        /**当前79行在主线程中（好像是废话。。这只是个函数调用）*/
        Request request = getRequestFromUrl(urlString);
        OK_HTTP_CLIENT.newCall(request).enqueue(callback);   //内部有个execute开启子线程异步操作
    }

    /********************************************************
     **********************  POST请求  **********************
     ********************************************************/
    /*获取上传key-value的requestBody对象*/
    private static RequestBody buildRequestBody(Map<String, String> map) {
        FormEncodingBuilder builder = new FormEncodingBuilder();  //用于存储key-value数据
        if (map != null || ! map.isEmpty()) {
            for(Map.Entry<String, String> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();     //返回的是requestBody
    }
    /**
     * 上传键值对 的 同步方法
     **/
    public static String postKeyValuePair(String urlString, Map<String, String> map) throws IOException {
        RequestBody requestBody = buildRequestBody(map);    //得到requestBody对象
        String result = postRequestBody(urlString, requestBody);
        return result;
    }
    /*获取post的request对象*/
    private static Request buildPostRequest(String urlString, RequestBody requestBody) {
        Request.Builder builder = new Request.Builder();
        builder.url(urlString).post(requestBody);  //requestBody 向服务器发送请求的数据
        return builder.build();
    }
    /*用于  上传requestBody对象*/
    public static String postRequestBody(String urlString, RequestBody requestBody) throws IOException {
        //要想上传requestBody需要一个用于post的requst对象
        Request request = buildPostRequest(urlString, requestBody);
        Response response = OK_HTTP_CLIENT.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string(); //response.body得到的就是一个responseBody对象 //得到上传后的服务端返回数据
        }
        return null;
    }
    /**
     *  POST 异步网络访问请求
     *      只要是异步必然存在一个callback回调方法
     */
    private static void postRequestBodyAsync(String urlString, RequestBody requestBody, Callback callback) {
        Request request = buildPostRequest(urlString, requestBody);   //老规矩 获取request对象
        OK_HTTP_CLIENT.newCall(request).enqueue(callback);    //异步就是enqueue方法
    }
    /*Post异步 提交键值对*/
    public static void postKeyValuePairAsync(String urlString, Map<String, String> map, Callback callback) {
        RequestBody requestBody = buildRequestBody(map);
        postRequestBodyAsync(urlString, requestBody, callback);
    }

    /**
     *  上传文件
     */
    /***
     *
     * @param urlString     服务端url
     * @param map           表单信息键值对（好像没鸟用）
     * @param files         文件数组（可以上传多个文件）
     * @param formFileName  需要提交的文件对应的文件input的name值
     */
    public static String postUploadFiles(String urlString, Map<String, String> map, File[] files, String[] formFieldName) throws IOException {
        RequestBody requestBody = buildRequestBody(map, files, formFieldName);
        return postRequestBody(urlString, requestBody);
    }
    /**
     * 生成分块请求时的RequestBody对象
     * @param map
     * @param files
     * @param formFieldName
     * @return
     */
    private static RequestBody buildRequestBody(Map<String, String> map, File[] files, String[] formFieldName) {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        //第一部分：提交除文件控件以外其他input控件的数据
        if (map != null) {   //有需要提交的表单控件的数据
            for (Map.Entry<String, String> entry : map.entrySet()) {               /**别问什么意思。。查看上传原理(然后猜。。) 然后跟着写。。。*/
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""), RequestBody.create(null, entry.getValue()));     //分块提交
            }
        }
        //第二部分：上传文件控件的数据
        //往MultipartBuilder对象中添加file input控件的数据
        if (files != null) {
            for ( int i = 0; i < files.length; i ++ ) {
                File file = files[i];
                String fileName = file.getName();
                RequestBody requestBody = RequestBody.create(MediaType.parse(getMimeType(fileName)), file);   /**传入文件！！！*/
                //添加file input块的数据
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + formFieldName[i] + "\"; filename=\"" + fileName + "\""), requestBody);     //分块提交
            }
        }
        return builder.build();
    }
    /**获取MIME格式*/
    private static String getMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(fileName);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
