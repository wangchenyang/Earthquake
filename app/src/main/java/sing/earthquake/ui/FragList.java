package sing.earthquake.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import sing.earthquake.MyApplication;
import sing.earthquake.R;
import sing.earthquake.bean.BuildListBean;
import sing.earthquake.common.Urls;
import sing.earthquake.common.gson.GsonImpl;
import sing.earthquake.common.pullrefresh.PullToRefreshListView;
import sing.earthquake.util.CommonUtil;
import sing.earthquake.util.ToastUtil;
import sing.okhttp.okhttputils.OkHttpUtils;
import sing.okhttp.okhttputils.cache.CacheMode;
import sing.okhttp.okhttputils.callback.StringCallback;
import sing.earthquake.bean.BuildListBean.*;

public class FragList extends Fragment {

    private Activity context;
    private PullToRefreshListView pullToRefreshListView;
    private List<DataRowsBean> list;
    private MyAdapter adapter;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = getActivity();

        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pullToRefreshListView);
        list = new ArrayList<>();
        adapter = new MyAdapter();
        pullToRefreshListView.setAdapter(adapter);

        pullToRefreshListView.setOnRefreshListener(() -> request(true));

        // 上拉加载更多数据
        pullToRefreshListView.setOnLoadListener(() -> request(false));

        request(true);
    }

    private int currentPage;//当前页
    private int totalPage;//总页数
    private void request(boolean isReflush) {
        if (isReflush) {
            currentPage = 1;
            totalPage = 1;
        } else {
            currentPage++;
        }
        OkHttpUtils.get(Urls.contentList)     // 请求方式和请求url
                .params("currentPage",currentPage+"")
                .headers("token", MyApplication.preference().getString("token", ""))
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("contentList")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new MyCallback(context,isReflush));
        Log.e("currentPage",currentPage+"");
    }

    private class MyCallback extends StringCallback {
        boolean isReflush;
        public MyCallback(Context context,boolean isReflush) {
            super(context, true);
            this.isReflush = isReflush;
        }

        @Override
        public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
            super.onResponse(isFromCache, s, request, response);
            pullToRefreshListView.onRefreshComplete();
            pullToRefreshListView.onLoadComplete();

            JSONObject json = null;
            try {
                json = new JSONObject(s);
                String status = json.optString("success");
                String msg = json.optString("msg");
                int code = json.optInt("code");//失败才有
                if ("true".equals(status)) {
                    BuildListBean bean = GsonImpl.get().toObject(json.optString("data"), BuildListBean.class);
                    currentPage = bean.getCurrentPage();//当前页
                    totalPage = bean.getTotalPage();//总页数
                    if (currentPage >= totalPage){//到底啦
                        pullToRefreshListView.setLoadFull(true);
                    }else{
                        pullToRefreshListView.setLoadFull(false);
                    }

                    reflush(bean.getDataRows(),isReflush);
                } else {
                    if (9430 == code) { //请重新登陆

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                pullToRefreshListView.onRefreshComplete();
                pullToRefreshListView.onLoadComplete();
                currentPage--;
            }
        }

        @Override
        public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
            super.onError(isFromCache, call, response, e);
            pullToRefreshListView.onRefreshComplete();
            pullToRefreshListView.onLoadComplete();
            currentPage--;
        }
    }

    private void reflush(List<DataRowsBean> dataRows, boolean isReflush) {
        if (isReflush){
            list.clear();
        }
        list.addAll(dataRows);
        adapter.setDate(list);
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