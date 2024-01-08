package com.example.demo;

import com.example.demo.service.PolarityAnalysisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest(classes = {DemoApplication.class})
@RunWith(SpringRunner.class)
public class PolarityAnalysisServiceTest {

    @Autowired
    private PolarityAnalysisService polarityAnalysisService;

    @Test
    public void testGetPolarityByYear() {
        System.out.println(polarityAnalysisService.getPolarityByYear(1, "美国", "2023"));
    }

    @Test
    public void test1() throws ParseException {
        String time = "2023-1";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date dateTime = sdf.parse(time);
        System.out.println(dateTime);
    }

}
