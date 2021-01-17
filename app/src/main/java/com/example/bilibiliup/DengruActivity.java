package com.example.bilibiliup;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class DengruActivity extends AppCompatActivity {

    dengru dengru_=new dengru();
    Timer timer = null;
    boolean shifou_zhengzai_zhixing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dengru);
        timer=new Timer();
        Context context=this;
        ImageView imageView=(ImageView)findViewById(R.id.imageView5);
        TextView textView=(TextView)findViewById(R.id.textView36);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ////检查扫码是否成功
                        //            string[] jieguo= daima.dengru.jiancha_shifou();
                        //            if(jieguo!=null)
                        //            {
                        //                //写入
                        //                daima.Peizhi.xieru_dengru(jieguo[0], jieguo[1], jieguo[2], jieguo[3],jieguo[4]);
                        //                //关闭窗口
                        //                this.Close();
                        //
                        //            }
                        //
                        //            //判断是否重新获取
                        //            if (daima.dengru.shenyushijian < 1)
                        //            {
                        //                daima.dengru.huoqu_erweima();
                        //                image1.Source = new BitmapImage(new Uri(lujing + "二维码\\"+daima.dengru.tupian_mingzi));
                        //            }
                        //            //减少时间
                        //            daima.dengru.shenyushijian--;
                        //            //同步UI
                        //            App.Current.Dispatcher.Invoke(new Action(() =>
                        //            {
                        //                textblock1.Text = "将在 " + daima.dengru.shenyushijian + "s 后刷新";
                        //            }));
                        //            //刷新图片
                        //            image1.Source = new BitmapImage(new Uri(lujing + "二维码\\" + daima.dengru.tupian_mingzi));
                        //            shifou_zhengzai_zhixing = false;
                        if(shifou_zhengzai_zhixing==true)
                        {
                            return;
                        }
                        shifou_zhengzai_zhixing=true;
                        String[] jieguo= dengru_.jiancha_shifou();
                        try {

                            if(jieguo!=null)
                            {
                                //写入
                                yonghu yonghu_ = (yonghu) (context.getApplicationContext());
                                //判断是否已经完成
                                if(!Objects.equals(yonghu_.peizhi_.dedeUserID, ""))
                                {
                                    finish();
                                    return;
                                }
                                try {
                                    yonghu_.peizhi_.xieru_dengru(context,jieguo[0],jieguo[1], jieguo[2], jieguo[3],jieguo[4]);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                //返回
                                finish();
                                return;
                            }
                        }
                        catch (Exception exc)
                        {
                            exc.printStackTrace();
                        }

                        //判断是否重新获取
                        if(dengru_.shenyushijian<1)
                        {
                            try {
                                //获得图片
                                imageView.setImageBitmap(dengru_.huoqu_erweima(context));
                            } catch (FileNotFoundException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        //减少时间
                        dengru_.shenyushijian--;
                        //同步UI
                        textView.setText("将在"+dengru_.shenyushijian+"s 后刷新");
                        //刷新图片
                        shifou_zhengzai_zhixing=false;
                    }
                });
            }
        }, 900,900);
    }
}