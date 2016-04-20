package ykq.demo.MultiselectDate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tv_date;//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_date= (TextView) findViewById(R.id.tv_date);
        MultipleDayCheckCalendar mcc_month= (MultipleDayCheckCalendar) findViewById(R.id.mcc_month);
        mcc_month.setMultipleDayCheckCalendarListener(new MultipleDayCheckCalendarListener() {

            /**
             * 日期选中事件
             *
             * @param first  第一个,不为空
             * @param second 第二个,可能为空
             */
            @Override
            public void onDayCheck(DayBean first, DayBean second) {
                if (null==first && null==second)
                {
                    tv_date.setText("");
                    return;
                }
                if (null!=first && null==second)
                {
                    tv_date.setText(first.getDay()+"");
                    return;
                }
                if (null!=first && null!=second)
                {
                    int f=first.getDay();
                    int s=second.getDay();
                    if (f>s)
                    {
                        int cache=f;
                        f=s;
                        s=cache;
                    }
                    tv_date.setText(f+"-"+s);
                    return;
                }
            }

            /**
             * 是否上一个月的日期点击
             *
             * @param isBefore true:上一个月,false:下一个月
             */
            @Override
            public void isBeforeMonthClick(boolean isBefore) {
                Toast.makeText(MainActivity.this,"isBefore:"+isBefore,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
