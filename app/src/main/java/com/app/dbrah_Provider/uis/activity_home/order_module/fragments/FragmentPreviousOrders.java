package com.app.dbrah_Provider.uis.activity_home.order_module.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.dbrah_Provider.R;

import com.app.dbrah_Provider.adapter.OrderAdapter;
import com.app.dbrah_Provider.databinding.FragmentOrdersBinding;
import com.app.dbrah_Provider.model.OrderDataModel;
import com.app.dbrah_Provider.model.OrderModel;
import com.app.dbrah_Provider.mvvm.FragmentOrderMvvm;
import com.app.dbrah_Provider.mvvm.GeneralMvvm;
import com.app.dbrah_Provider.uis.activity_base.BaseFragment;
import com.app.dbrah_Provider.uis.activity_current_prev_order_details.CurrentPreviousOrderDetailsActivity;
import com.app.dbrah_Provider.uis.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;


public class FragmentPreviousOrders extends BaseFragment {
    private FragmentOrdersBinding binding;
    private HomeActivity activity;
    private OrderAdapter orderAdapter;
    private FragmentOrderMvvm fragmentOrderMvvm;
    private GeneralMvvm generalMvvm;


    public static FragmentPreviousOrders newInstance() {
        FragmentPreviousOrders fragment = new FragmentPreviousOrders();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        fragmentOrderMvvm= ViewModelProviders.of(this).get(FragmentOrderMvvm.class);
        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);

        generalMvvm.getOnPreOrderRefreshed().observe(activity, isRefreshed -> {
            if (isRefreshed) {
                fragmentOrderMvvm.getOrders(getUserModel());
            }
        });
        orderAdapter = new OrderAdapter( activity, this,getLang());
        binding.recViewOrders.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewOrders.setAdapter(orderAdapter);
        fragmentOrderMvvm.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.swipeRefresh.setRefreshing(aBoolean);

            }
        });

        fragmentOrderMvvm.getMutableLiveData().observe(this, new Observer<OrderDataModel.Data>() {
            @Override
            public void onChanged(OrderDataModel.Data data) {
                if(data!=null){
                    if(data.getPrevious()!=null&&data.getPrevious().size()>0){
                        orderAdapter.updateList(data.getPrevious());
                        binding.tvNoData.setVisibility(View.GONE);


                    }
                    else {
                        orderAdapter.updateList(new ArrayList<>());
                        binding.tvNoData.setVisibility(View.VISIBLE);

                    }
                }
            }
        });
        fragmentOrderMvvm.getOrders(getUserModel());
        binding.swipeRefresh.setOnRefreshListener(() -> fragmentOrderMvvm.getOrders(getUserModel()));

    }

    public void navigateToDetails(OrderModel orderModel) {
        Intent intent=new Intent(activity, CurrentPreviousOrderDetailsActivity.class);
        intent.putExtra("order_id",orderModel.getId());
        startActivity(intent);
    }
}