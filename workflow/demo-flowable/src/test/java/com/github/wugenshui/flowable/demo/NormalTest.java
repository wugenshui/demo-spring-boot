package com.github.wugenshui.flowable.demo;

import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : chenbo
 * @date : 2025-02-11
 */
@SpringBootTest
class NormalTest {
    @Resource
    private ProcessEngineConfiguration processEngineConfiguration;
    @Resource
    private ProcessEngine processEngine;
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;

    // 普通流程完成 [Approve or reject request] -> [Holiday approved]
    @Test
    void normalFlow() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("holiday-request.bpmn20.xml")
                .deploy();

        // 流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        System.out.printf("创建流程定义（processDefinition） Id:%s \n", processDefinition.getId());

        // 发起流程
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", "张三");
        variables.put("nrOfHolidays", 1);
        variables.put("description", "请假");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);
        System.out.printf("发起流程实例（processInstance） Id:%s \n", processInstance.getId());

        // 查询任务
        List<Task> tasks = printCurrentTask();

        // 完成任务
        Task task = tasks.get(0);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        System.out.printf("申请内容：用户[%s]想要[%s]天假期 \n", processVariables.get("employee"), processVariables.get("nrOfHolidays"));
        variables = new HashMap<>();
        variables.put("approved", true);
        taskService.complete(task.getId(), variables);

        for (int i = 0; i < 5; i++) {
            tasks = printCurrentTask();
            if (!tasks.isEmpty()) {
                taskService.complete(tasks.get(0).getId());
            } else {
                // 可以查询历史所有
                historyService.createHistoricTaskInstanceQuery().processInstanceId(task.getProcessInstanceId()).list();
            }
        }
    }

    private List<Task> printCurrentTask() {
        // 查询任务
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().list();
        if (!tasks.isEmpty()) {
            System.out.printf("--> 您有%s个任务，当前任务：%s \n", tasks.size(), tasks.get(0).getName());
        }
        return tasks;
    }
}
