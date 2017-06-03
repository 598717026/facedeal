package com.frontcamera.zhousong.frontcamera;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;

import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.CommonOperateEx;
import com.megvii.cloud.http.FaceSetOperate;
import com.megvii.cloud.http.Response;
import com.megvii.cloud.http.CommonOperateEx;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

/**
 * Created by zhousong on 2016/9/28.
 * 单独的任务类。继承AsyncTask，来处理从相机实时获取的耗时操作
 */
public class FaceTask extends AsyncTask {
    private byte[] mData;
    Camera mCamera;
    private static boolean runningflag = false;
    private static final String TAG = "CameraTag";


    private static final String key = "GT4C6ju_JcOaygMbiK-yAp9cszoht44B";//api_key
    private static final String secret = "xwdADecHjn76hwVEqWmpq7uBbDCtuUwe";//api_secret

//    private static CommonOperate commonOperate = new CommonOperate(key, secret, false);
private static CommonOperateEx commonOperate = new CommonOperateEx(key, secret, false);
    private static FaceSetOperate FaceSet = new FaceSetOperate(key, secret, false);

//    private static SoundPool soundPool= new SoundPool(10, AudioManager.STREAM_SYSTEM,5);

    //构造函数
    FaceTask(byte[] data, Camera camera) {
        if (runningflag == true)
            return;

        this.mData = data;
        this.mCamera = camera;

    }

    @Override
    protected Object doInBackground(Object[] params) {

        if (runningflag == true)
            return null;

        runningflag = true;

        Camera.Parameters parameters = mCamera.getParameters();
        int imageFormat = parameters.getPreviewFormat();
        int w = parameters.getPreviewSize().width;
        int h = parameters.getPreviewSize().height;

        Rect rect = new Rect(0, 0, w, h);
        YuvImage yuvImg = new YuvImage(mData, imageFormat, w, h, null);
        try {
            ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
            yuvImg.compressToJpeg(rect, 100, outputstream);
            Bitmap rawbitmap = BitmapFactory.decodeByteArray(outputstream.toByteArray(), 0, outputstream.size());

            try {
                int width = rawbitmap.getWidth();
                int height = rawbitmap.getHeight();
                // 设置想要的大小
                int newWidth = 320;
                int newHeight = 480;
                // 计算缩放比例
                float scaleWidth = ((float) 1) / 4;
                float scaleHeight = ((float) 1) / 4;
                // 取得想要缩放的matrix参数
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                // 得到新的图片
                Bitmap newbm = Bitmap.createBitmap(rawbitmap, 0, 0, width, height, matrix,
                        true);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                newbm.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                Response response1 = commonOperate.detectByte(baos.toByteArray(), 0, null);
//                String faceToken1 = getFaceToken(response1);
//                Response response2 = commonOperate.analyze(faceToken1);
//                Log.d(TAG, new String(response2.getContent()));
//
//                MainActivity.vib.vibrate(1000);

                FaceDetect.faceDeal(newbm);

            }catch (Exception e){
                e.printStackTrace();
            }
            Log.i(TAG, "onPreviewFrame: rawbitmap:" + rawbitmap.toString() + "(" + w + "," + h + ")");

            //若要存储可以用下列代码，格式为jpg
//             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/fp.jpg"));
//            yuvImg.compressToJpeg(rect, 100, bos);
//            bos.flush();
//            bos.close();
//            mCamera.startPreview();

        } catch (Exception e) {
            Log.e(TAG, "onPreviewFrame: 获取相机实时数据失败" + e.getLocalizedMessage());
        }

        runningflag = false;

        return null;
    }

    private String getFaceToken(Response response) throws JSONException {
        if (response.getStatus() != 200) {
            return new String(response.getContent());
        }
        String res = new String(response.getContent());
        Log.e("response", res);
        JSONObject json = new JSONObject(res);
        String faceToken = json.optJSONArray("faces").optJSONObject(0).optString("face_token");
        return faceToken;
    }

    private void dealFace(Bitmap bitmap){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            Response response1 = commonOperate.detectByte(baos.toByteArray(), 0, null);
            String faceToken1 = getFaceToken(response1);
            Response response2 = commonOperate.analyze(faceToken1);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
