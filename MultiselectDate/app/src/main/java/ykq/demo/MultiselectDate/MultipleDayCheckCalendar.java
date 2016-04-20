package ykq.demo.MultiselectDate;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 可多选日期日历<br/>
 * Created on 2016/1/20
 *
 * @author yekangqi
 */
public class MultipleDayCheckCalendar extends LinearLayout implements AdapterView.OnItemClickListener {
    public static final SimpleDateFormat DATE_FORMAT=new SimpleDateFormat("yyyyMM");
    private GridView gv_calendar;
    private MultipleDayCheckCalendarAdapter adapter;
    private List<DayBean> days;//日期数据
    private String date;//显示的日期 yyyyMM
    private int startCurrentDay=0;//默认选中的开始天
    private int endCurrentDay=0;//默认选中的结束天

    private DayBean firstCheckDay;//第一个被选中
    private DayBean secondCheckDay;//第二个被选中
    private MultipleDayCheckCalendarListener multipleDayCheckCalendarListener;
    public MultipleDayCheckCalendar(Context context) {
        super(context);
        init(context,null);
    }

    public MultipleDayCheckCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MultipleDayCheckCalendar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs)
    {
        View v= LayoutInflater.from(context).inflate(R.layout.multiple_check_calendar,this,true);
        gv_calendar=(GridView)v.findViewById(R.id.gv_calendar);
        gv_calendar.setOnItemClickListener(this);
        if (isInEditMode())
        {
            setDate("201601");
        }else
        {
            if (null!=attrs)
            {
                TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.MultipleDayCheckCalendar);
                String dateStr=ta.getString(R.styleable.MultipleDayCheckCalendar_date);
                ta.recycle();
                if (!TextUtils.isEmpty(dateStr))
                {
                    setDate(dateStr);
                }
            }
        }
    }
    /**
     * 重置选中
     */
    public void resetCheckDay()
    {
        firstCheckDay=null;
        secondCheckDay=null;
    }
    /**
     * 重置状态
     */
    private void resetDays()
    {
        updateDays(0,null==adapter?0:adapter.getCount(),false);
    }
    /**
     * 进行选中/取消选中
     * @param start 开始
     * @param end 结束
     * @param check
     */
    private void updateDays(int start,int end,boolean check)
    {
        if (null!=adapter && null!=adapter.mList)
        {
            int size=adapter.mList.size();
            if (end<start)
            {
                int cache=end;
                end=start;
                start=cache;
            }
            if (start>=0 && end<=size)
            {
                for (int i = start; i <end ; i++) {
                    adapter.mList.get(i).setCheck(check);
                }
            }
        }
    }

    /**
     * 获取首个选中的日期
     * @return
     */
    public DayBean getFirstCheckDay() {
        return firstCheckDay;
    }
    /**
     * 获取第二个选中的日期
     * @return
     */
    public DayBean getSecondCheckDay() {
        return secondCheckDay;
    }

    public void setFirstCheckDay(DayBean firstCheckDay) {
        this.firstCheckDay = firstCheckDay;
    }

    public void setSecondCheckDay(DayBean secondCheckDay) {
        this.secondCheckDay = secondCheckDay;
    }

    public String getDate() {
        return date;
    }

    /**
     * 设置默认选中的日期
     * @param startCurrentDay
     * @param endCurrentDay
     */
    public void setDateByDefaultCheckDay(int startCurrentDay, int endCurrentDay, String date)
    {
        this.startCurrentDay=startCurrentDay;
        this.endCurrentDay=endCurrentDay;
        this.setDate(date);
    }
    /**
     * 设置时间
     * @param date 格式是yyyyMM
     */
    public void setDate(String date) {
        this.date=date;
        try {
            setDate(DATE_FORMAT.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置时间,默认是选择月初到月末
     * @param d
     */
    public void setDate(Date d)
    {
        if (null==days)
        {
            days=new ArrayList<DayBean>();
        }else
        {
            days.clear();
        }
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(d);
        //默认全选中当前月份的日期
/*        if (0==startCurrentDay)
        {
            startCurrentDay=calendar.getMinimum(Calendar.DAY_OF_MONTH);
        }
        if (0==endCurrentDay)
        {
            endCurrentDay=calendar.getMaximum(Calendar.DAY_OF_MONTH);
        }*/

        //获取第一天是星期几
        calendar.set(Calendar.DAY_OF_MONTH,1);
        int firstDayWeek=calendar.get(Calendar.DAY_OF_WEEK);
        if (firstDayWeek>1)
        {
            //要获取上一个月的日期
            for (int i = firstDayWeek ; i > 1; i--) {
                //重置时间
                calendar.setTime(d);
                //获取第一天是星期几
                calendar.set(Calendar.DAY_OF_MONTH,1);
                calendar.add(Calendar.DAY_OF_YEAR, -i + 1);

                DayBean dayBean=new DayBean();
                dayBean.setCheck(false);
                dayBean.setEndable(false);
                dayBean.setDay(calendar.get(Calendar.DAY_OF_MONTH));
                dayBean.setMonthOffset(DayBean.MONTH_BEFORE);
                days.add(dayBean);
            }
        }
        //当月所有天数
        DayBean startDefault=null;
        DayBean endDefault=null;
        calendar.setTime(d);
        int maxDayOfMonth=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int k = 0; k < maxDayOfMonth; k++) {
            calendar.setTime(d);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DAY_OF_YEAR,k);

            int thisdayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
            boolean defaultCheck;
            boolean endable;
            if (startCurrentDay<=thisdayOfMonth && endCurrentDay>=thisdayOfMonth)
            {
                defaultCheck=true;
                endable=true;
            }else
            {
                defaultCheck=false;
                endable=true;
            }
            DayBean dayBean=new DayBean();
            dayBean.setCheck(defaultCheck);
            dayBean.setEndable(endable);
            dayBean.setDay(thisdayOfMonth);
            dayBean.setMonthOffset(DayBean.MONTH_CURRENT);
            days.add(dayBean);

            if (thisdayOfMonth==startCurrentDay)
            {
                startDefault=dayBean;
            }else if (thisdayOfMonth==endCurrentDay)
            {
                endDefault=dayBean;
            }
        }

        //下一个月的时间
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH, maxDayOfMonth);
        int lastDayWeek=calendar.get(Calendar.DAY_OF_WEEK);
        if(lastDayWeek<7)
        {
            for (int i = 1; i <= 7-lastDayWeek; i++) {
                //重置时间
                calendar.setTime(d);
                //获取最后一天
                calendar.set(Calendar.DAY_OF_MONTH,maxDayOfMonth);
                calendar.add(Calendar.DAY_OF_YEAR,i);
                DayBean dayBean=new DayBean();
                dayBean.setCheck(false);
                dayBean.setEndable(false);
                dayBean.setDay(calendar.get(Calendar.DAY_OF_MONTH));
                dayBean.setMonthOffset(DayBean.MONTH_NEXT);
                days.add(dayBean);
            }
        }
        //设置index
        for (int i = 0; i < days.size(); i++) {
            days.get(i).setIndex(i);
        }
        //设置Adapter
        if (null==adapter)
        {
            adapter=new MultipleDayCheckCalendarAdapter(getContext(),days);
            gv_calendar.setAdapter(adapter);
        }else
        {
            adapter.setData(days);
            adapter.notifyDataSetChanged();
        }
        setFirstCheckDay(startDefault);
        setSecondCheckDay(endDefault);
        this.startCurrentDay=-1;
        this.endCurrentDay=-1;
        gv_calendar.setAdapter(adapter);
    }

    /**
     * 设置日期选择事件监听
     * @param multipleDayCheckCalendarListener
     */
    public void setMultipleDayCheckCalendarListener(MultipleDayCheckCalendarListener multipleDayCheckCalendarListener) {
        this.multipleDayCheckCalendarListener = multipleDayCheckCalendarListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        DayBean d= (DayBean) v.getTag(R.id.DayBean);
        if (null!=d)
        {
            if (DayBean.MONTH_CURRENT==d.getMonthOffset())//当前月份
            {
                //已经选中了两个,变成一个
                if (null!=firstCheckDay && null!=secondCheckDay)
                {
                    secondCheckDay=null;
                    resetDays();
                    d.setCheck(true);
                    firstCheckDay=d;
                    adapter.notifyDataSetChanged();
                    if(null!= multipleDayCheckCalendarListener)
                    {
                        multipleDayCheckCalendarListener.onDayCheck(firstCheckDay,null);
                    }
                    return;
                }
                //选中一个,变成两个
                if (null!=firstCheckDay && null==secondCheckDay)
                {
                    if (firstCheckDay.isDayEquals(d))//已经选中了第一个,然后再次点击第一个
                    {
                        //清空选中
                        d.setCheck(false);
                        resetCheckDay();
                    }else
                    {
                        //选中第二个
                        d.setCheck(true);
                        secondCheckDay=d;
                        updateDays(firstCheckDay.getIndex(), secondCheckDay.getIndex(),true);
                    }
                    adapter.notifyDataSetChanged();
                    if(null!= multipleDayCheckCalendarListener)
                    {
                        multipleDayCheckCalendarListener.onDayCheck(firstCheckDay,secondCheckDay);
                    }
                    return;
                }
                //一个也没有选中的时候,第一个选中
                if (null==firstCheckDay && null==secondCheckDay)
                {
                    d.setCheck(true);
                    firstCheckDay = d;
                    adapter.notifyDataSetChanged();
                    if(null!= multipleDayCheckCalendarListener)
                    {
                        multipleDayCheckCalendarListener.onDayCheck(firstCheckDay,null);
                    }
                    return;
                }
            }else if(null!= multipleDayCheckCalendarListener)//非当前月份
            {
                if (DayBean.MONTH_BEFORE==d.getMonthOffset())
                {
                    multipleDayCheckCalendarListener.isBeforeMonthClick(true);
                }else if (DayBean.MONTH_NEXT==d.getMonthOffset())
                {
                    multipleDayCheckCalendarListener.isBeforeMonthClick(false);
                }
            }
        }
    }
}
