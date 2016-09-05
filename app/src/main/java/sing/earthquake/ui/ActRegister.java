package sing.earthquake.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;
import sing.earthquake.R;
import sing.earthquake.adapter.StreetAdapter;
import sing.earthquake.bean.BottomMenuBean;
import sing.earthquake.common.BaseActivity;
import sing.earthquake.common.Urls;
import sing.earthquake.common.streetinfo.StreetInfo;
import sing.earthquake.util.ToastUtil;
import sing.okhttp.okhttputils.OkHttpUtils;
import sing.okhttp.okhttputils.cache.CacheMode;
import sing.okhttp.okhttputils.callback.StringCallback;

/**
 * @author LiangYx
 * @className ActRegister
 * @time 2016/8/19 11:24
 * @description 注册
 */
public class ActRegister extends BaseActivity {

    private Activity context;
    /** 用户名 */
    private EditText etUserName;
    /** 密码 */
    private EditText etPassword;
    /** 真实姓名 */
    private EditText etRealName;
    /** 电话 */
    private EditText etTel;
    /** 街道 */
    private TextView tvStreet;
    /** 社区 */
    private TextView tvCommunity;
    private String id;//选择的街道的id，用来选择社区

    @Override
    public int getLayoutId() {
        return R.layout.act_register;
    }

    @Override
    public void init() {
        context = this;

        etUserName = (EditText) findViewById(R.id.et_user_name);
        etPassword = (EditText) findViewById(R.id.et_password);
        etRealName = (EditText) findViewById(R.id.et_real_name);
        etTel = (EditText) findViewById(R.id.et_tel);
        tvStreet = (TextView) findViewById(R.id.tv_street);
        tvCommunity = (TextView) findViewById(R.id.tv_community);
    }

    /**
     * 选择街道
     *
     * @param v
     */
    private int street = 0;//所选的街道
    public void chooseStreet(View v) {
        PopupWindow popupWindow;
        final View view1 = LayoutInflater.from(context).inflate(R.layout.pop_list, null);
        popupWindow = new PopupWindow(view1, v.getWidth(), v.getHeight() * 6, true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAsDropDown(v);

        ListView listview = (ListView) view1.findViewById(R.id.listview);
        StreetAdapter adapter = new StreetAdapter(context,-100);
        listview.setAdapter(adapter);

        List<BottomMenuBean> list = StreetInfo.getStreet();
        listview.setOnItemClickListener((parent, view, position, id) -> {
            tvStreet.setText(list.get(position).content);
            tvCommunity.setText("");
            street = Integer.parseInt(list.get(position).id);
            popupWindow.dismiss();
        });
    }

    /**
     * 选择社区
     *
     * @param v
     */
    public void chooseCommunity(View v) {
        if (TextUtils.isEmpty(tvStreet.getText().toString())){
            ToastUtil.showToast("请先选择所属街道");
            return;
        }
        PopupWindow popupWindow;
        final View view1 = LayoutInflater.from(context).inflate(R.layout.pop_list, null);
        popupWindow = new PopupWindow(view1, v.getWidth(), v.getHeight() * 6, true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAsDropDown(v);

        ListView listview = (ListView) view1.findViewById(R.id.listview);
        StreetAdapter adapter = new StreetAdapter(context,street);
        listview.setAdapter(adapter);

        List<BottomMenuBean> list = StreetInfo.getCommunity(street+"");
        listview.setOnItemClickListener((parent, view, position, id) -> {
            tvCommunity.setText(list.get(position).content);
            popupWindow.dismiss();
        });
    }

    /**
     * 关闭
     * @param v
     */
    public void finish(View v){
        finish();
    }

    /**
     * 提交
     *
     * @param v
     */
    public void submit(View v) {
        String userName = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String realName = etRealName.getText().toString().trim();
        String tel = etTel.getText().toString().trim();
        String street = tvStreet.getText().toString().trim();
        String community = tvCommunity.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            ToastUtil.showToast("用户名不能为空");
        } else if (TextUtils.isEmpty(password)) {
            ToastUtil.showToast("密码不能为空");
        } else if (TextUtils.isEmpty(realName)) {
            ToastUtil.showToast("真实姓名不能为空");
        } else if (TextUtils.isEmpty(tel)) {
            ToastUtil.showToast("电话不能为空");
        } else if (TextUtils.isEmpty(street)) {
            ToastUtil.showToast("街道不能为空");
        } else if (TextUtils.isEmpty(community)) {
            ToastUtil.showToast("社区不能为空");
        } else {
            String[] params = new String[]{userName, password, realName, tel, street, community};
            register(params);
        }
    }

    /**
     * 调用接口注册
     * @param params
     *  params[0]:用户名
     *  params[1]:密码
     *  params[2]:真实姓名
     *  params[3]:电话
     *  params[4]:街道
     *  params[5]:社区
     */
    private void register(String[] params) {
        OkHttpUtils.get(Urls.register)     // 请求方式和请求url
                .params("userName", params[0])
                .params("password", params[1])
                .params("realName", params[2])
                .params("tel", params[3])
                .params("jd", params[4])
                .params("sq", params[5])
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("register")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new MyCallback(context));
    }

    private class MyCallback extends StringCallback{
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
                    ToastUtil.showToast("注册成功");
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