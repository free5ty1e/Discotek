package com.circuitstatic.discotek;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class DynamicColorScreen extends Activity {

    int color1, color2, red1, red2, blue1, blue2, green1, green2;

    View v;

    ObjectAnimator anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dynamiccolorscreen);


        v = findViewById(R.id.view);

        // Generate color1 before starting the thread
        red1 = (int)(Math.random() * 128 + 127);
        green1 = (int)(Math.random() * 128 + 127);
        blue1 = (int)(Math.random() * 128 + 127);
        color1 = 0xff << 24 | (red1 << 16) |
                (green1 << 8) | blue1;

        v.setBackgroundColor(color1);

        anim = ObjectAnimator.ofInt(v, "backgroundColor", color1);

        anim.setEvaluator(new ArgbEvaluator());

        anim.setDuration(100);


        new Thread() {
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    DynamicColorScreen.this.runOnUiThread(new Runnable() {
                        public void run() {

                            //generate color 2

                            red2 = (int)(Math.random() * 128 + 127);
                            green2 = (int)(Math.random() * 128 + 127);
                            blue2 = (int)(Math.random() * 128 + 127);
                            color2 = 0xff << 24 | (red2 << 16) |
                                    (green2 << 8) | blue2;

                            // Update the color values
                            anim.setIntValues(color1, color2);

                            anim.start();

                            // Order the colors
                            color1 = color2;

                        }
                    });
                }
            }
        }.start();
    }
}
