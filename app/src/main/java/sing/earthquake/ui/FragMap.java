package sing.earthquake.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Request;
import okhttp3.Response;
import sing.earthquake.MyApplication;
import sing.earthquake.R;
import sing.earthquake.bean.BuildListBean;
import sing.earthquake.common.Urls;
import sing.earthquake.common.gson.GsonImpl;
import sing.okhttp.okhttputils.OkHttpUtils;
import sing.okhttp.okhttputils.cache.CacheMode;
import sing.okhttp.okhttputils.callback.StringCallback;

/**
 * @author: LiangYX
 * @ClassName: FragMap
 * @date: 16/8/26 下午1:35
 * @Description: 地图模式
 */
public class FragMap extends Fragment {

    private View view;
    MapView mMapView = null;
    BaiduMap mBaiduMap;
    private Activity context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_map, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();

        init();
    }
    private InfoWindow mInfoWindow;
    private void init() {
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(msu);

        LatLng ll = new LatLng(39.936183,116.454574);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(13.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                marker.setIcon(bd);
                View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.pop_map, null);
//                TextView aa = (TextView) view.findViewById(R.id.tv_aaa);
//                aa.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mBaiduMap.hideInfoWindow();
//                    }
//                });
                LatLng ll = marker.getPosition();
                mInfoWindow = new InfoWindow(view, ll, -47);
                mBaiduMap.showInfoWindow(mInfoWindow);

                return true;
            }
        });

        OkHttpUtils.get(Urls.contentList)     // 请求方式和请求url
                .headers("token", MyApplication.preference().getString("token",""))
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("contentList")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new MyCallback(context));
    }

    private class MyCallback extends StringCallback {
        public MyCallback(Context context) {
            super(context,true);
        }

        @Override
        public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
            super.onResponse(isFromCache, s, request, response);
            JSONObject json = null;
            try {
                json = new JSONObject(s);
                String status = json.optString("success");
                String msg = json.optString("msg");
                int code = json.optInt("code");//失败才有
                if ("true".equals(status)){
                    BuildListBean bean = GsonImpl.get().toObject(json.optString("data"), BuildListBean.class);
                    initOverlay(bean);
                }else{
                    if (9430 == code){ //请重新登陆

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.mipmap.icon_gcoding);

    private void initOverlay(BuildListBean bean) {
        int a = bean.getDataRows().size();
        for (int i = 0;i<a;i++){
            LatLng llA = new LatLng(bean.getDataRows().get(i).getLat(), bean.getDataRows().get(i).getLon());
            Log.e("111",bean.getDataRows().get(i).getLat()+","+bean.getDataRows().get(i).getLon());
            MarkerOptions ooA = new MarkerOptions().position(llA).icon(bd).zIndex(9).draggable(true);
            mBaiduMap.addOverlay(ooA);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        bd.recycle();
    }
}