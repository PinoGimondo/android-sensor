package com.gimus.sensor;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class QrCodeReaderUtils {
    public static final int MY_PERMISSION_REQUEST_CAMERA = 0;

    public static QRCodeReaderView initQRCodeReaderView(MainActivity ma) {
        QRCodeReaderView  qrCodeReaderView = (QRCodeReaderView) ma.findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setAutofocusInterval(2000L);
        qrCodeReaderView.setOnQRCodeReadListener(ma);
        qrCodeReaderView.setBackCamera();
        qrCodeReaderView.setQRDecodingEnabled(true);
        qrCodeReaderView.startCamera();
        return qrCodeReaderView;
    }

    public static QRCodeReaderView tryInitQRCodeReaderView(final MainActivity ma){
        if (ActivityCompat.checkSelfPermission(ma, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
           return  initQRCodeReaderView(ma);
        } else {
            requestCameraPermission(ma);
            return null;
        }
    }

    public static void requestCameraPermission(final MainActivity ma) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(ma, Manifest.permission.CAMERA)) {
            Snackbar.make(ma.mainLayout, "Camera access is required to display the camera preview", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityCompat.requestPermissions(ma, new String[] { Manifest.permission.CAMERA }, MY_PERMISSION_REQUEST_CAMERA);
                }
            }).show();
        } else {
            Snackbar.make(ma.mainLayout, "Permission is not available. Requesting camera permission", Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(ma, new String[] { Manifest.permission.CAMERA }, MY_PERMISSION_REQUEST_CAMERA);
        }
    }

    public static QRCodeReaderView onRequestCameraPermissions( MainActivity ma, @NonNull int[] grantResults) {

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(ma.mainLayout, "Camera permission was granted.", Snackbar.LENGTH_SHORT).show();
            return initQRCodeReaderView(ma);
        } else {
            Snackbar.make(ma.mainLayout, "Camera permission request was denied.", Snackbar.LENGTH_SHORT)
                    .show();
            return null;
        }
    }

}
