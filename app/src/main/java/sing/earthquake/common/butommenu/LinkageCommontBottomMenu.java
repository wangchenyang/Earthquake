package sing.earthquake.common.butommenu;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import sing.earthquake.R;
import sing.earthquake.util.DateUtil;
import sing.earthquake.util.ToastUtil;


/** 底部按钮菜单 */
public class LinkageCommontBottomMenu extends BottomMenuWindow {

	private TextView ok;
	private LinkageMainPicker left;
	private LinkageMainPicker right;
	private BottomMenuBean bean1;
	private BottomMenuBean bean2;
	private BottomMenuBean bean3;
	private BottomMenuBean bean4;

	public LinkageCommontBottomMenu(Activity activity) {
		super(activity);
		setContentView(R.layout.linkage_commont_bottom_menu);

		ok = (TextView) getContentView().findViewById(R.id.ok);
		left = (LinkageMainPicker) getContentView().findViewById(R.id.left);
		right = (LinkageMainPicker) getContentView().findViewById(R.id.right);
		left.setVisibility(View.VISIBLE);
		right.setVisibility(View.VISIBLE);

		getContentView().findViewById(R.id.cancel).setOnClickListener(v -> dismiss());

	}

	/**
	 * 只有一个list，比如  年
	 */
	public void setData1(List<BottomMenuBean> year,String defaults){
		right.setVisibility(View.GONE);
		ok.setOnClickListener(v -> {
            dismiss();
            if (listener1 != null) {
                listener1.result1(left.getResultLeft());
            }
        });
		left.setData(year,defaults);
	}

	/**
	 * 左边一个，比如  年 月
	 */
	public void setData2(List<BottomMenuBean> year,List<BottomMenuBean> month,String defaults1,String defaults2){
		right.setVisibility(View.VISIBLE);
		ok.setOnClickListener(v -> {
            dismiss();
            if (listener2 != null) {
                listener2.result2(left.getResultLeft(),left.getResultLeft());
            }
        });
		left.setData(year,month,defaults1,defaults2);
	}

	/**
	 * 左右两个   两列，比如2015-2015
	 */
	public void setData3(List<BottomMenuBean> year1,List<BottomMenuBean> year2,String defaults1,String defaults2){
		right.setVisibility(View.VISIBLE);
		ok.setOnClickListener(v -> {
            if (left.getResultLeft().content.length() > 2 && right.getResultLeft().content.length() > 2 && (Integer.valueOf(left.getResultLeft().content) > Integer.valueOf(right.getResultLeft().content))) {
                ToastUtil.showToast("请选择正确的时间");
            }else {
                dismiss();
                if (listener3 != null) {
                    listener3.result3(left.getResultLeft(),right.getResultLeft());
                }
            }
        });
		left.setData(year1,defaults1);
		right.setData(year2,defaults2);
	}

	/**
	 * 4个，比如2015.01-2015.08
	 */
	public void setData4(List<BottomMenuBean> year1,List<BottomMenuBean> month1,List<BottomMenuBean> year2,List<BottomMenuBean> month2,String default1,String default2,String default3,String default4){
		right.setVisibility(View.VISIBLE);
		ok.setOnClickListener(v -> {
            if (listener4 != null && check()) {
                dismiss();
                listener4.result4(bean1,bean2,bean3,bean4);
            }
        });
		left.setData(year1,month1,default1,default2);
		right.setData(year2,month2,default3,default4);
	}

	/**
	 * 左右两个   两列，任意类型
	 */
	public void setData5(List<BottomMenuBean> year1,List<BottomMenuBean> year2,String defaults1,String defaults2){
		right.setVisibility(View.VISIBLE);
		ok.setOnClickListener(v -> {
            dismiss();
            if (listener5 != null) {
                listener5.result5(left.getResultLeft(),right.getResultLeft());
            }
        });
		left.setData(year1,defaults1);
		right.setData(year2,defaults2);
	}

	/**
	 * 结果处理
	 * */
	private boolean check(){
		bean1 = left.getResultLeft();
		bean2 = left.getResultRight();

		if ("至今".equals(right.getResultLeft().content)) {
			int year = Integer.valueOf(DateUtil.getCurrentTimeSpecifyFormat(DateUtil.FORMAT_YYYY));
			int month = Integer.valueOf(DateUtil.getCurrentTimeSpecifyFormat(DateUtil.FORMAT_MM));
			bean3 = new BottomMenuBean(year+"", year+"");
			bean4 = new BottomMenuBean(month + "", month + "");
		} else {
			bean3 = right.getResultLeft();
			bean4 = right.getResultRight();
		}

		if (Integer.parseInt(bean1.id) > Integer.parseInt(bean3.id) || (Integer.parseInt(bean1.id) == Integer.parseInt(bean3.id) && Integer.parseInt(bean2.id) > Integer.parseInt(bean4.id))) {
			ToastUtil.showToast("请选择正确的日期");
			return false;
		}
		return true;
	}

	private LinkageCommontBottomMenuListener1 listener1;
	private LinkageCommontBottomMenuListener2 listener2;
	private LinkageCommontBottomMenuListener3 listener3;
	private LinkageCommontBottomMenuListener4 listener4;
	private LinkageCommontBottomMenuListener5 listener5;

	public void setListener1(LinkageCommontBottomMenuListener1 listener) {
		this.listener1 = listener;
	}
	public void setListener2(LinkageCommontBottomMenuListener2 listener) {
		this.listener2 = listener;
	}
	public void setListener3(LinkageCommontBottomMenuListener3 listener) {
		this.listener3 = listener;
	}
	public void setListener4(LinkageCommontBottomMenuListener4 listener) {
		this.listener4 = listener;
	}
	public void setListener5(LinkageCommontBottomMenuListener5 listener) {
		this.listener5 = listener;
	}

	public interface LinkageCommontBottomMenuListener1 {
		void result1(BottomMenuBean bean1);
	}
	public interface LinkageCommontBottomMenuListener2 {
		void result2(BottomMenuBean bean1, BottomMenuBean bean2);
	}
	public interface LinkageCommontBottomMenuListener3 {
		void result3(BottomMenuBean bean1, BottomMenuBean bean2);
	}
	public interface LinkageCommontBottomMenuListener4 {
		void result4(BottomMenuBean bean1, BottomMenuBean bean2, BottomMenuBean bean3, BottomMenuBean bean4);
	}
	public interface LinkageCommontBottomMenuListener5 {
		void result5(BottomMenuBean bean1, BottomMenuBean bean2);
	}
}
