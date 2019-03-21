package com.example.snifferservice.entities;

import javax.validation.constraints.NotEmpty;

public class Configuration {
    @NotEmpty
    private boolean dumpMode;
    @NotEmpty
    private boolean privacyMode;
    @NotEmpty
    private String brokerAddress;
    @NotEmpty
    private int brokerPort;

    public boolean isDumpMode() {
        return dumpMode;
    }

    public void setDumpMode(boolean dumpMode) {
        this.dumpMode = dumpMode;
    }

    public boolean isPrivacyMode() {
        return privacyMode;
    }

    public void setPrivacyMode(boolean privacyMode) {
        this.privacyMode = privacyMode;
    }

    public String getBrokerAddress() {
        return brokerAddress;
    }

    public void setBrokerAddress(String brokerAddress) {
        this.brokerAddress = brokerAddress;
    }

    public int getBrokerPort() {
        return brokerPort;
    }

    public void setBrokerPort(int brokerPort) {
        this.brokerPort = brokerPort;
    }
}
