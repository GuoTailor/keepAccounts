package com.gyh.keepaccounts;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by GYH on 2021/6/7
 */
public class NmkaTest {
    @Test
    public void te() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        System.out.println(dateFormat.format(new Date()));
    }
    @Test
    public void test4() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE , 59);
        calendar.set(Calendar.SECOND , 59);
        long last = calendar.getTimeInMillis();
        System.out.println((int) ((last - Calendar.getInstance().getTimeInMillis()) / 1000) + 1);
    }

}
