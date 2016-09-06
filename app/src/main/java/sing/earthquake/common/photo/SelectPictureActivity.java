package sing.earthquake.common.photo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import sing.earthquake.R;
import sing.earthquake.common.BaseActivity;
import sing.earthquake.common.CommonConstant;
import sing.earthquake.util.CommonUtil;
import sing.earthquake.util.LoaderImage;

/**
 * @author: LiangYX
 * @ClassName: SelectPictureActivity
 * @date: 2016年4月12日 下午9:01:26
 * @Description: 拍照选图统一
 * 		
 * 	1、直接拍照
 *		Intent intent = new Intent(context, SelectPictureActivity.class);
 *		intent.putExtra(SelectPictureActivity.KEY_RESLUT, SelectPictureActivity.PHOTOGRAPH);
 *		startActivityForResult(intent, 1);
 *	2、只选择图库
 *		Intent intent = new Intent(context, SelectPictureActivity.class);
 *		intent.putExtra(SelectPictureActivity.KEY_RESLUT, SelectPictureActivity.PHOTOALBUM);
 *		intent.putExtra(SelectPictureActivity.INTENT_MAX_NUM, 9);
 *		startActivityForResult(intent, 1);
 *	3、拍照带选图
 *		Intent intent = new Intent(context, SelectPictureActivity.class);
 *		intent.putExtra(SelectPictureActivity.KEY_RESLUT, SelectPictureActivity.ALL);
 *		intent.putExtra(SelectPictureActivity.INTENT_MAX_NUM, 9);
 *		startActivityForResult(intent, 1);
 *	4、返回去接收的方法
 *		@Override
 *		protected void onActivityResult(int arg0, int arg1, Intent arg2) {
 *			super.onActivityResult(arg0, arg1, arg2);
 *			if (arg1 == Activity.RESULT_OK) {
 *				List<String> selectedPicture = (List<String>) arg2.getSerializableExtra(SelectPictureActivity.INTENT_SELECTED_PICTURE);
 *			}
 *		}
 */

@SuppressLint("SimpleDateFormat") 
public class SelectPictureActivity extends BaseActivity {

    /** 最多选择图片的个数 */
    private static int MAX_NUM = 9;
    private static final int TAKE_PICTURE = 520;

    public static final String INTENT_MAX_NUM = "intent_max_num";
    public static final String INTENT_SELECTED_PICTURE = "intent_selected_picture";

    private Activity context;
    private GridView gridview;
    private PictureAdapter adapter;
    /** 临时的辅助类，用于防止同一个文件夹的多次扫描 */
    private HashMap<String, Integer> tmpDir = new HashMap<String, Integer>();
    private ArrayList<ImageFloder> mDirPaths = new ArrayList<ImageFloder>();
    private ContentResolver mContentResolver;
    private Button btn_select;
    private ListView listview;
    private FolderAdapter folderAdapter;
    private ImageFloder imageAll, currentImageFolder;

    /** 已选择的图片 */
    private ArrayList<String> selectedPicture = new ArrayList<>();
    private String cameraPath = null;

    private String type = "";//类型，拍照、选图、全部
    public static final String PHOTOGRAPH = "photograph";//拍照
    public static final String PHOTOALBUM = "photoalbum";//相册
    public static final String ALL = "all";//拍照带相册选择
    public static final String KEY_RESLUT = "key_reslut";

    @Override
    public int getLayoutId() {
        return R.layout.act_select_picture;
    }

    @Override
    public void init() {
        context = this;

        MAX_NUM = getIntent().getIntExtra(INTENT_MAX_NUM, 9);
        type = getIntent().getExtras().getString(KEY_RESLUT,ALL);

        if (!type.equals(PHOTOGRAPH)) {//不是拍照的正常逻辑，是拍照的直接启动相机
            mContentResolver = getContentResolver();
            initView();
        } else {
            goCamare();
        }
    }

    public void ok(View v){
        if (selectedPicture == null || selectedPicture.size() < 1) {
            Toast.makeText(context, "请选择图片", Toast.LENGTH_SHORT).show();
        } else {
            Intent data = new Intent();
            data.putExtra(INTENT_SELECTED_PICTURE, selectedPicture);
            setResult(RESULT_OK, data);
            context.finish();
        }
    }

    // 预览
    public void preview(View v) {
		Intent intent = new Intent(context, ActPreViewIcon.class);
		intent.putExtra(ActPreViewIcon.KEY_ALL_ICON, selectedPicture);
		intent.putExtra(ActPreViewIcon.KEY_CURRENT_ICON, 0);
		intent.putExtra("locationX", CommonUtil.getDeviceSize(context).x / 2);
		intent.putExtra("locationY", CommonUtil.getDeviceSize(context).y / 2);
		context.startActivity(intent);
		overridePendingTransition(0, 0);
	}
    
    public void select(View v) {
        if (listview.getVisibility() == View.VISIBLE) {
            hideListAnimation();
        } else {
            listview.setVisibility(View.VISIBLE);
            showListAnimation();
            folderAdapter.notifyDataSetChanged();
        }
    }

