package com.allure.permisson;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.allure.libs.permisson.LMPermissonHelper;
import com.allure.libs.permisson.PermissionFailed;
import com.allure.libs.permisson.PermissionSucceed;

public class MainActivity extends AppCompatActivity {


    private static final int SINGLE_REQUEST_CODE = 1; // 单个请求
    private static final int MORE_REQUEST_CODE = 2; // 多个请求

    private String permission = Manifest.permission.CAMERA;
    private String permission_call_phone = Manifest.permission.CALL_PHONE;

    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openSinglePermisson(View view) {

        LMPermissonHelper.with(MainActivity.this)
                .requestCode(SINGLE_REQUEST_CODE)
                .permissionStrings(permission)
                .requst();
    }

    public void openMorePermisson(View view) {


        LMPermissonHelper.with(MainActivity.this)
                .requestCode(MORE_REQUEST_CODE)
                .permissionStrings(PERMISSIONS)
                .requst();
    }

    public void openSettings(View view) {
        showMissingPermissionDialog("打开设置");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LMPermissonHelper.onRequestPermissionsResult(MainActivity.this, requestCode, permissions, grantResults);
    }

    @PermissionSucceed(requestCode = SINGLE_REQUEST_CODE)
    private void singleSuccess() {
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_CALL);
//        intent.setData(Uri.parse("tel:" + "15928854783"));
//        MainActivity.this.startActivity(intent);

        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        MainActivity.this.startActivity(intent2);
        Toast.makeText(this, "单个权限授权成功", Toast.LENGTH_SHORT).show();
    }

    @PermissionFailed(requestCode = SINGLE_REQUEST_CODE)
    private void singleFailed() {
        showMissingPermissionDialog("单个失败");
    }

    @PermissionSucceed(requestCode = MORE_REQUEST_CODE)
    private void MoreSuccess() {

        Toast.makeText(this, "多个权限授权成功", Toast.LENGTH_SHORT).show();
    }

    @PermissionFailed(requestCode = MORE_REQUEST_CODE)
    private void MoreFailed() {
        showMissingPermissionDialog("多个失败");
    }


    // 显示缺失权限提示
    private void showMissingPermissionDialog(String s) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(s + ":" + "温馨提示");
        builder.setMessage("请打开必要权限。" + "\n"
                + "请点击" + "\"" + "设置" + "\"" + "-" + "\"" + "权限管理" + "\""
                + "打开必须的权限,返回后重试");

        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog alertDialog = builder.show();
                alertDialog.dismiss();
            }
        });

        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });

        builder.setCancelable(false);

        builder.show();
    }

    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

}
