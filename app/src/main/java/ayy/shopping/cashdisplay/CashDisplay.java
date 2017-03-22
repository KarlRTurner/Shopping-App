package ayy.shopping.cashdisplay;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ayy.shopping.R;

import static android.R.attr.button;

public class CashDisplay extends FragmentActivity {

    PagerAdapter mPagerAdapter;
    ViewPager mViewPager;
    HashMap<Float, Integer> pics;
    ArrayList<Float> cash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_display);

        pics = new HashMap<Float, Integer>();
        cash = new ArrayList<Float>();

        pics.put(0.50f , R.drawable.fiftycent);
        pics.put(1.0f , R.drawable.one);
        pics.put(2.0f , R.drawable.two);
        pics.put(5f , R.drawable.five);
        pics.put(10f , R.drawable.ten);
        pics.put(20f , R.drawable.twenty);
        pics.put(50f , R.drawable.fifty);
        pics.put(100f , R.drawable.onehundred);

        for(int j =0 ; j < 10 ; j++ ) {
            cash.add(10f);
        }


        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);

        Button button = (Button)findViewById(R.id.check);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mViewPager.getCurrentItem() < cash.size()-1) {
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
            if (mViewPager.getCurrentItem() < cash.size() - 1){
                Fragment fragment = new CashFragment();
                Bundle args = new Bundle();

                args.putInt("o", pics.get(cash.get(i)));
                fragment.setArguments(args);

                return fragment;
            }else{
                return null;
            }
        }

        @Override
        public int getCount() {
            return cash.size();
        }
    }
}


