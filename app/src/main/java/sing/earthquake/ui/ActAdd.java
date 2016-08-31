package sing.earthquake.ui;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.provider.Contacts;
import android.support.annotation.NonNull;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.w3c.dom.Text;

import java.util.List;

import sing.earthquake.R;
import sing.earthquake.adapter.StreetAdapter;
import sing.earthquake.bean.BottomMenuBean;
import sing.earthquake.bean.BuildBean;
import sing.earthquake.common.BaseActivity;
import sing.earthquake.common.streetinfo.StreetInfo;
import sing.earthquake.util.FormUtil;
import sing.earthquake.util.ToastUtil;

public class ActAdd extends BaseActivity implements View.OnClickListener {

    private Activity context;

    /** 建筑名称 */
    private EditText etBuildName;
    /** 建筑详细地址 */
    private EditText etBuildAddress;
    /** 邮政编码 */
    private EditText etPostcode;
    /** 选择的街道 */
    private TextView tvStreet;
    /** 选择的社区 */
    private TextView tvCommunity;
    /** 所属的小区或村 */
    private EditText etResidential;
    /** 楼座编号 */
    private EditText etBuildNum;
    /** 建筑物高度 */
    private EditText etBuildHeight;
    /** 建筑层数 地上8层 地下2层 */
    private String strUp, strDown;
    /** 设计单位 */
    private EditText etDesignUnit;
    /** 建设单位 */
    private EditText etBuildUnit;
    /** 施工单位 */
    private EditText etConstructionUnit;
    /** 监理单位 */
    private EditText etControlUnit;
    /** 竣工时间 */
    private EditText etEndTime;
    /** 建筑面积 */
    private EditText etBuildArea;
    /** 居住人数 */
    private EditText etLivingPeopleNum;
    /** 有无设计图纸，选中为有 */
    private RadioButton rbHaveImage;
    private RadioButton rbNoHaveImage;
    /** 现在用途或功能 */
    private TextView tvPurpose;
    /** 是否文物保护单位 */
    private RadioButton rbIsHistorical;
    private RadioButton rbIsNoHistorical;
    //文物级别
    private TextView tvHistoricalLevel;
    /** 抗震防裂度 */
    private TextView tvStrong;
    /** 抗震设防分类等级 */
    private TextView tvStrongLevel;
    /** 依据的设计规范 */
    private TextView tvStrongStandard;
    /** 建筑基础形式 */
    private TextView tvBuildForm;
    /** 请选择结构类型 */
    private TextView tvStructureType;
    /** 请选择墙体材料 */
    private TextView tvWallMaterial;
    /** 是否有圈梁 */
    private RadioButton rbHaveRingBeam;
    private RadioButton rbNoHaveRingBeam;
    /** 是否有构造柱 */
    private RadioButton rbHaveStructuralColumn;
    private RadioButton rbNoHaveStructuralColumn;
    /**请选择楼顶类型 */
    private TextView tvBuildTopType;
    /**请选场地类别 */
    private TextView tvFieldType;
    /** 请选择设施和施工材料 */
    private TextView tvFacilities;
    /** 有无坠落危险物 */
    private RadioButton rbHaveFallHazard;
    private RadioButton rbNoHaveFallHazard;
    /** 坠落危险物名 */
    private TextView tvFallHazard;
    /** 是否进行过抗震加固 */
    private RadioButton rbHaveReinforce;
    private RadioButton rbNoHaveReinforce;
    /** 加固时间 */
    private EditText etReinforceTime;
    /** 是否鉴定为危房 */
    private RadioButton rbAppraisalDangerous;
    private RadioButton rbNoAppraisalDangerous;
    /** 鉴定单位 */
    private EditText etAppraisalUnit;
    /** 主体结构是否有裂缝 */
    private RadioButton rbHaveCrack;
    private RadioButton rbNoHaveCrack;
    /** 裂缝位置 */
    private TextView tvCrack;
    /** 裂缝情况 */
    private EditText etCrackCondition;
    /** 平面是方形或矩形 */
    private RadioButton rbIsRect;
    private RadioButton rbIsNoRect;
    /** 立面是否规则 */
    private RadioButton rbIsRule;
    private RadioButton rbIsNoRule;
    /** 正面图 */
    private ImageView ivPositive;
    /** 侧面图 */
    private ImageView ivSide;
    /** 背面图 */
    private ImageView ivBack;

    private BuildBean bean;

    @Override
    public int getLayoutId() {
        return R.layout.act_add;
    }

