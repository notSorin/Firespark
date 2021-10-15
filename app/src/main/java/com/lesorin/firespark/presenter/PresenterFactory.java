package com.lesorin.firespark.presenter;

public class PresenterFactory
{
    public static MainContract.PresenterView getMainPresenter()
    {
        return new MainPresenter();
    }

    public static StartContract.PresenterView getStartPresenter()
    {
        return new StartPresenter();
    }
}