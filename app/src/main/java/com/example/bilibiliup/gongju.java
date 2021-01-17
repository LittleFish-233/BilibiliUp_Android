package com.example.bilibiliup;

import java.io.IOException;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public  class gongju {



    /// <summary>
    /// 拆分cookie
    /// </summary>
    /// <param name="url"></param>
    /// <returns></returns>
    public static String[] huoqu_xinxi(String url)
    {
        String[] jieguo = new String[5];
        String linshi = "";
        boolean shifou_fuzhi = false;
        int jishu = 0;
        for(int i=0;i<url.length();i++)
        {
            if(url.charAt(i)=='=')
            {
                shifou_fuzhi = true;
                linshi = "";
                continue;
            }
            if((url.charAt(i)=='&'))
            {
                jieguo[jishu] = linshi;
                jishu++;
                shifou_fuzhi = false;
                continue;
            }
            if(shifou_fuzhi==true)
            {
                linshi += url.charAt(i);
            }
        }
        return jieguo;
    }

    /// <summary>
    /// http请求
    /// </summary>
    /// <param name="url"></param>
    /// <param name="cook"></param>
    /// <returns></returns>
    public static String http_get(String url_, String[] cook) throws Exception {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_)
                .addHeader("Set-Cookie", cook[0])
                .addHeader("Set-Cookie", cook[1])
                .addHeader("Set-Cookie", cook[2])
                .addHeader("Set-Cookie", cook[3])
                .addHeader("Set-Cookie", cook[4])
                .build();
        Response response = null;
        try {

            response = client.newCall(request).execute();
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new Exception("第一.132步错误"+exc.getLocalizedMessage());
        }
        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }
        return response.body().string();
    }
    // 字符串
    public static String http_get(String url_,int fanhui) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }
        return response.body().string();
    }
    // 字节数组
    public static byte[] http_get(String url_) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url_)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }
        return response.body().bytes();
    }

    public static List<String> http_get_cookies(String url_) throws IOException {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("String"),"string");
        Request request = new Request.Builder()
                .url(url_)
                .post(body)
                .method("POST",body)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }
        return response.headers("Set-Cookie");
    }

    public static String fengli_id(String linshi)
    {
        String jieguo = "";
        Boolean shifou_kaishi = false;
        for(int i=0;i<linshi.length();i++)
        {
            if(linshi.charAt(i)=='=')
            {
                shifou_kaishi = true;
                continue;
            }
            if(linshi.charAt(i)==';')
            {
                break;
            }
            if(shifou_kaishi==true)
            {
                jieguo += linshi.charAt(i);
            }
        }
        return jieguo;
    }



}
