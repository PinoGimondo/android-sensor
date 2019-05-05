package com.gimus.sensor.model;


import com.gimus.gimus_lib.apiclient.ApiObject;

public class SystemInfo extends ApiObject {
    public boolean  blockMasterReady;
    public String blockChainVersion;
    public long currentBlockSerial;
    public int currentTransactionSerial;
    public int maxTransactionsPerBlock;
    public String certificationAuthorities;
    public long currentTimeStamp;
    public UserInfo requesterInfo;
    public UserInfo otherUserInfo;
}
