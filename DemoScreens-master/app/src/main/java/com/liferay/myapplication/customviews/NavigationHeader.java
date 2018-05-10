package com.liferay.myapplication.customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;
import com.liferay.myapplication.R;

/**
 * @author Víctor Galán Grande
 */

public class NavigationHeader extends LinearLayout {

	public NavigationHeader(Context context) {
		super(context);
	}

	public NavigationHeader(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public NavigationHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public NavigationHeader(Context context, AttributeSet attrs, int defStyleAttr,
		int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		UserPortraitScreenlet userPortraitScreenlet = findViewById(R.id.user_portrait_screenlet);
		userPortraitScreenlet.loadLoggedUserPortrait();

		TextView nameText = findViewById(R.id.name);
		TextView emailText = findViewById(R.id.email);

		User user = SessionContext.getCurrentUser();

		nameText.setText(user.getFullName());
		emailText.setText(user.getEmail());
	}
}
