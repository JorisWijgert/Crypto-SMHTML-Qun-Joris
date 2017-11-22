package crypto.org.crypto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Joris on 22-11-2017.
 */

public class SummaryPagerAdapter extends FragmentPagerAdapter {

    private Bundle bundle;

    public SummaryPagerAdapter(FragmentManager fm) {
        super(fm);
        bundle = null;
    }

    public SummaryPagerAdapter(FragmentManager fm, Bundle bundle) {
        super(fm);
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new BaseSummaryFragment();
                break;
            case 1:
                fragment = new GraphCardviewFragment();
                break;

            default:
                fragment = new BaseSummaryFragment();
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Summary";
            case 1:
                return "Graphs";
            default:
                return "Summary";
        }
    }
}
