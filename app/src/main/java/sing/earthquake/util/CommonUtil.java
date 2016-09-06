package sing.earthquake.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

/**
 * @author: LiangYX
 * @ClassName: CommonUtil
 * @date: 16/8/21 下午5:21
 * @Description: 公用工具类
 */
public class CommonUtil {

    private static Point deviceSize = null;

    public static Point getDeviceSize(Context context) {
        if (deviceSize == null) {
            deviceSize = new Point(0, 0);
        }
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        deviceSize.x = metric.widthPixels;
        deviceSize.y = metric.heightPixels;
        return deviceSize;
    }

    public static boolean isEmpty(String str){
        if(str == null || str.equals("") || str.equals("null")){
            return true;
        }
        return false;
    }
}
