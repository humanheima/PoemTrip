package com.brotherd.poemtrip.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.View;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.base.BaseFragment;
import com.brotherd.poemtrip.databinding.ActivityMainBinding;
import com.brotherd.poemtrip.fragment.HomeFragment;
import com.brotherd.poemtrip.fragment.MineFragment;
import com.brotherd.poemtrip.util.CheckLoginUtil;
import com.brotherd.poemtrip.viewmodel.MainViewModel;
import com.brotherd.poemtrip.viewmodel.model.MainModel;

public class MainActivity extends BaseDataBindingActivity<ActivityMainBinding> {

    private final String TAG = getClass().getSimpleName();
    private static final int NAVIGATION = 0;
    private static final int MINE = 1;
    private BaseFragment homeFragment;
    private BaseFragment mineFragment;
    private FragmentTransaction transaction;
    private SparseArray<BaseFragment> fragmentSparseArray = new SparseArray<>();
    private int preFrag = -1;
    private int nowFrag = NAVIGATION;

    private MainViewModel viewModel;

    public static void launch(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        viewModel = new MainViewModel(this, new MainModel());
        binding.setViewModel(viewModel);
        changeBottom(NAVIGATION);
        changeFragment();
    }

    @Override
    protected void bindEvent() {
        initListener();
    }

    private void initListener() {
        binding.llNavigation.setOnClickListener(new TabClickListener(NAVIGATION));
        binding.llMine.setOnClickListener(new TabClickListener(MINE));
    }

    private class TabClickListener implements View.OnClickListener {

        private int position;

        public TabClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (position != nowFrag) {
                if (position == MINE) {
                    if (CheckLoginUtil.haveLogin()) {
                        changeBottom(MINE);
                        preFrag = nowFrag;
                        nowFrag = position;
                        changeFragment();
                    } else {
                        LoginActivity.launch(MainActivity.this);
                    }
                } else {
                    changeBottom(NAVIGATION);
                    preFrag = nowFrag;
                    nowFrag = position;
                    changeFragment();
                }
            }
        }
    }

    private void changeBottom(int position) {
        if (position == NAVIGATION) {
            binding.imgNavigation.setImageResource(R.drawable.ic_navigation_pressed);
            binding.imgMine.setImageResource(R.drawable.ic_mine);
            binding.textNavigation.setTextColor(getResources().getColor(R.color.green));
            binding.textMine.setTextColor(getResources().getColor(R.color.gray));
        } else if (position == MINE) {
            binding.imgNavigation.setImageResource(R.drawable.ic_navigation);
            binding.imgMine.setImageResource(R.drawable.ic_mine_pressed);
            binding.textNavigation.setTextColor(getResources().getColor(R.color.gray));
            binding.textMine.setTextColor(getResources().getColor(R.color.green));
        }
    }

    private void changeFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        if (preFrag != -1) {
            transaction.hide(fragmentSparseArray.get(preFrag));
        }
        if (fragmentSparseArray.get(nowFrag) == null) {
            switch (nowFrag) {
                case NAVIGATION:
                    homeFragment = HomeFragment.newInstance();
                    fragmentSparseArray.put(NAVIGATION, homeFragment);
                    transaction.add(R.id.frameLayout, homeFragment);
                    break;
                case MINE:
                    mineFragment = MineFragment.newInstance();
                    fragmentSparseArray.put(MINE, mineFragment);
                    transaction.add(R.id.frameLayout, mineFragment);
                    break;
                default:
                    break;
            }
        } else {
            transaction.show(fragmentSparseArray.get(nowFrag));
        }
        transaction.commit();
    }
}
