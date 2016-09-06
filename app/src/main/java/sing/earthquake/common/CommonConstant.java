package sing.earthquake.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by Sing on 16/9/6.
 */
public class CommonConstant {

    /** 储存目录 */
    public static String SAVE_DIR_NAME = "earthquake";
    /** 储存主目录 */
    public static final String MAIN_PATH = Environment.getExternalStorageDirectory().getPath()+ File.separator + SAVE_DIR_NAME+ File.separator;
    /** 图片路劲 */
    public static final String IMAGE_PATH = MAIN_PATH + "images";
}
