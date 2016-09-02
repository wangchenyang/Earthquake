package sing.earthquake.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sing.earthquake.R;
import sing.earthquake.bean.BuildListBean.DataRowsBean;
import sing.earthquake.common.BaseActivity;
import sing.earthquake.util.ToastUtil;

/**
 * @className   ActBuildDetail
 * @time        2016/9/2 14:52
 * @author      LiangYx
 * @description 查看建筑详情
 */
public class ActBuildDetail extends BaseActivity{

    private DataRowsBean bean;

    /** 建筑名称 */
//    private EditText etBuildName;
//    /** 建筑详细地址 */
//    private EditText etBuildAddress;
//    /** 邮政编码 */
//    private EditText etPostcode;
//    /** 选择的街道 */
//    private TextView tvStreet;
//    /** 选择的社区 */
//    private TextView tvCommunity;
//    /** 所属的小区或村 */
//    private EditText etResidential;
//    /** 楼座编号 */
//    private EditText etBuildNum;
//    /** 建筑物高度 */
//    private EditText etBuildHeight;
//    /** 建筑层数 地上8层 地下2层 */
//    private String strUp, strDown;
//    /** 设计单位 */
//    private EditText etDesignUnit;
//    /** 建设单位 */
//    private EditText etBuildUnit;
//    /** 施工单位 */
//    private EditText etConstructionUnit;
//    /** 监理单位 */
//    private EditText etControlUnit;
//    /** 竣工时间 */
//    private EditText etEndTime;
//    /** 建筑面积 */
//    private EditText etBuildArea;
//    /** 居住人数 */
//    private EditText etLivingPeopleNum;
//    /** 有无设计图纸，选中为有 */
//    private RadioButton rbHaveImage;
//    private RadioButton rbNoHaveImage;
//    /** 现在用途或功能 */
//    private TextView tvPurpose;
//    /** 是否文物保护单位 */
//    private RadioButton rbIsHistorical;
//    private RadioButton rbIsNoHistorical;
//    //文物级别
//    private TextView tvHistoricalLevel;
//    /** 抗震防裂度 */
//    private TextView tvStrong;
//    /** 抗震设防分类等级 */
//    private TextView tvStrongLevel;
//    /** 依据的设计规范 */
//    private TextView tvStrongStandard;
//    /** 建筑基础形式 */
//    private TextView tvBuildForm;
//    /** 请选择结构类型 */
//    private TextView tvStructureType;
//    /** 请选择墙体材料 */
//    private TextView tvWallMaterial;
//    /** 是否有圈梁 */
//    private RadioButton rbHaveRingBeam;
//    private RadioButton rbNoHaveRingBeam;
//    /** 是否有构造柱 */
//    private RadioButton rbHaveStructuralColumn;
//    private RadioButton rbNoHaveStructuralColumn;
//    /**请选择楼顶类型 */
//    private TextView tvBuildTopType;
//    /**请选场地类别 */
//    private TextView tvFieldType;
//    /** 请选择设施和施工材料 */
//    private TextView tvFacilities;
//    /** 有无坠落危险物 */
//    private RadioButton rbHaveFallHazard;
//    private RadioButton rbNoHaveFallHazard;
//    /** 坠落危险物名 */
//    private TextView tvFallHazard;
//    /** 是否进行过抗震加固 */
//    private RadioButton rbHaveReinforce;
//    private RadioButton rbNoHaveReinforce;
//    /** 加固时间 */
//    private EditText etReinforceTime;
//    /** 是否鉴定为危房 */
//    private RadioButton rbAppraisalDangerous;
//    private RadioButton rbNoAppraisalDangerous;
//    /** 鉴定单位 */
//    private EditText etAppraisalUnit;
//    /** 主体结构是否有裂缝 */
//    private RadioButton rbHaveCrack;
//    private RadioButton rbNoHaveCrack;
//    /** 裂缝位置 */
//    private TextView tvCrack;
//    /** 裂缝情况 */
//    private EditText etCrackCondition;
//    /** 平面是方形或矩形 */
//    private RadioButton rbIsRect;
//    private RadioButton rbIsNoRect;
//    /** 立面是否规则 */
//    private RadioButton rbIsRule;
//    private RadioButton rbIsNoRule;
//    /** 正面图 */
//    private ImageView ivPositive;
//    /** 侧面图 */
//    private ImageView ivSide;
//    /** 背面图 */
//    private ImageView ivBack;

    List<EditText> list;
    @Override
    public int getLayoutId() {
        return R.layout.act_build_detail;
    }

