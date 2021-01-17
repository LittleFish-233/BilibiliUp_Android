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

public class Widgets_id {
    public JSONObject widgets_id=null;

    public void duqu_widgets_ids(Context context) throws FileNotFoundException, JSONException {
        //打开文件
        try
        {
            FileInputStream fis = context.openFileInput("widgets_ids_1.json");
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

            widgets_id=new JSONObject(contents);
        }
        catch (Exception e)
        {
            widgets_id=new JSONObject();
            e.printStackTrace();
        }


    }

    //保存新的id
    public void baocun_widgets_id(Context context, int id, String up) throws JSONException, FileNotFoundException {
        //检查是否存在
        if(widgets_id==null)
        {
            duqu_widgets_ids(context);
        }
        //检查是否有重复
        try
        {
            widgets_id.remove(String.valueOf(id));
        }
        catch (Exception exc)
        {

        }
        //添加
        widgets_id.put(String.valueOf(id),up);
        //保存
        //保存到文件
        String filename = "widgets_ids_1.json";
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE))
        {
            fos.write(widgets_id.toString().getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    //获取绑定的id
    public String huoqu_up(Context context,int id) throws FileNotFoundException, JSONException {
        if(widgets_id==null)
        {
            duqu_widgets_ids(context);
        }
        try
        {
            return widgets_id.getString(String.valueOf(id));
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
    }
    //删除id
    public void shanchu(Context context,int id) throws FileNotFoundException, JSONException {
        if(widgets_id==null)
        {
            duqu_widgets_ids(context);
        }
        try
        {
            widgets_id.remove(String.valueOf(id));
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return;
        }
    }
}
