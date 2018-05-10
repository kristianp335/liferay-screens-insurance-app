package com.liferay.myapplication.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import com.google.android.gms.maps.MapView;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.myapplication.R;
import com.liferay.myapplication.notification.SnackbarUtil;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder.StorageType.SHARED_PREFERENCES;

public class LoginActivity extends AppCompatActivity implements LoginListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		LoginScreenlet loginListener = findViewById(R.id.login_screenlet);
		loginListener.setListener(this);

		SessionContext.loadStoredCredentials(SHARED_PREFERENCES);

		if (SessionContext.isLoggedIn()) {
			goToMainActivity();
		} else {
			((EditText) findViewById(R.id.liferay_login)).setText(getString(R.string.default_user));
			((EditText) findViewById(R.id.liferay_password)).setText(
				getString(R.string.default_password));

			if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED
				&& ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED) {

				ActivityCompat.requestPermissions(this,
					new String[] { ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION }, 1);
			}
		}

		preInitializePlayServices();
	}

	private void preInitializePlayServices() {
		MapView mapView = findViewById(R.id.map);
		mapView.onCreate(null);

		mapView.getMapAsync(googleMap -> LiferayLogger.d("ready"));
	}

	@Override
	public void onLoginSuccess(User user) {
		goToMainActivity();
	}

	@Override
	public void onLoginFailure(Exception e) {
		SnackbarUtil.showMessage(this, "Login Failed");
	}

	private void goToMainActivity() {
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}