    @Override
    public void init() {
        bean = (DataRowsBean) getIntent().getExtras().getSerializable("build_bean");
        if (null == bean){
            ToastUtil.showToast("建筑信息为空");
            finish();
            return;
        }

        list = new ArrayList<>();
        list.add((EditText) findViewById(R.id.et_build_name));
        list.add((EditText) findViewById(R.id.et_build_address));
        list.add((EditText) findViewById(R.id.et_postcode));
//        etBuildName = (EditText) findViewById(R.id.et_build_name);
//        etBuildAddress = (EditText) findViewById(R.id.et_build_address);
//        etPostcode = (EditText) findViewById(R.id.et_postcode);
//        tvStreet = (TextView) findViewById(R.id.tv_street);
//        tvCommunity = (TextView) findViewById(R.id.tv_community);
//        etResidential = (EditText) findViewById(R.id.et_residential);
//        etBuildNum = (EditText) findViewById(R.id.et_build_num);
//        etBuildHeight = (EditText) findViewById(R.id.et_build_height);
//        etDesignUnit = (EditText) findViewById(R.id.et_design_unit);
//        etBuildUnit = (EditText) findViewById(R.id.et_build_unit);
//        etConstructionUnit = (EditText) findViewById(R.id.et_construction_unit);
//        etControlUnit = (EditText) findViewById(R.id.et_control_unit);
//        etEndTime = (EditText) findViewById(R.id.et_end_time);
//        etBuildArea = (EditText) findViewById(R.id.et_build_area);
//        etLivingPeopleNum = (EditText) findViewById(R.id.et_living_people_num);
//        rbHaveImage = (RadioButton) findViewById(R.id.rb_have_image);
//        rbNoHaveImage = (RadioButton) findViewById(R.id.rb_no_have_image);
//        tvPurpose = (TextView) findViewById(R.id.tv_purpose);
//        rbIsHistorical = (RadioButton) findViewById(R.id.rb_is_historical);
//        rbIsNoHistorical = (RadioButton) findViewById(R.id.rb_is_no_historical);
//        tvHistoricalLevel = (TextView) findViewById(R.id.tv_historical_level);
//        isHistorical();//设置监听
//
//        tvStrong = (TextView) findViewById(R.id.tv_strong);
//        tvStrongLevel = (TextView) findViewById(R.id.tv_strong_level);
//        tvStrongStandard = (TextView) findViewById(R.id.tv_strong_standard);
//        tvBuildForm = (TextView) findViewById(R.id.tv_build_form);
//        tvStructureType = (TextView) findViewById(R.id.tv_structure_type);
//        tvWallMaterial = (TextView) findViewById(R.id.tv_wall_material);
//        rbHaveRingBeam = (RadioButton) findViewById(R.id.rb_have_ring_beam);
//        rbNoHaveRingBeam = (RadioButton) findViewById(R.id.rb_no_have_ring_beam);
//        rbHaveStructuralColumn = (RadioButton) findViewById(R.id.rb_have_structural_column);
//        rbNoHaveStructuralColumn = (RadioButton) findViewById(R.id.rb_no_have_structural_column);
//        tvBuildTopType = (TextView) findViewById(R.id.tv_build_top_type);
//        tvFieldType = (TextView) findViewById(R.id.tv_field_type);
//        tvFacilities = (TextView) findViewById(R.id.tv_facilities);
//        rbHaveFallHazard = (RadioButton) findViewById(R.id.rb_have_fall_hazard);
//        rbNoHaveFallHazard = (RadioButton) findViewById(R.id.rb_no_have_fall_hazard);
//        tvFallHazard = (TextView) findViewById(R.id.tv_fall_hazard);
//        isFallHazard();//设置监听
//        rbHaveReinforce = (RadioButton) findViewById(R.id.rb_have_reinforce);
//        rbNoHaveReinforce = (RadioButton) findViewById(R.id.rb_no_have_reinforce);
//        etReinforceTime = (EditText) findViewById(R.id.et_reinforce_time);
//        rbAppraisalDangerous = (RadioButton) findViewById(R.id.rb_appraisal_dangerous);
//        rbNoAppraisalDangerous = (RadioButton) findViewById(R.id.rb_no_appraisal_dangerous);
//        etAppraisalUnit = (EditText) findViewById(R.id.et_appraisal_unit);
//        rbHaveCrack = (RadioButton) findViewById(R.id.rb_have_crack);
//        rbNoHaveCrack = (RadioButton) findViewById(R.id.rb_no_have_crack);
//        tvCrack = (TextView) findViewById(R.id.tv_crack);
//        isCrack();//设置监听
//        etCrackCondition = (EditText) findViewById(R.id.et_crack_condition);
//        rbIsRect = (RadioButton) findViewById(R.id.rb_is_rect);
//        rbIsNoRect = (RadioButton) findViewById(R.id.rb_is_no_rect);
//        rbIsRule = (RadioButton) findViewById(R.id.rb_is_rule);
//        rbIsNoRule = (RadioButton) findViewById(R.id.rb_is_no_rule);
//        ivPositive = (ImageView) findViewById(R.id.iv_positive);
//        ivSide = (ImageView) findViewById(R.id.iv_side);
//        ivBack = (ImageView) findViewById(R.id.iv_back);

        for (EditText edittext : list) {
            edittext.setEnabled(false);
            edittext.setFocusable(false);
        }

        setValue();
    }

    /**
     * 关闭
     * @param v
     */
    public void finish(View v) {
        finish();
    }

    /**
     * 定位到地图
     * @param v
     */
    public void location(View v) {

    }

    private void setValue() {
        list.get(0).setText(bean.getJzmc());
        list.get(1).setText(bean.getAddress());
        list.get(2).setText(bean.getPostcode());
    }
}