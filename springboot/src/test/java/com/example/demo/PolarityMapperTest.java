package com.example.demo;

import com.example.demo.entity.dto.PolarityDto;
import com.example.demo.mapper.PolarityAnalysisMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest(classes = {DemoApplication.class})
@RunWith(SpringRunner.class)
public class PolarityMapperTest {

    @Autowired
    private PolarityAnalysisMapper polarityAnalysisMapper;

    @Test
    public void testSelectPolarityByMonth() {
        PolarityDto polarityDto =
                polarityAnalysisMapper.selectPolarityByMonth(1, "美国", "2023-07");

        System.out.println(polarityDto);
    }

    @Test
    public void test1() {
        System.out.println(String.format("%s-%02d", "2023", 7));
    }

}
