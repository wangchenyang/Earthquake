package sing.earthquake.common.photo;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @className   PreViewPager
 * @time        2016/9/6 13:23
 * @author      LiangYx
 * @description ViewPager wrapContent解决方案
 */
public class PreViewPager extends ViewPager {

	public PreViewPager(Context context) {
		super(context);
	}

	public PreViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
			return super.onInterceptTouchEvent(ev);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		try {
			return super.onTouchEvent(arg0);
		} catch (IllegalArgumentException ex) {
			return false;
		}
	}
}