package com.app.dbrah_Provider.mvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.app.dbrah_Provider.model.NotificationDataModel;
import com.app.dbrah_Provider.model.NotificationModel;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.remote.Api;
import com.app.dbrah_Provider.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityNotificationMvvm extends AndroidViewModel {
    private static final String TAG = "ActivityNotiMvvm";
    private Context context;

    private MutableLiveData<List<NotificationModel>> notificationLiveData;
    private MutableLiveData<Boolean> isLoadingLivData;

    private CompositeDisposable disposable = new CompositeDisposable();


    public ActivityNotificationMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<List<NotificationModel>> getNotification() {
        if (notificationLiveData == null) {
            notificationLiveData = new MutableLiveData<>();
        }
        return notificationLiveData;
    }


    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoadingLivData == null) {
            isLoadingLivData = new MutableLiveData<>();
        }
        return isLoadingLivData;
    }

    //_________________________hitting api_________________________________

    public void getNotifications(UserModel userModel, String lang) {
        isLoadingLivData.setValue(true);

        Api.getService(Tags.base_url)
                .getNotifications(userModel.getData().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<NotificationDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<NotificationDataModel> response) {
                        isLoadingLivData.setValue(false);
                        Log.e("status",response.code()+"");
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {
                                    notificationLiveData.setValue(response.body().getData());
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: ", e);
                    }
                });

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

    }

}
