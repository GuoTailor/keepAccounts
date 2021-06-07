package com.gyh.keepaccounts;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
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
}
