package com.example.bilibiliup;
import org.json.JSONObject;

public class shuju {
    /// <summary>
    /// up主名字
    /// </summary>
    public String mingzi = "";

    public shuju_dan shuju_dan_=null;

    public String fenshishu = "";


    /// <summary>
    /// 使用给定uuid刷新数据
    /// </summary>
    /// <param name="uuid">用户id</param>
    public void shuaxin_shuju(String uuid, String[] cook) throws Exception {
        //清空
        shuju_dan_=new shuju_dan();

        //获取视频列表
        String serviceAddress = "https://api.bilibili.com/x/space/arc/search?mid=" + uuid + "&ps=10&tid=0&pn=1&keyword=&order=pubdate&jsonp=jsonp"; //请求地址
        String retString ="";
        retString= gongju.http_get(serviceAddress, cook);

        if(retString==null)
        {
            throw new Exception("//第一步错误");
        }

        //解析json 需要的信息 视频列表 视频id
        JSONObject wai =new JSONObject(retString);

        String linshi = wai.getJSONObject("data").getJSONObject("list").getJSONArray("vlist").getJSONObject(0).getString("bvid");
        shuju_dan_.chushihua(linshi, cook);

        //获取up主名字
        mingzi =  wai.getJSONObject("data").getJSONObject("list").getJSONArray("vlist").getJSONObject(0).getString("author");

        //获取up主粉丝
        serviceAddress = "https://api.bilibili.com/x/relation/stat?vmid=" + uuid + "&jsonp=jsonp"; //请求地址
        retString = gongju.http_get(serviceAddress, cook);

        //解析json
        wai = new JSONObject(retString);
        fenshishu = wai.getJSONObject("data").getString("follower");
    }
}
