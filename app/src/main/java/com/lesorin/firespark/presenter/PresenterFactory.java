package com.lesorin.firespark.presenter;

public class PresenterFactory
{
    public static MainActivityContract.PresenterView getMainPresenter()
    {
        return new MainActivityPresenter();
    }

    public static StartActivityContract.PresenterView getStartPresenter()
    {
        return new StartActivityPresenter();
    }
}