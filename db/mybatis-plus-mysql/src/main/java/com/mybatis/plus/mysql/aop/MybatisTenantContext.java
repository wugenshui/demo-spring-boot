package com.mybatis.plus.mysql.aop;

/**
 * @author : chenbo
 * @date : 2025-09-11
 */
public class MybatisTenantContext {
    private static final ThreadLocal<Boolean> TENANT_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    public static Boolean get() {
        return TENANT_CONTEXT_THREAD_LOCAL.get();
    }

    public static void set(boolean isIgnore) {
        TENANT_CONTEXT_THREAD_LOCAL.set(isIgnore);
    }

    public static void clear() {
        TENANT_CONTEXT_THREAD_LOCAL.remove();
    }
}
