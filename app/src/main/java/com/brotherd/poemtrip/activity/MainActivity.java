package com.brotherd.poemtrip.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.base.BaseActivity;
import com.brotherd.poemtrip.base.BaseFragment;
import com.brotherd.poemtrip.fragment.HomeFragment;
import com.brotherd.poemtrip.fragment.MineFragment;
import com.brotherd.poemtrip.util.CheckLoginUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;

public class MainActivity extends BaseActivity {

    private final String TAG = getClass().getSimpleName();
    private static final int NAVIGATION = 0;
    private static final int MINE = 1;

    @BindView(R.id.ll_navigation)
    LinearLayout llNavigation;
    @BindView(R.id.ll_mine)
    LinearLayout llMine;
    @BindViews({R.id.img_navigation, R.id.img_mine})
    List<ImageView> imageViewList;
    @BindViews({R.id.text_navigation, R.id.text_mine})
    List<TextView> textViewList;
    private BaseFragment homeFragment;
    private BaseFragment mineFragment;
    private FragmentTransaction transaction;
    private SparseIntArray sparseArrayNormal;
    private SparseIntArray sparseArrayPressed;
    private SparseArray<BaseFragment> fragmentSparseArray = new SparseArray<>();
    private int preFrag = -1;
    private int nowFrag = NAVIGATION;

    public static void launch(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        initBottomIcons();
        changeFragment();
    }

    @Override
    protected void bindEvent() {
        initListener();
    }

    private void initBottomIcons() {
        sparseArrayNormal = new SparseIntArray();
        sparseArrayPressed = new SparseIntArray();

        sparseArrayNormal.put(NAVIGATION, R.drawable.ic_navigation);
        sparseArrayNormal.put(MINE, R.drawable.ic_mine);

        sparseArrayPressed.put(NAVIGATION, R.drawable.ic_navigation_pressed);
        sparseArrayPressed.put(MINE, R.drawable.ic_mine_pressed);
    }

    private void initListener() {
        llNavigation.setOnClickListener(new TabClickListener(NAVIGATION));
        llMine.setOnClickListener(new TabClickListener(MINE));
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
                        preFrag = nowFrag;
                        nowFrag = position;
                        changeFragment();
                    } else {
                        LoginActivity.launch(MainActivity.this);
                    }
                } else {
                    preFrag = nowFrag;
                    nowFrag = position;
                    changeFragment();
                }
            }
        }
    }

    private void changeFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        if (preFrag != -1) {
            imageViewList.get(preFrag).setImageResource(sparseArrayNormal.get(preFrag));
            textViewList.get(preFrag).setTextColor(getResources().getColor(R.color.text_gray));
            transaction.hide(fragmentSparseArray.get(preFrag));
        }
        imageViewList.get(nowFrag).setImageResource(sparseArrayPressed.get(nowFrag));
        textViewList.get(nowFrag).setTextColor(getResources().getColor(R.color.green));
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
