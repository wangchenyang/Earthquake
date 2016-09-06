package sing.earthquake.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.List;

import sing.earthquake.R;
import sing.earthquake.bean.BuildListBean;
import sing.earthquake.bean.BuildListBean.DataRowsBean;

/**
 * Created by admin on 2016/9/2.
 */
public class BuildAdapter extends RecyclerView.Adapter<BuildAdapter.ViewHolder> {

    private Context context;
    public BuildAdapter(Context context) {
        this.context = context;
    }

    private onItemClickListener onItemClickListener;
    public void setOnItemClickListener(BuildAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private List<DataRowsBean> list;
    public void setDate(List<DataRowsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private LayoutInflater inflater;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            holder.pllTop.setVisibility(View.VISIBLE);
        } else {
            holder.pllTop.setVisibility(View.GONE);
        }

        if (position % 2 == 0) {
            holder.pllBottom.setBackgroundColor(context.getResources().getColor(R.color.color_f7f6f2));
        } else {
            holder.pllBottom.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        }
        final BuildListBean.DataRowsBean bean = list.get(position);
        holder.tvName.setText(bean.getJzmc());
        holder.tvStreet.setText(bean.getSsjd());
        holder.tvCommunity.setText(bean.getSsshequ());
        holder.tvUsed.setText(bean.getYt());
        holder.tvEndTime.setText(bean.getJgsj());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        PercentLinearLayout pllTop;
        PercentLinearLayout pllBottom;
        TextView tvName;
        TextView tvStreet;
        TextView tvCommunity;
        TextView tvUsed;
        TextView tvEndTime;

        public ViewHolder(View itemView) {
            super(itemView);
            pllTop = (PercentLinearLayout) itemView.findViewById(R.id.pll_top);
            pllBottom = (PercentLinearLayout) itemView.findViewById(R.id.pll_bottom);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvStreet = (TextView) itemView.findViewById(R.id.tv_street);
            tvCommunity = (TextView) itemView.findViewById(R.id.tv_community);
            tvUsed = (TextView) itemView.findViewById(R.id.tv_used);
            tvEndTime = (TextView) itemView.findViewById(R.id.tv_end_time);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null){
                    onItemClickListener.onClick(list.get(getLayoutPosition()));
                }
            });
        }
    }

    public interface onItemClickListener{
        void onClick(DataRowsBean bean);
    }
}
