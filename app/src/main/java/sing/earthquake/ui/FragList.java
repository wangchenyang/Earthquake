package sing.earthquake.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
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
import sing.earthquake.adapter.BuildAdapter;
import sing.earthquake.bean.BuildListBean;
import sing.earthquake.bean.BuildListBean.DataRowsBean;
import sing.earthquake.common.Urls;
import sing.earthquake.common.gson.GsonImpl;
import sing.okhttp.okhttputils.OkHttpUtils;
import sing.okhttp.okhttputils.cache.CacheMode;
import sing.okhttp.okhttputils.callback.StringCallback;

public class FragList extends Fragment {

    private Activity context;
    private List<DataRowsBean> list;
    private RecyclerView recyclerView;
    private MaterialRefreshLayout materialRefreshLayout;
    private BuildAdapter adapter;
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

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh);
        list = new ArrayList<>();
        adapter = new BuildAdapter(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));//必须有

        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {

            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                request(true);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                request(false);
            }
        });

        request(true);

        adapter.setOnItemClickListener(bean -> {
            new MaterialDialog.Builder(context)
                    .items(R.array.build_option)
                    .itemsCallback((dialog, view, which, text) -> {
                        Intent intent = new Intent();
                        intent.putExtra("build_bean", bean);
                        if (which == 0) {
                            intent.putExtra("type", 2);
                        } else if (which == 1) {
                            intent.putExtra("type", 1);
                        }
                        intent.setClass(context, ActBigForm.class);
                        startActivity(intent);
                    })
                    .show();
        });
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
                .params("currentPage", currentPage + "")
                .headers("token", MyApplication.preference().getString("token", ""))
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("contentList")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new MyCallback(context, isReflush));
        Log.e("currentPage", currentPage + "");
    }

    private class MyCallback extends StringCallback {
        boolean isReflush;

        public MyCallback(Context context, boolean isReflush) {
            super(context, true);
            this.isReflush = isReflush;
        }

        @Override
        public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
            super.onResponse(isFromCache, s, request, response);
            materialRefreshLayout.finishRefresh();
            materialRefreshLayout.finishRefreshLoadMore();
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
                    if (currentPage >= totalPage) {//到底啦
                        materialRefreshLayout.setLoadMore(false);
                    } else {
                        materialRefreshLayout.setLoadMore(true);
                    }
                    reflush(bean.getDataRows(), isReflush);
                } else {
                    if (9430 == code) { //请重新登陆

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                materialRefreshLayout.finishRefresh();
                materialRefreshLayout.finishRefreshLoadMore();
                currentPage--;
            }
        }

        @Override
        public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
            super.onError(isFromCache, call, response, e);
            materialRefreshLayout.finishRefresh();
            materialRefreshLayout.finishRefreshLoadMore();
            currentPage--;
        }
    }

    private void reflush(List<DataRowsBean> dataRows, boolean isReflush) {
        if (isReflush) {
            list.clear();
        }
        list.addAll(dataRows);
        adapter.setDate(list);
    }
}