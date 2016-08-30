package sing.earthquake.util;

import android.widget.Toast;

import sing.earthquake.MyApplication;

/**
 * @author: LiangYX
 * @ClassName: ToastUtil
 * @date: 16/8/21 下午5:50
 * @Description: Toast工具类
 */
public class ToastUtil {

    public static void showToast(String alertId) {
        Toast.makeText(MyApplication.getContext(), alertId, Toast.LENGTH_SHORT).show();
    }
}
