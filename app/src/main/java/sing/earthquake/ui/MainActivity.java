package sing.earthquake.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import sing.earthquake.R;
import sing.earthquake.util.ToastUtil;

/**
 * @author: LiangYX
 * @ClassName: MainActivity
 * @date: 16/8/22 下午9:53
 * @Description: 主界面
 */
public class MainActivity extends FragmentActivity {

    private FragmentManager fragmentManager = null;

    private Activity context;

    private boolean isList = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        context = this;

        init();
    }

    private void init() {
        fragmentManager = this.getSupportFragmentManager();

        if (isList) {
            CustomFragmentManager(FragList.class, null);
        } else  {
            CustomFragmentManager(FragMap.class, null);
        }
    }

    public void CustomFragmentManager(Class<?> fragmemtClass, Bundle args) {
        String fragmentName = fragmemtClass.getName();
        Fragment list = fragmentManager.findFragmentByTag(FragList.class.getName());
        Fragment map = fragmentManager.findFragmentByTag(FragMap.class.getName());

        FragmentTransaction ft = fragmentManager.beginTransaction();
        // 要显示的fragment
        Fragment currentFragment = fragmentManager.findFragmentByTag(fragmentName);

        if (fragmentName.equals(FragList.class.getName())) {
            if (map != null) {
                ft.hide(map);
            }
        } else if (fragmentName.equals(FragMap.class.getName())) {
            if (list != null) {
                ft.hide(list);
            }
        }
        // 不能通过隐藏上一个标签来实现，必须显示一个，隐藏另外三个的方式。
        // 因为手机操作场景是非常复杂的，只隐藏lastTag的话，会导致切换时界面隐藏和显示的混乱
        // 比如关闭飞行模式时频繁切换
        if (currentFragment == null) {
            currentFragment = Fragment.instantiate(this, fragmentName, args);
            ft.add(R.id.fragment_container, currentFragment,fragmentName);
        } else {
            ft.show(currentFragment);
        }
        ft.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    /**
     * 地图列表切换
     * @param v
     */
    public void change(View v) {
        if (isList) {
            CustomFragmentManager(FragMap.class, null);
        } else {
            CustomFragmentManager(FragList.class, null);
        }
        isList = !isList;
    }

    /**
     * 搜索
     * @param v
     */
    public void search(View v){
        startActivity(new Intent(context,ActSearch.class));
    }

    /**
     * 添加
     * @param v
     */
    public void add(View v) {
        Intent intent = new Intent();
        intent.setClass(context, ActBigForm.class);
        intent.putExtra("type", 0);
        startActivity(intent);
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}