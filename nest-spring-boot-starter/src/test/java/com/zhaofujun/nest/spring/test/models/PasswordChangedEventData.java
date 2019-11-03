package com.zhaofujun.nest.spring.test.models;

import com.zhaofujun.nest.context.event.EventData;

public class PasswordChangedEventData extends EventData {

    private String newPassword;
    private String oldPassword;
    private String userId;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PasswordChangedEventData(String newPassword, String oldPassword, String userId) {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
        this.userId = userId;
    }
    public static final String EVENT_CODE = "PASSWORD_CHANGED";

    @Override
    public String getEventCode() {
        return EVENT_CODE;
    }

}
