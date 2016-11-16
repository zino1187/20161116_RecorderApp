package com.solu.recorderapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_RECORD_PERMISSION=2;

    MediaRecorder mediaRecorder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick(View view){
        int writePermission=ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int recordPermission=ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

        if(writePermission== PackageManager.PERMISSION_DENIED || recordPermission ==PackageManager.PERMISSION_DENIED){

            Toast.makeText(this, "권한이 없네요", Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO
            }, REQUEST_RECORD_PERMISSION);
        }else{
            record();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case   REQUEST_RECORD_PERMISSION :
                Toast.makeText(this, "grantResults[0] : "+grantResults[0]+"grantResults[1] : "+grantResults[1], Toast.LENGTH_SHORT).show();

                if(grantResults.length >0 &&grantResults[0] == PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "디스크 사용 권한을 수락해야 사용이 가능합니다", Toast.LENGTH_SHORT).show();
                }else if(grantResults.length >0 &&grantResults[1] == PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "오디오 녹음 권한을 수락해야 사용이 가능합니다", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void record(){
        Toast.makeText(this, "녹음시작합니다", Toast.LENGTH_SHORT).show();

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            File dir=new File(Environment.getExternalStorageDirectory(), "iot_audio");
            mediaRecorder.setOutputFile(new File(dir , "test1.mp4").getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
