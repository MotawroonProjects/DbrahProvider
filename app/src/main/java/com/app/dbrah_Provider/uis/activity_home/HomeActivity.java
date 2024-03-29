package com.app.dbrah_Provider.uis.activity_home;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;


import com.app.dbrah_Provider.adapter.MyPagerAdapter;
import com.app.dbrah_Provider.general_ui.GeneralMethod;
import com.app.dbrah_Provider.interfaces.Listeners;
import com.app.dbrah_Provider.model.NotiFire;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.mvvm.ActivityHomeMvvm;
import com.app.dbrah_Provider.mvvm.GeneralMvvm;
import com.app.dbrah_Provider.uis.FragmentBaseNavigation;
import com.app.dbrah_Provider.uis.activity_base.BaseActivity;

import com.app.dbrah_Provider.R;

import com.app.dbrah_Provider.databinding.ActivityHomeBinding;
import com.app.dbrah_Provider.language.Language;
import com.app.dbrah_Provider.uis.activity_login.LoginActivity;
import com.google.android.material.navigation.NavigationBarView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import io.paperdb.Paper;

public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, NavigationBarView.OnItemSelectedListener {
    private ActivityHomeBinding binding;
    private ActivityHomeMvvm homeActivityMvvm;
    private Stack<Integer> stack;
    private Map<Integer, Integer> map;
    private MyPagerAdapter adapter;
    private List<Fragment> fragments;
    private UserModel userModel;
    private GeneralMvvm generalMvvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initView();


    }


    private void initView() {
        generalMvvm = ViewModelProviders.of(this).get(GeneralMvvm.class);
        userModel = getUserModel();
        fragments = new ArrayList<>();
        stack = new Stack<>();
        map = new HashMap<>();
        if (stack.isEmpty()) {
            stack.push(0);

        }

        map.put(0, R.id.home);
        map.put(1, R.id.order);
        map.put(2, R.id.settings);


        fragments.add(FragmentBaseNavigation.newInstance(R.layout.base_fragment_home, R.id.navHostFragmentHome));
        fragments.add(FragmentBaseNavigation.newInstance(R.layout.base_fragment_order, R.id.navHostFragmentOrder));
        fragments.add(FragmentBaseNavigation.newInstance(R.layout.base_fragment_profile, R.id.navHostFragmentProfile));

        adapter = new MyPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments, null);
        binding.pager.setAdapter(adapter);
        binding.pager.setOffscreenPageLimit(fragments.size());
        binding.pager.addOnPageChangeListener(this);
        binding.bottomNavigationView.setOnItemSelectedListener(this);

        homeActivityMvvm = ViewModelProviders.of(this).get(ActivityHomeMvvm.class);


        homeActivityMvvm.firebase.observe(this, token -> {
            if (getUserModel() != null) {
                userModel = getUserModel();
                userModel.getData().setFirebase_token(token);
                setUserModel(userModel);
            }
        });


        if (userModel != null) {
            homeActivityMvvm.updateFirebase(this, userModel);
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
    }


    public void refreshActivity(String lang) {
        Paper.book().write("lang", lang);
        Language.setNewLocale(this, lang);
        new Handler()
                .postDelayed(() -> {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }, 500);


    }


//    public void updateFirebase() {
//        if (getUserModel() != null) {
//             homeActivityMvvm.updateFirebase(this, getUserModel());
//        }
//    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int itemId = map.get(position);
        if (itemId != binding.bottomNavigationView.getSelectedItemId()) {
            binding.bottomNavigationView.setSelectedItemId(itemId);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemPos = getItemPos(item.getItemId());
        if (itemPos != binding.pager.getCurrentItem()) {
            setItemPos(itemPos);
        }
        return true;
    }

    private void setItemPos(int pos) {
        binding.pager.setCurrentItem(pos);
        stack.push(pos);
    }

    private int getItemPos(int item_id) {
        for (int pos : map.keySet()) {
            if (map.get(pos) == item_id) {
                return pos;
            }
        }
        return 0;
    }


    @Override
    public void onBackPressed() {
        FragmentBaseNavigation fragmentBaseNavigation = (FragmentBaseNavigation) adapter.getItem(binding.pager.getCurrentItem());
        if (!fragmentBaseNavigation.onBackPress()) {
            if (stack.size() > 1) {
                stack.pop();
                binding.pager.setCurrentItem(stack.peek());
            } else {
                super.onBackPressed();
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOrderStatusChanged(NotiFire model) {
        if (!model.getOrder_status().isEmpty()) {
            String status = model.getOrder_status();
            if (status.equals("new")) {
                generalMvvm.getOnCurrentOrderRefreshed().setValue(true);
            } else {
                generalMvvm.getOnCurrentOrderRefreshed().setValue(true);
                if (model.getOrder_status().equals("delivered")) {
                    generalMvvm.getOnPreOrderRefreshed().setValue(true);
                }

            }
            generalMvvm.getOnStaticRefreshed().setValue(true);
        }
    }
}
