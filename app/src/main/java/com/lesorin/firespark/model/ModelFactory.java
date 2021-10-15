package com.lesorin.firespark.model;

import com.lesorin.firespark.presenter.MainContract;
import com.lesorin.firespark.presenter.StartContract;

public class ModelFactory
{
    public static MainContract.Model getMainModel()
    {
        return new MainModel();
    }

    public static StartContract.Model getStartModel()
    {
        return new StartModel();
    }
}