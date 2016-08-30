package sing.earthquake.util;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import sing.earthquake.R;

/**
 * @author: LiangYX
 * @ClassName: FormUtil
 * @date: 16/8/29 下午9:37
 * @Description: 表单获取数据
 */
public class FormUtil {

    /**
     * 现在用途或功能
     * @param view
     * @return
     */
    public static List<AppCompatCheckBox> getUsedList(View view, final EditText etOther){
        final List<AppCompatCheckBox> list = new ArrayList<>();
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_1));//住宅
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_2));//办公楼
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_3));//宾馆旅店
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_4));// 工业厂房
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_5));// 仓库
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_6));// 政府
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_7));// 车库
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_8));// 幼儿园
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_9));// 小学
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_10));// 中学
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_11));// 大学
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_12));// 商业
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_13));// 医院
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_14));// 人防
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_15));// 应急服务
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_16));// 图书馆
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_17));// 纪念馆
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_18));// 博物馆
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_19));// 体育馆
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_20));// 电影院
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_21));// 其他
        list.get(list.size() -1).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    etOther.setText("");
                    etOther.setVisibility(View.VISIBLE);
                }else{
                    etOther.setVisibility(View.GONE);
                }
            }
        });
        return list;
    }


    /**
     * 结构类型
     * @param view
     * @param etOther
     * @return
     */
    public static List<AppCompatCheckBox> getStructureType(View view, final EditText etOther) {
        final List<AppCompatCheckBox> list = new ArrayList<>();
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_1));//钢结构
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_2));//筒体
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_3));//框架
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_4));//剪力墙
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_5));//钢混
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_6));//砖混
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_7));//砖木
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_8));//其他
        list.get(list.size() -1).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    etOther.setText("");
                    etOther.setVisibility(View.VISIBLE);
                }else{
                    etOther.setVisibility(View.GONE);
                }
            }
        });
        return list;
    }

    /**
     * 墙体材料
     *
     * @param view
     * @param etOther
     * @return
     */
    public static List<AppCompatRadioButton> getWallMaterial(View view, final EditText etOther) {
        final List<AppCompatRadioButton> list = new ArrayList<>();
        list.add((AppCompatRadioButton) view.findViewById(R.id.ccb_1));//砖墙
        list.add((AppCompatRadioButton) view.findViewById(R.id.ccb_2));//石墙
        list.add((AppCompatRadioButton) view.findViewById(R.id.ccb_3));//生土墙
        list.add((AppCompatRadioButton) view.findViewById(R.id.ccb_4));//多种材料混合
        list.add((AppCompatRadioButton) view.findViewById(R.id.ccb_5));//其他
        for (int i = 0; i < list.size(); i++) {
            final int finalI = i;
            list.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        for (AppCompatRadioButton radioButton:list) {
                            radioButton.setChecked(false);
                        }
                        list.get(finalI).setChecked(true);
                        if (finalI == list.size() - 1){
                            etOther.setVisibility(View.VISIBLE);
                        }else{
                            etOther.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
        return list;
    }

    /**
     * 楼顶类型
     * @param view
     * @param etOther
     * @return
     */
    public static List<AppCompatRadioButton> getBuildTopType(View view, final EditText etOther) {
        final List<AppCompatRadioButton> list = new ArrayList<>();
        list.add((AppCompatRadioButton) view.findViewById(R.id.ccb_1));//现浇板平屋面
        list.add((AppCompatRadioButton) view.findViewById(R.id.ccb_2));//预制板平屋面
        list.add((AppCompatRadioButton) view.findViewById(R.id.ccb_3));//现浇板坡屋面
        list.add((AppCompatRadioButton) view.findViewById(R.id.ccb_4));//非现浇板坡屋面
        list.add((AppCompatRadioButton) view.findViewById(R.id.ccb_5));//其他
        for (int i = 0; i < list.size(); i++) {
            final int finalI = i;
            list.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        for (AppCompatRadioButton radioButton:list) {
                            radioButton.setChecked(false);
                        }
                        list.get(finalI).setChecked(true);
                        if (finalI == list.size() - 1){
                            etOther.setVisibility(View.VISIBLE);
                        }else{
                            etOther.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
        return list;
    }

    /**
     * 有无坠落危险物
     * @param view
     * @param etOther
     * @return
     */
    public static List<AppCompatCheckBox> getDangerous(View view, final EditText etOther){
        final List<AppCompatCheckBox> list = new ArrayList<>();
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_1));//无钢筋烟囱
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_2));//无钢筋女儿墙
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_3));//护栏
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_4));// 空调室外机
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_5));// 大型广告牌
        list.add((AppCompatCheckBox) view.findViewById(R.id.ccb_6));// 其他
        list.get(list.size() -1).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    etOther.setText("");
                    etOther.setVisibility(View.VISIBLE);
                }else{
                    etOther.setVisibility(View.GONE);
                }
            }
        });
        return list;
    }
}