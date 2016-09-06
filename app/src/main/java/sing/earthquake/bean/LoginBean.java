package sing.earthquake.bean;

import java.io.Serializable;

/**
 * @author: LiangYX
 * @ClassName: LoginBean
 * @date: 16/8/22 下午9:24
 * @Description: 登陆返回的实体
 */
public class LoginBean implements Serializable{

    private String id;
    private String token;
    private String userName;

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getUserName() {
        return userName;
    }
}