package com.app.dbrah_Provider.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.app.dbrah_Provider.R;
import com.app.dbrah_Provider.model.AddBankAccountModel;
import com.app.dbrah_Provider.model.CountryModel;
import com.app.dbrah_Provider.model.ForgotPasswordModel;
import com.app.dbrah_Provider.model.LoginModel;
import com.app.dbrah_Provider.model.StatusResponse;
import com.app.dbrah_Provider.model.UserModel;
import com.app.dbrah_Provider.remote.Api;
import com.app.dbrah_Provider.share.Common;
import com.app.dbrah_Provider.tags.Tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityForgotPasswordMvvm extends AndroidViewModel {
    private static final String TAG = "ActivityVerificationMvvm";
    private Context context;

    public MutableLiveData<UserModel> userModelMutableLiveData = new MutableLiveData<>();
        public MutableLiveData<Boolean> send = new MutableLiveData<>();
    private MutableLiveData<List<CountryModel>> coListMutableLiveData;
    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityForgotPasswordMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();


    }

    public MutableLiveData<UserModel> getUserModelMutableLiveData() {
        if (userModelMutableLiveData == null) {
            userModelMutableLiveData = new MutableLiveData<>();
        }
        return userModelMutableLiveData;
    }

    public MutableLiveData<List<CountryModel>> getCoListMutableLiveData() {
        if (coListMutableLiveData == null) {
            coListMutableLiveData = new MutableLiveData<>();
        }
        return coListMutableLiveData;
    }

    public void setCountry() {
        CountryModel[] countries = new CountryModel[]{
                new CountryModel("EG", "Egypt", "+20", R.drawable.flag_eg, "EGP"), new CountryModel("SA", "Saudi Arabia", "+966", R.drawable.flag_sa, "SAR")};
        coListMutableLiveData.postValue(new ArrayList<>(Arrays.asList(countries)));
    }


//    public void login(LoginModel loginModel, Context context) {
//        Log.e("llll",loginModel.getPhone_code()+"_"+loginModel.getPhone()+"_"+loginModel.getEmail()+"_"+loginModel.getPassword()+"_"+loginModel.getType()+"_"+loginModel.getPhone());
//        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
//        dialog.setCancelable(false);
//        dialog.show();
//        Api.getService(Tags.base_url).login(loginModel.getPhone_code(), loginModel.getPhone(), loginModel.getEmail(), loginModel.getPassword(),loginModel.getType())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<Response<UserModel>>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        disposable.add(d);
//                    }
//
//                    @Override
//                    public void onSuccess(@NonNull Response<UserModel> response) {
//                        dialog.dismiss();
//                        Log.e("llklk", response.code() + " "+response.body().getStatus());
//                        if (response.isSuccessful()) {
//                            if (response.body().getStatus() == 200) {
//                                userModelMutableLiveData.postValue(response.body());
//                            } else if (response.body().getStatus() == 400||response.body().getStatus() == 403) {
////                                found.postValue("not_found");
//                                Toast.makeText(context, "not found", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        dialog.dismiss();
//                        Log.e("error", e.toString());
//                    }
//                });
//    }

    public void checkuser(Context context, ForgotPasswordModel forgotPasswordModel){
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        Api.getService(Tags.base_url)
                .checkUser(forgotPasswordModel.getPhone_code(), forgotPasswordModel.getPhone(), forgotPasswordModel.getEmail(),forgotPasswordModel.getType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);

                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()){
                            if (response.body().getStatus()==200){
                                send.postValue(true);
                            }
                            else{
                                Toast.makeText(context,context.getResources().getString(R.string.user_not_found),Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(";d;d;d;",e.toString());
                        dialog.dismiss();
                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
