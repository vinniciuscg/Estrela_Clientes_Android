package com.example.vinicius.estrelaclientes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class AnswerCallBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "Número: ";
    private static boolean hasRinged = false;

    @Override
    public void onReceive(Context myContext, Intent service) {

        if(service.getAction().equals("android.intent.action.PHONE_STATE")){

            String state = service.getStringExtra(TelephonyManager.EXTRA_STATE);

            if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                if(hasRinged){
                    Log.d(TAG, "Ligação atendida");
                    String number = service.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

                    if(number.length() > 11){
                        number = number.substring(4);
                    }else {
                        number = number.substring(3);
                    }

                    Intent it = new Intent(myContext, MainActivity.class);
                    it.putExtra("numero", number);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    myContext.startActivity(it);
                    hasRinged = false;
                }
            }

            else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                Log.e(TAG, "Telefone tocando");

                hasRinged = true;

                /*String number = service.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

                if(number.length() > 11){
                    number = number.substring(4);
                }else {
                    number = number.substring(3);
                }

                MainActivity.caixaBuscaPrincipal.setText(number);
                MainActivity.botaoBuscar.callOnClick();*/
            }
            else if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                Log.d(TAG, "Inside EXTRA_STATE_IDLE");
            }
        }
    }
}