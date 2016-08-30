package sing.earthquake.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
import sing.earthquake.bean.BuildListBean.*;

public class FragList extends Fragment {

    private Activity context;
    private ListView list_view;
    private List<DataRowsBean> list;
    private MyAdapter adapter;

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_list,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = getActivity();

        list_view = (ListView) view.findViewById(R.id.list_view);
        list = new ArrayList<>();
        adapter = new MyAdapter();
        list_view.setAdapter(adapter);

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
                    list.addAll(bean.getDataRows());
                    adapter.setDate(list);
                }else{
                    if (9430 == code){ //请重新登陆

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class MyAdapter extends BaseAdapter {

        List<DataRowsBean> list;

        public void setDate(List<DataRowsBean> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Object getItem(int position) {
            return list == null ? 0 : list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {

                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.row_list, parent, false);

                holder.pllTop = (PercentLinearLayout) convertView.findViewById(R.id.pll_top);
                holder.pllBottom = (PercentLinearLayout) convertView.findViewById(R.id.pll_bottom);

                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tvStreet = (TextView) convertView.findViewById(R.id.tv_street);
                holder.tvCommunity = (TextView) convertView.findViewById(R.id.tv_community);
                holder.tvUsed = (TextView) convertView.findViewById(R.id.tv_used);
                holder.tvEndTime = (TextView) convertView.findViewById(R.id.tv_end_time);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == 0) {
                holder.pllTop.setVisibility(View.VISIBLE);
            } else {
                holder.pllTop.setVisibility(View.GONE);
            }

            if (position % 2 == 0) {
                holder.pllBottom.setBackgroundColor(getResources().getColor(R.color.color_f7f6f2));
            } else {
                holder.pllBottom.setBackgroundColor(getResources().getColor(android.R.color.white));
            }
            final DataRowsBean bean = list.get(position);
            holder.tvName.setText(bean.getJzmc());
            holder.tvStreet.setText(bean.getSsjd());
            holder.tvCommunity.setText(bean.getSsshequ());
            holder.tvUsed.setText(bean.getYt());
            holder.tvEndTime.setText(bean.getJgsj());

            return convertView;
        }

        public class ViewHolder {
            PercentLinearLayout pllTop;
            PercentLinearLayout pllBottom;
            TextView tvName;
            TextView tvStreet;
            TextView tvCommunity;
            TextView tvUsed;
            TextView tvEndTime;
        }
    }
}