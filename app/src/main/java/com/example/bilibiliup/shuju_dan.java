package com.example.bilibiliup;
import org.json.JSONObject;

public class shuju_dan {
    /// <summary>
    /// 视频id
    /// </summary>
    public String id = "";
    /// <summary>
    /// 名字
    /// </summary>
    public   String shiping_mingzi_shou = "";
    /// <summary>
    /// 播放数
    /// </summary>
    public   String bofenshu = "";
    /// <summary>
    /// 点赞
    /// </summary>
    public   String dianzan = "";
    /// <summary>
    /// 投币
    /// </summary>
    public   String toubi = "";
    /// <summary>
    /// 收藏
    /// </summary>
    public   String shoucang = "";
    /// <summary>
    /// 评论
    /// </summary>
    public   String pinglun = "";
    /// <summary>
    /// 弹幕
    /// </summary>
    public   String danmu = "";
    /// <summary>
    /// 分享
    /// </summary>
    public   String fenxiang = "";
    /// <summary>
    /// 初始化
    /// </summary>

    public void chushihua(String id_,String[] cook) throws Exception {
        id = id_;

        //获取视频信息
        String serviceAddress = "https://api.bilibili.com/x/web-interface/view?bvid=" + id ; //请求地址
        String retString = gongju.http_get(serviceAddress, cook);

        //解析json
        JSONObject wai =new JSONObject(retString);

        dianzan = wai.getJSONObject("data").getJSONObject("stat").getString("like");
        toubi = wai.getJSONObject("data").getJSONObject("stat").getString("coin");
        shoucang = wai.getJSONObject("data").getJSONObject("stat").getString("favorite");
        pinglun = wai.getJSONObject("data").getJSONObject("stat").getString("reply");
        danmu = wai.getJSONObject("data").getJSONObject("stat").getString("danmaku");
        fenxiang = wai.getJSONObject("data").getJSONObject("stat").getString("share");
        bofenshu = wai.getJSONObject("data").getJSONObject("stat").getString("view");
        shiping_mingzi_shou = wai.getJSONObject("data").getString("title");
    }
}