    @Override
    public void init() {
        context = this;
        bean = new BuildBean();

        etBuildName = (EditText) findViewById(R.id.et_build_name);
        etBuildAddress = (EditText) findViewById(R.id.et_build_address);
        etPostcode = (EditText) findViewById(R.id.et_postcode);
        tvStreet = (TextView) findViewById(R.id.tv_street);
        tvCommunity = (TextView) findViewById(R.id.tv_community);
        etResidential = (EditText) findViewById(R.id.et_residential);
        etBuildNum = (EditText) findViewById(R.id.et_build_num);
        etBuildHeight = (EditText) findViewById(R.id.et_build_height);
        etDesignUnit = (EditText) findViewById(R.id.et_design_unit);
        etBuildUnit = (EditText) findViewById(R.id.et_build_unit);
        etConstructionUnit = (EditText) findViewById(R.id.et_construction_unit);
        etControlUnit = (EditText) findViewById(R.id.et_control_unit);
        etEndTime = (EditText) findViewById(R.id.et_end_time);
        etBuildArea = (EditText) findViewById(R.id.et_build_area);
        etLivingPeopleNum = (EditText) findViewById(R.id.et_living_people_num);
        rbHaveImage = (RadioButton) findViewById(R.id.rb_have_image);
        rbNoHaveImage = (RadioButton) findViewById(R.id.rb_no_have_image);
        tvPurpose = (TextView) findViewById(R.id.tv_purpose);
        rbIsHistorical = (RadioButton) findViewById(R.id.rb_is_historical);
        rbIsNoHistorical = (RadioButton) findViewById(R.id.rb_is_no_historical);
        tvHistoricalLevel = (TextView) findViewById(R.id.tv_historical_level);
        isHistorical();//设置监听

        tvStrong = (TextView) findViewById(R.id.tv_strong);
        tvStrongLevel = (TextView) findViewById(R.id.tv_strong_level);
        tvStrongStandard = (TextView) findViewById(R.id.tv_strong_standard);
        tvBuildForm = (TextView) findViewById(R.id.tv_build_form);
        tvStructureType = (TextView) findViewById(R.id.tv_structure_type);
        tvWallMaterial = (TextView) findViewById(R.id.tv_wall_material);
        rbHaveRingBeam = (RadioButton) findViewById(R.id.rb_have_ring_beam);
        rbNoHaveRingBeam = (RadioButton) findViewById(R.id.rb_no_have_ring_beam);
        rbHaveStructuralColumn = (RadioButton) findViewById(R.id.rb_have_structural_column);
        rbNoHaveStructuralColumn = (RadioButton) findViewById(R.id.rb_no_have_structural_column);
        tvBuildTopType = (TextView) findViewById(R.id.tv_build_top_type);
        tvFieldType = (TextView) findViewById(R.id.tv_field_type);
        tvFacilities = (TextView) findViewById(R.id.tv_facilities);
        rbHaveFallHazard = (RadioButton) findViewById(R.id.rb_have_fall_hazard);
        rbNoHaveFallHazard = (RadioButton) findViewById(R.id.rb_no_have_fall_hazard);
        tvFallHazard = (TextView) findViewById(R.id.tv_fall_hazard);
        isFallHazard();//设置监听
        rbHaveReinforce = (RadioButton) findViewById(R.id.rb_have_reinforce);
        rbNoHaveReinforce = (RadioButton) findViewById(R.id.rb_no_have_reinforce);
        etReinforceTime = (EditText) findViewById(R.id.et_reinforce_time);
        rbAppraisalDangerous = (RadioButton) findViewById(R.id.rb_appraisal_dangerous);
        rbNoAppraisalDangerous = (RadioButton) findViewById(R.id.rb_no_appraisal_dangerous);
        etAppraisalUnit = (EditText) findViewById(R.id.et_appraisal_unit);
        rbHaveCrack = (RadioButton) findViewById(R.id.rb_have_crack);
        rbNoHaveCrack = (RadioButton) findViewById(R.id.rb_no_have_crack);
        tvCrack = (TextView) findViewById(R.id.tv_crack);
        isCrack();//设置监听
        etCrackCondition = (EditText) findViewById(R.id.et_crack_condition);
        rbIsRect = (RadioButton) findViewById(R.id.rb_is_rect);
        rbIsNoRect = (RadioButton) findViewById(R.id.rb_is_no_rect);
        rbIsRule = (RadioButton) findViewById(R.id.rb_is_rule);
        rbIsNoRule = (RadioButton) findViewById(R.id.rb_is_no_rule);
        ivPositive = (ImageView) findViewById(R.id.iv_positive);
        ivSide = (ImageView) findViewById(R.id.iv_side);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivPositive.setOnClickListener(this);
        ivSide.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }


