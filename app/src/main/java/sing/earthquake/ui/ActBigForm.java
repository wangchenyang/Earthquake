package sing.earthquake.ui;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import sing.earthquake.MyApplication;
import sing.earthquake.R;
import sing.earthquake.adapter.StreetAdapter;
import sing.earthquake.bean.BottomMenuBean;
import sing.earthquake.bean.BuildListBean;
import sing.earthquake.bean.BuildListBean.DataRowsBean;
import sing.earthquake.common.BaseActivity;
import sing.earthquake.common.Urls;
import sing.earthquake.common.gson.GsonImpl;
import sing.earthquake.common.photo.ActPreViewIcon;
import sing.earthquake.common.photo.SelectPictureActivity;
import sing.earthquake.common.streetinfo.StreetInfo;
import sing.earthquake.util.CommonUtil;
import sing.earthquake.util.FormUtil;
import sing.earthquake.util.LoaderImage;
import sing.earthquake.util.ToastUtil;
import sing.okhttp.okhttputils.OkHttpUtils;
import sing.okhttp.okhttputils.cache.CacheMode;
import sing.okhttp.okhttputils.callback.StringCallback;

/**
 * @className   ActBigForm
 * @time        2016/9/2 16:44
 * @author      LiangYx
 * @description 大表单页面，添加、修改、查看
 */
public class ActBigForm extends BaseActivity implements View.OnClickListener{

    private Activity context;

    /** 跳转类型，0为添加建筑物，1为修改建筑物，2为查看建筑物 */
    private int type = 0;
    private List<TextView> textList;//保存所有的TextView控件
    private List<EditText> editList;//保存所有的EditText控件
    private List<RadioButton> radioList;//保存所有的RadioButton控件
    /** 所有赋值都从bean里面取，所有请求都从bean里面取，提交数据时先将list的值保存至bean */
    private DataRowsBean bean;
    /** 地上楼层数和地下楼层数 */
    private String buildLayersUp,buildLayersDown;
    /** 正面图 */
    private ImageView ivPositive;
    /** 侧面图 */
    private ImageView ivSide;
    /** 背面图 */
    private ImageView ivBack;
    /** 第一次进来修改的时候防止本来有值的选项弹框 */
    private boolean isFirst;


    @Override
    public int getLayoutId() {
        context = this;
        return R.layout.act_big_form;
    }

    @Override
    public void init() {
        isFirst = true;
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        type = getIntent().getExtras().getInt("type",0);
        if (type == 0){
            bean = new DataRowsBean();
            tvTitle.setText("添加建筑信息");
        }else if (type == 1){
            bean = (DataRowsBean) getIntent().getExtras().getSerializable("build_bean");
            tvTitle.setText("修改建筑信息");
        }else if (type == 2){
            bean = (DataRowsBean) getIntent().getExtras().getSerializable("build_bean");
            tvTitle.setText("查看建筑信息");
            findViewById(R.id.bt_submit).setVisibility(View.GONE);
        }

        initList();
        setValue();
    }

    /**
     * 关闭
     * @param v
     */
    public void finish(View v){
        finish();
    }
    /**
     * 关闭
     * @param v
     */
    public void location(View v){
        Intent intent = new Intent(context,ActLocation.class);
        intent.putExtra("type",type);
        if (type == 2){//查看
            if (TextUtils.isEmpty(bean.getLon()) || TextUtils.isEmpty(bean.getLat())){
                ToastUtil.showToast("缺少位置信息");
                return;
            }
        }
        intent.putExtra("lon",bean.getLon());
        intent.putExtra("lat",bean.getLat());
        startActivityForResult(intent, 100);
    }

