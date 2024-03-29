package com.app.dbrah_Provider.model;

import androidx.lifecycle.LifecycleService;

import java.io.Serializable;
import java.util.List;

public class AddOFFerDataModel implements Serializable {
    private String provider_id;
    private String order_id;
    private String delivery_date_time_id;
    private String delivery_date_time;
    private String total_before_tax;
    private String total_price;
    private String total_tax;
    private String note;
    private String time;
    private String date;
    private OrderModel orderModel;
    private List<OfferDataModel> offer_details;

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDelivery_date_time() {
        return delivery_date_time;
    }

    public void setDelivery_date_time(String delivery_date_time) {
        this.delivery_date_time = delivery_date_time;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getTotal_before_tax() {
        return total_before_tax;
    }

    public void setTotal_before_tax(String total_before_tax) {
        this.total_before_tax = total_before_tax;
    }

    public String getTotal_tax() {
        return total_tax;
    }

    public void setTotal_tax(String total_tax) {
        this.total_tax = total_tax;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<OfferDataModel> getOffer_details() {
        return offer_details;
    }

    public void setOffer_details(List<OfferDataModel> offer_details) {
        this.offer_details = offer_details;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public String getDelivery_date_time_id() {
        return delivery_date_time_id;
    }

    public void setDelivery_date_time_id(String delivery_date_time_id) {
        this.delivery_date_time_id = delivery_date_time_id;
    }
}
