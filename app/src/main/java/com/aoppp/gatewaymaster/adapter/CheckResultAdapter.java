package com.aoppp.gatewaymaster.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.aoppp.gatewaysdk.domain.CheckRule;
import com.aoppp.gatewaysdk.domain.Indicator;

import java.util.ArrayList;
import java.util.List;

public class CheckResultAdapter extends BaseAdapter {

    public  List<CheckItem> checkItems;
    LayoutInflater infater = null;
    private Context mContext;
    public static List<Integer> clearIds;

    public CheckResultAdapter(Context context, List<CheckItem> checkItems) {
        this.checkItems = checkItems;
        infater = LayoutInflater.from(context);
        mContext = context;
        clearIds = new ArrayList<Integer>();
    }

    @Override
    public int getCount() {
        return checkItems.size();
    }

    @Override
    public Object getItem(int position) {
        return checkItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = infater.inflate(R.layout.listview_check_item,
                    null);
            holder = new ViewHolder();
            holder.appIcon = (ImageView) convertView
                    .findViewById(R.id.image);
            holder.appName = (TextView) convertView
                    .findViewById(R.id.text_name);
            holder.memory = (TextView) convertView
                    .findViewById(R.id.text_detail);

            holder.cb = (RadioButton) convertView
                    .findViewById(R.id.cb_check);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CheckItem checkItem = (CheckItem) getItem(position);
//        holder.appIcon.setImageDrawable(appInfo.icon); //TODO 这里有个图标,group?
        holder.appName.setText(checkItem.getDesc());
        StringBuilder sb = new StringBuilder();
        if(checkItem.getIndicators()!=null && checkItem.getIndicators().size() > 0){

            for(Indicator indicator:checkItem.getIndicators()){

                sb.append(indicator.getDesc() + ":" + (indicator.getValue()==null?"<未知>":indicator.getValue()));
                List<CheckRule> failedRules = checkItem.findFailedRulesByIndicator(indicator.getName());
                if(failedRules!=null && failedRules.size() > 0) {
                    sb.append("\t");
                    for(CheckRule failedRule:failedRules) {
                        sb.append(failedRule.getError() + ";");
                    }
                }
                sb.append("\n");
            }
        }else{
            sb.append("NONE");
        }
        holder.memory.setText(sb.toString());

        if (!checkItem.hasError()) {
            holder.cb.setChecked(true);
        } else {
            holder.cb.setChecked(false);
            holder.memory.setTextColor(mContext.getResources().getColor(R.color.red_500));
            //holder.cb.setTextColor();
        }

        return convertView;
    }

    class ViewHolder {
        ImageView appIcon;
        TextView appName;
        TextView memory;
        TextView tvProcessMemSize;
        RelativeLayout cb_rl;
        RadioButton cb;

        public RadioButton getCb() {
            return cb;
        }

        public void setCb(RadioButton cb) {
            this.cb = cb;
        }
    }

}
