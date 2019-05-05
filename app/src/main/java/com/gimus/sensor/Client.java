package com.gimus.sensor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.gimus.gimus_lib.apiclient.ApiClient;
import com.gimus.gimus_lib.apiclient.ApiCommand;
import com.gimus.gimus_lib.apiclient.ApiObject;
import com.gimus.gimus_lib.apiclient.ApiTask;
import com.gimus.gimus_lib.apiclient.J;
import com.gimus.gimus_lib.apiclient.iAsyncDataReceiver;
import com.gimus.gimus_lib.crypto.AES128;
import com.gimus.sensor.model.Factory;

public class Client extends ApiClient {

    protected String server;
    protected String password;
    protected String password_hash;

    public Client(String serverURL, String _password) {
        server=serverURL;
        password=_password;
        password_hash= AES128.SHA128(this.password);
    }

    @Override
    public void onTaskExecuted(ApiCommand ac){
        if(ac.result== null ) {
            ac.stato=3;
            ac.receiver.onDataError(ac, ac.error);
        }
        else
        {
            ac.stato=2;

            try {
                switch ( ac.command) {
                    case "relay_push":
                        break;
                    case "device_check_in":
                        ac.data= Factory.creaSubject(J.deserialize(ac.result));
                        break;
                    case "ip":
                        ApiObject o=new ApiObject();
                        byte[] b= Base64.decode(ac.result, Base64.DEFAULT);
                        Bitmap myBitmap = BitmapFactory.decodeByteArray(b,0,b.length) ;
                        o.tag=myBitmap;
                        ac.data=o;
                        break;
                }
            }
            catch (Exception e) {
                ac.error = e.getMessage();
                ac.data=null;
            }
            ac.receiver.onDataReceived(ac,ac.result,ac.data);
        }
    }

    public void SendSensorReading(iAsyncDataReceiver adr, String sensorId, String sensorValue) {
        ApiCommand ac=new ApiCommand("GET", server + "api/relay_push?sensorId=" + sensorId + "&sensorvalue=" + sensorValue, adr);
        new ApiTask().executeCommand(this,ac);
    }

}
