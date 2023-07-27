package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
public class DataCrawlService {
    @Value("${python.env}")
    private String pythonEnv;

    @Value("${python.home}")
    private String pythonHome;

    public boolean crawlComments(Integer workId, String workName) throws UnsupportedEncodingException {
        // 本地环境下的python的启动文件地址，要执行的python脚本
//        pythonHome = "E:\\homework\\大二下学期\\软件综合实训\\开发\\爬虫和算法\\src\\";
        String[] arguments = new String[]{pythonEnv, pythonHome + "test.py", "-i " + workId, "-n " + workName};
        System.out.println(Arrays.toString(arguments));
        Process proc;
        try {
            proc = Runtime.getRuntime().exec(arguments);// 执行py文件
            //用输入输出流来截取结果
            /*BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "gbk"));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();*/
            //waitFor是用来显示脚本是否运行成功，1表示失败，0表示成功，还有其他的表示其他错误
            int res = proc.waitFor();
            return res == 0;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
