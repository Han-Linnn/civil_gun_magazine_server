package com.jingde.equipment.app.face.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jingde.equipment.util.Logging;
import com.jingde.equipment.util.RedisUtil;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by oceanover on 2019-04-02.
 * 对接人脸识别
 *
 * @author
 */
@Service
public class FaceService {
    @Resource
    RedisUtil redisUtil;

    public Map cacheSeesion() throws Exception {
        OkHttpClient httpClient = buildHttpClient();
        Request request = new Request.Builder().url("http://192.168.0.164/api/cgi-bin/subscribe/picture").get()
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept-Encoding", "identity")
                .build();
        Response response = httpClient.newCall(request).execute();
        Logging.info("code ### " + response.code());
        HashMap<Object, Object> headers = new HashMap<>();
        // 未授权
        if (response.code() == 401) {
            String authInfo = response.header("WWW-Authenticate");
            redisUtil.set("authInfo", authInfo);
            Logging.info("authInfo ### " + authInfo);
            String setCookie = response.header("Set-Cookie");
            String sessionId = setCookie.split("=")[1];
            String cookie = String.format("SessionID=%s", sessionId);
            redisUtil.set("cookie", cookie);
            Logging.info("cookie ### " + sessionId);
            String uri = "/api/cgi-bin/rpc";
            String authorization = auth(authInfo, "POST", uri);
            Logging.info("authorization ### " + authorization);
            headers.put("Authorization", authorization);
            headers.put("Cookie", cookie);
        }
        return headers;
    }

    // 人脸识别记录查询
    public Map getFaceLogInfo() throws Exception {
        String ip = "192.168.0.164";
        String uri = "/api/cgi-bin/rpc";
        String url = String.format("http://%s%s", ip, uri);
        RequestBody requestBody = recordFindRequestBody(null, null);
        OkHttpClient httpClient = buildHttpClient();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response = httpClient.newCall(request).execute();
        Logging.info("code ### " + response.code());
        // 未授权
        if (response.code() == 401) {
            String authInfo = response.header("WWW-Authenticate");
            Logging.info("authInfo ### " + authInfo);
            if (authInfo != null) {
                String authorization = auth(authInfo, "POST", uri);
                Logging.info("authorization ### " + authorization);
                String cookie = response.header("Set-Cookie");
                String sessionId = cookie.split("=")[1];
                Logging.info("sessionId ### " + sessionId);
                Request req = new Request.Builder()
                        .header("Cookie", String.format("SessionID=%s", sessionId))
                        .header("Authorization", authorization)
                        .url(url)
                        .post(requestBody)
                        .build();
                Response res = httpClient.newCall(req).execute();
                Logging.info(res.code());
                if (res.code() == 200) {
                    if (res.isSuccessful()) {
                        String responseStr = res.body().string();
                        Logging.info(responseStr);
                        if (responseStr != null) {
                            ObjectMapper mapper = new ObjectMapper();
                            return mapper.readValue(responseStr, new TypeReference<HashMap<String, Object>>() {
                            });
                        }
                    }
                }
            }
        }
        return null;
    }

    // 人脸识别记录查询
    public Map _getFaceLogInfo() throws Exception {
        String ip = "192.168.0.164";
        String uri = "/api/cgi-bin/rpc";
        String url = String.format("http://%s%s", ip, uri);
        RequestBody requestBody = recordFindRequestBody(null, null);
        String authInfo = (String) redisUtil.get("authInfo");
        Logging.info("authInfo ### " + authInfo);
        if (authInfo != null) {
            String authorization = auth(authInfo, "POST", uri);
            Logging.info("authorization ### " + authorization);
            String cookie = (String) redisUtil.get("cookie");
            Logging.info("cookie ### " + cookie);
            Request req = new Request.Builder()
                    .addHeader("Authorization", authorization)
                    .addHeader("Cookie", cookie)
                    .addHeader("Accept", "application/json")
                    .addHeader("Connection", "close")
                    .addHeader("Accept-Encoding", "identity")
                    .url(url)
                    .post(requestBody)
                    .build();
            OkHttpClient httpClient = buildHttpClient();
            Response res = httpClient.newCall(req).execute();
            Logging.info(res.code());
            if (res.code() == 200) {
                if (res.isSuccessful()) {
                    String responseStr = res.body().string();
                    Logging.info(responseStr);
                    if (responseStr != null) {
                        ObjectMapper mapper = new ObjectMapper();
                        return mapper.readValue(responseStr, new TypeReference<HashMap<String, Object>>() {
                        });
                    }
                }
            }
        }

        return null;
    }

