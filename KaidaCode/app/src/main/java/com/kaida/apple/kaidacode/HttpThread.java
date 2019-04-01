package com.kaida.apple.kaidacode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

class HttpThread extends Thread {
    private Handler handler = null;
    private Context context = null;
    private String url = null;
    private String namespace = null;
    private String methodName = null;
    HashMap<String, Object> params = new HashMap<String, Object>();// 参数列表；
    private ProgressDialog progressDialog = null;

    // 构造函数
    public HttpThread(Handler handler, Context context) {
        this.handler = handler;
        this.context = context;
    }

    /**
     * 启动线程
     */
    /**
     * 要向服务器传递的四个参数分别是： （1）URL （2）命名空间 （3）方法名 （4）参数列表
     *
     * @param url
     * @param namespace
     * @param methodName
     * @param params
     */
    public void doStart(String url, String namespace, String methodName,
                        HashMap<String, Object> params) {
        this.url = url;
        this.namespace = namespace;
        this.methodName = methodName;
        this.params = params;
        progressDialog = ProgressDialog.show(context, "提示", "正在请求请稍等...", true);
        this.start();// 启动这个线程；
    }

    /**
     * 线程运行
     */
    @Override
    public void run() {
        try {
            // web service 请求
            SoapObject result = (SoapObject) CallWebService();
            Log.i("HttpThread_getPropertyCount", result.getPropertyCount() + "");
            Log.i("HttpThread_getProperty(0)", result.getProperty(0) + "");
            // 构造数据
            String value = null;
            if (result != null && result.getPropertyCount() > 0) {
                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapPrimitive primitive = (SoapPrimitive) result
                            .getProperty(i);
                    value = primitive.toString();
                }
                /**
                 * 这里获得的value就是WebService的值： 18710498511：陕西 西安 陕西移动全球通卡
                 */
                // Log.i("HttpThread_value", value);
                // 取消进度框
                progressDialog.dismiss();
                // 构造消息
                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("data", value);// 传递到界面进行显示；
                message.setData(bundle);
                handler.sendMessage(message);// 传递到到Activity的handmessage()中进行处理；
            }// if
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
            //Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
        }
    }// run();

    private Object CallWebService() {
        String SOAP_ACTION = namespace + methodName;// 命名空间+方法名称；
        SoapObject request = new SoapObject(namespace, methodName);// 创建SoapObject实例
        // 生成调用web service 方法的soap请求消息
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // 设置.net web service
        envelope.dotNet = true;

        // 请求参数
        if (params != null && !params.isEmpty()) {
            for (Iterator it = params.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, Object> e = (Entry) it.next();
                request.addProperty(e.getKey().toString(), e.getValue());
            }
        }
        // 发送请求
        envelope.setOutputSoapObject(request);
        HttpTransportSE transport = new HttpTransportSE(url);
        SoapObject result = null;
        try {
            // web service请求
            transport.call(SOAP_ACTION, envelope);
            Log.d("exxxxxxxxxxxx","dump = "+transport.requestDump);
            result = (SoapObject) envelope.bodyIn;
            //Log.i("envelope.bodyIn", (String) envelope.bodyIn));
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i("exxxxxxxxxxxx", ex.toString());
        }
        Log.i("result", result.toString());
        return result;
    }// CallWebService();
}// HttpThread类

