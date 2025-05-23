package com.heima.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.schedule.pojos.Taskinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 *  Mapper 接口
 */
@Mapper
public interface TaskinfoMapper extends BaseMapper<Taskinfo> {

    List<Taskinfo> queryFutureTime(@Param("taskType")int type, @Param("priority")int priority, @Param("future")Date future);

}