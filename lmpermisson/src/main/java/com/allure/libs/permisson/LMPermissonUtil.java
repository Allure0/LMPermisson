package com.allure.libs.permisson;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：luomin
 * 邮箱：asddavid@163.com
 * 1、判断是否具有权限
 * 2、没有权限就请求权限，有权限就执行逻辑
 * 3、请求授权后的回调
 */

public class LMPermissonUtil {


    public LMPermissonUtil() {
//        throw new RuntimeException(" 全部都是静态方法");
    }

    /**
     * 权限判断是否所有都获取了权限
     *
     * @param object
     * @param permissions
     * @return
     */
    public static boolean hasPermission(Object object, String... permissions) {
        Context mContext = getActiivtyContext(object);
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    public static Activity getActiivtyContext(Object object) {
        if (object instanceof Activity) {
            return   (Activity) object;
        } else if (object instanceof Fragment) {
            return  ((Fragment) object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            return  ((Fragment) object).getActivity();
        }
        return null;
    }


    /**
     * 请求权限
     *
     * @param object
     * @param code
     * @param permissions
     */
    public static void requestPermission(Object object, int code, String... permissions) {
        ActivityCompat.requestPermissions(getActiivtyContext(object), permissions, code);
    }


    /**
     * 回调处理,任何一个失败都表示失败
     *
     * @param context
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(Context context, int requestCode, String[] permissions, int[] grantResults) {
        List<String> grantedList = new ArrayList<>();
        List<String> deniedList = new ArrayList<>();
        deniedList.clear();
        grantedList.clear();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                grantedList.add(permissions[i]);
            } else {
                deniedList.add(permissions[i]);
            }
        }
        if (deniedList.size() == 0) {//没有拒绝的权限
            executeSucceedMethod(context, requestCode);
        } else {
            executeFailedMethod(context, requestCode);
        }

    }


    /**
     * 获取被拒绝的权限
     * @param object
     * @param permissions
     * @return
     */
    public  static  List<String> getDeniedListPermisson(Object object,String[] permissions){
        List<String> deniedPermissions = new ArrayList<>();
        for (String requestPermission:permissions){
            if(ContextCompat.checkSelfPermission(getActiivtyContext(object), requestPermission)
                    == PackageManager.PERMISSION_DENIED){
                deniedPermissions.add(requestPermission);
            }
        }
        return deniedPermissions;
    }
    
    /**
     * @param reflectObject 类
     * @param method        参数
     */
    private static void executeMethod(Object reflectObject, Method method) {
        try {
            method.setAccessible(true); // 允许执行私有方法
            method.invoke(reflectObject, new Object[]{});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void executeSucceedMethod(Object reflectObject, int requestCode) {
        Method[] methods = reflectObject.getClass().getDeclaredMethods();

        for (Method method : methods) {
            Log.e("SucceedMethod", method + "");
            PermissionSucceed succeedMethod = method.getAnnotation(PermissionSucceed.class);
            if (succeedMethod != null) {
                int methodCode = succeedMethod.requestCode();
                if (methodCode == requestCode) {
                    Log.e("SucceedMethod", "PermissionSucceed：" + method);
                    executeMethod(reflectObject, method);
                }
            }
        }
    }

    public static void executeFailedMethod(Object reflectObject, int requestCode) {
        Method[] methods = reflectObject.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Log.e("FailedMethod", method + "");
            PermissionFailed succeedMethod = method.getAnnotation(PermissionFailed.class);
            if (succeedMethod != null) {
                int methodCode = succeedMethod.requestCode();
                if (methodCode == requestCode) {
                    Log.e("FailedMethod", "PermissionFailed：" + method);
                    executeMethod(reflectObject, method);
                }
            }
        }
    }


}
