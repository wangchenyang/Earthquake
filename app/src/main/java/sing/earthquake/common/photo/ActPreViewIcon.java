package sing.earthquake.common.photo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import sing.earthquake.R;
import sing.earthquake.common.BaseActivity;
import sing.earthquake.common.CommonConstant;
import sing.earthquake.util.LoaderImage;

/**
 * @className   ActPreViewIcon
 * @time        2016/9/6 13:21
 * @author      LiangYx
 * @description 图片预览
 */
public class ActPreViewIcon extends BaseActivity {

	public static final String KEY_ALL_ICON = "key_all_icon";
	public static final String KEY_CURRENT_ICON = "key_current_icon";
	private SamplePagerAdapter mAdapter;
	private ArrayList<String> mListPhotoPath = null;
	private int mIndexImage = 0;
	private PreViewPager mViewPager = null;
	private CircleDot cirDot;
	/**预览图片 类型*/
	public static int IMAGE_PREVIEW =100101;

	@Override
	public int getLayoutId() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		return R.layout.act_preview_icon;
	}

	@Override
	public void init() {
		Window window = getWindow();
		LayoutParams windowLayoutParams = window.getAttributes(); // 获取对话框当前的参数值
		// dimAmount在0.0f和1.0f之间，0.0f完全不暗，1.0f全暗
		windowLayoutParams.dimAmount = 1.0f;
		window.setAttributes(windowLayoutParams);
		context = this;
		sdCardPath = Environment.getExternalStorageDirectory().getPath();

		initViewPager();
	}

	private int mLocationX;
	private int mLocationY;
	private int mWidth;
	private int mHeight;
	SmoothImageView imageView = null;
	private Activity context;
	private String sdCardPath;


	private void initViewPager() {
		mLocationX = getIntent().getIntExtra("locationX", 0);
		mLocationY = getIntent().getIntExtra("locationY", 0);
		mWidth = getIntent().getIntExtra("width", 0);
		mHeight = getIntent().getIntExtra("height", 0);

		mIndexImage = getIntent().getIntExtra(KEY_CURRENT_ICON, 0);
		mListPhotoPath = getIntent().getStringArrayListExtra(KEY_ALL_ICON);

		cirDot = new CircleDot(this, (LinearLayout)findViewById(R.id.ltAddDot),mListPhotoPath.size(),mIndexImage);
		cirDot.selected(mIndexImage);
		mViewPager = (PreViewPager) findViewById(R.id.vpIcon);
		mAdapter = new SamplePagerAdapter();
		mAdapter.setItems(mListPhotoPath);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(mIndexImage, true);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (cirDot != null) {
					cirDot.selected(arg0);
				}
				mViewPager.setCurrentItem(arg0, true);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
			
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent i = new Intent ();
			i.putExtra("key_reslut", false);
			setResult(RESULT_OK, i);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public class SamplePagerAdapter extends PagerAdapter {
		private List<String> mListLog = null;

		public void setItems(List<String> logs) {
			mListLog = logs;
		}

		@Override
		public int getCount() {
			if (null == mListLog)
				return 0;
			return mListLog.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {

			final String photo = mListLog.get(position);
			SmoothImageView photoView = new SmoothImageView(container.getContext());
			if (position == mIndexImage) {	
				photoView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
				photoView.transformIn();
				photoView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
				photoView.setScaleType(ScaleType.FIT_CENTER);
			}
			try {
				if (photo.startsWith("http")) {
					LoaderImage.getInstance(R.mipmap.empty_photo).ImageLoaders(photo, photoView);
				}else if(photo.startsWith("file://")){
					LoaderImage.getInstance(R.mipmap.empty_photo).ImageLoaders(photo, photoView);
				}else{
					LoaderImage.getInstance(R.mipmap.empty_photo).ImageLoaders("file://"+photo, photoView);
				}
				
			} catch (Exception e) {
			}
			container.addView(photoView, 0, new LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT));

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}
	
	/**
	 * 缓存图片信息
	 * @param view
	 * @return
	 */
	private String downloadImage(View view) {
		String filePath = "";
		view.setDrawingCacheEnabled(true); 
		view.buildDrawingCache();   
		Bitmap bmp = view.getDrawingCache();
		if (bmp != null){
			try {
				// 图片文件路径
				filePath = CommonConstant.IMAGE_PATH + "yxck_temp_"+System.currentTimeMillis()+".png";
				File file = new File(filePath);
				FileOutputStream os = new FileOutputStream(file);
				bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
				os.flush();
				os.close();
			} catch (Exception e) {
			}
		}
		view.setDrawingCacheEnabled(false);
		return filePath;
	}
}
