package com.aoppp.gatewaymaster.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aoppp.gatewaymaster.R;
import com.aoppp.gatewaysdk.domain.CheckItem;
import com.aoppp.gatewaysdk.domain.Indicator;

import java.util.ArrayList;
import java.util.List;

public class AllViewAdapter extends BaseAdapter {

    public  List<CheckItem> checkItems;
    LayoutInflater infater = null;
    private Context mContext;
    public static List<Integer> clearIds;

    public AllViewAdapter(Context context, List<CheckItem> checkItems) {
        this.checkItems = checkItems;
        infater = LayoutInflater.from(context);
        mContext = context;
        clearIds = new ArrayList<Integer>();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return checkItems.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return checkItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = infater.inflate(R.layout.listview_all_view,
                    null);
            holder = new ViewHolder();
            holder.appIcon = (ImageView) convertView
                    .findViewById(R.id.image);
            holder.appName = (TextView) convertView
                    .findViewById(R.id.name);
            holder.memory = (TextView) convertView
                    .findViewById(R.id.memory);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CheckItem appInfo = (CheckItem) getItem(position);
//        holder.appIcon.setImageDrawable(appInfo.icon); //TODO 这里有个图标,group?
        holder.appName.setText(appInfo.getDesc());
        StringBuilder sb = new StringBuilder();
        if(appInfo.getIndicators()!=null && appInfo.getIndicators().size() > 0){

            for(Indicator indicator:appInfo.getIndicators()){

                sb.append(indicator.getDesc() + ":" + (indicator.getValue()==null?"<未知>":indicator.getValue()) + "\n");
            }
        }else{
            sb.append("NONE");
        }
        holder.memory.setText(sb.toString());

        return convertView;
    }

    class ViewHolder {
        ImageView appIcon;
        TextView appName;
        TextView memory;
    }

}
