package sing.earthquake.util;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class LoaderImage {
	private static LoaderImage instance = null;
	private static int image;
	public static LoaderImage getInstance(int images) {
		image = images;
		if (null == instance) {
			instance = new LoaderImage();
		}
		return instance;
	}

	 
	public void ImageLoaders(String url, ImageView iv) {
		DisplayImageOptions	options = new DisplayImageOptions.Builder()
				.showImageOnLoading(image)
				.showImageForEmptyUri(image)
				.showImageOnFail(image)
				.cacheInMemory(true)
 				.cacheOnDisk(true)
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		if (CommonUtil.isEmpty(url)){
			ImageLoader.getInstance().displayImage(url, iv, options );
		}else if (url.startsWith("http") || url.startsWith("file")){
			ImageLoader.getInstance().displayImage(url, iv, options );
		}else{
			ImageLoader.getInstance().displayImage("file://" + url, iv, options );
		}
	}
}
