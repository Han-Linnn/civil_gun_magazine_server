package com.jingde.equipment.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jingde.equipment.app.cabinets.dto.CabinetApiDTO;

/**
 * @author
 */
public class ApiUtils {

    public static String sendGet(String url, Map<String, String> parameters) {
        String result = "";
        // 读取响应输入流
        BufferedReader in = null;
        // 存储参数
        StringBuffer sb = new StringBuffer();
        // 编码之后的参数
        String params;
        try {
            // 编码请求参数  
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8")).append("&");
                }
                String tempParams = sb.toString();
                params = tempParams.substring(0, tempParams.length() - 1);
            }
            String fullUrl = url + "?" + params;
            System.out.println(fullUrl);
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(fullUrl);
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 建立实际的连接  
            httpConn.connect();
            // 响应头部获取  
            Map<String, List<String>> headers = httpConn.getHeaderFields();
            // 遍历所有的响应头字段  
            for (String key : headers.keySet()) {
                System.out.println(key + "\t：\t" + headers.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), StandardCharsets.UTF_8));
            String line;
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送POST请求
     *
     * @param url        目的地址
     * @param parameters 请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendPost(String url, Map<String, String> parameters) {
        // 返回的结果
        String result = "";
        // 读取响应输入流
        BufferedReader in = null;
        PrintWriter out = null;
        // 处理请求参数
        //StringBuffer sb = new StringBuffer();
        // 编码之后的参数
        String params;
        try {
            JSONObject obj = (JSONObject) JSON.toJSON(parameters);
            params = obj.toString();
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(url);
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性  
            httpConn.setRequestProperty("Content-Type", "application/json");
            // 设置POST方式  
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流  
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数  
            System.out.println(obj.toString());
            out.write(params);
            // flush输出流的缓冲  
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), StandardCharsets.UTF_8));
            String line;
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        return result;
    }

    /**
     * 主函数，测试请求
     *
     * @param args
     */
    public static void main(String[] args) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("ip", "192.168.0.126");
        parameters.put("port", "80");
        parameters.put("dynamicCode", "6f39e6f42e26");
        String result = sendGet("http://192.168.0.103:7979/cabinet", parameters);
        Map<String, Object> list = new HashMap<String, Object>();
        list.put("data", JSON.parseObject(result));
        //List<CabinetApiDTO> user = new ArrayList<CabinetApiDTO>();
        //将用户信息转成list集合
        System.out.println(JSON.parseObject(JSON.parseObject(result).getString("data")).getString("cabinet"));
        //user = (List<UserVo>) JSON.parseObject(userList.get("result").toString());
        //user = JSON.parseArray(JSON.parseObject(result).getString("data"), CabinetApiDTO.class);
        CabinetApiDTO cabinetApiDTO = JSON.parseObject(JSON.parseObject(JSON.parseObject(result).getString("data")).getString("cabinet"), CabinetApiDTO.class);
        System.out.println(cabinetApiDTO.getDoor().get(1).getArea().get(0).getBulletDrawerSeat().get(0).getName());
    }
}
