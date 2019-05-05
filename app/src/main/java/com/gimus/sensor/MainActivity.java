package com.gimus.sensor;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.gimus.gimus_lib.apiclient.ApiCommand;
import com.gimus.gimus_lib.apiclient.ApiObject;
import com.gimus.gimus_lib.apiclient.iAsyncDataReceiver;

public class MainActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback,
        QRCodeReaderView.OnQRCodeReadListener, iAsyncDataReceiver


{
    public ViewGroup mainLayout;
    QRCodeReaderView qrv;
    private  String lastQrCode="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainLayout = (ViewGroup) findViewById(R.id.top_lo);
        A.a.mainActivity=this;

        A.client =new Client(A.getResourceString(R.string.apiServer), A.a.getPreferenceString("PASSWORD"));

        qrv=QrCodeReaderUtils.tryInitQRCodeReaderView(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public  void tick(){
        lastQrCode="";
    }

    protected void onResume() {
        super.onResume();
        A.a.mainActivity=this;
        NfcUtils.createPendingIntent(this);
        if(qrv !=null)  qrv.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        NfcUtils.disableForegroundDispatch(this);
        if(qrv !=null)  qrv.stopCamera();
    }

    @Override
    protected void onNewIntent(Intent intent) {
            String id=NfcUtils.getIdTag(intent);
            if (id !="") newReading("NFC-ID",id);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        if ( !this.lastQrCode.equals(text)) {
            lastQrCode=text;
            newReading("QRCODE", lastQrCode);
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (requestCode == QrCodeReaderUtils.MY_PERMISSION_REQUEST_CAMERA) {
            QrCodeReaderUtils.onRequestCameraPermissions(this, grantResults);
        }
    }

    protected  void newReading(String type, String value ){
        A.client.SendSensorReading( this, A.getResourceString(R.string.sensorId) , type +"|" + value);
        Snackbar.make( mainLayout, type + ": " + value  , Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
        A.click();

    }


    @Override
    public void onDataReceived(ApiCommand ac, String JSON, ApiObject o) {

    }

    @Override
    public void onDataError(ApiCommand ac, String error) {

    }
}
