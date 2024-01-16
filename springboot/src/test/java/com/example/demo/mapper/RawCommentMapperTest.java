package com.example.demo.mapper;

import com.example.demo.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {DemoApplication.class})
@RunWith(SpringRunner.class)
public class RawCommentMapperTest {

    @Autowired
    private RawCommentMapper rawCommentMapper;

    @Test
    public void testSelectCommentCountBySubCategoryAndOrigin() {
        System.out.println(rawCommentMapper.selectCommentCountBySubCategoryAndOrigin(null, null, true));
        System.out.println(rawCommentMapper.selectCommentCountBySubCategoryAndOrigin("电影", null, true));
        System.out.println(rawCommentMapper.selectCommentCountBySubCategoryAndOrigin(null, "湘西", true));
    }

}
