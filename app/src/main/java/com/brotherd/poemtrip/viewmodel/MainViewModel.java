package com.brotherd.poemtrip.viewmodel;

import com.brotherd.poemtrip.activity.SearchActivity;
import com.brotherd.poemtrip.base.BaseDataBindingActivity;
import com.brotherd.poemtrip.viewmodel.model.MainModel;

/**
 * Created by dumingwei on 2017/11/19 0019.
 */

public class MainViewModel extends BaseViewModel {

    private MainModel model;

    public MainViewModel(BaseDataBindingActivity context, MainModel model) {
        super(context);
        this.model = model;
    }

    public void launchSearchActivity() {
        SearchActivity.launch(context);
    }

}
