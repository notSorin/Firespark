package com.lesorin.firespark.view.fragments;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * A page transformer that uses alpha transparency to go from one page to the other.
 */
public class AlphaPageTransformer implements ViewPager.PageTransformer
{
    @Override
    public void transformPage(@NonNull View view, float position)
    {
        if(position <= -1f || position >= 1f)
        {
            view.setTranslationX(view.getWidth() * position);
            view.setAlpha(0f);
        }
        else if(position == 0f)
        {
            view.setTranslationX(view.getWidth() * position);
            view.setAlpha(1f);
        }
        else
        {
            view.setTranslationX(view.getWidth() * -position);
            view.setAlpha(1f - Math.abs(position));
        }
    }
}