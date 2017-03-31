package ayy.shopping.cashdisplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import ayy.shopping.R;

/**
 * Created by acale on 27/03/2017.
 */

public class ChangeDisplay extends FragmentActivity {

    ChangeDisplay.PagerAdapter mPagerAdapter;
    ViewPager mViewPager;
    HashMap<Float, Integer> pics;
    ArrayList<Float> cash;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_display);

        pics = new HashMap<>();

        pics.put(0.05f, R.drawable.fivecent);
        pics.put(0.10f, R.drawable.tencent);
        pics.put(0.20f, R.drawable.twentycent);
        pics.put(0.50f, R.drawable.fiftycent);
        pics.put(1.0f, R.drawable.one);
        pics.put(2.0f, R.drawable.two);
        pics.put(5f, R.drawable.five);
        pics.put(10f, R.drawable.ten);
        pics.put(20f, R.drawable.twenty);
        pics.put(50f, R.drawable.fifty);
        pics.put(100f, R.drawable.onehundred);

        cash = new ArrayList<>();

        Intent intent = getIntent();
        float chnge = intent.getFloatExtra("changemsg", 0.0f);

        DecimalFormat df = new DecimalFormat("0.00");

        String myChange = String.valueOf(df.format(chnge));

        String[] convert = myChange.split("\\.");   // Splits the change into euro and cent integers
        int euro = Integer.parseInt(convert[0]);
        int cent = Integer.parseInt(convert[1]);

        int coin[] = {50, 20, 10, 5, 2, 1};    // Array of notes and euro coins
        int num;

        int cent_coin[] = {50, 20, 10, 5};    // Array of cent coins
        int num2;

        // Calculates least number of notes or 2Euro/1Euro coins
        for (int i = 0; i < coin.length; i++) {
            if (coin[i] <= euro) {
                num = euro / coin[i];
                System.out.println(num + " x " + "€" + coin[i]);
                euro -= num * coin[i];

                float moneyValue = coin[i];
                for( int j = 0; j < num; j++)
                {
                    cash.add(moneyValue);
                }
            }
        }
        // Calculates least number of cent coins
        for (int i = 0; i < cent_coin.length; i++) {
            if (cent_coin[i] <= cent) {
                num2 = cent / cent_coin[i];
                System.out.println(num2 + " x " + "0." + cent_coin[i] + "c");
                cent -= num2 * cent_coin[i];

                float centValue = (cent_coin[i]*0.01f);
                //System.out.println("float cent val: " + centValue);

                float cent_number = Float.parseFloat(df.format(centValue));
                //System.out.println("The number is: " + number);

                for( int j = 0; j < num2; j++)
                {
                    cash.add(cent_number);
                }


            }
        }

        mPagerAdapter = new ChangeDisplay.PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);

        Button button = (Button) findViewById(R.id.check);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mViewPager.getCurrentItem() < cash.size() - 1) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
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
