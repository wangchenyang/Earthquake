package sing.earthquake.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import sing.earthquake.MyApplication;

/**
 * @author: LiangYX
 * @ClassName: BasePreference
 * @date: 16/8/22 下午9:34
 * @Description: xml文件存储基类
 */
public class MyPreference {

    private String preferenceName = "earthquake";

    private Context context;
    public MyPreference(Context context) {
        this.context = context;
    }

    public void setString(String key, String value) {
        if (context == null) {
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(preferenceName, Context.MODE_MULTI_PROCESS|Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
        editor = null;
        pref = null;
    }

    public String getString(String key, String defaultValue) {
        if (context == null) {
            return "";
        }
        SharedPreferences pref = context.getSharedPreferences(preferenceName, Context.MODE_MULTI_PROCESS|Context.MODE_PRIVATE);
        return pref.getString(key, defaultValue);
    }

    public void setInt(String key, int value) {
        if (context == null) {
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(preferenceName, 0);
        Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
        editor = null;
        pref = null;
    }

    public int getInt(String key, int defaultValue) {
        if (context == null) {
            return defaultValue;
        }
        SharedPreferences pref = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        return pref.getInt(key, defaultValue);
    }
}
