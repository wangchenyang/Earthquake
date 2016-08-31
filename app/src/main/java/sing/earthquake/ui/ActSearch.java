package sing.earthquake.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import sing.earthquake.R;
import sing.earthquake.adapter.StreetAdapter;
import sing.earthquake.bean.BottomMenuBean;
import sing.earthquake.common.BaseActivity;
import sing.earthquake.common.streetinfo.StreetInfo;

public class ActSearch extends BaseActivity {

    private Activity context;

    /** 建筑名称 */
    private EditText etBuildName;
    /** 建筑物高度 */
    private EditText etMinHoldHeight,etMaxHoldHeight;
    /** 地上楼层 */
    private EditText etMinBuildFloor,etMaxbuildFloor;
    /** 竣工时间 */
    private EditText etMinEndTime,etMaxEndTime;
    /** 居住人数 */
    private EditText etMinLiveNum,etMaxLiveNum;
    /** 用途 */
    private TextView tvUsed;
    /** 街道 */
    private TextView tvStreet;
    /** 社区 */
    private TextView tvCommunity;

    @Override
    public int getLayoutId() {
        return R.layout.act_search;
    }

    @Override
    public void init() {
        context = this;

        etBuildName = (EditText) findViewById(R.id.et_build_name);
        etMinHoldHeight = (EditText) findViewById(R.id.et_min_hold_height);
        etMaxHoldHeight = (EditText) findViewById(R.id.et_max_hold_height);
        etMinBuildFloor = (EditText) findViewById(R.id.et_min_build_floor);
        etMaxbuildFloor = (EditText) findViewById(R.id.et_max_build_floor);
        etMinEndTime = (EditText) findViewById(R.id.et_min_end_time);
        etMaxEndTime = (EditText) findViewById(R.id.et_max_end_time);
        etMinLiveNum = (EditText) findViewById(R.id.et_min_live_num);
        etMaxLiveNum = (EditText) findViewById(R.id.et_max_live_num);
        tvUsed = (TextView) findViewById(R.id.tv_used);
        tvStreet = (TextView) findViewById(R.id.tv_street);
        tvCommunity = (TextView) findViewById(R.id.tv_community);
        tvUsed.setText("不限");
        tvStreet.setText("不限");
        tvCommunity.setText("不限");
    }

    /**
     * 关闭
     * @param v
     */
    public void finish(View v){
        finish();
    }

    /**
     * 选择用途
     * @param v
     */
    public void used(View v){
        PopupWindow popupWindow;
        final View view1 = LayoutInflater.from(context).inflate(R.layout.pop_list, null);
        popupWindow = new PopupWindow(view1, v.getWidth(), v.getHeight() * 6, true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAsDropDown(v);

        ListView listview = (ListView) view1.findViewById(R.id.listview);
        StreetAdapter adapter = new StreetAdapter(context,-200);
        listview.setAdapter(adapter);

        List<BottomMenuBean> list = StreetInfo.getUsed();
        listview.setOnItemClickListener((parent, view, position, id) -> {
            tvUsed.setText(list.get(position).content);
            popupWindow.dismiss();
        });
    }

    /**
     * 选择街道
     * @param v
     */
    int street = 0;
    public void street(View v){
        PopupWindow popupWindow;
        final View view1 = LayoutInflater.from(context).inflate(R.layout.pop_list, null);
        popupWindow = new PopupWindow(view1, v.getWidth(), v.getHeight() * 6, true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAsDropDown(v);

        ListView listview = (ListView) view1.findViewById(R.id.listview);
        StreetAdapter adapter = new StreetAdapter(context,-100);
        listview.setAdapter(adapter);

        List<BottomMenuBean> list = StreetInfo.getStreet();
        listview.setOnItemClickListener((parent, view, position, id) -> {
            tvStreet.setText(list.get(position).content);
            tvCommunity.setText("不限");
            street = Integer.parseInt(list.get(position).id);
            popupWindow.dismiss();
        });
    }
    /**
     * 选择社区
     * @param v
     */
    public void community(View v){
        PopupWindow popupWindow;
        final View view1 = LayoutInflater.from(context).inflate(R.layout.pop_list, null);
        popupWindow = new PopupWindow(view1, v.getWidth(), v.getHeight() * 6, true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAsDropDown(v);

        ListView listview = (ListView) view1.findViewById(R.id.listview);
        StreetAdapter adapter = new StreetAdapter(context,street);
        listview.setAdapter(adapter);

        List<BottomMenuBean> list = StreetInfo.getCommunity(street + "");
        listview.setOnItemClickListener((parent, view, position, id) -> {
            tvCommunity.setText(list.get(position).content);
            popupWindow.dismiss();
        });
    }

    /**
     * 查询
     * @param v
     */
    public void submit(View v){
        String[] parms = new String[12];
        parms[0] = etBuildName.getText().toString().trim();//建筑名称
        parms[1] = tvUsed.getText().toString().trim();//现在用途或功能
        parms[2] = tvStreet.getText().toString().trim();//所属街道
        parms[3] = tvCommunity.getText().toString().trim();//所属社区
        parms[4] = etMinHoldHeight.getText().toString().trim();//建筑物开始高度
        parms[5] = etMaxHoldHeight.getText().toString().trim();//建筑物结束高度
        parms[6] = etMinBuildFloor.getText().toString().trim();//地上建筑层数开始
        parms[7] = etMaxbuildFloor.getText().toString().trim();//地上建筑层数结束
        parms[8] = etMinEndTime.getText().toString().trim();//竣工时间开始
        parms[9] = etMaxEndTime.getText().toString().trim();//竣工时间结束
        parms[10] = etMinLiveNum.getText().toString().trim();//常驻人数开始
        parms[11] = etMaxLiveNum.getText().toString().trim();//常熟人数结束

        if ("不限".equals(parms[1])){
            parms[1] = "";
        }
        if ("不限".equals(parms[2])){
            parms[2] = "";
        }
        if ("不限".equals(parms[3])){
            parms[3] = "";
        }
        if (parms[1].endsWith(",")){
            parms[1] = parms[1].substring(0,parms[1].length()-1);
        }
        Intent intent = new Intent(context,ActShowSearch.class);
        intent.putExtra("parms",parms);
        startActivity(intent);
    }
}