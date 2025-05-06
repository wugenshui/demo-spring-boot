package org.flowable;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @author : chenbo
 * @date : 2024-12-09
 */
public class CallExternalSystemDelegate implements JavaDelegate {
    public void execute(DelegateExecution execution) {
        System.out.println("JavaDelegate：Calling the external system for employee " + execution.getVariable("employee"));
    }
}
