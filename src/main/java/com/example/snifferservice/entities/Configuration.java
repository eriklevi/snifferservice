package com.example.snifferservice.entities;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Configuration {
    @NotEmpty
    private boolean dumpMode;
    @NotEmpty
    private boolean privacyMode;
    @NotEmpty
    private String brokerAddress;
    @NotNull
    private int brokerPort;
    @NotEmpty
    private String lwtTopic;
    @NotEmpty
    private String lwtMessage;
    @NotEmpty
    private String topic;
    @NotNull
    @Min(value = -100)
    @Max(value = 0)
    private int powerThrashold;

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



    public String getLwtTopic() {
        return lwtTopic;
    }

    public void setLwtTopic(String lwtTopic) {
        this.lwtTopic = lwtTopic;
    }

    public String getLwtMessage() {
        return lwtMessage;
    }

    public void setLwtMessage(String lwtMessage) {
        this.lwtMessage = lwtMessage;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getPowerThrashold() {
        return powerThrashold;
    }

    public void setPowerThrashold(int powerThrashold) {
        this.powerThrashold = powerThrashold;
    }
}
