package com.app.dbrah_Provider.model;

import java.io.Serializable;
import java.util.List;

public class RecentProductDataModel extends StatusResponse implements Serializable {
    private List<ProductModel> data;

    public List<ProductModel> getData() {
        return data;
    }


}
