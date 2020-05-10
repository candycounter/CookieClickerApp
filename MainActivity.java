package com.example.cookieclickerproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    public static TextView kobeScore;
    int timesRun = 0;
    int lakersColors;
    int numRequired;
    boolean idChecked = false;
    String choice="";
    Toast myToast;
    RadioGroup radioGroup;
    TextView kobeperSecond;
    TextView upgradeCounter;
    TextView description;
    ImageView kobeImage;
    ImageView kobeLogo;
    ImageView kobeEight;
    ImageView kobeTwentyFour;
    ImageView plusOne;
    TranslateAnimation addPlus;
    AlphaAnimation plusFadeOut;
    AnimationSet animationSet;
    AnimationDrawable animationDrawable;
    PassiveIncome passiveIncome;

    public static ImageView kobeShoe;
    public static AtomicInteger kobeCounter;
    int scoreToBuy = 24;
    int scoreFromSell = 8;
    int intCounter;
    int perSecondCount = 0;
    ConstraintLayout constraintLayout;
    //MyThread thread1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kobeScore = findViewById(R.id.id_kobeScore);
        kobeLogo = findViewById(R.id.id_kobelogo);
        kobeEight = findViewById(R.id.id_kobeeight);
        kobeEight.setImageResource(R.drawable.kobeeight);
        kobeTwentyFour = findViewById(R.id.id_kobetwentyfour);
        kobeTwentyFour.setImageResource(R.drawable.kobetwentyfour);
        kobeLogo.setImageResource(R.drawable.kobelogo);
        description = findViewById(R.id.id_upgradeDescrip);
        description.setText("Project in honor of Kobe Bean Bryant");
        radioGroup = findViewById(R.id.id_radioGroup);
        kobeScore.setText("0 Points");
        lakersColors = Color.parseColor("#235708");
        kobeperSecond = findViewById(R.id.id_perSecond);
        kobeperSecond.setText("Per Second: " + perSecondCount);
        kobeImage = findViewById(R.id.id_kobeImage);
        kobeImage.setImageResource(R.drawable.kobe);
        constraintLayout = (ConstraintLayout) findViewById(R.id.id_layout);
        numRequired = 24;
        kobeCounter = new AtomicInteger(0);
        passiveIncome = new PassiveIncome();
        passiveIncome.start();
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
//        thread1 = new MyThread();
//        thread1.start();

        final ScaleAnimation animation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        final ScaleAnimation animation2 = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(400);
        animation2.setDuration(400);

        //new MyThread().start();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.id_buy){
                    idChecked = true;
                    choice = "Buy";
                }
                else if(checkedId == R.id.id_sell){
                    idChecked = true;
                    choice = "Sell";
                }
            }
        });

        kobeLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(idChecked)){
                    myToast = Toast.makeText(MainActivity.this,"No Choice selected! Please choose whether to buy or sell", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if(kobeCounter.get() < scoreToBuy && choice.equals("Buy")){
                    int scoreNeeded = kobeCounter.get();
                    scoreNeeded = scoreToBuy- scoreNeeded;
                    myToast = Toast.makeText(MainActivity.this, "Score is too low! You need "+scoreNeeded+" more points!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if(choice.equals("Sell") && timesRun == 0){
                    myToast = Toast.makeText(MainActivity.this, "You do not have any Mambas to sell!",Toast.LENGTH_SHORT );
                    myToast.show();
                }
                else if (choice.equals("Sell") && timesRun > 0){
                    intCounter = kobeCounter.get();
                    intCounter = intCounter+8;
                    perSecondCount--;
                    timesRun--;
                    kobeShoe.startAnimation(animation2);
                    if(timesRun==0) {
                        constraintLayout.removeView(kobeShoe);
                        constraintLayout.removeView(upgradeCounter);
                    }
                    kobeCounter.getAndSet(intCounter);
                    kobeScore.setText(kobeCounter.get() + " Points");
                    kobeperSecond.setText("Per Second: " + perSecondCount);
                    upgradeCounter.setText("x"+timesRun);
                    upgradeCounter.setTextSize(30);
                    upgradeCounter.setTextColor(lakersColors);

                }
                else if(kobeCounter.get() >= scoreToBuy && idChecked){
                    intCounter = kobeCounter.get();
                    if(choice.equals("Sell") && timesRun > 0){
                        intCounter +=scoreFromSell;
                        timesRun--;
                        perSecondCount--;
                    }
                    else if(choice.equals("Buy")) {
                        intCounter -= scoreToBuy;
                        timesRun++;
                        perSecondCount = perSecondCount + 1;
                    }
                    kobeCounter.getAndSet(intCounter);
                    kobeScore.setText(kobeCounter.get() + " Points");
                    kobeperSecond.setText("Per Second: " + perSecondCount);
                    constraintLayout.removeView(upgradeCounter);
                    constraintLayout.removeView(kobeShoe);

                    kobeShoe = new ImageView(MainActivity.this);
                    kobeShoe.setId(View.generateViewId());
                    kobeShoe.setImageResource(R.drawable.kobelogo);
                    kobeShoe.startAnimation(animation);


                    upgradeCounter = new TextView(MainActivity.this);
                    upgradeCounter.setId(View.generateViewId());
                    upgradeCounter.setText("x"+timesRun);
                    upgradeCounter.setTextSize(30);
                    upgradeCounter.setTextColor(lakersColors);

                    ConstraintLayout.LayoutParams params3 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    upgradeCounter.setLayoutParams(params3);
                    constraintLayout.addView(upgradeCounter);
                    ConstraintSet constraintSet3 = new ConstraintSet();
                    constraintSet3.clone(constraintLayout);
                    constraintSet3.connect(upgradeCounter.getId(), ConstraintSet.TOP, constraintLayout.getId(), constraintSet3.TOP);
                    constraintSet3.connect(upgradeCounter.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), constraintSet3.BOTTOM);
                    constraintSet3.connect(upgradeCounter.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), constraintSet3.RIGHT);
                    constraintSet3.connect(upgradeCounter.getId(), ConstraintSet.LEFT, constraintLayout.getId(), constraintSet3.LEFT);
                    constraintSet3.setVerticalBias(upgradeCounter.getId(), .3f);
                    constraintSet3.setHorizontalBias(upgradeCounter.getId(),.66f);
                    constraintSet3.applyTo(constraintLayout);

                    ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    kobeShoe.setLayoutParams(params2);
                    constraintLayout.addView(kobeShoe);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);
                    constraintSet.connect(kobeShoe.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
                    constraintSet.connect(kobeShoe.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
                    constraintSet.connect(kobeShoe.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);
                    constraintSet.connect(kobeShoe.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);
                    constraintSet.setHorizontalBias(kobeShoe.getId(),.5f);
                    constraintSet.setVerticalBias(kobeShoe.getId(), .3f);
                    constraintSet.applyTo(constraintLayout);
                }
            }
        });

        kobeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(animation);
                plusOne = new ImageView(MainActivity.this);
                kobeCounter.getAndAdd(1);
                //kobeScoreString = ""+kobeCounter;
                kobeScore.setText(kobeCounter + " Points");

                plusOne.setId(View.generateViewId());
                plusOne.setImageResource(R.drawable.plusone);

                ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                params1.height = 200;
                params1.width = 200;
                plusOne.setLayoutParams(params1);


                constraintLayout.addView(plusOne);
                ConstraintSet constraintSet1 = new ConstraintSet();
                constraintSet1.clone(constraintLayout);

                constraintSet1.connect(plusOne.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP);
                constraintSet1.connect(plusOne.getId(), ConstraintSet.BOTTOM, constraintLayout.getId(), ConstraintSet.BOTTOM);
                constraintSet1.connect(plusOne.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT);
                constraintSet1.connect(plusOne.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT);

                double rand1 = (Math.random() * .25)+.25;
                double rand2 = (Math.random() * .1) + .5;
                constraintSet1.setHorizontalBias(plusOne.getId(), (float) rand1);
                constraintSet1.setVerticalBias(plusOne.getId(), (float) rand2);
                constraintSet1.applyTo(constraintLayout);

                addPlus = new TranslateAnimation(0, 0, 0, -400);
                addPlus.setDuration(300);

                plusFadeOut = new AlphaAnimation(1, 0);
                plusFadeOut.setDuration(300);

                animationSet = new AnimationSet(false);
                animationSet.addAnimation(addPlus);
                animationSet.addAnimation(plusFadeOut);

                plusOne.startAnimation(animationSet);
                plusOne.setVisibility(View.INVISIBLE);

            }
        });
    }
    public class PassiveIncome extends Thread{
        @Override
        public void run() {
            while(true){
                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
                kobeCounter.getAndAdd(perSecondCount);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        kobeScore.setText(kobeCounter + " Points");
                    }
                });
            }
        }
    }
}
