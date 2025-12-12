package com.wugenshui.service.impl;

import com.wugenshui.service.IStudentService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class StudentServiceImpl implements IStudentService {
    @Override
    @Tool(name="getStudentInfoByName",description = "获取学生基本信息")
    public String getStudentInfoByName(@ToolParam(description = "学生姓名") String name) {
        return String.format("%s，26，180cm身高",name);
    }
    @Override
    @Tool(name="getStudentScoreByName",description = "获取某个学生单个课程的成绩")
    public String getStudentScoreByName(@ToolParam(description = "学生姓名") String name,
                                        @ToolParam(description = "课程名称") String subjectName) {
        Random rand = new Random();
        int randomInt = rand.nextInt(100); // n是上限，不包括n
        return String.format("%s，%s:%d分",name, subjectName, randomInt);
    }
}