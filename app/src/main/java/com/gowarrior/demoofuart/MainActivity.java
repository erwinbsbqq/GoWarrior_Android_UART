package com.gowarrior.demoofuart;

import android.app.Activity;
import android.shokai.firmata.ArduinoFirmata;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends Activity {

    private static boolean isRun=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startUARTButton= (Button) findViewById(R.id.start_uart_button);
        startUARTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUART();
            }
        });
        Button stopUARTButton= (Button) findViewById(R.id.stop_uart_button);
        stopUARTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopUART();
            }
        });
    }
    private void startUART(){
        final int led_pin=13;
        isRun=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArduinoFirmata arduino=new ArduinoFirmata("/dev/ttyS1");
                try {
                    arduino.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                    try {
                        while (isRun){
                            arduino.digitalWrite(led_pin,true);
                            Thread.sleep(500);
                            arduino.digitalWrite(led_pin, false);
                            Thread.sleep(500);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                arduino.close();
            }
        }).start();
    }
    private void stopUART(){
        isRun=false;
    }
}
