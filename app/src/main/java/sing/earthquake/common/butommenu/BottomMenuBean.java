package sing.earthquake.common.butommenu;

import java.io.Serializable;

/**
 * @author: LiangYX
 * @ClassName: BottomMenuBean
 * @date: 16/8/21 下午5:23
 * @Description: 封装的底部菜单的实体
 */
public class BottomMenuBean implements Serializable {
 
	private static final long serialVersionUID = -6176408100059466279L;
	
	public String id;
	public String content;
	
	public BottomMenuBean(String id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

	public BottomMenuBean() {
		super();
	} 
}
