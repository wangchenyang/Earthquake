package sing.earthquake.common.gson;

import java.util.List;

/**
 * @author: LiangYX
 * @ClassName: Json
 * @date: 16/8/22 下午9:27
 * @Description: json工具类
 */
public abstract class Json {

    private static Json json;

    public static Json get() {
        if (json == null) {
            json = new GsonImpl();
        }
        return json;
    }

    public abstract String toJson(Object src);

    public abstract <T> T toObject(String json, Class<T> claxx);

    public abstract <T> T toObject(byte[] bytes, Class<T> claxx);

    public abstract <T> List<T> toList(String json, Class<T> claxx);
}