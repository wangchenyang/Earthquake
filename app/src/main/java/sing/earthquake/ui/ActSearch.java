package sing.earthquake.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Request;
import okhttp3.Response;
import sing.earthquake.R;
import sing.earthquake.common.BaseActivity;
import sing.earthquake.common.Urls;
import sing.earthquake.util.ToastUtil;
import sing.okhttp.okhttputils.OkHttpUtils;
import sing.okhttp.okhttputils.cache.CacheMode;
import sing.okhttp.okhttputils.callback.StringCallback;

public class ActSearch extends BaseActivity {

    /** 建筑名称 */
    private EditText etBuildName;
    /** 建筑物高度 */
    private EditText etMinHoldHeight,etMaxHoldHeight;
    /** 地上楼层 */
    private EditText etMinBuildFloor,etMaxbuildFloor;
    /** 竣工时间 */
    private EditText etMinEndTime,etMaxEndTime;
    /** 居住人数 */
    private EditText etMinLiveNum,etMaxLiveNum;


    @Override
    public int getLayoutId() {
        return R.layout.act_search;
    }

    @Override
    public void init() {
        etBuildName = (EditText) findViewById(R.id.et_build_name);
        etMinHoldHeight = (EditText) findViewById(R.id.et_min_hold_height);
        etMaxHoldHeight = (EditText) findViewById(R.id.et_max_hold_height);
        etMinBuildFloor = (EditText) findViewById(R.id.et_min_build_floor);
        etMaxbuildFloor = (EditText) findViewById(R.id.et_max_build_floor);
        etMinEndTime = (EditText) findViewById(R.id.et_min_end_time);
        etMaxEndTime = (EditText) findViewById(R.id.et_max_end_time);
        etMinLiveNum = (EditText) findViewById(R.id.et_min_live_num);
        etMaxLiveNum = (EditText) findViewById(R.id.et_max_live_num);
    }

    /**
     * 关闭
     * @param v
     */
    public void finish(View v){
        finish();
    }

    /**
     * 查询
     * @param v
     */
    public void submit(View v){
//        jzmc-建筑名称
//        yt-现在用途或功能
//        ssjd-所属街道
//        ssshequ-所属社区
//        jzwgdStart-建筑物开始高度
//        jzwgdEnd-建筑物结束高度
//        dsjzcsStart-地上建筑层数开始
//        dsjzcsEnd-地上建筑层数结束
//        jgsjStart-竣工时间开始
//        jgsjEnd-竣工时间结束
//        peopleCountStart-常驻人数开始
//        peopleCountEnd-常熟人数结束

//        OkHttpUtils.get(Urls.register)     // 请求方式和请求url
//                .params("userName", params[0])
//                .params("password", params[1])
//                .params("realName", params[2])
//                .params("tel", params[3])
//                .params("jd", params[4])
//                .params("sq", params[5])
//                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
//                .cacheKey("register")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
//                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
//                .execute(new MyCallback(context));
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
