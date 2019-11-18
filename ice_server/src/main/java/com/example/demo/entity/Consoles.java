package com.example.demo.entity;

public class Consoles {
    private Integer consoleId;

    private String consoleName;

    public Integer getConsoleId() {
        return consoleId;
    }

    public void setConsoleId(Integer consoleId) {
        this.consoleId = consoleId;
    }

    public String getConsoleName() {
        return consoleName;
    }

    public void setConsoleName(String consoleName) {
        this.consoleName = consoleName == null ? null : consoleName.trim();
    }
}