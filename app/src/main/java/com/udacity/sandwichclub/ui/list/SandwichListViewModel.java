package com.udacity.sandwichclub.ui.list;

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
import com.udacity.sandwichclub.utils.SingleLiveEvent;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SandwichListViewModel extends AndroidViewModel {

    private final Context mContext;

    private final MutableLiveData<List<Sandwich>> mObservableSandwiches;

    private final SingleLiveEvent<Integer> mOpenSandwichEvent = new SingleLiveEvent<>();

    public SandwichListViewModel(@NonNull Application application) {
        super(application);
        
        mContext = application.getApplicationContext();
        AppExecutors mExecutors = AppExecutors.getInstance();
        mObservableSandwiches = new MutableLiveData<>();
        final List<Sandwich> sandwichList = new ArrayList<>();
        
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {

                String[] sandwiches = mContext.getResources().getStringArray(R.array.sandwich_details);
                for (String sandwich : sandwiches) {
                    Sandwich sandwichItem = null;
                    try {
                        sandwichItem = JsonUtils.parseSandwichJson(sandwich);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sandwichList.add(sandwichItem);
                }

                if (!sandwichList.isEmpty()) {
                    mObservableSandwiches.postValue(sandwichList);
                }
            }
        });
    }

    public LiveData<List<Sandwich>> getSandwichList() {
        return mObservableSandwiches;
    }

    public MutableLiveData<Integer> getOpenSandwichEvent() {
        return mOpenSandwichEvent;
    }
}
