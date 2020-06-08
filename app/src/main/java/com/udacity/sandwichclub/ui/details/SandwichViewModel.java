package com.udacity.sandwichclub.ui.details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;


import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.AppExecutors;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class SandwichViewModel extends AndroidViewModel {

    private final Context mContext;

    private final MutableLiveData<Sandwich> mSandwich = new MutableLiveData<>();

    public SandwichViewModel(@NonNull Application application, final int position) {
        super(application);

        mContext = application.getApplicationContext();
        AppExecutors mExecutors = AppExecutors.getInstance();

        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                String[] sandwiches = mContext.getResources().getStringArray(R.array.sandwich_details);

                Sandwich sandwich = null;
                try {
                    sandwich = JsonUtils.parseSandwichJson(sandwiches[position]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (sandwich != null) {
                    mSandwich.postValue(sandwich);
                }
            }
        });
    }

    public LiveData<Sandwich> getSandwich() {
        return mSandwich;
    }
}
