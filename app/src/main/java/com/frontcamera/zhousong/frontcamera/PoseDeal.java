package com.frontcamera.zhousong.frontcamera;

/**
 * Created by vr on 2017/6/3.
 */

public class PoseDeal {
    private final static double YAW_MAX = 20;
    private final static double PITCH_MAX = 20;
    private final static double ROLL_MAX = 20;
    private final static long ALARM_TIME = 10 * 1000;
    private static long statusTime = 0;
    private static int yawStatus = 0;
    private static int pitchStatus = 0;
    private static int rollStatus = 0;

    public static void posePlay(double yaw, double pitch, double roll){
        int nowYawStatus = 0;
        int nowPitchStatus = 0;
        int nowRollStatus = 0;
        boolean remainFlag = false;

        if (yaw < -YAW_MAX){
            nowYawStatus = -1;
        } else if (yaw > YAW_MAX){
            nowYawStatus = 1;
        }
        if (pitch < -PITCH_MAX){
            nowPitchStatus = -1;
        }else if (pitch > PITCH_MAX){
            nowPitchStatus = 1;
        }
        if (roll < -ROLL_MAX){
            nowRollStatus = -1;
        }else if (roll > ROLL_MAX){
            nowRollStatus = 1;
        }

        if (nowYawStatus == 0 && nowPitchStatus == 0 && nowRollStatus == 0){
        }else {
            if (nowYawStatus != 0 && nowYawStatus == yawStatus){
                remainFlag = true;
            }
            if (nowPitchStatus != 0 && nowPitchStatus == pitchStatus){
                remainFlag = true;
            }
            if (nowRollStatus != 0 && nowRollStatus == rollStatus){
                remainFlag = true;
            }
            if (remainFlag == true){
                if (System.currentTimeMillis() - statusTime > ALARM_TIME){
                    MainActivity.vib.vibrate(1000);
                }
            }else {
                statusTime = System.currentTimeMillis();
            }
        }
        yawStatus = nowYawStatus;
        pitchStatus = nowPitchStatus;
        rollStatus = nowRollStatus;

    }
}
