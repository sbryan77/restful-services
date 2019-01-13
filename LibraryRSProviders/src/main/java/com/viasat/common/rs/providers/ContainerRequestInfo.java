package com.viasat.common.rs.providers;

public class ContainerRequestInfo {
    private String username;
    private String application;

    public ContainerRequestInfo() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApplication() {
        return this.application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}