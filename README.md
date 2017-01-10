# LMPermisson
Permisson With Android6.0，Android6.0权限处理工具

<font color=#0099ff size=4 face="黑体">Gradle引与使用：</font>

Gradle:  

``` xml
dependencies {
 compile 'com.allure0:LMPermisson:1.0.0'
}
```

<font color=#0099ff size=4 face="黑体">使用方法：</font> 


```
  LMPermissonHelper.with(MainActivity.this)
                .requestCode(MORE_REQUEST_CODE)
                .permissionStrings(PERMISSIONS)
                .requst();
                
                 @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LMPermissonHelper.onRequestPermissionsResult(MainActivity.this, requestCode, permissions, grantResults);
    }
```
<font color=#0099ff size=4 face="黑体">注解反射成功与失败：</font> 

```
 @PermissionSucceed(requestCode = MORE_REQUEST_CODE)
    private void MoreSuccess() {
        Toast.makeText(this, "多个权限授权成功", Toast.LENGTH_SHORT).show();
    }

    @PermissionFailed(requestCode = MORE_REQUEST_CODE)
    private void MoreFailed() {
        showMissingPermissionDialog("多个失败");
    }
```
<font color=#0099ff size=4 face="黑体">注意事项：</font>

-  在onRequestPermissionsResult中需要使用LMPermissonHelper.onRequestPermissionsResult();

<font color=#0099ff size=4 face="黑体">TODO</font>

若有BUG或者疑问,请提交Issues。者QQ群:[482906631]()

## License

Copyright 2016 Allure

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
