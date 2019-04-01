package com.kaida.apple.kaidacode;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;

    public Button button3 = null;
    public Button clearBtn = null;
    public EditText gdh = null;
    public EditText gh = null;
    public EditText scgx = null;
    public EditText wgs = null;
    public Button kssc = null;
    public Button scwc = null;
    public Button exitBtn = null;
    public EditText showCode = null;
    public Button save_ip = null;
    public EditText ip_ed = null;

    private static String URL;// 请求的url
    private static String NAMESPACE = "http://www.X5106.com/";// 请求的命名空间；
    private static final String METHODNAME_GetWorkOpers = "GetWorkOpers";
    private static final String METHODNAME_WorkFinish = "WorkFinish";
    private static final String METHODNAME_WorkStart = "WorkStart";
    private Context context;
    private static String Mac;
    private Handler handler = new Handler() {// 刷新界面；
        public void handleMessage(Message msg) {
            String myData = msg.getData().getString("data");// 这里是完全解析好后的数据；用于显示；
            if (myData != null || !"".equals(myData)) {
                if (myData.equals("True")){

                }else {
                    showCode.setText(myData);
                }

            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZXingLibrary.initDisplayOpinion(this);
        setContentView(R.layout.activity_main);
        this.context = this;
        /**
         * 初始化组件
         */
        initView();
        //初始化权限
        initPermission();

        String ip_adress =  CodeSettins.getInstance(getBaseContext()).getIP_Adress();
        if (ip_adress != null) {
            ip_ed.setText(ip_adress);
        }

        TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService(this.TELEPHONY_SERVICE);
        String imei = telephonyManager.getImei();
        Mac = imei;
        String code_id = CodeSettins.getInstance(this).getSbId();
        if (code_id == null) {
            CodeSettins.getInstance(this).setSbId(imei);
        }

    }

    private void initPermission() {
        //检查权限
        String[] permissions = CheckPermissionUtils.checkPermission(this);
        if (permissions.length == 0) {
            //权限都申请了
            //是否登录
        } else {
            //申请权限
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }
    /**
     * 初始化组件
     */
    private void initView() {
        button3 = (Button) findViewById(R.id.button3);
        exitBtn = (Button) findViewById(R.id.exit_btn);
        showCode = (EditText) findViewById(R.id.code_show);
        clearBtn = (Button) findViewById(R.id.clearBtn);
        kssc = (Button) findViewById(R.id.kssc_btn);
        scwc = (Button) findViewById(R.id.scwc_btn);
        save_ip = (Button) findViewById(R.id.save_ip);
        ip_ed = (EditText) findViewById(R.id.ip_address);
        gdh = (EditText) findViewById(R.id.gdh);
        gh = (EditText) findViewById(R.id.gh_et);
        scgx = (EditText) findViewById(R.id.scgx_et);
        wgs = (EditText) findViewById(R.id.wgs_et);
        /**
         * 打开默认二维码扫描界面
         *
         * 打开系统图片选择界面
         *
         * 定制化显示扫描界面
         *
         * 测试生成二维码图片
         */
        button3.setOnClickListener(new ButtonOnClickListener(button3.getId()));
        button3.setTextSize(13);
        exitBtn.setOnClickListener(new ButtonOnClickListener(exitBtn.getId()));
        kssc.setOnClickListener(new ButtonOnClickListener(kssc.getId()));
        scwc.setOnClickListener(new ButtonOnClickListener(scwc.getId()));
        save_ip.setOnClickListener(new ButtonOnClickListener(save_ip.getId()));
        clearBtn.setOnClickListener(new ButtonOnClickListener(clearBtn.getId()));

    }

    /**
     * 请求CAMERA权限码
     */
    public static final int REQUEST_CAMERA_PERM = 101;


    /**
     * EsayPermissions接管权限处理逻辑
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @AfterPermissionGranted(REQUEST_CAMERA_PERM)
    public void cameraTask(int viewId) {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            onClick(viewId);
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "需要请求camera权限",
                    REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Toast.makeText(this, "执行onPermissionsGranted()...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "执行onPermissionsDenied()...", Toast.LENGTH_SHORT).show();
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            new AppSettingsDialog.Builder(this, "当前App需要申请camera权限,需要打开设置页面么?")
//                    .setTitle("权限申请")
//                    .setPositiveButton("确认")
//                    .setNegativeButton("取消", null /* click listener */)
//                    .setRequestCode(REQUEST_CAMERA_PERM)
//                    .build()
//                    .show();
            //new AppSettingsDialog.Builder(this,"权限").setTitle("")
        }
    }


    /**
     * 按钮点击监听
     */
    class ButtonOnClickListener implements View.OnClickListener{

        private int buttonId;

        public ButtonOnClickListener(int buttonId) {
            this.buttonId = buttonId;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.exit_btn) {
                System.exit(0);
            }else if (v.getId() == R.id.clearBtn){
                showCode.getText().clear();
            }else if (v.getId() == R.id.kssc_btn) {
                if (Mac.length()> 0 && gdh.getText().length()> 0 && gh.getText().length() > 0 && scgx.getText().length()>0 && ip_ed.getText().length()>0) {
                    HttpThread thread = new HttpThread(handler, context);
                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put("machineID",Mac);
                    params.put("workBillCode",gdh.getText().toString());
                    params.put("userCode",gh.getText().toString());
                    params.put("operIndex",scgx.getText().toString());
                    thread.doStart(ip_ed.getText().toString()+"/Service.asmx",NAMESPACE,METHODNAME_WorkStart,params);
                }


            }else  if (v.getId() == R.id.scwc_btn) {
                HttpThread thread = new HttpThread(handler, context);
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("machineID",Mac);
                params.put("workBillCode",gdh.getText().toString());
                params.put("userCode",gh.getText().toString());
                params.put("operIndex",scgx.getText().toString());
                params.put("quantity",wgs.getText().toString());
                thread.doStart(ip_ed.getText().toString()+"/Service.asmx",NAMESPACE,METHODNAME_WorkFinish,params);

            }else if (v.getId() == R.id.save_ip) {
                String ip_address = ip_ed.getText().toString();
                CodeSettins.getInstance(getBaseContext()).setIP_Adress(ip_address);
                Toast.makeText(getBaseContext(), "存储成功", Toast.LENGTH_SHORT).show();
            }
            else {
                cameraTask(buttonId);
            }
        }
    }


    /**
     * 按钮点击事件处理逻辑
     * @param buttonId
     */
    private void onClick(int buttonId) {
        switch (buttonId) {
            case R.id.button3:
                if (ip_ed.getText().toString().length() > 0) {
                    Intent intent = new Intent(MainActivity.this, CodeVc.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }else {
                    Toast.makeText(this, "请输入IP！！！" , Toast.LENGTH_LONG).show();

                }

                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    assert result != null;
                    if (result.contains("W-")) {
                        gdh.setText(result);
                        String code_id = CodeSettins.getInstance(this).getSbId();
                        HttpThread thread = new HttpThread(handler, context);
                        HashMap<String, Object> params = new HashMap<String, Object>();
                        params.put("machineID",code_id);
                        params.put("workBillCode",result);
                        thread.doStart(ip_ed.getText().toString()+"/Service.asmx",NAMESPACE,METHODNAME_GetWorkOpers,params);

                    }else {
                        gh.setText(result);
                    }

                    Toast.makeText(this, "解析二维码成功" , Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }

        /**
         * 选择系统图片并解析
         */
        else if (requestCode == REQUEST_IMAGE) {
//            if (data != null) {
//                Uri uri = data.getData();
//                try {
//                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
//                        @Override
//                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//                            Toast.makeText(MainActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onAnalyzeFailed() {
//                            Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        }

        else if (requestCode == REQUEST_CAMERA_PERM) {
            Toast.makeText(this, "从设置页面返回...", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void initget() {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();

        //创建一个Request
        final Request request = new Request.Builder()
                .url(CodeSettins.getInstance(this).getIP_Adress() +"GetWorkOpers")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {//回调的方法执行在子线程。
                    String htmlStr = response.body().string();

                }
            }
        });
    }


}
