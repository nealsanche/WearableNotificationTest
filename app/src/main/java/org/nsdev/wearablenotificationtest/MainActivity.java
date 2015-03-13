package org.nsdev.wearablenotificationtest;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSendClicked(View button) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("When in danger,\nWhen in doubt,\nRun in circles,\nScream and shout!");

        // Create an intent for the reply action
        Intent actionIntent = new Intent(this, MainActivity.class);
        PendingIntent actionPendingIntent =
                PendingIntent.getActivity(this, 0, actionIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the action
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_love,
                        getString(R.string.ic_love_label), actionPendingIntent)
                        .build();

        NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender();

        wearableExtender.setGravity(Gravity.TOP); // Gravity.BOTTOM, Gravity.CENTER_VERTICAL
        wearableExtender.setHintScreenTimeout(NotificationCompat.WearableExtender.SCREEN_TIMEOUT_LONG); // ms or SCREEN_TIMEOUT_SHORT

        wearableExtender.setContentIconGravity(Gravity.START);
        wearableExtender.addAction(action);

        wearableExtender.setHintShowBackgroundOnly(true);
        wearableExtender.addPage(new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .extend(
                        new NotificationCompat.WearableExtender()
                                .setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.punk))
                                .setHintAvoidBackgroundClipping(true)
                                .setHintShowBackgroundOnly(true)
                ).build());

        wearableExtender.addPage(new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("Third Page").extend(new NotificationCompat.WearableExtender().setGravity(Gravity.TOP)).build());

        mBuilder.extend(wearableExtender);

        int mNotificationId = 1;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }
}
