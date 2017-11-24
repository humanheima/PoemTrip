package com.brotherd.poemtrip.activity;

import android.content.Context;
import android.content.Intent;

import com.brotherd.poemtrip.R;
import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.databinding.ActivityPoemBinding;
import com.brotherd.poemtrip.util.NetWorkUtil;
import com.brotherd.poemtrip.util.Toast;
import com.brotherd.poemtrip.viewmodel.PoemViewModel;
import com.brotherd.poemtrip.viewmodel.model.PoemModel;

public class PoemActivity extends BaseDataBindingActivity<ActivityPoemBinding> {

    public static final String POEM_ID = "poemId";
    private static final String TAG = "PoemActivity";

    private PoemViewModel viewModel;

    public static void launch(Context context, long poemId) {
        Intent starter = new Intent(context, PoemActivity.class);
        starter.putExtra(POEM_ID, poemId);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_poem;
    }

    @Override
    protected void initData() {
        long poemId = getIntent().getLongExtra(POEM_ID, -1);
        viewModel = new PoemViewModel(this, new PoemModel(poemId));
        binding.setViewModel(viewModel);
        if (NetWorkUtil.hasNetwork()) {
            viewModel.getPoem();
        } else {
            Toast.showToast(getString(R.string.no_network));
        }
    }

}
