package com.bing.lan.comm.mvp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import com.bing.lan.comm.R;
import com.bing.lan.comm.app.AppUtil;
import com.bing.lan.comm.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bing.lan.comm.cons.BaseCons.REQUEST_CODE_PERMISSION_BASE;
import static com.bing.lan.comm.cons.BaseCons.REQUEST_CODE_PERMISSION_CALL_PHONE;

/**
 * @author 蓝兵
 */
public class PermissionActivity extends ImmersionActivity {

    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);
    private CharSequence mPhone;

    /* 启动activity时 进行权限请求 的开关 */
    protected boolean isCheckPermissions() {
        return false;
    }

    /* 返回权限数组资源id */
    protected int getPermissionArrId() {
        return 0;
        //return R.array.basic_permissions;
    }

    /* 请求权限 */
    protected void requestPermissions(int requestCode, int arrId) {

        // 获得权限字符串数组
        final String[] permissions = AppUtil.getStrArr(arrId);
        for (String per : permissions) {
            log.i("requestPermissions()正在请求的权限：" + per);
        }

        // 判断系统是不是大于等于M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //检查基本权限是否授权成功
            if (checkBasePermissions(permissions)) {
                //全部授权成功
                if (requestCode == REQUEST_CODE_PERMISSION_BASE) {
                    //log.i("requestPermissions() 全部授权成功: " + Arrays.toString(permissions));
                    requestBasePermissionSucceed(Arrays.asList(permissions));
                } else {
                    requestPermissionSucceed(requestCode, Arrays.asList(permissions));
                }
            } else {
                //检查是否有被拒绝过的权限
                checkDeniedPermissions(requestCode, permissions);
            }
        } else {
            log.i("requestPermissions(): 版本低于 23 , 没有运行时权限");
            if (requestCode == REQUEST_CODE_PERMISSION_BASE) {
                requestBasePermissionSucceed(Arrays.asList(permissions));
            } else {
                requestPermissionSucceed(requestCode, Arrays.asList(permissions));
            }
        }
    }

    /* 检查基本权限 */
    private boolean checkBasePermissions(String[] permissions) {
        boolean result = true;
        for (String permission : permissions) {
            //PackageManager.PERMISSION_GRANTED 表示授权成功
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, permission)) {
                result = false;
                log.i("checkBasePermissions() 未授权权限：" + permission);
            } else {
                log.i("checkBasePermissions() 已授权权限：" + permission);
            }
        }
        return result;
    }

    /*  检查是否有被拒绝过的权限 */
    private void checkDeniedPermissions(int requestCode, final String[] permissions) {
        List<String> shouldShowPermission = new ArrayList<>();
        List<String> notShowPermission = new ArrayList<>();
        for (String permission : permissions) {

            // false: 1.没有被拒绝过(第一次申请)
            //        2.被拒绝,并且在权限申请对话框中设置了,不再弹窗
            // true: 1.被拒绝过,弹窗向用户解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                shouldShowPermission.add(permission);
            } else {
                notShowPermission.add(permission);
            }
        }
        if (shouldShowPermission.size() > 0) {
            log.i("checkDeniedPermissions():被拒绝过,会弹窗向用户解释 的权限: " + shouldShowPermission.toString());
            //被拒绝过需要解释
            if (requestCode == REQUEST_CODE_PERMISSION_BASE) {
                showRationaleDialog(requestCode, permissions, shouldShowPermission, true);
            } else {
                showRationaleDialog(requestCode, permissions, shouldShowPermission, false);
            }
        } else {
            log.i("checkDeniedPermissions():未被拒绝(第一次申请),或 永久拒绝(不再弹窗了) 的权限: " + notShowPermission.toString());
            //未被拒绝(第一次申请)
            //永久拒绝(不弹窗了)
            requestPermissionsImpl(requestCode, permissions);
        }
    }

    /* 解释弹窗 */
    private void showRationaleDialog(final int requestCode, final String[] permissions, final List<String> failed, final boolean needFinish) {

        AlertDialog permissionsTipAlertDialog = new AlertDialog.Builder(this)
                .setTitle("授权温馨提示:")
                .setMessage("亲爱的用户,您好,麻烦您授权一下,不然无法进行下一步操作,谢谢您的配合")
                .setPositiveButton("授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        //真正请求权限
                        requestPermissionsImpl(requestCode, permissions);
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //请求权限失败
                        dialogInterface.dismiss();
                        //拒绝直接关闭
                        if (needFinish) {
                            finish();
                        } else {
                            requestPermissionFailed(requestCode, failed);
                        }
                    }
                })
                //.setCancelable(false)
                .create();
        permissionsTipAlertDialog.setCanceledOnTouchOutside(false);
        permissionsTipAlertDialog.show();
    }

    /* 真正请求权限的操作 */
    private void requestPermissionsImpl(final int requestCode, final String[] permissions) {
        //请求权限
        AppUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                //弹系统请求权限对话框,只弹出没有勾选 不再显示 的对话框
                ActivityCompat.requestPermissions(PermissionActivity.this, permissions, requestCode);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        List<String> failed = new ArrayList<>();
        List<String> success = new ArrayList<>();

        // 检查权限请求结果
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                log.i("onRequestPermissionsResult():请求失败的权限：" + permissions[i]);
                failed.add(permissions[i]);
            } else {
                log.i("onRequestPermissionsResult():请求成功的权限：" + permissions[i]);
                success.add(permissions[i]);
            }
        }

        // 报告每个请求失败的权限
        if (!failed.isEmpty()) {
            if (requestCode == REQUEST_CODE_PERMISSION_BASE) {
                //一定要true,否则打开设置页面后，不开启权限，直接返回后，权限仍然未开启但是却可以操作了，绕过了权限申请
                showPermissionsFailedAlertDialog(failed, true);
            } else {
                requestPermissionFailed(requestCode, failed);
            }
            return;
        }

        // 全部权限请求成功 才到这里
        if (requestCode == REQUEST_CODE_PERMISSION_BASE) {
            requestBasePermissionSucceed(success);
        } else {
            requestPermissionSucceed(requestCode, success);
        }
    }

    /*  请求基本权限成功时调用 */
    protected void requestBasePermissionSucceed(List<String> successPermissions) {
        for (String successPermission : successPermissions) {
            log.i("requestBasePermissionSucceed():请求基本权限成功: " + successPermission);
        }
    }

    /*  权限请求成功时调用 */
    protected void requestPermissionSucceed(int requestCode, List<String> successPermissions) {
        for (String successPermission : successPermissions) {
            log.i("requestPermissionSucceed():请求成功的权限：" + successPermission);
        }

        if (requestCode == REQUEST_CODE_PERMISSION_CALL_PHONE && mPhone != null) {
            callPhoneImpl(mPhone);
        }
    }

    /*  权限请求失败时调用 */
    protected void requestPermissionFailed(int requestCode, List<String> failedPermissions) {
        for (String successPermission : failedPermissions) {
            log.i("requestPermissionSucceed():请求失败的权限：" + successPermission);
        }
        showPermissionsFailedAlertDialog(failedPermissions, false);
    }

    //显示请求权限失败对话框
    protected void showPermissionsFailedAlertDialog(List<String> failedPermissions, final boolean needFinish) {
        AlertDialog permissionsFailedAlertDialog = new AlertDialog.Builder(this)
                .setTitle("温馨提示:")
                .setMessage("您好,还有权限未得到您的授权,无法进行下一步操作哦.")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (needFinish) {
                            finish();
                        }
                    }
                })
                .setNegativeButton("去设置页面开启权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        AppUtil.goToDetailApp(PermissionActivity.this );
                        if (needFinish) {
                            finish();
                        }
                    }
                })
                //.setCancelable(false)
                .create();
        permissionsFailedAlertDialog.setCanceledOnTouchOutside(false);
        permissionsFailedAlertDialog.show();
    }

    // 拨打电话
    public void callPhone(CharSequence phone) {
        this.mPhone = phone;
        requestPermissions(REQUEST_CODE_PERMISSION_CALL_PHONE, R.array.call_phone_runtime_permissions);
    }

    public void callPhoneImpl(CharSequence phone) {

        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data1 = Uri.parse("tel:" + phone);
        intent.setData(data1);

        //部分手机 manifest有申请 就一定是 true 模拟器不会
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            log.i("callPhone(): 没权限");
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        startActivity(intent);
    }
}
