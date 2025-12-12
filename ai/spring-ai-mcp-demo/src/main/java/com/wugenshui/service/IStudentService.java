package com.wugenshui.service;

public interface IStudentService {
    //查询学生的基本信息
    String getStudentInfoByName(String name);
    //查询学生的分数信息
    String getStudentScoreByName(String name, String subjectName);
}