package com.apps.dbrah_Provider.uis.activity_preview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.apps.dbrah_Provider.R;
import com.apps.dbrah_Provider.adapter.OfferAdapter;
import com.apps.dbrah_Provider.databinding.ActivityPreviewBinding;
import com.apps.dbrah_Provider.uis.activity_base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PreviewActivity extends BaseActivity {
    private ActivityPreviewBinding binding;
    private OfferAdapter offerAdapter;
    private List<Object> offerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_preview);
        initView();
    }

    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.preview), R.color.white, R.color.black);
        offerList=new ArrayList<>();
        offerAdapter =new OfferAdapter(offerList,this);
        binding.recViewOffer.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewOffer.setAdapter(offerAdapter);
    }
}