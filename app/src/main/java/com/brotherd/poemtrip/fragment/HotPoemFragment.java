package com.brotherd.poemtrip.fragment;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.adapter.GridViewAdapter;
import com.brotherd.poemtrip.base.BaseFragment;
import com.brotherd.poemtrip.model.PoemModel;
import com.brotherd.poemtrip.util.Images;
import com.brotherd.poemtrip.util.Toast;
import com.brotherd.poemtrip.widget.NoScrollGridView;
import com.brotherd.poemtrip.widget.banner.banner.SimpleBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HotPoemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotPoemFragment extends BaseFragment {

    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.simpleBanner)
    SimpleBanner simpleBanner;
    @BindView(R.id.grid_view)
    NoScrollGridView gridView;

    private List<PoemModel> poemModelList;
    private GridViewAdapter adapter;
    private List<String> bannerImages;
    private List<String> bannerTitles;

    public HotPoemFragment() {
    }

    public static HotPoemFragment newInstance() {
        HotPoemFragment fragment = new HotPoemFragment();
        return fragment;
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_hot_poem;
    }

    @Override
    protected void initData() {
        bannerImages = new ArrayList<>();
        bannerTitles = new ArrayList<>();
        poemModelList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            PoemModel model = new PoemModel(1, "", "李白作品集", "李白");
            poemModelList.add(model);
            bannerImages.add(Images.imageUrls[i]);
            bannerTitles.add("");
        }
        simpleBanner.setImages(bannerImages)
                .setBannerTitles(bannerTitles)
                .start();
        adapter = new GridViewAdapter(getContext(), R.layout.item_grid_view, poemModelList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.showToast(getActivity(), "position=" + position);
            }
        });
    }

}
