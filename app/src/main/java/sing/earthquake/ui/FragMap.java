package sing.earthquake.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sing.earthquake.bean.BuildListBean.DataRowsBean;

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

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;
import sing.earthquake.MyApplication;
import sing.earthquake.R;
import sing.earthquake.bean.BuildBean;
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
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private Activity context;
    private List<DataRowsBean> list;
    private Marker marker;

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
        list = new ArrayList<>();
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(msu);

        LatLng ll = new LatLng(39.936183,116.454574);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(13.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                mBaiduMap.hideInfoWindow();
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                if (marker != null && aa == 2) {
                    aa = 3;
                    setShowMarker(marker, false);
                }
            }
        });

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                FragMap.this.marker = marker;
                aa = 1;
                setShowMarker(marker, true);
                return true;
            }
        });

        OkHttpUtils.get(Urls.contentList)     // 请求方式和请求url
                .headers("token", MyApplication.preference().getString("token",""))
                .params("totalRecord", "200")
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("contentList")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new MyCallback(context));
    }

    int aa = 1;
    public void setShowMarker(Marker marker,boolean a){
        aa = aa + 1;
        if (list != null && list.size() > 0) {
            marker.setIcon(bd);
            View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.pop_map, null);
            TextView tvBuildName = (TextView) view.findViewById(R.id.tv_build_name);
            TextView tvAddress = (TextView) view.findViewById(R.id.tv_address);
            TextView tvHeight = (TextView) view.findViewById(R.id.tv_height);
            TextView tvBuildLayers = (TextView) view.findViewById(R.id.tv_build_layers);
            TextView tvEndTime = (TextView) view.findViewById(R.id.tv_end_time);
            TextView tvBuildArea = (TextView) view.findViewById(R.id.tv_build_area);
            TextView tvBuildPeopleNum = (TextView) view.findViewById(R.id.tv_build_people_num);
            TextView tvUsed = (TextView) view.findViewById(R.id.tv_used);

            view.findViewById(R.id.iv_hide).setOnClickListener(v -> {
                mBaiduMap.hideInfoWindow();//隐藏弹出物
            });
            LatLng ll = marker.getPosition();
            mInfoWindow = new InfoWindow(view, ll, -47);
            mBaiduMap.showInfoWindow(mInfoWindow);

            if (a){
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(13.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

            for (DataRowsBean bean : list) {
                if (ll.latitude == Double.parseDouble(bean.getLat()) && ll.longitude == Double.parseDouble(bean.getLon())) {
                    tvBuildName.setText(bean.getJzmc());
                    tvAddress.setText(bean.getAddress());
                    tvHeight.setText(bean.getJzwgd() + "M");
                    if (!TextUtils.isEmpty(bean.getDsjzcs()) && !TextUtils.isEmpty(bean.getDxjzcs())) {
                        tvBuildLayers.setText("地上 " + bean.getDsjzcs() + "层，地下" + bean.getDxjzcs() + "层");
                    } else if (TextUtils.isEmpty(bean.getDsjzcs()) && !TextUtils.isEmpty(bean.getDxjzcs())) {
                        tvBuildLayers.setText("地下" + bean.getDxjzcs() + "层");
                    } else if (!TextUtils.isEmpty(bean.getDsjzcs()) && TextUtils.isEmpty(bean.getDxjzcs())) {
                        tvBuildLayers.setText("地上" + bean.getDsjzcs() + "层");
                    } else {
                        tvBuildLayers.setText("");
                    }
                    tvEndTime.setText(bean.getJgsj());
                    tvBuildArea.setText(bean.getJzmj());
                    tvBuildPeopleNum.setText(bean.getPeopleCount() + "人");
                    tvUsed.setText(bean.getYt());

                    view.findViewById(R.id.tv_detail).setOnClickListener(v -> {//查看
                        Intent intent = new Intent();
                        intent.putExtra("build_bean", bean);
                        intent.putExtra("type", 2);
                        intent.setClass(context, ActBigForm.class);
                        startActivity(intent);
                    });
                    view.findViewById(R.id.tv_change).setOnClickListener(v -> {//修改
                        Intent intent = new Intent();
                        intent.putExtra("build_bean", bean);
                        intent.putExtra("type", 1);
                        intent.setClass(context, ActBigForm.class);
                        startActivity(intent);
                    });
                }
            }
        }
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
                    list = bean.getDataRows();
                    initOverlay(list);
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

    private void initOverlay(List<DataRowsBean> list) {
        int a =list.size();
        for (int i = 0;i<a;i++){
            LatLng llA = new LatLng((Double.parseDouble(list.get(i).getLat())), (Double.parseDouble(list.get(i).getLon())));
            Log.e("111",list.get(i).getLat()+","+list.get(i).getLon());
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