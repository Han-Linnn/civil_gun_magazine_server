package com.jingde.equipment.app.face.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jingde.equipment.util.Logging;
import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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
public class FaceRecognizeService {
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
                        .header("Authorization", authorization)
                        .header("Cookie", String.format("SessionID=%s", sessionId))
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

    private OkHttpClient buildHttpClient() {
        return new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(30, TimeUnit.SECONDS).build();
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
        return String.format("Digest username=\"%s\", realm=\"%s\", nonce=\"%s\", uri=\"%s\", qop=auth, cnonce=\"%s\", response=\"%s\", opaque=\"%s\", nc=%s"
                , username, realm, nonce, uri, cnonce, response, opaque, nc);
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
