package com.example.bilibiliup;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class peizhi {
    //版本
    public  int banben = 1;

    public  String up = "";
    public  String dedeUserID = "";
    public  String dedeUserID__ckMd5 = "";
    public  String expires = "";
    public  String sESSDATA = "";
    public  String bili_jct = "";
    public  String name = "";
    public  String sid = "";
    public  double toumingdu = 0.5;
    public  double donghua_shijian = 3;
    public  double huoqu_jiange = 10;


    private  Boolean shifou_zhengzai=false;

    // 保存基本信息
    public  void baocun_jiben(Context context) throws JSONException, FileNotFoundException
    {
        if (shifou_zhengzai == true)
        {
            return;
        }
        shifou_zhengzai = true;

        //序列化数据
        //添加基本数据
        JSONObject allData = new JSONObject();
        allData.put("banben", banben);
        allData.put("up", up);
        allData.put("dedeUserID", dedeUserID);
        allData.put("dedeUserID__ckMd5", dedeUserID__ckMd5);
        allData.put("expires", expires);
        allData.put("sESSDATA", sESSDATA);
        allData.put("bili_jct", bili_jct);
        allData.put("name", name);
        allData.put("sid", sid);
        allData.put("toumingdu", toumingdu);
        allData.put("donghua_shijian", donghua_shijian);
        allData.put("huoqu_jiange", huoqu_jiange);
        String linshi= allData.toString();

        //保存到文件
        String filename = "infor.json";
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE))
        {
            fos.write(linshi.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        shifou_zhengzai = false;
    }
    /// <summary>
    /// 读取基本信息
    /// </summary>
    /// <returns>是否成功</returns>
    public  boolean duqu_jiben(Context context) throws FileNotFoundException, JSONException {
        try
        {
            //打开文件
            FileInputStream fis = context.openFileInput("infor.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder linshi= new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader))
            {
                String line = reader.readLine();
                while (line != null)
                {
                    linshi.append(line);
                    line = reader.readLine();
                }
            }
            catch (IOException e)
            {
                // Error occurred when opening raw file for reading.
            }
            String contents = linshi.toString();

            String[] result = peizhi_jiben_jiexi(contents);//解析Json
            //根据读取的数据初始化相应变量
            try
            {
                if (result[1] != "")
                {
                    up = result[1];
                }
                if (result[2] != "")
                {
                    dedeUserID = result[2];
                }
                if (result[3] != "")
                {
                    dedeUserID__ckMd5 = result[3];
                }
                if (result[4] != "")
                {
                    expires = result[4];
                }
                if (result[5] != "")
                {
                    sESSDATA = result[5];
                }
                if (result[6] != "")
                {
                    bili_jct = result[6];
                }
                if (result[7] != "")
                {
                    name = result[7];
                }
                if (result[8] != "")
                {
                    sid = result[8];
                }
                if (result[9] != "")
                {
                    toumingdu = Double.parseDouble(result[9]);
                }
                if (result[10] != "")
                {
                    donghua_shijian = Double.parseDouble(result[10]);
                }
                if (result[11] != "")
                {
                    huoqu_jiange = Double.parseDouble(result[11]);
                }
            }
            catch(Exception exc)
            {

            }
            //检查版本号是否一致
            if (banben !=Long.parseLong( result[0])) {
                //保存
                baocun_jiben(context);
            }
            return true;
        }
        catch (Exception exc)
        {
            baocun_jiben(context);
            return false;
        }
    }
    /// <summary>
    /// 解析读取的配置文件Json 外部 负责匹配版本号
    /// </summary>
    /// <param name="str">配置文件json字符串</param>
    /// <returns>按照最新的变量列表返回字符串数组</returns>
    public  String[] peizhi_jiben_jiexi(String str) throws JSONException {

        JSONObject json =new JSONObject(str);
        switch ((int) Long.parseLong(json.getString("banben")))
        {
            case 1:
                return peizhi_jiben_jiexi_1(str);
            default:
                return null;
        }
    }
    /// <summary>
    /// 解析读取的配置文件 版本1
    /// </summary>
    /// <param name="str">配置文件json字符串</param>
    /// <returns>按照最新的变量列表返回字符串数组</returns>
    private  String[] peizhi_jiben_jiexi_1(String str) throws JSONException {

        JSONObject json =new JSONObject(str);

        //保存结果
        String[] re = new String[12];

        re[0] = json.getString("banben");
        re[1] = json.getString("up");
        re[2] = json.getString("dedeUserID");
        re[3] = json.getString("dedeUserID__ckMd5");
        re[4] = json.getString("expires");
        re[5] = json.getString("sESSDATA");
        re[6] = json.getString("bili_jct");
        re[7] = json.getString("name");
        re[8] = json.getString("sid");
        re[9] = json.getString("toumingdu");
        re[10] = json.getString("donghua_shijian");
        re[11] = json.getString("huoqu_jiange");
        return re;
    }
    /// <summary>
    /// 退出登入 清空信息
    /// </summary>
    public  void tuichudengru(Context context) throws FileNotFoundException, JSONException {
        dedeUserID = "";
        dedeUserID__ckMd5 = "";
        expires = "";
        sESSDATA = "";
        bili_jct = "";
        name = "";

        baocun_jiben(context);
    }

    // 写入登入后获取的信息
    public  void xieru_dengru(Context context, String sid_,String DedeUserID_,String DedeUserID__ckMd5_,String SESSDATA_,String bili_jct_) throws Exception {
        //检查
        if(DedeUserID_=="")
        {
            return;
        }
        //写入
        dedeUserID = DedeUserID_;
        dedeUserID__ckMd5 = DedeUserID__ckMd5_;
        expires = "100000";
        sESSDATA = SESSDATA_;
        bili_jct = bili_jct_;
        sid = sid_;
        //获取用户名
         String serviceAddress = "https://api.bilibili.com/x/space/acc/info?mid=" + gongju.fengli_id(dedeUserID); //请求地址
         String retString = gongju.http_get(serviceAddress, huoqu_cook());

        //解析json
        JSONObject wai = new JSONObject(retString);
        name = wai.getJSONObject("data").getString("name");

        baocun_jiben(context);
    }
    /// <summary>
    /// 获取 登入信息用于传递
    /// </summary>
    /// <returns></returns>
    public  String[] huoqu_cook()
    {
        String[] jieguo = new String[5];
        jieguo[0] = sid;
        jieguo[1] = dedeUserID;
        jieguo[2] = dedeUserID__ckMd5;
        jieguo[3] = sESSDATA;
        jieguo[4] = bili_jct;

        return jieguo;
    }

    // 修改信息
    public  void xiugai(Context context,String up_,String DedeUserID_, String DedeUserID__ckMd5_, String Expires_, String SESSDATA_, String bili_jct_,String sid_,double toumingdu_,double donghua_shijian_,double huoqu_jiange_) throws FileNotFoundException, JSONException {
        up = up_;
        dedeUserID = DedeUserID_;
        dedeUserID__ckMd5 = DedeUserID__ckMd5_;
        expires = Expires_;
        sESSDATA = SESSDATA_;
        bili_jct = bili_jct_;
        toumingdu = toumingdu_;
        donghua_shijian = donghua_shijian_;
        huoqu_jiange = huoqu_jiange_;
        sid = sid_;

        baocun_jiben(context);
    }
}
