package sing.earthquake.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sing.earthquake.R;
import sing.earthquake.bean.BottomMenuBean;
import sing.earthquake.common.streetinfo.StreetInfo;

/**
 * @author: LiangYX
 * @ClassName: StreetAdapter
 * @date: 16/8/29 下午10:32
 * @Description: 所属街道和社区
 */
public class StreetAdapter extends BaseAdapter {

    List<BottomMenuBean> list;
    private Context context;

    // -100为街道 -200为用途 其他为社区
    public StreetAdapter(Context context,int type) {
        this.context = context;
        if (type == -100) {
            list = StreetInfo.getStreet();
        }else if(type == -200){
            list = StreetInfo.getUsed();
        }else{
            list = StreetInfo.getCommunity(type + "");
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.row_text, parent, false);

            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BottomMenuBean bean = list.get(position);
        holder.tvContent.setText(bean.content);

        return convertView;
    }

    public class ViewHolder {
        TextView tvContent;
    }
}
