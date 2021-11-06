package com.lesorin.firespark.presenter;

/**
 * A Factory which can create different types of presenters.
 */
public class PresenterFactory
{
    /**
     *
     * @return A @{@link MainContract.PresenterView}.
     */
    public static MainContract.PresenterView getMainPresenter()
    {
        return new MainPresenter();
    }

    /**
     *
     * @return A @{@link StartContract.PresenterView}.
     */
    public static StartContract.PresenterView getStartPresenter()
    {
        return new StartPresenter();
    }
}