package com.yeputra.moviecatalogue.adapter;


import com.yeputra.moviecatalogue.model.VPager;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by yovi.putra
 *    on 03/Mar/2019 17:22
 * Company SIEMO - PT. Multipolar Technology, Tbk
 */
public class VPagerAdapter extends FragmentPagerAdapter {
    private List<VPager> fragments;
    private FragmentManager fmManager;

    public VPagerAdapter(List<VPager> fragments, FragmentManager fm) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }
}
