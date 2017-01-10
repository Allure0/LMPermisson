package com.allure.libs.permisson;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * 作者：luomin
 * 邮箱：asddavid@163.com
 */

public class LMPermissonHelper {

    private Object mContext;
    private int mRequestCode;
    private String[] mRequestPermission;

    public LMPermissonHelper(Object object) {
        this.mContext = object;
    }

    public static LMPermissonHelper with(Context context) {
        return new LMPermissonHelper(context);
    }

    public static LMPermissonHelper with(Activity activity) {
        return new LMPermissonHelper(activity);
    }

    public static LMPermissonHelper with(Fragment fragmentV4) {
        return new LMPermissonHelper(fragmentV4);
    }

    public static LMPermissonHelper with(android.app.Fragment fragment) {
        return new LMPermissonHelper(fragment);
    }

    public LMPermissonHelper requestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    public LMPermissonHelper permissionStrings(String... requestPermission) {
        this.mRequestPermission = requestPermission;
        return this;
    }


    public String[] getDeniedListPermisson() {
        List<String> list = LMPermissonUtil.getDeniedListPermisson(mContext, mRequestPermission);
        return list.toArray(new String[list.size()]);
    }

    public static void onRequestPermissionsResult(Context context, int requestCode, String[] permissions, int[] grantResults){
        LMPermissonUtil.onRequestPermissionsResult(context, requestCode, permissions, grantResults);
    }

    public void requst() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            LMPermissonUtil.executeSucceedMethod(mContext, mRequestCode);
//            return;
//        }
        List<String> list = LMPermissonUtil.getDeniedListPermisson(mContext, mRequestPermission);
        //如果有任何一个权限没有请求,继续请求
        if (list.size() > 0) {
            LMPermissonUtil.requestPermission(mContext, mRequestCode, list.toArray(new String[list.size()]));
        } else {//全部允许了直接返回成功
            LMPermissonUtil.executeSucceedMethod(mContext, mRequestCode);
        }

    }
}
