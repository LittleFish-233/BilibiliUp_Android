package com.example.bilibiliup;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Random;

public class dengru {

    public  String oauthKey = "";
    public  String url_er = "";
    public  int shenyushijian = 0;
    public  String tupian_mingzi = "";

    
    /// <summary>
    /// 获取二维码图片
    /// </summary>
    public Bitmap huoqu_erweima(Context context) throws Exception {
        //获取链接
        String serviceAddress = "https://passport.bilibili.com/qrcode/getLoginUrl"; //请求地址
        String retString=  gongju.http_get(serviceAddress,new String[]{"123","123","123","123","123"});

        //解析url
        JSONObject wai =new JSONObject(retString);

        url_er = wai.getJSONObject("data").getString("url");

        oauthKey =wai.getJSONObject("data").getString("oauthKey");

        //获取二维码图片
        File directory = context.getFilesDir();
        File file = new File(directory, "二维码");
        Random random = new Random();
        tupian_mingzi = random.nextLong()+".png";
        File file_ = new File(file, tupian_mingzi);byte[] linshi_=null;
        //设置时间
        shenyushijian = 175;
        return getBitMap("https://api.pwmqr.com/qrcode/create/?url="+url_er);



    }

    public Bitmap getBitMap(String strUrl) {
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            URL url = new URL(strUrl);
            URLConnection conn = url.openConnection();
            is = conn.getInputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }


    public String[] jiancha_shifou()
    {
        if(oauthKey=="")
        {
            return null;
        }
        String serviceAddress = "https://passport.bilibili.com/qrcode/getLoginInfo?oauthKey=" + oauthKey; //请求地址
        //检查返回值
        try
        {
            List<String> linshi=gongju.http_get_cookies(serviceAddress);
            String[] jieguo=null;
            if(linshi!=null)
            {
                if(linshi.size()<5)
                {
                    return null;
                }
                jieguo=new String[5];
               for(int i=0;i<linshi.size();i++)
               {
                   jieguo[i]=linshi.get(i);
               }
            }

            return jieguo;

            //成功
            //return gongju.huoqu_xinxi(url);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
