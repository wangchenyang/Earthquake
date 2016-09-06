package sing.earthquake.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

import sing.earthquake.R;
import sing.earthquake.common.BaseActivity;
import sing.earthquake.util.ToastUtil;

public class ActLocation extends BaseActivity {

    private MapView mapview;
    private BaiduMap map;
    private int type = 0;//2是查看
    private String lon;
    private String lat;
    private BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.mipmap.icon_gcoding);
    /** 屏幕中间显示,用于定位位置 */
    private ImageView iv;

    @Override
    public int getLayoutId() {
        return R.layout.act_location;
    }

    @Override
    public void init() {
        iv = (ImageView) findViewById(R.id.iv);
        mapview = (MapView) findViewById(R.id.mapview);
        map = mapview.getMap();

        type = getIntent().getExtras().getInt("type", 0);
        lon = getIntent().getExtras().getString("lon", "116.454574");
        lat = getIntent().getExtras().getString("lat","39.936183");

        LatLng ll = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(13.0f);
        map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        if (type != 2){
            iv.setVisibility(View.VISIBLE);
            map.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
                @Override
                public void onMapStatusChangeStart(MapStatus status) {
                }

                @Override
                public void onMapStatusChangeFinish(MapStatus status) {
                    updateMapState(status);
                }

                @Override
                public void onMapStatusChange(MapStatus status) {
                }
            });
        }else{
            iv.setVisibility(View.GONE);
            MarkerOptions ooA = new MarkerOptions().position(ll).icon(bd).zIndex(13).draggable(true);
            map.addOverlay(ooA);
        }
    }

    private void updateMapState(MapStatus status) {
        LatLng mCenterLatLng = status.target;
        lat = String.valueOf(mCenterLatLng.latitude);
        lon = String.valueOf(mCenterLatLng.longitude);
    }

    public void finish(View v){
        finish();
    }

    public void ok(View v){
        if (!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lon)){
            Intent intent = new Intent();
            intent.putExtra("lat",lat);
            intent.putExtra("lon",lon);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }else{
            ToastUtil.showToast("请选择位置");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bd.recycle();
        mapview.onDestroy();
    }
}
