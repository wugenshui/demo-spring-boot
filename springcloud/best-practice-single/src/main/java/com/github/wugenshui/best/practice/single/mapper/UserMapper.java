package com.github.wugenshui.best.practice.single.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.wugenshui.best.practice.single.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author chenbo
 * @since 2019-12-01
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 恢复删除标记
     *
     * @param id 主键
     * @return 影响行数
     */
    @Update("UPDATE User SET deleted = 0 WHERE ID = #{id}")
    int resumeDelete(@Param("id") Long id);

    /**
     * 版本恢复
     *
     * @param id 主键
     * @return 影响行数
     */
    @Update("UPDATE User SET version = 1 WHERE ID = #{id}")
    int resumeVersion(@Param("id") Long id);

    /**
     * 自定义查询语句
     *
     * @param wrapper
     * @return
     */
    @Select("SELECT * FROM USER ${ew.customSqlSegment} ")
    List<User> customQueryList(@Param(Constants.WRAPPER) Wrapper<User> wrapper);
}