    /**
     * 初始化所有的List
     */
    private void initList() {

        textList = new ArrayList<>();
        editList = new ArrayList<>();
        radioList = new ArrayList<>();

        editList.add((EditText) findViewById(R.id.et_build_name));//建筑物名称
        editList.add((EditText) findViewById(R.id.et_build_address));//详细地址
        editList.add((EditText) findViewById(R.id.et_postcode));//邮政编码
        textList.add((TextView) findViewById(R.id.tv_street));//所属街道
        textList.add((TextView) findViewById(R.id.tv_community));//所属社区
        editList.add((EditText) findViewById(R.id.et_residential));//所属小区
        editList.add((EditText) findViewById(R.id.et_build_num));//楼座编号
        editList.add((EditText) findViewById(R.id.et_build_height));//建筑物高度
        textList.add((TextView) findViewById(R.id.tv_build_layers));//楼层数
        editList.add((EditText) findViewById(R.id.et_design_unit));//设计单位
        editList.add((EditText) findViewById(R.id.et_build_unit));//建设单位
        editList.add((EditText) findViewById(R.id.et_construction_unit));//施工单位
        editList.add((EditText) findViewById(R.id.et_control_unit));//监理单位
        editList.add((EditText) findViewById(R.id.et_end_time));//竣工时间
        editList.add((EditText) findViewById(R.id.et_build_area));//建筑面积
        editList.add((EditText) findViewById(R.id.et_living_people_num));//办公人数
        radioList.add((RadioButton) findViewById(R.id.rb_have_image));//有建筑图纸
        radioList.add((RadioButton) findViewById(R.id.rb_no_have_image));//无建筑图纸
        textList.add((TextView) findViewById(R.id.tv_purpose));//用途
        radioList.add((RadioButton) findViewById(R.id.rb_is_no_historical));//不是文物单位
        radioList.add((RadioButton) findViewById(R.id.rb_is_historical));//是文物单位
        textList.add((TextView) findViewById(R.id.tv_historical_level));//文物单位级别
        isHistorical();//添加监听
        textList.add((TextView) findViewById(R.id.tv_strong));//抗震防裂度
        textList.add((TextView) findViewById(R.id.tv_strong_level));//抗震设防分类等级
        textList.add((TextView) findViewById(R.id.tv_strong_standard));//设计规范
        textList.add((TextView) findViewById(R.id.tv_build_form));//建筑基础形式
        textList.add((TextView) findViewById(R.id.tv_structure_type));//结构类型
        textList.add((TextView) findViewById(R.id.tv_wall_material));//墙体材料
        radioList.add((RadioButton) findViewById(R.id.rb_have_ring_beam));//有圈梁
        radioList.add((RadioButton) findViewById(R.id.rb_no_have_ring_beam));//无圈梁
        radioList.add((RadioButton) findViewById(R.id.rb_have_structural_column));//有构造柱
        radioList.add((RadioButton) findViewById(R.id.rb_no_have_structural_column));//无构造柱
        textList.add((TextView) findViewById(R.id.tv_build_top_type));//房顶类型
        textList.add((TextView) findViewById(R.id.tv_field_type));//场地类别
        textList.add((TextView) findViewById(R.id.tv_facilities));//设施和施工材料
        radioList.add((RadioButton) findViewById(R.id.rb_no_have_fall_hazard));//无坠落危险物
        radioList.add((RadioButton) findViewById(R.id.rb_have_fall_hazard));//有坠落危险物
        textList.add((TextView) findViewById(R.id.tv_fall_hazard));//坠落危险物名
        isFallHazard();//设置监听
        radioList.add((RadioButton) findViewById(R.id.rb_no_have_reinforce));//进行抗震加固 否
        radioList.add((RadioButton) findViewById(R.id.rb_have_reinforce));//进行抗震加固 是
        editList.add((EditText) findViewById(R.id.et_reinforce_time));//加固时间
        radioList.add((RadioButton) findViewById(R.id.rb_no_appraisal_dangerous));//是否鉴定为危房  否
        radioList.add((RadioButton) findViewById(R.id.rb_appraisal_dangerous));//是否鉴定为危房  是
        editList.add((EditText) findViewById(R.id.et_appraisal_unit));//鉴定单位
        radioList.add((RadioButton) findViewById(R.id.rb_no_have_crack));//主体结构无裂缝
        radioList.add((RadioButton) findViewById(R.id.rb_have_crack));//主体结构有裂缝
        textList.add((TextView) findViewById(R.id.tv_crack));//裂缝位置
        isCrack();//设置监听
        editList.add((EditText) findViewById(R.id.et_crack_condition));//裂缝情况
        radioList.add((RadioButton) findViewById(R.id.rb_is_rect));//平面是方形或矩形
        radioList.add((RadioButton) findViewById(R.id.rb_is_no_rect));//平面不是方形或矩形
        radioList.add((RadioButton) findViewById(R.id.rb_is_rule));//里面规则
        radioList.add((RadioButton) findViewById(R.id.rb_is_no_rule));//里面规则
        ivPositive = (ImageView) findViewById(R.id.iv_positive);
        ivSide = (ImageView) findViewById(R.id.iv_side);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivPositive.setOnClickListener(this);
        ivSide.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        if (type == 2){
            for (EditText et:editList) {
                et.setFocusable(false);
                et.setEnabled(false);
            }
            for (RadioButton rb:radioList){
                rb.setEnabled(false);
            }
        }
    }

