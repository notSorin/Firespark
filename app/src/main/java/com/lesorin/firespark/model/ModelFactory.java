package com.lesorin.firespark.model;

import android.content.Context;
import com.lesorin.firespark.presenter.MainContract;
import com.lesorin.firespark.presenter.StartContract;

public class ModelFactory
{
    public static MainContract.Model getFirebaseMainModel()
    {
        return new com.lesorin.firespark.model.firebase.MainModel();
    }

    public static StartContract.Model getFirebaseStartModel()
    {
        return new com.lesorin.firespark.model.firebase.StartModel();
    }

    public static MainContract.Model getRESTMainModel()
    {
        return new com.lesorin.firespark.model.rest.MainModel();
    }

    public static StartContract.Model getRESTStartModel(Context context)
    {
        return new com.lesorin.firespark.model.rest.StartModel(context);
    }
}