package com.example.dinesh.silent_to_normal;

/**
 * Created by dinesh on 18/8/16.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    private final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    public void onReceive(Context context, Intent intent) {
        SharedPreferences sData;
        sData = context.getSharedPreferences("codes",0);
        String codereturned = sData.getString("code","couldn't load data");
        Toast.makeText(context, codereturned, Toast.LENGTH_SHORT).show();

        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle myBundle = intent.getExtras();
            SmsMessage[] messages = null;
            String strMessage = "";

            if (myBundle != null) {
                //get message in pdus format(protocol discription unit)
                Object[] pdus = (Object[]) myBundle.get("pdus");
                //create an array of messages
                messages = new SmsMessage[pdus.length];

                for (int i = 0; i < messages.length; i++) {
                    //Create an SmsMessage from a raw PDU.
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    //get the originating address (sender) of this SMS message in String form or null if unavailable
                    //strMessage += "SMS From: " + messages[i].getOriginatingAddress();
                    //strMessage += " : ";
                    //get the message body as a String, if it exists and is text based.
                    strMessage += messages[i].getMessageBody();
                    strMessage += "\n";
                }
                // Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
                //i.putExtra("new_variable_name",strMessage);
                // context.startActivity(i);
                if(strMessage.contains(codereturned))
                {
                    /*Intent myStarterIntent = new Intent(context, MainActivity.class);
                    myStarterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(myStarterIntent);*/
                    AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    if(am.getRingerMode()==AudioManager.RINGER_MODE_SILENT |am.getRingerMode()==AudioManager.RINGER_MODE_VIBRATE )
                    {
                        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    }

                    am.setStreamVolume(
                            AudioManager.STREAM_MUSIC,
                            am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                            0);   //set volume to maximum
                    MediaPlayer player = MediaPlayer.create(context,Settings.System.DEFAULT_RINGTONE_URI);
                    player.start();
                    Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();
                }


            }

        }
    }
}
