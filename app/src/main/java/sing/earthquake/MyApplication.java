package sing.earthquake;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

import sing.earthquake.common.MyPreference;
import sing.okhttp.okhttputils.OkHttpUtils;
import sing.okhttp.okhttputils.cookie.store.PersistentCookieStore;
import sing.okhttp.okhttputils.model.HttpHeaders;
import sing.okhttp.okhttputils.model.HttpParams;

/**
 * @className   MyApplication
 * @time        2016/7/26 13:43
 * @author      LiangYx
 * @description Application
 */
public class MyApplication extends Application {

    private static Context context;
    public static Context getContext() {
        return context;
    }

    private static MyPreference preference;
    public static MyPreference preference() {
        if (preference == null){
            preference = new MyPreference(context);
        }
        return preference;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        SDKInitializer.initialize(context);

        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //所有的 header 都 不支持 中文
//        headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //所有的 params 都 支持 中文
//        params.put("commonParamsKey2", "这里支持中文参数");

        //必须调用初始化
        OkHttpUtils.init(this);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
                .debug("OkHttpUtils")                                              //是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                 //全局的写入超时时间
//                .setCookieStore(new MemoryCookieStore())                           //cookie使用内存缓存（app退出后，cookie消失）
                .setCookieStore(new PersistentCookieStore())                       //cookie持久化存储，如果cookie不过期，则一直有效
                .addCommonHeaders(headers)                                         //设置全局公共头
                .addCommonParams(params);                                          //设置全局公共参数
    }
}