    public void test() throws Exception {
        String ip = "192.168.0.164";
        String uri = "/api/cgi-bin/rpc";
        String url = String.format("http://%s%s", ip, uri);
        String jsonString = requestBody(null, null);
        System.out.println(jsonString);

        String authInfo = (String) redisUtil.get("authInfo");
        Logging.info("authInfo ### " + authInfo);
        String authorization = auth(authInfo, "POST", uri);
        Logging.info("authorization ### " + authorization);
        String cookie = (String) redisUtil.get("cookie");

        String res = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000)
                .build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setHeader("Authorization", authorization);
        httpPost.setHeader("Cookie", cookie);

        StringEntity entity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
        httpPost.setEntity(entity);

        CloseableHttpResponse response = httpclient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
        System.out.println(statusCode);//200
        if (statusCode == HttpStatus.SC_OK) {
            HttpEntity respEnt = response.getEntity();
            res = EntityUtils.toString(respEnt, "UTF-8");
            System.out.println(res);
            JSONObject jsonObject = JSONObject.parseObject(res);//响应结果
        }
        System.out.println(res);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resp", res);
        Logging.info(jsonObject);
    }

    private OkHttpClient buildHttpClient() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(new LoggingInterceptor())
//                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    private static String getMD5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    private String auth(String authInfo, String method, String uri) throws UnsupportedEncodingException {
        String username = "developer";
        String password = "jd12345678";
        String[] parts = authInfo.split(",");
        HashMap<String, String> authMap = new HashMap<>();
        for (String part : parts) {
            String[] result = part.split("=");
            // 去掉首尾空格，去掉双引号
            String key = result[0].trim().replace("\"", "");
            String value = result[1].trim().replace("\"", "");
            authMap.put(key, value);
        }

        String realm = authMap.get("Digest realm");
//        String qop = authMap.get("qop");
        String nonce = authMap.get("nonce");
        String opaque = authMap.get("opaque");

        String cnonce = "ksjdfljwofsldj4687skjd";
        String nc = "00000001";
        String A1 = username + ":" + realm + ":" + password;
        String A2 = method + ":" + uri;
        String HA1 = getMD5(A1);
        String HA2 = getMD5(A2);

        String response = getMD5(String.format("%s:%s:%s:%s:%s:%s", HA1, nonce, nc, cnonce, "auth", HA2));
        return String.format("Digest username=\"%s\", realm=\"%s\", nonce=\"%s\", uri=\"%s\", qop=\"auth\", cnonce=\"%s\", response=\"%s\", opaque=\"%s\", nc=\"%s\""
                , username, realm, nonce, uri, cnonce, response, opaque, nc);
    }

    private Map requestBodyMap(String startTime, String endTime) {
        HashMap<Object, Object> condition = new HashMap<>();
        condition.put("AccessType", "Face");
        if (startTime != null) {
            condition.put("StartTime", startTime);
        }
        if (endTime != null) {
            condition.put("EndTime", endTime);
        }
        condition.put("Offset", 0);
        condition.put("Limit", 10);
        HashMap<Object, Object> params = new HashMap<>();
        params.put("Condition", condition);
        HashMap<Object, Object> requestMap = new HashMap<>();
        requestMap.put("method", "accessRecord.find");
        requestMap.put("id", "12");
        requestMap.put("params", params);
        return requestMap;
    }

    private String requestBody(String startTime, String endTime) {
        HashMap<Object, Object> condition = new HashMap<>();
        condition.put("AccessType", "Face");
        if (startTime != null) {
            condition.put("StartTime", startTime);
        }
        if (endTime != null) {
            condition.put("EndTime", endTime);
        }
        condition.put("Offset", 0);
        condition.put("Limit", 10);
        HashMap<Object, Object> params = new HashMap<>();
        params.put("Condition", condition);
        HashMap<Object, Object> requestMap = new HashMap<>();
        requestMap.put("method", "accessRecord.find");
        requestMap.put("id", "12");
        requestMap.put("params", params);
        return JSON.toJSONString(requestMap);
    }

    private RequestBody recordFindRequestBody(String startTime, String endTime) {
        HashMap<Object, Object> condition = new HashMap<>();
        condition.put("AccessType", "Face");
        if (startTime != null) {
            condition.put("StartTime", startTime);
        }
        if (endTime != null) {
            condition.put("EndTime", endTime);
        }
        condition.put("Offset", 0);
        condition.put("Limit", 10);
        HashMap<Object, Object> params = new HashMap<>();
        params.put("Condition", condition);
        HashMap<Object, Object> requestMap = new HashMap<>();
        requestMap.put("method", "accessRecord.find");
        requestMap.put("id", "12");
        requestMap.put("params", params);
        String jsonString = JSON.toJSONString(requestMap);
        Logging.info(jsonString);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return RequestBody.create(JSON, jsonString);
    }
}
