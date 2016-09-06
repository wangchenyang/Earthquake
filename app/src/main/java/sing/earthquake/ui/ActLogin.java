package sing.earthquake.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Request;
import okhttp3.Response;
import sing.earthquake.MyApplication;
import sing.earthquake.R;
import sing.earthquake.bean.LoginBean;
import sing.earthquake.common.BaseActivity;
import sing.earthquake.common.Urls;
import sing.earthquake.common.gson.GsonImpl;
import sing.earthquake.util.ToastUtil;
import sing.okhttp.okhttputils.OkHttpUtils;
import sing.okhttp.okhttputils.cache.CacheMode;
import sing.okhttp.okhttputils.callback.StringCallback;

/**
 * @author LiangYx
 * @className ActLogin
 * @time 2016/8/19 11:20
 * @description 登录界面
 *        注册：{@link #register(View)}
 */
public class ActLogin extends BaseActivity {

    private Activity context;
    private EditText etUserName;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_login;
    }

    @Override
    public void init() {
        etUserName = (EditText) findViewById(R.id.et_user_name);
        etPassword = (EditText) findViewById(R.id.et_password);
    }

    /**
     * 注册
     *
     * @param v
     */
    public void register(View v) {
        startActivity(new Intent(context, ActRegister.class));
    }

    /**
     * 提交登陆
     * @param v
     */
    public void submit(View v){
        String userName = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(userName)){
            ToastUtil.showToast("请输入用户名");
        }else if (TextUtils.isEmpty(password)){
            ToastUtil.showToast("请输入密码");
        }else{
            login(userName,password);
        }
    }

    /**
     * 提交接口登陆
     * @param userName 用户名
     * @param password 密码
     */
    private void login(String userName, String password) {
        OkHttpUtils.get(Urls.login)     // 请求方式和请求url
                .params("userName", userName)
                .params("password", password)
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("login")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new MyCallback(context));
    }

    private class MyCallback extends StringCallback {
        public MyCallback(Context context) {
            super(context,true);
        }

        @Override
        public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
            super.onResponse(isFromCache, s, request, response);
            JSONObject json = null;
            try {
                json = new JSONObject(s);
                String code = json.optString("success");
                String msg = json.optString("msg");
                if ("true".equals(code)){
                    LoginBean bean = GsonImpl.get().toObject(json.optString("data"),LoginBean.class);
                    MyApplication.preference().setString("id", bean.getId());
                    MyApplication.preference().setString("token", bean.getToken());
                    MyApplication.preference().setString("userName", bean.getUserName());

                    startActivity(new Intent(context,MainActivity.class));
                    finish();
                }else{
                    ToastUtil.showToast(msg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
