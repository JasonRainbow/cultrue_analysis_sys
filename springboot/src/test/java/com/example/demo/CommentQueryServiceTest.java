package com.example.demo;

import com.example.demo.service.CommentQueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = {DemoApplication.class})
@RunWith(SpringRunner.class)
public class CommentQueryServiceTest {

    @Autowired
    private CommentQueryService commentQueryService;

    @Test
    public void testQueryLanguageCommentNum() {

    }

}
