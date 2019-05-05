package com.gimus.sensor.model;
import com.gimus.gimus_lib.apiclient.J;

import org.json.JSONObject;

public class Factory {

    public static Subject creaSubject(JSONObject j) {
        Subject o = new Subject();
        o.id =J.getString(j,"id");
        o.name=J.getString(j, "name");
        o.email=J.getString(j,"email");
        return o;
    }

    public static SystemInfo creaSystemInfo(JSONObject j) {
        SystemInfo o = new SystemInfo();
        o.blockMasterReady= J.getBoolean(j,"blockMasterReady");
        o.blockChainVersion=J.getString(j,"blockChainVersion");
        o.currentBlockSerial= Long.parseLong( J.getString(j,"currentBlockSerial"));
        o.currentTransactionSerial=J.getInt(j,"currentTransactionSerial");
        o.maxTransactionsPerBlock=J.getInt(j,"maxTransactionsPerBlock");
        o.certificationAuthorities=J.getString(j,"certificationAuthorities");
        o.currentTimeStamp= Long.parseLong( J.getString(j,"currentTimeStamp"));
        o.requesterInfo=  creaUserInfo(J.deserialize(J.getString(j,"requesterInfo")));
        String s= J.getString(j,"otherUserInfo");

        if (s != "null")
            o.otherUserInfo=  creaUserInfo(J.deserialize(s));
        else
            o.otherUserInfo=null;

        return o;
    }

    public static UserInfo creaUserInfo(JSONObject j) {
        UserInfo o = new UserInfo();
        o.userId=J.getString(j,"userId");
        o.isOnline=J.getBoolean(j,"isOnline");
        o.userOutgoingPendingTransfersCount=J.getInt(j,"userOutgoingPendingTransfersCount");
        o.userIncomingPendingTransfersCount=J.getInt(j,"userIncomingPendingTransfersCount");
        o.token=J.getString(j,"token");
        o.coinBalance = J.getDouble(j,"coinBalance");
        return o;
    }

}
