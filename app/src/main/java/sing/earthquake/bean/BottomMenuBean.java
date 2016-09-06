package sing.earthquake.bean;

import java.io.Serializable;

public class BottomMenuBean implements Serializable {

    public String id;
    public String content;

    public BottomMenuBean(String id, String content) {
        this.id = id;
        this.content = content;
    }
}
