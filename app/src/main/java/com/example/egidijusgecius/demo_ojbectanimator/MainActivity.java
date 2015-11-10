package com.example.egidijusgecius.demo_ojbectanimator;

import android.R.interpolator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {

	private View viewToAnimate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewToAnimate = findViewById(R.id.view_to_animate);

		setListeners();
	}

	@Override
	protected void onResume() {
		super.onResume();

		new Handler().post(new Runnable() {
			@Override
			public void run() {
				animate();
			}
		});
	}

	private void setListeners() {
		findViewById(R.id.animate).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				animate();
			}
		});
		findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				reset();
			}
		});
	}

	private void animate() {
		ObjectAnimator fadeOutAnim = getFadeOutAnimator();
		AnimatorSet contractAnim = getScalingAnimator(0.2f);

		AnimatorSet set = new AnimatorSet();
		set.playTogether(fadeOutAnim, contractAnim);
		set.start();
	}

	@NonNull
	private AnimatorSet getScalingAnimator(float endValue) {
		ObjectAnimator animatorX = ObjectAnimator.ofFloat(viewToAnimate, "scaleX", endValue);
		animatorX.setInterpolator(new AnimationUtils().loadInterpolator(this, interpolator.overshoot));

		ObjectAnimator animatorY = ObjectAnimator.ofFloat(viewToAnimate, "scaleY", endValue);
		animatorY.setInterpolator(new AnimationUtils().loadInterpolator(this, interpolator.overshoot));

		AnimatorSet set = new AnimatorSet();
		set.setDuration(1000);
		set.playTogether(animatorX, animatorY);

		return set;
	}

	private void reset() {
		ObjectAnimator fadeOutAnim = getFadeInAnimator();
		AnimatorSet expand = getScalingAnimator(1.0f);

		AnimatorSet set = new AnimatorSet();
		set.playTogether(fadeOutAnim, expand);
		set.start();
	}

	@NonNull
	private ObjectAnimator getFadeOutAnimator() {
		ObjectAnimator animator = ObjectAnimator.ofFloat(viewToAnimate, "alpha", 0f);
		animator.setDuration(1000);
		return animator;
	}

	@NonNull
	private ObjectAnimator getFadeInAnimator() {
		ObjectAnimator animator = ObjectAnimator.ofFloat(viewToAnimate, "alpha", 1f);
		animator.setDuration(1000);
		return animator;
	}

}
