package com.helloalanturing.framework.utils;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Pactera on 2017/6/10.
 * This is a tool class for http requests.
 */
public class HttpHelper {


    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @return URL 所代表远程资源的响应结果
     */
    private static String sendGet(String url) {
        StringBuffer buffer = new StringBuffer();
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            //URLConnection connection = realUrl.openConnection();
            HttpURLConnection httpUrlConn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            //connection.setRequestProperty("accept", "*/*");
            //connection.setRequestProperty("connection", "Keep-Alive");
            //connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            httpUrlConn.setRequestMethod("GET");
            // 建立实际的连接
            httpUrlConn.connect();
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            httpUrlConn.disconnect();
            inputStream = null;
            System.out.println(buffer);
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return buffer.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param urlString   发送请求的 URL
     * @param paramsString 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String urlString, String paramsString) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(urlString);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
//            System.out.println("paramsString = " + paramsString);
            // 发送请求参数
            out.print(paramsString.trim());
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
//        System.out.println("result = " + result.toString());
        return result.toString();
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        //发送 GET 请求
        // String s=HttpRequestUtis.sendGet("http://localhost:6144/Home/RequestString", "key=123&v=456");
        //System.out.println(s);
        //String jsonStr = HttpRequestUtis.sendGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxb6046cd738463fbe&secret=45e5e0305ddee92705fd6399b2377fad");
        //AccessToken token =(AccessToken) JsonUtils.jsonToObject(jsonStr, AccessToken.class);
        //System.out.println(token);
        //发送 POST 请求
        //String sr=HttpRequestUtis.sendPost("http://www.baidu.com", "key=nba");
        // System.out.println(sr);

    /*	String url=URLEncoder.encode("http://www.daolangliu.cn/wenxin/auth/authToken.do");
        System.out.println(url);
		System.out.println(RequestUrlType.authorizeUrl.replace("APPID", Account.appID).replace("REDIRECT_URI", url).replace("SCOPE", ScopeType.SNSAPI_INFO));*/

        //获取用户信息
        /*String userInfo = "{\"openid\":\"oQXFtwIQ3HR0RbkHdTts8kS3OLDA\",\"nickname\":\"邢涛\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"红河\",\"province\":\"云南\",\"country\":\"中国\",\"headimgurl\":\"http://wx.qlogo.cn/mmopen/nnbhbN4vgs2uReyLZOsI0piaib0LY0wp7tUzZj51CCibDFBSoa96jvrhxu008diaicO6FOpxZ3akNsOZMlCYKiaS9nYicDJN0PZGGwK/0\",\"privilege\":[]}";
        AuthUserInfo authUserInfo = (AuthUserInfo)JsonUtils.jsonToObject(userInfo.replace("\\/", "/"), AuthUserInfo.class);
		System.out.println(authUserInfo);*/


        String token = sendGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxb6046cd738463fbe&secret=45e5e0305ddee92705fd6399b2377fad");
        System.out.println(token);
    }
}
