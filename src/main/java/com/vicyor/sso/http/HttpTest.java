package com.vicyor.sso.http;

import com.sun.xml.internal.ws.util.StreamUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 作者:姚克威
 * 时间:2020/3/18 1:01
 **/
public class HttpTest {
    public static void main(String[] args) {
        //定义访问地址
        try {
            URL url=new URL("https://way.jd.com/he/freeweather");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            //携带参数
            connection.setDoOutput(true);
            StringBuilder params=new StringBuilder();
            params.append("city=").append("beijing").append("&appkey=").append("512f789e72e7dbd93c85b5b5b576289a");
            //将params放入请求body中
            connection.getOutputStream().write(params.toString().getBytes("UTF-8"));
            //发送请求
            connection.connect();
            //获取相应
            InputStream in = connection.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(in));
            String line=null;
            while ((line=br.readLine())!=null){
                System.out.println(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
