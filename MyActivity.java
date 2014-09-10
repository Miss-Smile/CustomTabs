package tw.com.eztravel.scrolltabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MyActivity extends FragmentActivity {

    ViewPager viewPager=null;
    private TextView tab1,tab2,tab3;
    private View indicator;

    private int currIndex = 0;
    private int tabW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        initTabs();

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    private void initTabs() {

        tab1 = (TextView) findViewById(R.id.tab1);
        tab2 = (TextView) findViewById(R.id.tab2);
        tab3 = (TextView) findViewById(R.id.tab3);

        tab1.setText("TAB 1");
        tab2.setText("TAB 2");
        tab3.setText("TAB 3");

        tab1.setOnClickListener(new TabClickListener(0));
        tab2.setOnClickListener(new TabClickListener(1));
        tab3.setOnClickListener(new TabClickListener(2));

        indicator = (View) findViewById(R.id.indicator);


        ViewTreeObserver vto = tab1.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                tab1.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.tabsLinearLayout);
                linearLayout.getLayoutParams().height = getActionBar().getHeight();

                tabW = tab1.getWidth();
                indicator.getLayoutParams().width = tabW;
            }

        });


    }

    private class TabClickListener implements OnClickListener {

        private int index=0;
        public TabClickListener(int i){
            index=i;
        }

        @Override
        public void onClick(View view) {
            viewPager.setCurrentItem(index);
        }
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i2) {

            float x = tabW * (i+v);
            indicator.setX(x);

        }

        @Override
        public void onPageSelected(int i) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

}

class MyAdapter extends FragmentPagerAdapter {

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        Fragment newFragment;
        switch(i)
        {
            case 0:
                newFragment = new FragmentA();
                break;
            case 1:
                newFragment = new FragmentB();
                break;
            default:
                newFragment = new FragmentC();
                break;
        }
        return newFragment;

    }

    @Override
    public int getCount() {
        return 3;
    }



}