    public void showListAnimation() {
        TranslateAnimation ta = new TranslateAnimation(1, 0f, 1, 0f, 1, 1f, 1, 0f);
        ta.setDuration(200);
        listview.startAnimation(ta);
    }

    public void hideListAnimation() {
        TranslateAnimation ta = new TranslateAnimation(1, 0f, 1, 0f, 1, 0f, 1, 1f);
        ta.setDuration(200);
        listview.startAnimation(ta);
        ta.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                listview.setVisibility(View.GONE);
            }
        });
    }

    private int imgSize = 0;
    
    private void initView() {
        imageAll = new ImageFloder();
        imageAll.setDir("/所有图片");
        currentImageFolder = imageAll;
        mDirPaths.add(imageAll);
        btn_select = (Button) findViewById(R.id.btn_select);
        gridview = (GridView) findViewById(R.id.gridview);
        
        int mImageSpacing = getResources().getDimensionPixelSize(R.dimen.share_img_margin_5);
        int mImageMargin = getResources().getDimensionPixelSize(R.dimen.share_img_margin_5);
        
        // 屏幕宽度
        int screenWidth = CommonUtil.getDeviceSize(this).x;
        // 计算得出图片大小
        imgSize = (screenWidth - mImageMargin * 2 - (3 - 1) * mImageSpacing) / 3;
        
        adapter = new PictureAdapter();
        gridview.setAdapter(adapter);

        listview = (ListView) findViewById(R.id.listview);
        folderAdapter = new FolderAdapter();
        listview.setAdapter(folderAdapter);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentImageFolder = mDirPaths.get(position);
                hideListAnimation();
                adapter.notifyDataSetChanged();
                btn_select.setText(currentImageFolder.getName());
            }
        });
        
        btn_select.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listview.getVisibility() == View.VISIBLE) {
                    hideListAnimation();
                } else {
                	listview.setVisibility(View.VISIBLE);
                    showListAnimation();
                    folderAdapter.notifyDataSetChanged();
                }
            }
        });
        
        getThumbnail();
    }

    /** 使用相机拍照 */
    protected void goCamare() {
        if (selectedPicture.size() + 1 > MAX_NUM) {
            Toast.makeText(context, "最多选择" + MAX_NUM + "张", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = getOutputMediaFileUri();
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    /** 用于拍照时获取输出的Uri */
    protected Uri getOutputMediaFileUri() {
        File mediaStorageDir = new File(CommonConstant.IMAGE_PATH);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        cameraPath = mediaFile.getAbsolutePath();
        return Uri.fromFile(mediaFile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && cameraPath != null) {
            selectedPicture.add(cameraPath);
            Intent data2 = new Intent();
            data2.putExtra(INTENT_SELECTED_PICTURE, selectedPicture);
            setResult(RESULT_OK, data2);
            this.finish();
        }else{
        	this.finish();
        }
    }

    public void finish(View v) {
        onBackPressed();
    }

    class PictureAdapter extends BaseAdapter {
        @Override
        public int getCount() {
        	if (type.equals(PHOTOALBUM)) {
        		return currentImageFolder.images.size();
			}
            return currentImageFolder.images.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.grid_item_picture, null);
                holder = new ViewHolder();
                holder.iv = (ImageView) convertView.findViewById(R.id.iv);
                holder.check = (Button) convertView.findViewById(R.id.check);
                
                LayoutParams param = new LayoutParams(imgSize, imgSize);
				holder.iv.setLayoutParams(param);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            
            if (type.equals(PHOTOALBUM)) {// 只有图库
                holder.check.setVisibility(View.VISIBLE);
                final ImageItem item = currentImageFolder.images.get(position);
                LoaderImage.getInstance(R.mipmap.empty_photo).ImageLoaders("file://" + item.path, holder.iv);
                boolean isSelected = selectedPicture.contains(item.path);
                final Button box = holder.check;
                convertView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!box.isSelected() && selectedPicture.size() + 1 > MAX_NUM) {
                        	Toast.makeText(context, "最多选择" + MAX_NUM + "张", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (selectedPicture.contains(item.path)) {
                            selectedPicture.remove(item.path);
                        } else {
                            selectedPicture.add(item.path);
                        }
                        box.setSelected(selectedPicture.contains(item.path));
                    }
                });
                holder.check.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!box.isSelected() && selectedPicture.size() + 1 > MAX_NUM) {
                        	Toast.makeText(context, "最多选择" + MAX_NUM + "张", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (selectedPicture.contains(item.path)) {
                            selectedPicture.remove(item.path);
                        } else {
                            selectedPicture.add(item.path);
                        }
                        box.setSelected(selectedPicture.contains(item.path));
                    }
                });
                holder.check.setSelected(isSelected);
            } else if (type.equals(ALL)) {//全部
                if (position == 0) {
                	LoaderImage.getInstance(R.mipmap.pickphotos_to_camera_normal).ImageLoaders("", holder.iv);
                    holder.check.setVisibility(View.INVISIBLE);
                    convertView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							goCamare();
						}
					});
                } else {
                    position = position - 1;
                    holder.check.setVisibility(View.VISIBLE);
                    final ImageItem item = currentImageFolder.images.get(position);
                    LoaderImage.getInstance(R.mipmap.empty_photo).ImageLoaders("file://" + item.path, holder.iv);
                    boolean isSelected = selectedPicture.contains(item.path);
                    final Button box = holder.check;
                    
                    convertView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!box.isSelected() && selectedPicture.size() + 1 > MAX_NUM) {
                            	Toast.makeText(context, "最多选择" + MAX_NUM + "张", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (selectedPicture.contains(item.path)) {
                                selectedPicture.remove(item.path);
                            } else {
                                selectedPicture.add(item.path);
                            }
                            box.setSelected(selectedPicture.contains(item.path));
                        }
                    });
                    holder.check.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!box.isSelected() && selectedPicture.size() + 1 > MAX_NUM) {
                            	Toast.makeText(context, "最多选择" + MAX_NUM + "张", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (selectedPicture.contains(item.path)) {
                                selectedPicture.remove(item.path);
                            } else {
                            	try {
									selectedPicture.add(new String(item.path.getBytes(), "GBK"));
								} catch (UnsupportedEncodingException e) {
									e.printStackTrace();
								}
                            }
                            box.setSelected(selectedPicture.contains(item.path));
                        }
                    });
                    holder.check.setSelected(isSelected);
                }
            }
            return convertView;
        }
    }

    class ViewHolder {
        ImageView iv;
        Button check;
    }

    class FolderAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDirPaths.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FolderViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.list_dir_item, null);
                holder = new FolderViewHolder();
                holder.id_dir_item_image = (ImageView) convertView.findViewById(R.id.id_dir_item_image);
                holder.id_dir_item_name = (TextView) convertView.findViewById(R.id.id_dir_item_name);
                holder.id_dir_item_count = (TextView) convertView.findViewById(R.id.id_dir_item_count);
                holder.choose = (ImageView) convertView.findViewById(R.id.choose);
                convertView.setTag(holder);
            } else {
                holder = (FolderViewHolder) convertView.getTag();
            }
            ImageFloder item = mDirPaths.get(position);
            LoaderImage.getInstance(R.mipmap.empty_photo).ImageLoaders("file://" + item.getFirstImagePath(), holder.id_dir_item_image);
            holder.id_dir_item_count.setText(item.images.size() + "张");
            holder.id_dir_item_name.setText(item.getName());
            holder.choose.setVisibility(currentImageFolder == item ? View.VISIBLE : View.GONE);
            return convertView;
        }
    }

    class FolderViewHolder {
        ImageView id_dir_item_image;
        ImageView choose;
        TextView id_dir_item_name;
        TextView id_dir_item_count;
    }

    /** 得到缩略图 */
    private void getThumbnail() {
        Cursor mCursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.ImageColumns.DATA }, "", null,
                MediaStore.MediaColumns.DATE_ADDED + " DESC");
        Log.e("TAG", mCursor.getCount() + "");
        if (mCursor.moveToFirst()) {
            int _date = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            do { // 获取图片的路径
                String path = mCursor.getString(_date);
                Log.e("TAG", path);
                imageAll.images.add(new ImageItem(path));
                // 获取该图片的父路径名
                File parentFile = new File(path).getParentFile();
                if (parentFile == null) {
                    continue;
                }
                ImageFloder imageFloder = null;
                String dirPath = parentFile.getAbsolutePath();
                if (!tmpDir.containsKey(dirPath)) {
                    // 初始化imageFloder
                    imageFloder = new ImageFloder();
                    imageFloder.setDir(dirPath);
                    imageFloder.setFirstImagePath(path);
                    mDirPaths.add(imageFloder);
                    Log.d("zyh", dirPath + "," + path);
                    tmpDir.put(dirPath, mDirPaths.indexOf(imageFloder));
                } else {
                    imageFloder = mDirPaths.get(tmpDir.get(dirPath));
                }
                imageFloder.images.add(new ImageItem(path));
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        for (int i = 0; i < mDirPaths.size(); i++) {
            ImageFloder f = mDirPaths.get(i);
            Log.d("zyh", i + "-----" + f.getName() + "---" + f.images.size());
        }
        tmpDir = null;
    }

    class ImageFloder {
        /** 图片的文件夹路径 */
        private String dir;

        /** 第一张图片的路径 */
        private String firstImagePath;
        /** 文件夹的名称 */
        private String name;
        public List<ImageItem> images = new ArrayList<ImageItem>();

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
            int lastIndexOf = this.dir.lastIndexOf("/");
            this.name = this.dir.substring(lastIndexOf);
        }

        public String getFirstImagePath() {
            return firstImagePath;
        }

        public void setFirstImagePath(String firstImagePath) {
            this.firstImagePath = firstImagePath;
        }

        public String getName() {
            return name;
        }
    }

	class ImageItem {
		String path;
		public ImageItem(String p) {
			this.path = p;
		}
	}
}
