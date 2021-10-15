package com.lesorin.firespark.model;

import com.lesorin.firespark.presenter.MainActivityContract;
import com.lesorin.firespark.presenter.StartActivityContract;

public class ModelFactory
{
    public static MainActivityContract.Model getMainModel()
    {
        return new MainActivityModel();
    }

    public static StartActivityContract.Model getStartModel()
    {
        return new StartActivityModel();
    }
}