package com.xll.administrator.democollection;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static android.R.attr.path;
import static android.app.DownloadManager.ACTION_NOTIFICATION_CLICKED;

public class DownloadActivity extends Activity implements View.OnClickListener{
    private Button btnStartDownload;
    private Button btnCancelDownload;
    private ProgressBar downloadProgress;
    private DownloadManager.Query query;
    private static final int downProgress=1;
    private  DownloadManager dm;
    private  long downloadId=-1;
    private float totalSize;

    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

           String action= intent.getAction();
            if(ACTION_NOTIFICATION_CLICKED.equals(action)){

            }else if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){
                downloadProgress.setProgress(100);
                Toast.makeText(DownloadActivity.this,"下载完成,马上安装",Toast.LENGTH_SHORT);

                //TODO
                String path= Environment.getExternalStoragePublicDirectory("DemoCollection").getPath()+File.separator+"app-release.apk";

                Log.e("apk install path",path);


                //1、更改权限
                String command=" chmod 777 "+path;
                Runtime runtime=Runtime.getRuntime();
                try {
                    runtime.exec(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //2、使用Intent调用安装
                Intent install=new Intent(Intent.ACTION_VIEW);
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.setDataAndType(Uri.fromFile(new File(path)),"application/vnd.android.package-archive");
                DownloadActivity.this.startActivity(install);

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        btnStartDownload=(Button)findViewById(R.id.startDownload);
        btnCancelDownload=(Button)findViewById(R.id.cancelDownload);
        downloadProgress=(ProgressBar)findViewById(R.id.progress);
        btnStartDownload.setOnClickListener(this);
        btnCancelDownload.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter=new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        this.registerReceiver(receiver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startDownload:
                startDownload();

                break;
            case R.id.cancelDownload:
                removeDownload();

                break;

        }
    }

    private void showNotification(){

    }

    Handler hanlder=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            switch (msg.what){
                case downProgress:
                    Log.e("handler","receive message");
                    downloadProgress.setProgress((int)msg.obj);
                    break;
            }
        }
    };

   class DownloadProgressObserver extends ContentObserver {
       Handler handler;
       Cursor cursor;

      public  DownloadProgressObserver(Handler handler){
           super(handler);
          this.handler=handler;


       }

       @Override
       public void onChange(boolean selfChange) {
           super.onChange(selfChange);
           Log.e("tt","onChange");
           query.setFilterById(downloadId);
           cursor=dm.query(query);
           if(cursor==null||!cursor.moveToFirst()){
               return;
           }
           int status=cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
           switch (status){
               case DownloadManager.STATUS_FAILED:
                   Log.e("status","failed");
                 break;
               case DownloadManager.STATUS_PAUSED:
                   Log.e("status","paused");
                   break;
               case DownloadManager.STATUS_PENDING:
                   Log.e("status","pending");
                   break;
               case DownloadManager.STATUS_RUNNING:
                   Log.e("status","running");
                   break;
               case DownloadManager.STATUS_SUCCESSFUL:
                   Log.e("status","successful");
                   break;
               default:
                   break;



           }

           totalSize=cursor.getFloat(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
          float currentSize= cursor.getFloat(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
           int progress=(int)(100*currentSize/totalSize);
           handler.obtainMessage(downProgress,progress).sendToTarget();
           Log.e("total--current-prog",totalSize+","+currentSize+","+progress);
           if(progress>=99){
               cursor.close();
           }

       }
   }


    private long startDownload(){
         dm= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse("http://192.168.1.166:8090/XiaoYangServer/source/app-release.apk"));
        request.setDestinationInExternalPublicDir("DemoCollection","app-release.apk");
        request.allowScanningByMediaScanner();
        request.setDescription("DemoCollection descriptor");
        request.setTitle("DemoCollection title");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);
        downloadId= dm.enqueue(request);
        query=new DownloadManager.Query();
        query.setFilterById(downloadId);
        DownloadProgressObserver dpo=new DownloadProgressObserver(hanlder);
        this.getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"),true,dpo);
        return downloadId;

    }
    private int removeDownload(){
        return dm.remove(downloadId);
    }
}