    /**
     * 关闭
     *
     * @param v
     */
    public void finish(View v) {
        finish();
    }

    /**
     * 查看位置
     *
     * @param v
     */
    public void location(View v) {
        ToastUtil.showToast("查看位置");
    }

    /**
     * 提交
     *
     * @param v
     */
    public void submit(View v) {
        bean.setJzmc(etBuildName.getText().toString().trim());//建筑名称
        bean.setAddress(etBuildAddress.getText().toString().trim());//详细地址
        bean.setPostcode(etPostcode.getText().toString().trim());//邮政编码
        bean.setSsjd(tvStreet.getText().toString().trim());//所属街道
        bean.setSsshequ(tvCommunity.getText().toString().trim());//所属社区
        bean.setSsxiaoqu(etResidential.getText().toString().trim());//所属小区或村
        bean.setLouzuobianhao(etBuildNum.getText().toString().trim());//楼座编号
        bean.setJzwgd(etBuildHeight.getText().toString().trim());//建筑物高度
        bean.setDsjzcs(strUp);//建筑物地上层数
        bean.setDxjzcs(strDown);//建筑物地下层数
        bean.setSjdw(etDesignUnit.getText().toString().trim());//设计单位
        bean.setJsdw(etBuildUnit.getText().toString().trim());//建设单位
        bean.setSgdw(etConstructionUnit.getText().toString().trim());//施工单位
        bean.setJldw(etControlUnit.getText().toString().trim());//监理单位
        bean.setJgsj(etEndTime.getText().toString().trim());//竣工时间
        bean.setJzmj(etBuildArea.getText().toString().trim());//建筑面积
        bean.setPeopleCount(etLivingPeopleNum.getText().toString().trim());//居住人数或办公人数
        if (rbHaveImage.isChecked()){//有设计图纸
            bean.setTuzhi("有");
        }else if(rbNoHaveImage.isChecked()){
            bean.setTuzhi("无");
        }
        bean.setYt(tvPurpose.getText().toString().trim());//用途
        if (rbIsNoHistorical.isChecked()){//文物等级
            bean.setWenwudanwei("否");
        }else {
            bean.setWenwudanwei(tvHistoricalLevel.getText().toString().trim());
        }
        bean.setKz(tvStrong.getText().toString().trim());//抗震防裂度
        bean.setFldj(tvStrongLevel.getText().toString().trim());//抗震设防分类等级
        bean.setGuifan(tvStrongStandard.getText().toString().trim());//依据的设计规范
        bean.setJzjcxs(tvBuildForm.getText().toString().trim());//建筑基础形式
        bean.setJglx(tvStructureType.getText().toString().trim());//结构类型
        bean.setQtcl(tvWallMaterial.getText().toString().trim());//墙体材料
        if (rbHaveRingBeam.isChecked()){//是否有圈梁
            bean.setYwql("有");
        }else if(rbNoHaveRingBeam.isChecked()){
            bean.setYwql("无");
        }
        if (rbHaveStructuralColumn.isChecked()) {//有无构造柱
            bean.setYwgzz("有");
        }else if(rbNoHaveStructuralColumn.isChecked()){
            bean.setYwgzz("无");
        }
        bean.setLdlx(tvBuildTopType.getText().toString().trim());//楼顶类型
        bean.setCdlx(tvFieldType.getText().toString().trim());//场地类别
        bean.setSgzl(tvFacilities.getText().toString().trim());//设施和施工材料
        if (rbNoHaveFallHazard.isChecked()){//坠落危险物
            bean.setZzwxw("无");
        }else if(rbHaveFallHazard.isChecked() && !TextUtils.isEmpty(tvFallHazard.getText().toString())){
            bean.setZzwxw(tvFallHazard.getText().toString());
        }
        if (rbHaveReinforce.isChecked() ){// 是否抗震加固
            bean.setSfjgkzjg("是");
        }else if(rbNoHaveReinforce.isChecked()){
            bean.setSfjgkzjg("否");
        }
        bean.setKzjgsj(etReinforceTime.getText().toString().trim());//抗震加固时间
        if (rbNoAppraisalDangerous.isChecked()){//是否危房
            bean.setShifouweifang("否");
        }else if(rbAppraisalDangerous.isChecked()){
            bean.setShifouweifang("是");
        }
        bean.setJiandingdanwei(etAppraisalUnit.getText().toString().trim());//鉴定单位
        if (rbNoHaveCrack.isChecked()){//裂缝
            bean.setZtjglf("无");
        }else if (rbHaveCrack.isChecked()){
            bean.setZtjglf(tvCrack.getText().toString().trim());
        }
        bean.setZtjglfqk(etCrackCondition.getText().toString().trim());//裂缝情况
        if (rbIsRect.isChecked()){//平面是放行或矩形
            bean.setPmgz("是");
        }else if(rbIsNoRect.isChecked()){
            bean.setPmgz("否");
        }
        if (rbIsRule.isChecked()){//里面是否规则
            bean.setPmgz("是");
        }else if (rbIsNoRule.isChecked()){
            bean.setPmgz("否");
         }

    }

