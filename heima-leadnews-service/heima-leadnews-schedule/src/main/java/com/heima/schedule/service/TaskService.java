package com.heima.schedule.service;

import com.heima.model.schedule.dtos.Task;

/**
 * 对外访问接口
 */
public interface TaskService {
    /**
     * 添加任务
     * @param task   任务对象
     * @return       任务id
     */
    long addTask(Task task) ;

    /**
     * 取消任务
     * @param taskId        任务id
     * @return              取消结果
     */
    boolean cancelTask(long taskId);

    /**
     * 按照类型和优先级来拉取任务
     */
    Task poll(int type,int priority);
}