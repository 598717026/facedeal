package com.megvii.cloud.http;

import com.megvii.cloud.http.HttpRequest;
import com.megvii.cloud.http.Response;

import java.util.HashMap;

/**
 * Created by vr on 2017/6/1.
 */

public class CommonOperateEx {
    private String apiKey = "";
    private String apiSecret = "";
    private String webUrl;

    public CommonOperateEx(String apiKey, String apiSecret, boolean isInternationalVersion) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        if(isInternationalVersion) {
            this.webUrl = "https://api-us.faceplusplus.com/facepp/v3";
        } else {
            this.webUrl = "https://api-cn.faceplusplus.com/facepp/v3";
        }

    }

    public static Response postTo(String url, HashMap<String, String> map, HashMap<String, byte[]> fileByte) throws Exception {
        return HttpRequest.post(url, map, fileByte);
    }

    public Response detectUrl(String imageUrl, int landmark, String attributes) throws Exception {
        String url = this.webUrl + "/" + "detect";
        HashMap map = new HashMap();
        map.put("api_key", this.apiKey);
        map.put("api_secret", this.apiSecret);
        map.put("image_url", imageUrl);
        map.put("return_landmark", String.valueOf(landmark));
        if(!HttpRequest.isEmpty(attributes)) {
            map.put("return_attributes", attributes);
        }

        return HttpRequest.post(url, map, (HashMap)null);
    }

    public Response detectByte(byte[] fileByte, int landmark, String attributes) throws Exception {
        String url = this.webUrl + "/" + "detect";
        HashMap map = new HashMap();
        HashMap fileMap = new HashMap();
        map.put("api_key", this.apiKey);
        map.put("api_secret", this.apiSecret);
        fileMap.put("image_file", fileByte);
        if(landmark != 0) {
            map.put("return_landmark", String.valueOf(landmark));
        }

        if(!HttpRequest.isEmpty(attributes)) {
            map.put("return_attributes", attributes);
        }

        return HttpRequest.post(url, map, fileMap);
    }

    public Response detectBase64(String base64, int landmark, String attributes) throws Exception {
        String url = this.webUrl + "/" + "detect";
        HashMap map = new HashMap();
        map.put("api_key", this.apiKey);
        map.put("api_secret", this.apiSecret);
        map.put("image_base64", base64);
        if(landmark != 0) {
            map.put("return_landmark", String.valueOf(landmark));
        }

        if(!HttpRequest.isEmpty(attributes)) {
            map.put("return_attributes", attributes);
        }

        return HttpRequest.post(url, map, (HashMap)null);
    }

    public Response compare(String faceToken1, String image_url1, byte[] fileByte1, String base64_1, String faceToken2, String image_url2, byte[] fileByte2, String base64_2) throws Exception {
        String url = this.webUrl + "/" + "compare";
        HashMap map = new HashMap();
        HashMap fileByte = new HashMap();
        map.put("api_key", this.apiKey);
        map.put("api_secret", this.apiSecret);
        if(!HttpRequest.isEmpty(faceToken1)) {
            map.put("face_token1", faceToken1);
        }

        if(!HttpRequest.isEmpty(faceToken2)) {
            map.put("face_token2", faceToken2);
        }

        if(!HttpRequest.isEmpty(image_url1)) {
            map.put("image_url1", image_url1);
        }

        if(!HttpRequest.isEmpty(image_url2)) {
            map.put("image_url2", image_url2);
        }

        if(fileByte1 != null) {
            fileByte.put("image_file1", fileByte1);
        }

        if(fileByte2 != null) {
            fileByte.put("image_file2", fileByte2);
        }

        if(!HttpRequest.isEmpty(base64_1)) {
            map.put("image_base64_1", base64_1);
        }

        if(!HttpRequest.isEmpty(base64_2)) {
            map.put("image_base64_2", base64_2);
        }

        return HttpRequest.post(url, map, fileByte);
    }

    public Response searchByFaceSetToken(String faceToken, String image_url, byte[] buff, String faceSetToken, int returnResultCount) throws Exception {
        String url = this.webUrl + "/" + "search";
        HashMap map = new HashMap();
        HashMap fileMap = new HashMap();
        map.put("api_key", this.apiKey);
        map.put("api_secret", this.apiSecret);
        map.put("faceset_token", faceSetToken);
        map.put("return_result_count", String.valueOf(returnResultCount));
        if(faceToken != null) {
            map.put("face_token", faceToken);
        }

        if(image_url != null) {
            map.put("image_url", image_url);
        }

        if(buff != null) {
            fileMap.put("image_file", buff);
        }

        return HttpRequest.post(url, map, fileMap);
    }

    public Response searchByOuterId(String faceToken, String image_url, byte[] buff, String outerId, int returnResultCount) throws Exception {
        String url = this.webUrl + "/" + "search";
        HashMap map = new HashMap();
        HashMap fileMap = new HashMap();
        map.put("api_key", this.apiKey);
        map.put("api_secret", this.apiSecret);
        map.put("outer_id", outerId);
        map.put("return_result_count", String.valueOf(returnResultCount));
        if(faceToken != null) {
            map.put("face_token", faceToken);
        }

        if(image_url != null) {
            map.put("image_url", image_url);
        }

        if(buff != null) {
            fileMap.put("image_file", buff);
        }

        return HttpRequest.post(url, map, fileMap);
    }
    public Response analyze(String faceToken) throws Exception {
        String url = this.webUrl + "/" + "face/analyze";
        HashMap map = new HashMap();
        HashMap fileMap = new HashMap();
        map.put("api_key", this.apiKey);
        map.put("api_secret", this.apiSecret);
        map.put("face_tokens", faceToken);
        map.put("return_landmark", String.valueOf(0));
        map.put("return_attributes","headpose");

        return HttpRequest.post(url, map, fileMap);
    }
}
