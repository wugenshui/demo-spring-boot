package com.github.wugenshui.flowable.demo;

import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : chenbo
 * @date : 2025-02-11
 */
@SpringBootTest
class RedoTest {
    @Resource
    private ProcessEngineConfiguration processEngineConfiguration;
    @Resource
    private ProcessEngine processEngine;
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;

    // 重新发起实现 [Approve or reject request] -> [Holiday approved]
    @Test
    void redo() {
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
        System.out.printf("----> 发起流程实例（processInstance） [%s] \n", processInstance.getId());

        // 查询任务
        List<Task> tasks = printCurrentTask();

        variables = new HashMap<>();
        variables.put("approved", true);
        // 完成任务
        taskService.complete(tasks.get(0).getId(), variables);
        System.out.printf("--> 完成任务：%s \n", tasks.get(0).getName());

        tasks = printCurrentTask();

        // 取消任务
        runtimeService.deleteProcessInstance(processInstance.getId(), "取消流程实例");
        System.out.printf("--> 取消流程实例：%s \n", tasks.get(0).getName());

        // 重新发起
        ProcessInstance processInstance2 = runtimeService.startProcessInstanceByKey("holidayRequest", variables);
        System.out.printf("----> 发起新的流程实例（processInstance） [%s] \n", processInstance2.getId());
        // 关联至旧的流程实例
        runtimeService.setVariable(processInstance2.getId(), "LAST_HANDLE_RUNTIME_ID", processInstance.getId());

        // 查询历史实例
        List<HistoricProcessInstance> total = historyService.createHistoricProcessInstanceQuery().list();
        List<HistoricProcessInstance> part = historyService.createHistoricProcessInstanceQuery().variableExists("LAST_HANDLE_RUNTIME_ID").list();
        System.out.printf("HistoricProcessInstance total=%s part=%s  \n", total.size(), part.size());

        // 查询历史任务
        List<HistoricTaskInstance> history = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(part.get(0).getId())
                .list();
        List<String> names = history.stream().map(TaskInfo::getName).collect(Collectors.toList());
        System.out.printf("执行历史：[%s] %s \n", part.get(0).getId(), String.join(" --> ", names));

        // 通过变量查询关联的旧任务
        HistoricVariableInstance variable = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(part.get(0).getId())
                .variableName("LAST_HANDLE_RUNTIME_ID")
                .singleResult();
        if (variable != null) {
            String value = (String) variable.getValue();
            System.out.println("Variable LAST_HANDLE_RUNTIME_ID=" + value);
            List<HistoricTaskInstance> history2 = historyService.createHistoricTaskInstanceQuery()
                    .processVariableNotExists("LAST_HANDLE_RUNTIME_ID")
                    .processInstanceId(value)
                    .list();
            List<String> names2 = history2.stream().map(TaskInfo::getName).collect(Collectors.toList());
            System.out.printf("执行历史2：[%s] %s \n", value, String.join(" --> ", names2));
        } else {
            System.out.println("Variable not found.");
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
