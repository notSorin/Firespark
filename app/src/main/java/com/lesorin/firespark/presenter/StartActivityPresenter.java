package com.lesorin.firespark.presenter;

public class StartActivityPresenter implements StartActivityContract.Presenter
{
    private final StartActivityContract.View _view;
    private final StartActivityContract.Model _model;

    public StartActivityPresenter(StartActivityContract.View view, StartActivityContract.Model model)
    {
        _view = view;
        _model = model;
    }
}