package com.lesorin.firespark.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.lesorin.firespark.R;
import com.lesorin.firespark.view.activities.MainActivity;

public class SendSparkFragment extends FiresparkFragmentAdapter
{
    private View _view;
    private LinearLayout _upToSend;
    private ConstraintLayout _sparkLayout;
    private float _differenceY;
    private TextInputEditText _sparkBody;

    public SendSparkFragment(MainActivity activity)
    {
        super(activity);

        _view = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(_view == null)
        {
            _view = inflater.inflate(R.layout.fragment_send_spark, container, false);

            initializeSparkLayout();
            initializeUpToSend();
        }

        return _view;
    }

    private void initializeSparkLayout()
    {
        _sparkLayout = _view.findViewById(R.id.SparkLayout);
        _sparkBody = _view.findViewById(R.id.SparkBody);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeUpToSend()
    {
        _upToSend = _view.findViewById(R.id.UpToSend);

        _upToSend.setOnTouchListener((view, event) ->
        {
            if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE)
            {
                //Action down initializes some data.
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    _differenceY = event.getY();
                }

                //Action move moves the view around.
                if(event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    final int parentTop = (int)_view.getY();
                    final int parentHeight = _view.getHeight();
                    final int oldSparkY = (int)_sparkLayout.getY();
                    int newSparkY = (int)(oldSparkY + (event.getY() - _differenceY));

                    if(newSparkY - parentTop < 40)
                        newSparkY = parentTop;

                    if(newSparkY < parentTop)
                        newSparkY = parentTop;

                    if(newSparkY + _sparkLayout.getHeight() > parentTop + parentHeight)
                        newSparkY = parentTop + parentHeight - _sparkLayout.getHeight();

                    _sparkLayout.setY(newSparkY);
                }
            }

            if(event.getAction() == MotionEvent.ACTION_UP)
            {
                if((int)_sparkLayout.getY() == (int)_view.getY())
                {
                    _activity.sendSparkClicked(_sparkBody.getText().toString());
                }
            }

            return true;
        });
    }

    public void resetSparkPosition()
    {
        if(_view != null)
        {
            _sparkLayout.setY(_view.getHeight() / 2f - (_sparkLayout.getHeight() / 2f));
        }
    }

    public void emptySparkBody()
    {
        if(_view != null)
        {
            _sparkBody.setText("");
        }
    }

    @Override
    public void displayElements()
    {
        resetSparkPosition();
        emptySparkBody();
    }
}