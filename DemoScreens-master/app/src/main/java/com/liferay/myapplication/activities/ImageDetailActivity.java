package com.liferay.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.liferay.mobile.screens.context.PicassoScreens;
import com.liferay.myapplication.R;

public class ImageDetailActivity extends AppCompatActivity {

	public static final String URL_EXTRA = "URL_EXTRA";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_detail);

		String imageUrl = getIntent().getStringExtra(URL_EXTRA);
		ImageView imageView = findViewById(R.id.image);
		PicassoScreens.load(imageUrl).into(imageView);
	}
}
