package ca.klapstein.nklapste_feelsbook;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements EditFeelingDialog.OnSaveButtonClickListener {
    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private FeelingsTab getFeelingsTab() {
        int index = mViewPager.getCurrentItem();
        SampleFragmentPagerAdapter adapter = ((SampleFragmentPagerAdapter) mViewPager.getAdapter());
        return (FeelingsTab) adapter.getFragment(index);
    }

    @Override
    public void onSaveButtonClick(Feel feel) {
        FeelingsTab feelingsTab = getFeelingsTab();
        feelingsTab.addFeeling(feel);
    }

    @Override
    public void onSaveButtonClick(Feel feel, int position) {
        FeelingsTab feelingsTab = getFeelingsTab();
        feelingsTab.editFeeling(feel, position);
    }
}