    /**
     * 赋值
     */
    private void setValue() {
        if (type == 1 || type == 2) {//修改或查看
            editList.get(0).setText(bean.getJzmc());//建筑物名称
            editList.get(1).setText(bean.getAddress());//详细地址
            editList.get(2).setText(bean.getPostcode());//邮政编码
            textList.get(0).setText(bean.getSsjd());//所属街道
            textList.get(1).setText(bean.getSsshequ());//所属社区
            editList.get(3).setText(bean.getSsxiaoqu());//所属小区
            editList.get(4).setText(bean.getLouzuobianhao());//楼座编号
            editList.get(5).setText(bean.getJzwgd());//建筑物高度
            buildLayersUp = bean.getDsjzcs();//地上楼层数
            buildLayersDown = bean.getDxjzcs();//地下楼层数
            setLayers();
            editList.get(6).setText(bean.getSjdw());//设计单位
            editList.get(7).setText(bean.getJsdw());//建设单位
            editList.get(8).setText(bean.getSgdw());// 施工单位
            editList.get(9).setText(bean.getJldw());//监理单位
            editList.get(10).setText(bean.getJgsj());//竣工时间
            editList.get(11).setText(bean.getJzmj());// 建筑面积
            editList.get(12).setText(bean.getPeopleCount());// 办公人数
            if ("有".equals(bean.getTuzhi())){//有建筑图纸
                radioList.get(0).setChecked(true);
                radioList.get(1).setChecked(false);
            }else if ("无".equals(bean.getTuzhi())){//无建筑图纸
                radioList.get(0).setChecked(false);
                radioList.get(1).setChecked(true);
            }else{//未填
                radioList.get(0).setChecked(false);
                radioList.get(1).setChecked(false);
            }
            textList.get(3).setText(bean.getYt());//用途
            if ("否".equals(bean.getWenwudanwei())){//文物单位
                radioList.get(2).setChecked(true);
                radioList.get(3).setChecked(false);
                textList.get(4).setVisibility(View.GONE);
            }else if ("".equals(bean.getWenwudanwei())){
                radioList.get(2).setChecked(false);
                radioList.get(3).setChecked(false);
                textList.get(4).setVisibility(View.GONE);
            }else{
                radioList.get(2).setChecked(false);
                radioList.get(3).setChecked(true);
                textList.get(4).setVisibility(View.VISIBLE);
                textList.get(4).setText(bean.getWenwudanwei());
            }
            textList.get(5).setText(bean.getKz());//抗震防裂度
            textList.get(6).setText(bean.getFldj());//抗震设防分类等级
            textList.get(7).setText(bean.getGuifan());//设计规范
            textList.get(8).setText(bean.getJzjcxs());//建筑基础形式
            textList.get(9).setText(bean.getJglx());//结构类型
            textList.get(10).setText(bean.getQtcl());//墙体材料
            if ("有圈梁".equals(bean.getYwql())){
                radioList.get(4).setChecked(true);
                radioList.get(5).setChecked(false);
            }else if ("无圈梁".equals(bean.getYwql())){
                radioList.get(4).setChecked(false);
                radioList.get(5).setChecked(true);
            }else{
                radioList.get(4).setChecked(false);
                radioList.get(5).setChecked(false);
            }
            if ("有构造柱".equals(bean.getYwgzz())){
                radioList.get(6).setChecked(true);
                radioList.get(7).setChecked(false);
            }else if ("无构造柱".equals(bean.getYwgzz())){
                radioList.get(6).setChecked(false);
                radioList.get(7).setChecked(true);
            } else {
                radioList.get(6).setChecked(false);
                radioList.get(7).setChecked(false);
            }
            textList.get(11).setText(bean.getLdlx());//楼顶类型
            textList.get(12).setText(bean.getCdlx());//场地类别
            textList.get(13).setText(bean.getSgzl());//设施和施工材料
            if ("".equals(bean.getZzwxw())){
                radioList.get(8).setChecked(false);
                radioList.get(9).setChecked(false);
                textList.get(14).setText("");
            }else if ("无".equals(bean.getZzwxw())){
                radioList.get(8).setChecked(true);
                radioList.get(9).setChecked(false);
                textList.get(14).setText("");
            }else {
                radioList.get(8).setChecked(false);
                radioList.get(9).setChecked(true);
                textList.get(14).setText(bean.getZzwxw());
            }
            if ("否".equals(bean.getSfjgkzjg())){//进行抗震加固 否
                radioList.get(10).setChecked(true);
                radioList.get(11).setChecked(false);
            }else if ("是".equals(bean.getSfjgkzjg())){
                radioList.get(10).setChecked(false);
                radioList.get(11).setChecked(true);
            }
            editList.get(13).setText(bean.getKzjgsj());//抗震加固时间
            if ("否".equals(bean.getShifouweifang())){//不是危房
                radioList.get(12).setChecked(true);
                radioList.get(13).setChecked(false);
            }else if ("是".equals(bean.getShifouweifang())){
                radioList.get(12).setChecked(false);
                radioList.get(13).setChecked(true);
            }
            editList.get(14).setText(bean.getJiandingdanwei());//危房鉴定单位
            if ("无".equals(bean.getZtjglf())){//主体结构裂缝
                radioList.get(14).setChecked(true);
                radioList.get(15).setChecked(false);
                textList.get(15).setText("");
            }else if (TextUtils.isEmpty(bean.getZtjglf())){
                radioList.get(14).setChecked(false);
                radioList.get(15).setChecked(true);
                textList.get(15).setText(bean.getZtjglf());
            }
            editList.get(15).setText(bean.getZtjglfqk());//主体结构裂缝情况
            if ("是".equals(bean.getPmgz())){//平面是方形或矩形
                radioList.get(16).setChecked(true);
                radioList.get(17).setChecked(false);
            }else if ("否".equals(bean.getPmgz())){
                radioList.get(16).setChecked(false);
                radioList.get(17).setChecked(true);
            }
            if ("是".equals(bean.getLmgz())){//立面规则
                radioList.get(18).setChecked(true);
                radioList.get(19).setChecked(false);
            }else if ("否".equals(bean.getLmgz())) {
                radioList.get(18).setChecked(false);
                radioList.get(19).setChecked(true);
            }
        }

        positive = bean.getZhengmian();
        side = bean.getCemian();
        back = bean.getBeimian();
        LoaderImage.getInstance(0).ImageLoaders(positive,ivPositive);
        LoaderImage.getInstance(0).ImageLoaders(side,ivSide);
        LoaderImage.getInstance(0).ImageLoaders(back,ivBack);
        isFirst = false;
    }

