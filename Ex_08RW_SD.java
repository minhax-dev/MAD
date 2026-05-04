// MainActivity.java
package com.example.externalstorage;

import android.Manifest;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    String CHANNEL_ID = "my_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        createNotificationChannel();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }
    }

    // WRITE FILE
    public void writeFile(View view) {
        try {
            File file = new File(getExternalFilesDir(null), "sample.txt");
            FileOutputStream fos = new FileOutputStream(file);

            fos.write(editText.getText().toString().getBytes());
            fos.close();

            Toast.makeText(this, "File Written", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ FILE
    public void readFile(View view) {
        try {
            File file = new File(getExternalFilesDir(null), "sample.txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            editText.setText(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // PERSISTENT NOTIFICATION
    public void showPersistentNotification(View view) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Persistent Notification")
                        .setContentText("This cannot be swiped away")
                        .setOngoing(true);

        NotificationManagerCompat.from(this).notify(1, builder.build());
    }

    // TIME-BASED NOTIFICATION
    public void setTimeNotification(View view) {

        AlarmManager alarmManager =
                (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, MyReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 10000, // 10 sec
                pendingIntent
        );

        Toast.makeText(this,
                "Notification in 10 seconds",
                Toast.LENGTH_SHORT).show();
    }

    // CREATE CHANNEL
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "My Channel",
                            NotificationManager.IMPORTANCE_DEFAULT
                    );

            NotificationManager manager =
                    getSystemService(NotificationManager.class);

            manager.createNotificationChannel(channel);
        }
    }
}


// MyReceiver.java
package com.example.externalstorage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "my_channel")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Time Notification")
                        .setContentText("This is a scheduled notification")
                        .setAutoCancel(true);

        NotificationManagerCompat.from(context).notify(2, builder.build());
    }
}


// AndroidManifest.xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

<application ... >

    <receiver android:name=".MyReceiver" />

</application>