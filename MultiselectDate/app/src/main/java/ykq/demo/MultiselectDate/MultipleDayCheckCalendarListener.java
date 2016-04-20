package ykq.demo.MultiselectDate;

/**
 * <br/>
 * Created on 2016/1/20
 *
 * @author yekangqi
 */
public interface MultipleDayCheckCalendarListener {
    /**
     * 日期选中事件
     * @param first 第一个,不为空
     * @param second 第二个,可能为空
     */
    void onDayCheck(DayBean first, DayBean second);

    /**
     * 是否上一个月的日期点击
     * @param isBefore true:上一个月,false:下一个月
     */
    void isBeforeMonthClick(boolean isBefore);
}