    /**
     * 选择街道
     *
     * @param v
     */
    private int street = 0;//所选的街道
    public void chooseStreet(View v) {
        PopupWindow popupWindow;
        final View view1 = LayoutInflater.from(context).inflate(R.layout.pop_list, null);
        int a = getResources().getDimensionPixelSize(R.dimen.margin);
        popupWindow = new PopupWindow(view1, v.getWidth() + a, v.getHeight() * 6, true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAsDropDown(v);

        ListView listview = (ListView) view1.findViewById(R.id.listview);
        StreetAdapter adapter = new StreetAdapter(context,0);
        listview.setAdapter(adapter);

        List<BottomMenuBean> list = StreetInfo.getStreet();
        listview.setOnItemClickListener((parent, view, position, id) -> {
            tvStreet.setText(list.get(position).content);
            tvCommunity.setText("");
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
        if (0 == street){
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
            tvCommunity.setText(list.get(position).content);
            popupWindow.dismiss();
        });
    }

    /**
     * 楼层选择
     *
     * @param v
     */
    public void buildLayers(View v) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_build_layers, null);
        EditText etUp = (EditText) view.findViewById(R.id.et_up);
        EditText etDowm = (EditText) view.findViewById(R.id.et_down);
        etUp.setText(strUp);
        etDowm.setText(strDown);
        new MaterialDialog.Builder(context)
                .title("请输入楼层")
                .customView(view, false)
                .negativeText("取消")
                .positiveText("确定")
                .onPositive((dialog, which) -> {
                    strUp = etUp.getText().toString().trim();
                    strDown = etDowm.getText().toString().trim();
                    if (!TextUtils.isEmpty(strUp) && TextUtils.isEmpty(strDown)) {
                        ((TextView) v).setText("地上 " + strUp + " 层");
                    } else if (TextUtils.isEmpty(strUp) && !TextUtils.isEmpty(strDown)) {
                        ((TextView) v).setText("地下 " + strDown + " 层");
                    } else if (!TextUtils.isEmpty(strUp) && !TextUtils.isEmpty(strDown)) {
                        ((TextView) v).setText("地上 " + strUp + " 层 , 地下 " + strDown + " 层");
                    } else {
                        ((TextView) v).setText("");
                    }
                })
                .show();
    }

