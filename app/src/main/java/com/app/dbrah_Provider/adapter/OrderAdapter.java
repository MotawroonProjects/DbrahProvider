package com.app.dbrah_Provider.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.databinding.OrderRowBinding;
import com.app.dbrah_Provider.model.OrderModel;
import com.app.dbrah_Provider.uis.activity_home.order_module.fragments.FragmentCurrentOrders;
import com.app.dbrah_Provider.uis.activity_home.order_module.fragments.FragmentNewOrders;
import com.app.dbrah_Provider.uis.activity_home.order_module.fragments.FragmentPreviousOrders;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OrderModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;
    private String lang;

    public OrderAdapter( Context context, Fragment fragment,String lang) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
        this.lang=lang;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.order_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setLang(lang);
        myHolder.binding.setModel(list.get(position));
        if(list.get(position).getIs_pin().equals("1")){
            ((MyHolder) holder).binding.imPin.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pin2));
            ((MyHolder) holder).binding.imPin.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);

        }

        else{
            ((MyHolder) holder).binding.imPin.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pin));

            ((MyHolder) holder).binding.imPin.setColorFilter(ContextCompat.getColor(context, R.color.grey7), PorterDuff.Mode.SRC_IN);

        }
        myHolder.binding.llDetails.setOnClickListener(view -> {
            if (fragment instanceof FragmentNewOrders) {
                FragmentNewOrders fragmentNew = (FragmentNewOrders) fragment;
                fragmentNew.navigateToDetails(list.get(holder.getAdapterPosition()));
            }
        });
        myHolder.binding.llpin.setOnClickListener(view -> {
            if (fragment instanceof FragmentNewOrders) {

                FragmentNewOrders fragmentNew = (FragmentNewOrders) fragment;
                fragmentNew.pinOrder(list.get(holder.getAdapterPosition()));
            }
        });
        myHolder.itemView.setOnClickListener(view -> {
           if (fragment instanceof FragmentCurrentOrders) {
                FragmentCurrentOrders fragmentCurrent = (FragmentCurrentOrders) fragment;
                fragmentCurrent.navigateToDetails(list.get(holder.getAdapterPosition()));
            } else if (fragment instanceof FragmentPreviousOrders) {
                FragmentPreviousOrders fragmentPrevious = (FragmentPreviousOrders) fragment;
                fragmentPrevious.navigateToDetails(list.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public OrderRowBinding binding;

        public MyHolder(OrderRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<OrderModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
