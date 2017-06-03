package com.frontcamera.zhousong.frontcamera;

import android.graphics.Bitmap;
import android.util.Log;

import com.megvii.cloud.http.CommonOperateEx;
import com.megvii.cloud.http.FaceSetOperate;
import com.megvii.cloud.http.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by vr on 2017/6/3.
 */

public class FaceDetect {
    private static final String key = "GT4C6ju_JcOaygMbiK-yAp9cszoht44B";//api_key
    private static final String secret = "xwdADecHjn76hwVEqWmpq7uBbDCtuUwe";//api_secret

    private static CommonOperateEx commonOperate = new CommonOperateEx(key, secret, false);
    private static FaceSetOperate FaceSet = new FaceSetOperate(key, secret, false);
    private static String faceToken;
    private static double yaw;
    private static double pitch;
    private static double roll;

    public static void faceDeal(Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            Response response1 = commonOperate.detectByte(baos.toByteArray(), 0, null);
            getFaceToken(response1);
            Response response2 = commonOperate.analyze(faceToken);
            getEuler(response2);
            PoseDeal.posePlay(yaw, pitch, roll);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getFaceToken(Response response) throws JSONException {
        if (response.getStatus() != 200) {
            throw new JSONException("May net error");
        }
        String res = new String(response.getContent());
        Log.e("response", res);
        JSONObject json = new JSONObject(res);
        faceToken = json.optJSONArray("faces").optJSONObject(0).optString("face_token");
    }

    private static void getEuler(Response response) throws JSONException {
        if (response.getStatus() != 200) {
            throw new JSONException("May net error");
        }
        String res = new String(response.getContent());
        Log.e("response", res);
        JSONObject json = new JSONObject(res);
        yaw = json.optJSONArray("faces").optJSONObject(0).optJSONObject("attributes").optJSONObject("headpose").getDouble("yaw_angle");
        pitch = json.optJSONArray("faces").optJSONObject(0).optJSONObject("attributes").optJSONObject("headpose").getDouble("pitch_angle");
        roll = json.optJSONArray("faces").optJSONObject(0).optJSONObject("attributes").optJSONObject("headpose").getDouble("roll_angle");
        Log.e("getEuler", "(" + yaw + "," + pitch + "," + roll + ")");
    }
}
