DROP TABLE IF EXISTS sims_class;
CREATE TABLE sims_class(
    `id` VARCHAR(32) NOT NULL  COMMENT '主键' ,
    `class_name` VARCHAR(90)   COMMENT '班级名称' ,
    `creater` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '创建人' ,
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    `updater` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '更新人' ,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    `tenant_id` VARCHAR(32) NOT NULL DEFAULT 0 COMMENT '租户号' ,
    PRIMARY KEY (id)
)  COMMENT = '班级';
DROP TABLE IF EXISTS sims_student;
CREATE TABLE sims_student(
    `id` VARCHAR(32) NOT NULL  COMMENT '主键' ,
    `class_id` VARCHAR(32) NOT NULL  COMMENT '班级ID' ,
    `student_name` VARCHAR(16) NOT NULL  COMMENT '学生姓名' ,
    `phone` VARCHAR(255)   COMMENT '手机号' ,
    `gender` TINYINT  DEFAULT 0 COMMENT '性别;0=男，1=女' ,
    `birth` DATE   COMMENT '出生日期' ,
    `entry_date` DATE   COMMENT '入学日期' ,
    `remark` VARCHAR(500)   COMMENT '备注' ,
    `creater` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '创建人' ,
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    `updater` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '更新人' ,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    `tenant_id` VARCHAR(32) NOT NULL DEFAULT 0 COMMENT '租户号' ,
    PRIMARY KEY (id)
)  COMMENT = '学生';
DROP TABLE IF EXISTS sims_teacher;
CREATE TABLE sims_teacher(
    `id` VARCHAR(32) NOT NULL  COMMENT '主键' ,
    `class_id` VARCHAR(32) NOT NULL  COMMENT '班级ID' ,
    `teacher_name` VARCHAR(90) NOT NULL  COMMENT '姓名' ,
    `phone` VARCHAR(255)   COMMENT '手机号' ,
    `gender` TINYINT NOT NULL DEFAULT 0 COMMENT '性别;0=男，1=女' ,
    `birth` DATE   COMMENT '出生日期' ,
    `remark` VARCHAR(500)   COMMENT '备注' ,
    `monthly_pay` DECIMAL(9,2)   COMMENT '月薪' ,
    `creater` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '创建人' ,
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    `updater` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '更新人' ,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    `tenant_id` VARCHAR(32) NOT NULL DEFAULT 0 COMMENT '租户号' ,
    PRIMARY KEY (id)
)  COMMENT = '教师';
