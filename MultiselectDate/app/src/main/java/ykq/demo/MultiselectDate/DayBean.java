package ykq.demo.MultiselectDate;

/**
 * 日期<br/>
 * Created on 2016/1/20
 *
 * @author yekangqi
 */
public class DayBean {
    public static final int MONTH_BEFORE=-1,MONTH_CURRENT=0,MONTH_NEXT=1;
    private int index;//顺序
    private boolean endable;//可用
    private boolean check;//是否已经选中
    private int day;//日期
    private int monthOffset;//月份偏移值   -1:上一个月,0:当前月,1:下一个月

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isEndable() {
        return endable;
    }

    public void setEndable(boolean endable) {
        this.endable = endable;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonthOffset() {
        return monthOffset;
    }

    public void setMonthOffset(int monthOffset) {
        this.monthOffset = monthOffset;
    }

    /**
     * 通过日期比较
     * @param other
     * @return other.getDay()==this.getDay()
     */
    public boolean isDayEquals(DayBean other)
    {
        if (null!=other)
        {
            return other.getDay()==this.getDay();
        }
        return false;
    }
}
