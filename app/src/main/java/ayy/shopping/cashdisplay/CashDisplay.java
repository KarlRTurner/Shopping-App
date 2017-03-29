package ayy.shopping.cashdisplay;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

import ayy.shopping.R;

public class CashDisplay extends FragmentActivity {

    PagerAdapter mPagerAdapter;
    ViewPager mViewPager;
    HashMap<Float, Integer> pics;
    ArrayList<Float> cash;
    private TextToSpeech tts;
    float total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_display);

        pics = new HashMap<>();

        pics.put(0.50f, R.drawable.fiftycent);
        pics.put(1.0f, R.drawable.one);
        pics.put(2.0f, R.drawable.two);
        pics.put(5f, R.drawable.five);
        pics.put(10f, R.drawable.ten);
        pics.put(20f, R.drawable.twenty);
        pics.put(50f, R.drawable.fifty);
        pics.put(100f, R.drawable.onehundred);

        cash = new ArrayList<>();

        //int euro;
        int cent = 0;
        Intent intent = getIntent();
        //euro = intent.getIntExtra("euromsg", 0);
        cent = intent.getIntExtra("centmsg", 0);

        total = intent.getFloatExtra("totalmsg", 0);

        /**
         * These if statements get the euro value which is stored in total
         * and it will display the most appropriate note**/
        //If the float in total is less than 5 it'll display and 5 euro note and tell the user
        //what note to give
        if (total <= 5) {
            //Text to speech on button press
            tts = new TextToSpeech(CashDisplay.this, new TextToSpeech.OnInitListener() {
                //Getting text from the button and storing it in s
                //String s=enterAmount2.getText().toString();
                @Override
                public void onInit(int status) {
                    //Instead of using the text in the button we are using our own message
                    tts.speak("You need to Pay 5 Euro", TextToSpeech.QUEUE_FLUSH, null);
                }
            });
            cash.add(5f);
        } else if (total > 5 && total <= 10) {
            //Text to speech on button press
            tts = new TextToSpeech(CashDisplay.this, new TextToSpeech.OnInitListener() {
                //Getting text from the button and storing it in s
                //String s=enterAmount2.getText().toString();
                @Override
                public void onInit(int status) {
                    //Instead of using the text in the button we are using our own message
                    tts.speak("You need to Pay 10 Euro", TextToSpeech.QUEUE_FLUSH, null);
                }
            });
            cash.add(10f);
        } else if (total > 10 && total <= 20) {
            //Text to speech on button press
            tts = new TextToSpeech(CashDisplay.this, new TextToSpeech.OnInitListener() {
                //Getting text from the button and storing it in s
                //String s=enterAmount2.getText().toString();
                @Override
                public void onInit(int status) {
                    //Instead of using the text in the button we are using our own message
                    tts.speak("You need to Pay 20 Euro", TextToSpeech.QUEUE_FLUSH, null);
                }
            });
            cash.add(20f);
        }
        //if the total is 40 you will have to give 2 20 euro notes
        else if (total == 40) {
            tts = new TextToSpeech(CashDisplay.this, new TextToSpeech.OnInitListener() {
                //Getting text from the button and storing it in s
                //String s=enterAmount2.getText().toString();
                @Override
                public void onInit(int status) {
                    //Instead of using the text in the button we are using our own message
                    tts.speak("You need to Give 2 20 euro notes", TextToSpeech.QUEUE_FLUSH, null);
                }
            });
            for (int i = 0; i < 2; i++) {
                cash.add(20f);
            }
        } else if (total > 20 && total <= 50) {
            //Text to speech on button press
            tts = new TextToSpeech(CashDisplay.this, new TextToSpeech.OnInitListener() {
                //Getting text from the button and storing it in s
                //String s=enterAmount2.getText().toString();
                @Override
                public void onInit(int status) {
                    //Instead of using the text in the button we are using our own message
                    tts.speak("You need to Pay 50 Euro", TextToSpeech.QUEUE_FLUSH, null);
                }
            });
            cash.add(50f);
        }


        //((TextView) findViewById(R.id.payable)).setText(euro + "." + cent);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);

        Button button = (Button) findViewById(R.id.check);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mViewPager.getCurrentItem() < cash.size() - 1) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                } else {
                    float totalPayed =0;
                    for(float c : cash){
                        totalPayed += c;
                    }
                    float chnge = totalPayed- total;
                    Intent intent = new Intent(CashDisplay.this, ChangeDisplay.class);
                    intent.putExtra("changemsg", chnge);
                    startActivity(intent);
                }
            }
        });
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (mViewPager.getCurrentItem() <= cash.size() - 1) {
                Fragment fragment = new CashFragment();
                Bundle args = new Bundle();

                args.putInt("o", pics.get(cash.get(i)));
                fragment.setArguments(args);

                return fragment;
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return cash.size();
        }
    }
}