    /**
     * 现在用途或功能
     *
     * @param v
     */
    public void purpose(View v) {
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
                    tvPurpose.setText(a.toString());
                }).build()
                .show();
    }

    /**
     * 文物保护级别
     */
    private void isHistorical() {
        tvHistoricalLevel.setVisibility(View.GONE);
        rbIsNoHistorical.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tvHistoricalLevel.setText("");
                tvHistoricalLevel.setVisibility(View.GONE);
            }
        });
        rbIsHistorical.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showProtectionLevel();
            }
        });
        tvHistoricalLevel.setOnClickListener(v -> showProtectionLevel());
    }
    int protectionLevel = 0;
    private void showProtectionLevel() {
        new MaterialDialog.Builder(this)
                .title("文物保护级别")
                .items(R.array.protection_level)
                .itemsCallbackSingleChoice(protectionLevel, (dialog, view, which1, text) -> {
                    protectionLevel = which1;
                    tvHistoricalLevel.setVisibility(View.VISIBLE);
                    tvHistoricalLevel.setText(text);
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
        new MaterialDialog.Builder(this)
                .title("抗震防裂度")
                .items(R.array.strong)
                .itemsCallbackSingleChoice(which, (dialog, view, which1, text) -> {
                    ActAdd.this.which = which1;
                    tvStrong.setText(text);
                    return true;
                })
                .positiveText("确定")
                .show();
    }

    /**
     * 抗震设防分类等级
     *
     * @param v
     */
    private int earthquakeLevel = 2;
    public void strongLevel(View v) {
        new MaterialDialog.Builder(this)
                .title("抗震设防分类等级")
                .items(R.array.earthquake_level)
                .itemsCallbackSingleChoice(earthquakeLevel, (dialog, view, which1, text) -> {
                    earthquakeLevel = which1;
                    tvStrongLevel.setText(text);
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
        new MaterialDialog.Builder(this)
                .title("抗震防裂度")
                .items(R.array.design_standard)
                .itemsCallbackSingleChoice(strongStandard, (dialog, view, which1, text) -> {
                    strongStandard = which1;
                    tvStrongStandard.setText(text);
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
        new MaterialDialog.Builder(this)
                .title("建筑基础形式")
                .items(R.array.build_form)
                .itemsCallbackMultiChoice(build_form, (dialog, which1, text) -> {
                    build_form = which1;
                    StringBuffer a = new StringBuffer();
                    for (int i = 0; i < text.length; i++) {
                        a.append(text[i] + ",");
                    }
                    tvBuildForm.setText(a.toString());
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
                    tvStructureType.setText(a.toString());
                }).build()
                .show();
    }

    /**
     * 墙体材料
     *
     * @param v
     */
    public void wallMaterial(View v) {
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
                    tvWallMaterial.setText(a.toString());
                }).build()
                .show();
    }

    /**
     * 楼顶类型
     *
     * @param v
     */
    public void buildTopType(View v) {
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
                    tvBuildTopType.setText(a.toString());
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
        new MaterialDialog.Builder(this)
                .title("场地类别")
                .items(R.array.place_type)
                .itemsCallbackSingleChoice(placeType, (dialog, view, which1, text) -> {
                    placeType = which1;
                    tvFieldType.setText(text);
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
        new MaterialDialog.Builder(this)
                .title("设备和施工资料")
                .items(R.array.construction_material)
                .itemsCallbackSingleChoice(constructionMaterial, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        constructionMaterial = which;
                        tvFacilities.setText(text);
                        return true;
                    }
                })
                .positiveText(android.R.string.ok)
                .show();
    }

    /**
     * 坠落危险物
     */
    private void isFallHazard() {
        tvFallHazard.setVisibility(View.GONE);
        rbNoHaveFallHazard.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tvFallHazard.setText("");
                tvFallHazard.setVisibility(View.GONE);
            }
        });
        rbHaveFallHazard.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showFallHazard();
            }
        });
        tvFallHazard.setOnClickListener(v -> showFallHazard());
    }
    private void showFallHazard() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_dangerous, null);
        final EditText etOther = (EditText) view.findViewById(R.id.et_other);
        etOther.setVisibility(View.GONE);
        final List<AppCompatCheckBox> list = FormUtil.getDangerous(view, etOther);

        new MaterialDialog.Builder(this)
                .title("有无坠落危险物")
                .customView(view, true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
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
                        tvFallHazard.setText(a.toString());
                        tvFallHazard.setVisibility(View.VISIBLE);
                    }
                }).build()
                .show();
    }

    /**
     * 主体结构裂缝
     */
    private void isCrack() {
        tvCrack.setVisibility(View.GONE);
        rbNoHaveCrack.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tvCrack.setText("");
                tvCrack.setVisibility(View.GONE);
            }
        });
        rbHaveCrack.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showCrack();
            }
        });
        tvCrack.setOnClickListener(v -> showCrack());
    }
    Integer[] crack = new Integer[]{};
    private void showCrack() {
            new MaterialDialog.Builder(this)
                    .title("主体结构是否有裂缝")
                    .items(R.array.crack)
                    .itemsCallbackMultiChoice(crack, (dialog, which1, text) -> {
                        crack = which1;
                        StringBuffer a = new StringBuffer();
                        for (int i = 0; i < text.length; i++) {
                            a.append(text[i] + ",");
                        }
                        tvCrack.setText(a.toString());
                        tvCrack.setVisibility(View.VISIBLE);
                        return true;
                    })
                    .positiveText(android.R.string.ok)
                    .alwaysCallMultiChoiceCallback()
                    .show();
    }

    int a = R.array.image1;
    int b = R.array.image2;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_positive:
                new MaterialDialog.Builder(this)
                        .items(a)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                            }
                        })
                        .show();
                break;
            case R.id.iv_side:
                new MaterialDialog.Builder(this)
                        .items(b)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                            }
                        })
                        .show();
                break;
            case R.id.iv_back:
                new MaterialDialog.Builder(this)
                        .items(a)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                            }
                        })
                        .show();
                break;
        }



    }
}
