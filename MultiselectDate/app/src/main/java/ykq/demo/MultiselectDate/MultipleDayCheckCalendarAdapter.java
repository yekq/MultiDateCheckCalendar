package ykq.demo.MultiselectDate;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * 日历适配器<br/>
 * Created on 2016/1/20
 *
 * @author yekangqi
 */
public class MultipleDayCheckCalendarAdapter extends BaseListAdapter<DayBean>{
    public MultipleDayCheckCalendarAdapter(Context context, List<DayBean> mList) {
        super(context, mList);
    }

    @Override
    public void setData(List<DayBean> mList) {
        super.setData(mList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null==convertView)
        {
            convertView=inflater.inflate(R.layout.view_item_multiple_check_calendar,parent,false);
        }
        DayBean d=getItem(position);
        ViewHolder holder= (ViewHolder) convertView.getTag();
        if (null==holder)
        {
            holder=new ViewHolder();
            holder.tv_day= (TextView) convertView.findViewById(R.id.tv_day);
            convertView.setTag(holder);
        }
        holder.tv_day.setText(d.getDay()+"");
        convertView.setTag(R.id.DayBean,d);
        if (d.isEndable())
        {
            if (d.isCheck())
            {
                holder.tv_day.setTextColor(mContext.getResources().getColor(R.color.checkedTextColor));
                holder.tv_day.setBackgroundResource(R.drawable.shape_orange);
            }else
            {
                holder.tv_day.setTextColor(mContext.getResources().getColor(R.color.unCheckedTextColor));
                holder.tv_day.setBackgroundResource(R.drawable.shape_stroke_gray_corners);
            }
        }else
        {
            holder.tv_day.setTextColor(mContext.getResources().getColor(R.color.unCheckedTextColor_gray));
            holder.tv_day.setBackgroundResource(R.drawable.shape_stroke_gray_corners_30);
        }
        return convertView;
    }

    class ViewHolder{
        TextView tv_day;
    }
}
