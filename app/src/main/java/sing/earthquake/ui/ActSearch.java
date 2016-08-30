package sing.earthquake.ui;

import android.view.View;
import android.widget.EditText;

import sing.earthquake.R;
import sing.earthquake.common.BaseActivity;

public class ActSearch extends BaseActivity {

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


    @Override
    public int getLayoutId() {
        return R.layout.act_search;
    }

    @Override
    public void init() {
        etBuildName = (EditText) findViewById(R.id.et_build_name);
        etMinHoldHeight = (EditText) findViewById(R.id.et_min_hold_height);
        etMaxHoldHeight = (EditText) findViewById(R.id.et_max_hold_height);
        etMinBuildFloor = (EditText) findViewById(R.id.et_min_build_floor);
        etMaxbuildFloor = (EditText) findViewById(R.id.et_max_build_floor);
        etMinEndTime = (EditText) findViewById(R.id.et_min_end_time);
        etMaxEndTime = (EditText) findViewById(R.id.et_max_end_time);
        etMinLiveNum = (EditText) findViewById(R.id.et_min_live_num);
        etMaxLiveNum = (EditText) findViewById(R.id.et_max_live_num);
    }

    /**
     * 关闭
     * @param v
     */
    public void finish(View v){
        finish();
    }

    /**
     * 查询
     * @param v
     */
    public void submit(View v){

    }
}
