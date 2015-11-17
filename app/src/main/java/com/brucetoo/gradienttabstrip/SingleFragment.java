package com.brucetoo.gradienttabstrip;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Bruce Too
 * On 8/19/15.
 * At 19:15
 */
public class SingleFragment extends Fragment {

    private ColorGradientView viewColor;
    private GradientTextView text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.text_layout,null);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewColor = (ColorGradientView) view.findViewById(R.id.view);
        text = (GradientTextView) view.findViewById(R.id.text);
        view.findViewById(R.id.left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewColor.setmDirection(0);
                ObjectAnimator.ofFloat(viewColor, "offset", 0, 1).setDuration(2000)
                        .start();
                text.setDirection(0);
                ObjectAnimator.ofFloat(text, "offset", 0, 1).setDuration(2000)
                        .start();

            }
        });

        view.findViewById(R.id.right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewColor.setmDirection(1);
                ObjectAnimator.ofFloat(viewColor, "offset", 0, 1).setDuration(2000)
                        .start();

                text.setDirection(1);
                ObjectAnimator.ofFloat(text, "offset", 0, 1).setDuration(2000)
                        .start();
            }
        });


    }
}
