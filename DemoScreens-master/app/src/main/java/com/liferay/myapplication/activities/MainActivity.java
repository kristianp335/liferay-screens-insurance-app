package com.liferay.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.push.PushScreensActivity;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.myapplication.R;
import com.liferay.myapplication.fragments.ContactFragment;
import com.liferay.myapplication.fragments.IncidencesFragment;
import com.liferay.myapplication.fragments.NewsFragment;
import com.liferay.myapplication.fragments.ReportIncidenceFragment;
import org.json.JSONObject;

import static com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder.StorageType.SHARED_PREFERENCES;

public class MainActivity extends PushScreensActivity
	implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

	private DrawerLayout drawer;
	private NavigationView navigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialize();

		addMainFragment();
	}

	private void addMainFragment() {
		getSupportActionBar().setTitle(R.string.news);
		NewsFragment fragment = new NewsFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_container, fragment);
		transaction.commit();
	}

	private void initialize() {
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		drawer = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle =
			new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
				R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
	}

	@Override
	public void onClick(View view) {
		Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
			.setAction("Action", null)
			.show();
	}

	@Override
	public void onBackPressed() {
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		for (Fragment fragment : getSupportFragmentManager().getFragments()) {
			fragment.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {

		if (navigationView.getMenu().findItem(item.getItemId()).isChecked()) {
			drawer.closeDrawer(GravityCompat.START);
			return true;
		}

		switch (item.getItemId()) {
			case R.id.news:
				getSupportActionBar().setTitle(R.string.news);
				moveToFragment(new NewsFragment());
				break;
			case R.id.report_incidence:
				getSupportActionBar().setTitle(R.string.report_incidence);
				moveToFragment(new ReportIncidenceFragment());
				break;
			case R.id.incidences:
				getSupportActionBar().setTitle(R.string.incidence);
				moveToFragment(new IncidencesFragment());
				break;
			case R.id.contact:
				getSupportActionBar().setTitle(R.string.contact);
				moveToFragment(new ContactFragment());
				break;
			case R.id.exit:

				SessionContext.removeStoredCredentials(SHARED_PREFERENCES);
				SessionContext.logout();

				Intent intent = new Intent(this, LoginActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

				startActivity(intent);
				break;
			default:
				break;
		}

		item.setChecked(true);
		drawer.closeDrawer(GravityCompat.START);

		return true;
	}

	private void moveToFragment(Fragment fragment) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_container, fragment);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.commit();
	}

	@Override
	protected Session getDefaultSession() {
		return SessionContext.createSessionFromCurrentSession();
	}

	@Override
	protected void onPushNotificationReceived(JSONObject jsonObject) {
		LiferayLogger.d(jsonObject.toString());
	}

	@Override
	protected void onErrorRegisteringPush(String message, Exception e) {
		LiferayLogger.e(e.getMessage());
	}

	@Override
	protected String getSenderId() {
		return getString(R.string.sender_id);
	}
}