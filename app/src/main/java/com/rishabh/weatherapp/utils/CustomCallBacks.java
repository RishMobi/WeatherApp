package com.rishabh.weatherapp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.rishabh.weatherapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rishabh on 22-Sep-18.
 */
public abstract class CustomCallBacks<T> implements Callback<T> {
    Context context;
    ProgressDialog mProgressDialog;

    public CustomCallBacks(Context context, boolean showProgress) {
        this.context = context;
        if (showProgress) {
            setDialog();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        t.printStackTrace();
        stopDialog();
        this.onFailure(t);
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        try {
            stopDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.onSucess(call, response);
    }

    public abstract void onSucess(Call<T> call, Response<T> response);

    public abstract void onFailure(Throwable arg0);

    private void setDialog() {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.custom_progress_dialog);
        mProgressDialog.setCancelable(false);
    }

    void stopDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {

            }
        }
    }
}
