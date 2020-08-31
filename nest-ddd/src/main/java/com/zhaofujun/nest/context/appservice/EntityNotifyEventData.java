package com.zhaofujun.nest.context.appservice;

import com.zhaofujun.nest.standard.EventData;

public class EntityNotifyEventData implements EventData {
    public static final String CODE = "Entity_Notify";

    @Override
    public String getEventCode() {
        return CODE;
    }

    private String beginSnapshot;
    private String endSnapshot;
    private String serviceName;
    private String methodName;
    private EntityOperateEnum operateEnum;

    public String getBeginSnapshot() {
        return beginSnapshot;
    }

    public void setBeginSnapshot(String beginSnapshot) {
        this.beginSnapshot = beginSnapshot;
    }

    public String getEndSnapshot() {
        return endSnapshot;
    }

    public void setEndSnapshot(String endSnapshot) {
        this.endSnapshot = endSnapshot;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public EntityOperateEnum getOperateEnum() {
        return operateEnum;
    }

    public void setOperateEnum(EntityOperateEnum operateEnum) {
        this.operateEnum = operateEnum;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
