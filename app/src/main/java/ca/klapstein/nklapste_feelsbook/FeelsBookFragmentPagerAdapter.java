package ca.klapstein.nklapste_feelsbook;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;


/**
 * Manages the TabLayout for FeelsBook navigating between the {@code FeelTab} and {@code StatsTab}.
 *
 * @see FeelTab
 * @see StatsTab
 */
class FeelsBookFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "FeelsBookFragmentPagerAdapter";

    private final SparseArray<Fragment> mPageReferenceMap = new SparseArray<>();
    private final String[] tabTitles = new String[]{"Feelings", "Stats"};

    FeelsBookFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FeelTab();
            case 1:
                return new StatsTab();
            default:
                throw new RuntimeException("Invalid tab position selected");
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mPageReferenceMap.put(position, fragment);
        return fragment;
    }

    Fragment getFragment(int key) {
        return mPageReferenceMap.get(key);
    }

    /**
     * Get the pages Title based on the item's position.
     *
     * @param position {@code int}
     * @return {@code CharSequence}
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}