    /**
     * 提交数据
     */
    public void submit(View v) {
        bean.setJzmc(getValus(editList.get(0)));//建筑物名称
        bean.setAddress(getValus(editList.get(1)));//详细地址
        bean.setPostcode(getValus(editList.get(2)));//邮政编码
        bean.setSsjd(getValus(textList.get(0)));//所属街道
        bean.setSsshequ(getValus(textList.get(1)));//所属社区
        bean.setSsxiaoqu(getValus(editList.get(3)));//所属小区
        bean.setLouzuobianhao(getValus(editList.get(4)));//楼座编号
        bean.setJzwgd(getValus(editList.get(5)));//建筑物高度
        bean.setDsjzcs(buildLayersUp);//地上楼层数
        bean.setDxjzcs(buildLayersDown);//地下楼层数
        bean.setSjdw(getValus(editList.get(6)));//设计单位
        bean.setJsdw(getValus(editList.get(7)));//建设单位
        bean.setSgdw(getValus(editList.get(8)));// 施工单位
        bean.setJldw(getValus(editList.get(9)));// 监理单位
        bean.setJgsj(getValus(editList.get(10)));// 竣工时间
        bean.setJzmj(getValus(editList.get(11)));// 建筑面积
        bean.setPeopleCount(getValus(editList.get(12)));// 办公人数
        if (radioList.get(0).isChecked()){
            bean.setTuzhi("有");
        }else if (radioList.get(1).isChecked()){
            bean.setTuzhi("无");
        }else{
            bean.setTuzhi("");
        }
        bean.setYt(getValus(textList.get(3)));//用途

        if (radioList.get(2).isChecked()){//文物单位
            bean.setWenwudanwei("否");
        }else if (radioList.get(3).isChecked()){
            bean.setWenwudanwei(getValus(textList.get(4)));
        }
        bean.setKz(getValus(textList.get(5)));//抗震防裂度
        bean.setFldj(getValus(textList.get(6)));//抗震设防分类等级
        bean.setGuifan(getValus(textList.get(7)));//设计规范
        bean.setJzjcxs(getValus(textList.get(8)));//建筑基础形式
        bean.setJglx(getValus(textList.get(9)));//结构类型
        bean.setQtcl(getValus(textList.get(10)));//墙体材料
        if (radioList.get(4).isChecked()){
            bean.setYwql("有圈梁");
        }else if (radioList.get(5).isChecked()){
            bean.setYwql("无圈梁");
        }
        if (radioList.get(6).isChecked()){
            bean.setYwgzz("有构造柱");
        }else if (radioList.get(7).isChecked()){
            bean.setYwgzz("无构造柱");
        }
        bean.setLdlx(getValus(textList.get(11)));//楼顶类型
        bean.setCdlx(getValus(textList.get(12)));//场地类别
        bean.setSgzl(getValus(textList.get(13)));//设施和施工材料
        if (radioList.get(8).isChecked()){
            bean.setZzwxw("无");
        }else if (radioList.get(9).isChecked()){
            bean.setZzwxw(getValus(textList.get(14)));
        }
        if (radioList.get(10).isChecked()){
            bean.setSfjgkzjg("否");
        }else if (radioList.get(11).isChecked()){
            bean.setSfjgkzjg("是");
        }
        bean.setKzjgsj(getValus(editList.get(13)));//抗震加固时间
        if (radioList.get(12).isChecked()){//是否危房
            bean.setShifouweifang("否");
        }else if (radioList.get(13).isChecked()){
            bean.setShifouweifang("是");
        }
        bean.setKzjgsj(getValus(editList.get(14)));//危房鉴定单位
        if (radioList.get(14).isChecked()){//主体结构是否有裂缝
            bean.setZtjglf("无");
        }else if (radioList.get(15).isChecked()){
            bean.setZtjglf(getValus(textList.get(15)));
        }
        bean.setZtjglfqk(getValus(editList.get(15)));//主体结构裂缝情况
        if (radioList.get(16).isChecked()){
            bean.setPmgz("是");
        }else if (radioList.get(17).isChecked()){
            bean.setPmgz("否");
        }
        if (radioList.get(18).isChecked()){
            bean.setLmgz("是");
        }else if (radioList.get(19).isChecked()){
            bean.setLmgz("否");
        }
        bean.setZhengmian(positive);
        bean.setCemian(side);
        bean.setBeimian(back);

        request();
    }

    /**
     * 获取控件的值
     * @return
     */
    private String getValus(TextView v){
        return v.getText().toString().trim();
    }

    /** 设置楼层数 */
    private void setLayers() {
        if (!TextUtils.isEmpty(buildLayersUp)){
            textList.get(2).append("地上 " + buildLayersUp + " 层  ");
        }
        if (!TextUtils.isEmpty(buildLayersDown)){
            textList.get(2).append("地下 " + buildLayersDown + " 层");
        }
    }
//=====点击事件================================================================================================

