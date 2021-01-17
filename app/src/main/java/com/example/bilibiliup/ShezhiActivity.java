package com.example.bilibiliup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.util.Objects;

public class ShezhiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shezhi);

        //初始化
        yonghu yonghu_ = (yonghu) this.getApplication();
        if(yonghu_.shifou_chushihua==false)
        {
            try {
                yonghu_.peizhi_.duqu_jiben(this);
                yonghu_.shifou_chushihua=true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            yonghu_.shifou_chushihua=true;
        }
        //设置值
        ((TextView)findViewById(R.id.editTextTextPersonName)).setText(yonghu_.peizhi_.up);
        ((TextView)findViewById(R.id.editTextTextPersonName2)).setText(yonghu_.peizhi_.dedeUserID);
        ((TextView)findViewById(R.id.editTextTextPersonName3)).setText(yonghu_.peizhi_.dedeUserID__ckMd5);
        ((TextView)findViewById(R.id.editTextTextPersonName4)).setText(yonghu_.peizhi_.expires);
        ((TextView)findViewById(R.id.editTextTextPersonName5)).setText(yonghu_.peizhi_.sESSDATA);
        ((TextView)findViewById(R.id.editTextTextPersonName6)).setText(yonghu_.peizhi_.bili_jct);
        ((TextView)findViewById(R.id.editTextTextPersonName7)).setText(yonghu_.peizhi_.sid);

        ((SeekBar)findViewById(R.id.seekBar2)).setProgress((int) (yonghu_.peizhi_.toumingdu*100));
        ((SeekBar)findViewById(R.id.seekBar3)).setProgress((int) (yonghu_.peizhi_.donghua_shijian*10));
        ((SeekBar)findViewById(R.id.seekBar4)).setProgress((int) yonghu_.peizhi_.huoqu_jiange);


        ((SeekBar)findViewById(R.id.seekBar2)).setOnSeekBarChangeListener(toumingdu_gaibian);

        ((SeekBar)findViewById(R.id.seekBar3)).setOnSeekBarChangeListener(yanchi_gaibian);

        ((SeekBar)findViewById(R.id.seekBar4)).setOnSeekBarChangeListener(jiange_gaibian);

        //提示
        if(!Objects.equals(yonghu_.peizhi_.dedeUserID, ""))
        {
            ((TextView)findViewById(R.id.textView43)).setText("已经登入的账号："+yonghu_.peizhi_.name);
            ((Button)findViewById(R.id.button4)).setText("退出登入");
        }
        else
        {
            ((TextView)findViewById(R.id.textView43)).setText("未登入账号，请登入");
            ((Button)findViewById(R.id.button4)).setText("登入");
        }
        ((TextView)findViewById(R.id.textView44)).setText("背景不透明度：" + yonghu_.peizhi_.toumingdu*100+"%");
        ((TextView)findViewById(R.id.textView45)).setText("动画延迟时间："+yonghu_.peizhi_.donghua_shijian+"s");
        ((TextView)findViewById(R.id.textView46)).setText("数据获取间隔时间："+yonghu_.peizhi_.huoqu_jiange+"s");
    }
    SeekBar.OnSeekBarChangeListener toumingdu_gaibian=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            ((TextView)findViewById(R.id.textView44)).setText("背景不透明度：" + ((SeekBar)findViewById(R.id.seekBar2)).getProgress()+"%");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    SeekBar.OnSeekBarChangeListener yanchi_gaibian=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            ((TextView)findViewById(R.id.textView45)).setText("动画延迟时间："+((SeekBar)findViewById(R.id.seekBar3)).getProgress()/(double)10+"s");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    SeekBar.OnSeekBarChangeListener jiange_gaibian=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            ((TextView)findViewById(R.id.textView46)).setText("数据获取间隔时间："+ ((SeekBar)findViewById(R.id.seekBar4)).getProgress()+"s");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    public void Baocun(View view) throws FileNotFoundException, JSONException {
        try
        {
            yonghu yonghu_ = (yonghu) (this.getApplicationContext());
            yonghu_.peizhi_.up= (String) ((TextView)findViewById(R.id.editTextTextPersonName)).getText().toString();
            yonghu_.peizhi_.dedeUserID= (String) ((TextView)findViewById(R.id.editTextTextPersonName2)).getText().toString();
            yonghu_.peizhi_.dedeUserID__ckMd5= (String) ((TextView)findViewById(R.id.editTextTextPersonName3)).getText().toString();
            yonghu_.peizhi_.expires= (String) ((TextView)findViewById(R.id.editTextTextPersonName4)).getText().toString();
            yonghu_.peizhi_.sESSDATA= (String) ((TextView)findViewById(R.id.editTextTextPersonName5)).getText().toString();
            yonghu_.peizhi_.bili_jct= (String) ((TextView)findViewById(R.id.editTextTextPersonName6)).getText().toString();
            yonghu_.peizhi_.sid= (String) ((TextView)findViewById(R.id.editTextTextPersonName7)).getText().toString();
            yonghu_.peizhi_.toumingdu=  ((SeekBar)findViewById(R.id.seekBar2)).getProgress()/(double)100;
            yonghu_.peizhi_.donghua_shijian=  ((SeekBar)findViewById(R.id.seekBar3)).getProgress()/(double)10;
            yonghu_.peizhi_.huoqu_jiange=  ((SeekBar)findViewById(R.id.seekBar4)).getProgress();

            yonghu_.peizhi_.baocun_jiben(this);
            finish();
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }

    }
    public void dengru(View view) throws FileNotFoundException, JSONException {
        yonghu yonghu_ = (yonghu) (this.getApplicationContext());
        if(Objects.equals(yonghu_.peizhi_.dedeUserID, ""))
        {
            Intent intent = new Intent(this, DengruActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            ((TextView)findViewById(R.id.editTextTextPersonName2)).setText("");
            ((TextView)findViewById(R.id.editTextTextPersonName3)).setText("");
            ((TextView)findViewById(R.id.editTextTextPersonName4)).setText("");
            ((TextView)findViewById(R.id.editTextTextPersonName5)).setText("");
            ((TextView)findViewById(R.id.editTextTextPersonName6)).setText("");
            ((TextView)findViewById(R.id.editTextTextPersonName7)).setText("");

            yonghu_.peizhi_.tuichudengru(this);
            ((TextView)findViewById(R.id.textView43)).setText("未登入账号，请登入");
            ((Button)findViewById(R.id.button4)).setText("登入");
        }
    }
}