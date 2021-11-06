package com.lesorin.firespark.model;

import android.content.Context;
import com.lesorin.firespark.presenter.MainContract;
import com.lesorin.firespark.presenter.StartContract;

/**
 * A Factory which can create different types of models.
 */
public class ModelFactory
{
    /**
     *
     * @return A Firebase @{@link MainContract.Model}.
     */
    public static MainContract.Model getFirebaseMainModel()
    {
        return new com.lesorin.firespark.model.firebase.MainModel();
    }

    /**
     *
     * @return A Firebase @{@link StartContract.Model}.
     */
    public static StartContract.Model getFirebaseStartModel()
    {
        return new com.lesorin.firespark.model.firebase.StartModel();
    }

    /**
     *
     * @param context A context required to instantiate the model.
     * @return A REST @{@link MainContract.Model}.
     */
    public static MainContract.Model getRESTMainModel(Context context)
    {
        return new com.lesorin.firespark.model.rest.MainModel(context);
    }

    /**
     *
     * @param context A context required to instantiate the model.
     * @return A REST @{@link StartContract.Model}.
     */
    public static StartContract.Model getRESTStartModel(Context context)
    {
        return new com.lesorin.firespark.model.rest.StartModel(context);
    }
}