    /**
     * 选择街道
     *
     * @param v
     */
    private int street = 0;//所选的街道
    public void chooseStreet(View v) {
        if (type == 2){
            return;
        }
        PopupWindow popupWindow;
        final View view1 = LayoutInflater.from(context).inflate(R.layout.pop_list, null);
        int a = getResources().getDimensionPixelSize(R.dimen.margin);
        popupWindow = new PopupWindow(view1, v.getWidth() + a, v.getHeight() * 6, true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAsDropDown(v);

        ListView listview = (ListView) view1.findViewById(R.id.listview);
        StreetAdapter adapter = new StreetAdapter(context,-100);
        listview.setAdapter(adapter);

        List<BottomMenuBean> list = StreetInfo.getStreet();
        listview.setOnItemClickListener((parent, view, position, id) -> {
            textList.get(0).setText(list.get(position).content);
            textList.get(1).setText("");
            street = Integer.parseInt(list.get(position).id);
            popupWindow.dismiss();
        });
    }

    /**
     * 选择社区
     *
     * @param v
     */
    public void chooseCommunity(View v) {
        if (type == 2){
            return;
        }
        if (TextUtils.isEmpty(textList.get(0).getText().toString())){
            ToastUtil.showToast("请先选择所属街道");
            return;
        }
        PopupWindow popupWindow;
        final View view1 = LayoutInflater.from(context).inflate(R.layout.pop_list, null);
        int margin = getResources().getDimensionPixelSize(R.dimen.margin);
        popupWindow = new PopupWindow(view1, v.getWidth() + margin, v.getHeight() * 6, true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAsDropDown(v);

        ListView listview = (ListView) view1.findViewById(R.id.listview);
        StreetAdapter adapter = new StreetAdapter(context,street);
        listview.setAdapter(adapter);

        List<BottomMenuBean> list = StreetInfo.getCommunity(street +"");
        listview.setOnItemClickListener((parent, view, position, id) -> {
            textList.get(1).setText(list.get(position).content);
            popupWindow.dismiss();
        });
    }
    /** 楼层选择 */
    public void buildLayers(View v) {
        if (type == 2){
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_build_layers, null);
        EditText etUp = (EditText) view.findViewById(R.id.et_up);
        EditText etDowm = (EditText) view.findViewById(R.id.et_down);
        etUp.setText(buildLayersUp);
        etDowm.setText(buildLayersDown);
        new MaterialDialog.Builder(context)
                .title("请输入楼层")
                .customView(view, false)
                .negativeText("取消")
                .positiveText("确定")
                .onPositive((dialog, which) -> {
                    buildLayersUp = getValus(etUp);
                    buildLayersDown = getValus(etDowm);
                    setLayers();
                })
                .show();
    }

    /**
     * 现在用途或功能
     *
     * @param v
     */
    public void purpose(View v) {
        if (type == 2){
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_used, null);
        final EditText etOther = (EditText) view.findViewById(R.id.et_other);
        etOther.setVisibility(View.GONE);
        final List<AppCompatCheckBox> list = FormUtil.getUsedList(view, etOther);

        new MaterialDialog.Builder(this)
                .title("现在用途或功能")
                .customView(view, true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which1) -> {
                    StringBuffer a = new StringBuffer();
                    for (int i = 0; i < list.size() - 1; i++) {
                        if (list.get(i).isChecked()) {
                            a.append(list.get(i).getText().toString() + ",");
                        }
                    }
                    if (list.get(list.size() - 1).isChecked()) {
                        if (TextUtils.isEmpty(etOther.getText().toString())) {
                            a.append(list.get(list.size() - 1).getText().toString() + ",");
                        } else {
                            a.append(etOther.getText().toString() + ",");
                        }
                    }
                    textList.get(3).setText(a.toString());
                }).build()
                .show();
    }

    /**
     * 文物保护级别
     */
    private void isHistorical() {
        if (type == 2){
            return;
        }
        radioList.get(3).setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked && !isFirst) {
                showProtectionLevel();
            } else {
                textList.get(4).setText("");
                textList.get(4).setVisibility(View.GONE);
            }
        });
        textList.get(4).setOnClickListener(v -> showProtectionLevel());
    }
    int protectionLevel = 0;
    private void showProtectionLevel() {
        if (type == 2){
            return;
        }
        new MaterialDialog.Builder(this)
                .title("文物保护级别")
                .items(R.array.protection_level)
                .itemsCallbackSingleChoice(protectionLevel, (dialog, view, which1, text) -> {
                    protectionLevel = which1;
                    textList.get(4).setVisibility(View.VISIBLE);
                    textList.get(4).setText(text);
                    return true;
                })
                .positiveText(android.R.string.ok)
                .show();
    }
    /**
     * 抗震防裂度选择
     *
     * @param v
     */
    int which = 0;
    public void strong(View v) {
        if (type == 2){
            return;
        }
        new MaterialDialog.Builder(this)
                .title("抗震防裂度")
                .items(R.array.strong)
                .itemsCallbackSingleChoice(which, (dialog, view, which1, text) -> {
                    this.which = which1;
                    textList.get(5).setText(text);
                    return true;
                })
                .positiveText("确定")
                .show();
    }
    /**
     * 抗震设防分类等级
     * @param v
     */
    private int earthquakeLevel = 2;
    public void strongLevel(View v) {
        if (type == 2){
            return;
        }
        new MaterialDialog.Builder(this)
                .title("抗震设防分类等级")
                .items(R.array.earthquake_level)
                .itemsCallbackSingleChoice(earthquakeLevel, (dialog, view, which1, text) -> {
                    earthquakeLevel = which1;
                    textList.get(6).setText(text);
                    return true;
                })
                .positiveText(android.R.string.ok)
                .show();
    }

    /**
     * 依据的抗震设计规范
     * @param v
     */
    int strongStandard = 0;
    public void strongStandard(View v) {
        if (type == 2){
            return;
        }
        new MaterialDialog.Builder(this)
                .title("抗震防裂度")
                .items(R.array.design_standard)
                .itemsCallbackSingleChoice(strongStandard, (dialog, view, which1, text) -> {
                    strongStandard = which1;
                    textList.get(7).setText(text);
                    return true;
                })
                .positiveText("确定")
                .show();
    }

    /**
     * 建筑基础形式
     *
     * @param v
     */
    Integer[] build_form = new Integer[]{};
    public void buildForm(View v) {
        if (type == 2){
            return;
        }
        new MaterialDialog.Builder(this)
                .title("建筑基础形式")
                .items(R.array.build_form)
                .itemsCallbackMultiChoice(build_form, (dialog, which1, text) -> {
                    build_form = which1;
                    StringBuffer a = new StringBuffer();
                    for (int i = 0; i < text.length; i++) {
                        a.append(text[i] + ",");
                    }
                    textList.get(8).setText(a.toString());
                    return true;
                })
                .positiveText(android.R.string.ok)
                .alwaysCallMultiChoiceCallback()
                .show();
    }

    /**
     * 结构类型
     *
     * @param v
     */
    public void structureType(View v) {
        if (type == 2){
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_structure_type, null);
        final EditText etOther = (EditText) view.findViewById(R.id.et_other);
        etOther.setVisibility(View.GONE);
        final List<AppCompatCheckBox> list = FormUtil.getStructureType(view, etOther);

        new MaterialDialog.Builder(this)
                .title("结构类型")
                .customView(view, true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which1) -> {
                    StringBuffer a = new StringBuffer();
                    for (int i = 0; i < list.size() - 1; i++) {
                        if (list.get(i).isChecked()) {
                            a.append(list.get(i).getText().toString() + ",");
                        }
                    }
                    if (list.get(list.size() - 1).isChecked()) {
                        if (TextUtils.isEmpty(etOther.getText().toString())) {
                            a.append(list.get(list.size() - 1).getText().toString() + ",");
                        } else {
                            a.append(etOther.getText().toString() + ",");
                        }
                    }
                    textList.get(9).setText(a.toString());
                }).build()
                .show();
    }

    /**
     * 墙体材料
     *
     * @param v
     */
    public void wallMaterial(View v) {
        if (type == 2){
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_wall_material, null);
        final EditText etOther = (EditText) view.findViewById(R.id.et_other);
        etOther.setVisibility(View.GONE);
        final List<AppCompatRadioButton> list = FormUtil.getWallMaterial(view, etOther);

        new MaterialDialog.Builder(this)
                .title("墙体材料")
                .customView(view, true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which1) -> {
                    for (int i = 0; i < list.size(); i++) {
                        if (i == list.size() - 1 && !TextUtils.isEmpty(getValus(etOther))) {
                            textList.get(10).setText(getValus(etOther));
                        } else if (list.get(i).isChecked()) {
                            textList.get(10).setText(list.get(i).getText().toString());
                        }
                    }
                }).build()
                .show();
    }

    /**
     * 楼顶类型
     *
     * @param v
     */
    public void buildTopType(View v) {
        if (type == 2){
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_build_top_type, null);
        final EditText etOther = (EditText) view.findViewById(R.id.et_other);
        etOther.setVisibility(View.GONE);
        final List<AppCompatRadioButton> list = FormUtil.getBuildTopType(view, etOther);

        new MaterialDialog.Builder(this)
                .title("楼顶类型")
                .customView(view, true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which1) -> {
                    for (int i = 0; i < list.size(); i++) {
                        if (i == list.size() - 1 && !TextUtils.isEmpty(getValus(etOther))) {
                            textList.get(11).setText(getValus(etOther));
                        } else if (list.get(i).isChecked()) {
                            textList.get(11).setText(list.get(i).getText().toString());
                        }
                    }
                }).build()
                .show();
    }

    /**
     * 场地类别
     *
     * @param v
     */
    int placeType = 0;
    public void fieldType(View v) {
        if (type == 2){
            return;
        }
        new MaterialDialog.Builder(this)
                .title("场地类别")
                .items(R.array.place_type)
                .itemsCallbackSingleChoice(placeType, (dialog, view, which1, text) -> {
                    placeType = which1;
                    textList.get(12).setText(text);
                    return true;
                })
                .positiveText(android.R.string.ok)
                .show();
    }

    /**
     * 设施和施工材料
     *
     * @param v
     */
    int constructionMaterial = 0;
    public void facilities(View v) {
        if (type == 2){
            return;
        }
        new MaterialDialog.Builder(this)
                .title("设备和施工资料")
                .items(R.array.construction_material)
                .itemsCallbackSingleChoice(constructionMaterial, (dialog, view, which1, text) -> {
                    constructionMaterial = which1;
                    textList.get(13).setText(text);
                    return true;
                })
                .positiveText(android.R.string.ok)
                .show();
    }

    /**
     * 坠落危险物
     */
    private void isFallHazard() {
        if (type == 2){
            return;
        }
        radioList.get(8).setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                textList.get(14).setText("");
            }
        });
        radioList.get(9).setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked && !isFirst) {
                showFallHazard();
            }
        });
        textList.get(14).setOnClickListener(v -> showFallHazard());
    }
    private void showFallHazard() {
        if (type == 2){
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_dangerous, null);
        final EditText etOther = (EditText) view.findViewById(R.id.et_other);
        etOther.setVisibility(View.GONE);
        final List<AppCompatCheckBox> list = FormUtil.getDangerous(view, etOther);

        new MaterialDialog.Builder(this)
                .title("有无坠落危险物")
                .customView(view, true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which1) -> {
                    StringBuffer a = new StringBuffer();
                    for (int i = 0; i < list.size() - 1; i++) {
                        if (list.get(i).isChecked()) {
                            a.append(list.get(i).getText().toString() + ",");
                        }
                    }
                    if (list.get(list.size() - 1).isChecked()) {
                        if (TextUtils.isEmpty(etOther.getText().toString())) {
                            a.append(list.get(list.size() - 1).getText().toString() + ",");
                        } else {
                            a.append(etOther.getText().toString() + ",");
                        }
                    }
                    textList.get(14).setText(a.toString());
                }).build()
                .show();
    }
    /**
     * 主体结构裂缝
     */
    private void isCrack() {
        if (type == 2){
            return;
        }
        radioList.get(14).setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                textList.get(15).setText("");
            }
        });
        radioList.get(15).setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked && !isFirst) {
                showCrack();
            }
        });
        textList.get(15).setOnClickListener(v -> showCrack());
    }
    Integer[] crack = new Integer[]{};
    private void showCrack() {
        if (type == 2){
            return;
        }
        new MaterialDialog.Builder(this)
                .title("主体结构是否有裂缝")
                .items(R.array.crack)
                .itemsCallbackMultiChoice(crack, (dialog, which1, text) -> {
                    crack = which1;
                    StringBuffer a = new StringBuffer();
                    for (int i = 0; i < text.length; i++) {
                        a.append(text[i] + ",");
                    }
                    textList.get(15).setText(a.toString());
                    return true;
                })
                .positiveText(android.R.string.ok)
                .alwaysCallMultiChoiceCallback()
                .show();
    }

    private String positive,side,back;//三种图片的a
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_positive:
                type(0,v);
                break;
            case R.id.iv_side:
                type(1,v);
                break;
            case R.id.iv_back:
                type(2,v);
                break;
        }
    }

    /**
     * 点击图片的操作
     * @param types 0为正面，1为侧面，2为背面
     */
    public void type(int types,View v){
        if (type == 2){//预览
            preview(types,v);
        }else{
            String str = "";
            if (types == 0){
                str = positive;
            }else if (types == 1){
                str = side;
            }else if (types == 2){
                str = back;
            }
            if (CommonUtil.isEmpty(str)){
                chooseImage(types);//无照片进行添加照片
            }else{
                new MaterialDialog.Builder(this)
                        .items(R.array.image)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if (which == 0){//预览
                                    preview(types,v);
                                }else if (which == 1){//删除
                                    if (types == 0){
                                        positive = "";
                                        LoaderImage.getInstance(0).ImageLoaders("",ivPositive);
                                    }else if (types == 1){
                                        side = "";
                                        LoaderImage.getInstance(0).ImageLoaders("",ivSide);
                                    }else if (types == 2){
                                        back = "";
                                        LoaderImage.getInstance(0).ImageLoaders("",ivBack);
                                    }
                                }else if (which == 2) {//重新上传
                                    chooseImage(types);
                                }
                            }
                        })
                        .show();
            }
        }
    }
    /**
     * 选择图片
     * @param type 0为正面，1为侧面，2为背面
     */
    public void chooseImage(int type){
        Intent intent = new Intent(context, SelectPictureActivity.class);
        intent.putExtra(SelectPictureActivity.KEY_RESLUT, SelectPictureActivity.PHOTOALBUM);
        intent.putExtra(SelectPictureActivity.INTENT_MAX_NUM, 1);
        startActivityForResult(intent, type);
    }

    // 预览
    public void preview(int types,View v) {
        ArrayList<String> selectedPicture = new ArrayList<>();
        if (types == 0 && !CommonUtil.isEmpty(positive)){
            selectedPicture.add(positive);
        }else if(types == 1 && !CommonUtil.isEmpty(positive)){
            selectedPicture.add(side);
        }else if (types == 2 && !CommonUtil.isEmpty(positive)){
            selectedPicture.add(back);
        }
        if (selectedPicture != null && selectedPicture.size() > 0){
            Intent intent = new Intent(context, ActPreViewIcon.class);
            intent.putExtra(ActPreViewIcon.KEY_ALL_ICON, selectedPicture);
            intent.putExtra(ActPreViewIcon.KEY_CURRENT_ICON, 0);
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            intent.putExtra("locationX", location[0]);
            intent.putExtra("locationY", location[1]);
            context.startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null){
            List<String> selectedPicture = (List<String>) data.getSerializableExtra(SelectPictureActivity.INTENT_SELECTED_PICTURE);
            switch (requestCode){
                case 100://选择位置
                    String lon = data.getExtras().getString("lon","");
                    String lat = data.getExtras().getString("lat","");
                    if (!TextUtils.isEmpty(lon) && !TextUtils.isEmpty(lat)){
                        bean.setLon(lon);
                        bean.setLat(lat);
                    }
                    break;
                case 0://正面照片
                    if (selectedPicture != null && selectedPicture.size() > 0){
                        positive = selectedPicture.get(0);
                    }
                    LoaderImage.getInstance(0).ImageLoaders(positive,ivPositive);
                    break;
                case 1://侧面
                    if (selectedPicture != null && selectedPicture.size() > 0){
                        side = selectedPicture.get(0);
                    }
                    LoaderImage.getInstance(0).ImageLoaders(side,ivSide);
                    break;
                case 2://背面
                    if (selectedPicture != null && selectedPicture.size() > 0){
                        back = selectedPicture.get(0);
                    }
                    LoaderImage.getInstance(0).ImageLoaders(back,ivBack);
                    break;
            }
        }
    }

    /**
     * 提交数据到服务器
     */
    private void request() {
        String url = "";
        if (type == 0){//添加
            url = Urls.add;
        }else if (type == 1){//修改
            url = Urls.modify;
        }
        List<File> files = new ArrayList<>();
        files.add(new File(bean.getZhengmian()));
//        OkHttpUtils.post(url)
//                .params("id", bean.getId())
//                .params("jzmc", bean.getJzmc())//建筑物名称
//                .addFileParams("zhengmian", files)
//                .params("address", bean.getAddress())//详细地址
//                .params("postcode", bean.getPostcode())//邮政编码
//                .params("ssjd", bean.getSsjd())//所属街道
//                .params("ssshequ", bean.getSsshequ())//所属社区
//                .params("ssxiaoqu", bean.getSsxiaoqu())//所属小区
//                .params("louzuobianhao",bean.getLouzuobianhao())//楼座编号
//                .params("jzwgd",bean.getJzwgd())//建筑物高度
//                .params("dsjzcs",bean.getDsjzcs())//地上楼层数
//                .params("dxjzcs",bean.getDxjzcs())//地下楼层数
//                .params("sjdw",bean.getSjdw())//设计单位
//                .params("jsdw",bean.getJsdw())//建设单位
//                .params("sgdw",bean.getSgdw())// 施工单位
//                .params("jldw",bean.getJldw())// 监理单位
//                .params("jgsj",bean.getJgsj())// 竣工时间
//                .params("jzmj",bean.getJzmj())// 建筑面积
//                .params("peopleCount",bean.getPeopleCount())// 办公人数
//                .params("tuzhi",bean.getTuzhi())//图纸
//                .params("yt",bean.getYt())//用途
//                .params("wenwudanwei",bean.getWenwudanwei())//文物单位
//                .params("kz",bean.getKz())//抗震防裂度
//                .params("fldj",bean.getFldj())//抗震设防分类等级
//                .params("guifan",bean.getGuifan())//设计规范
//                .params("jzjcxs",bean.getJzjcxs())//建筑基础形式
//                .params("jglx",bean.getJglx())//结构类型
//                .params("qtcl",bean.getQtcl())//墙体材料
//                .params("ywql",bean.getYwql())//有无圈梁
//                .params("ywgzz",bean.getYwgzz())//有无构造柱
//                .params("ldlx",bean.getLdlx())//楼顶类型
//                .params("cdlx",bean.getCdlx())//场地类别
//                .params("sgzl",bean.getSgzl())//设施和施工材料
//                .params("zzwxw",bean.getZzwxw())
//                .params("sfjgkzjg",bean.getSfjgkzjg())
//                .params("kzjgsj",bean.getKzjgsj())//抗震加固时间
//                .params("shifouweifang",bean.getShifouweifang())//是否危房
//                .params("kzjgsj",bean.getKzjgsj())//危房鉴定单位
//                .params("ztjglf",bean.getZtjglf())//主体结构是否有裂缝
//                .params("ztjglfqk",bean.getZtjglfqk())//主体结构裂缝情况
//                .params("pmgz",bean.getPmgz())
//                .params("lmgz",bean.getLmgz())
//                .params("lon", bean.getLon())
//                .params("lat",bean.getLat())
//                .params("zhengmian",new File(bean.getZhengmian()))
//                .params("cemian",new File(bean.getCemian()))
//                .params("beimian",new File(bean.getBeimian()))
//                .headers("token", MyApplication.preference().getString("token", ""))
//                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
//                .cacheKey("add_modify")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
//                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
//                .execute(new MyCallback(context));

        com.zhy.http.okhttp.OkHttpUtils.post()//
                .url(url)
                .addHeader("token", MyApplication.preference().getString("token", ""))//
                .addParams("jzmc", bean.getJzmc()) //建筑物名称
                .addFile("zhengmian", "test1", files.get(0))//
                .build()//
                .execute(new MyStringCallback());
    }

    private class MyCallback extends StringCallback {

        public MyCallback(Context context) {
            super(context, true);
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
                if ("true".equals(status)) {
                    ToastUtil.showToast("操作成功");
                    finish();
                } else {
                    if (9430 == code) { //请重新登陆

                    }else{
                        ToastUtil.showToast(msg);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class MyStringCallback extends com.zhy.http.okhttp.callback.StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            ToastUtil.showToast(e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            ToastUtil.showToast(response);
        }
    }
}