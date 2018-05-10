package com.liferay.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.liferay.myapplication.R;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

	private final Queue<LatLng> positions = new LinkedList<LatLng>() {{
		add(new LatLng(40.407271150293575, -3.6932516098022456));
		add(new LatLng(40.406568566432306, -3.6933374404907227));
		add(new LatLng(40.40604570856648, -3.693358898162842));
		add(new LatLng(40.405359451453656, -3.6934447288513184));
		add(new LatLng(40.40478756518154, -3.693530559539795));
		add(new LatLng(40.404182994410526, -3.693573474884033));
		add(new LatLng(40.40366011801344, -3.69363784790039));
		add(new LatLng(40.403055537116174, -3.6936807632446285));
		add(new LatLng(40.40241827029216, -3.693766593933105));
		add(new LatLng(40.401813678241375, -3.693873882293701));
		add(new LatLng(40.401413337273624, -3.6938953399658203));
		add(new LatLng(40.40094763151963, -3.6939489841461177));
		add(new LatLng(40.40050643362298, -3.694013357162475));
		add(new LatLng(40.40011425528743, -3.6940455436706543));
		add(new LatLng(40.399534153977186, -3.694099187850952));
		add(new LatLng(40.39915831102332, -3.6941313743591304));
		add(new LatLng(40.39854551909925, -3.694206476211548));
		add(new LatLng(40.397957233604714, -3.6942815780639653));
		add(new LatLng(40.397385284445804, -3.6943566799163814));
		add(new LatLng(40.3969604047825, -3.6943995952606197));
		add(new LatLng(40.39644564313691, -3.6944854259490962));
		add(new LatLng(40.39599624483187, -3.6945068836212163));
		add(new LatLng(40.39546513387702, -3.6945819854736324));
		add(new LatLng(40.395113781250636, -3.6946034431457515));
		add(new LatLng(40.394697058317, -3.69464635848999));
		add(new LatLng(40.394231306103876, -3.694678544998169));
		add(new LatLng(40.39374920831472, -3.6947429180145264));
		add(new LatLng(40.3931772234101, -3.6948609352111816));
		add(new LatLng(40.39266243284184, -3.694925308227539));
		add(new LatLng(40.39220483792181, -3.6949682235717773));
		add(new LatLng(40.39169821134756, -3.6950433254241943));
		add(new LatLng(40.39120792393698, -3.695118427276611));
		add(new LatLng(40.39076666221554, -3.6955583095550537));
		add(new LatLng(40.39047248612828, -3.6961162090301514));
		add(new LatLng(40.39016196553099, -3.6965560913085933));
		add(new LatLng(40.389843271850225, -3.6969852447509766));
		add(new LatLng(40.38961446520066, -3.6974143981933594));
		add(new LatLng(40.389287597209965, -3.697843551635742));
		add(new LatLng(40.38899341466191, -3.6982083320617676));
		add(new LatLng(40.38877277690759, -3.6986052989959717));
		add(new LatLng(40.38847859211076, -3.698970079421997));
		add(new LatLng(40.388176234175, -3.6992704868316646));
		add(new LatLng(40.38833149923047, -3.6996567249298096));
		add(new LatLng(40.388609341068005, -3.6999785900115967));
		add(new LatLng(40.388903525293685, -3.7003004550933833));
		add(new LatLng(40.389254910323636, -3.700686693191528));
		add(new LatLng(40.38951640497003, -3.7010085582733154));
		add(new LatLng(40.39006390609754, -3.701748847961426));
	}};

	private static final int DELAY = 500;

	private Marker craneMarker;
	private Handler handler = new Handler(Looper.getMainLooper());
	private Runnable craneMovingRunnable = new Runnable() {
		@Override
		public void run() {
			if (positions.isEmpty()) {
				showHelpArrivedDialog();
				return;
			}

			LatLng nextPosition = positions.poll();
			craneMarker.setPosition(nextPosition);

			handler.postDelayed(this, DELAY);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		SupportMapFragment mapFragment =
			(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);

		mapFragment.getMapAsync(this);
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		LatLng centerLocation = new LatLng(40.389574, -3.702393);
		LatLng craneLocation = new LatLng(40.407031, -3.693810);

		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerLocation, 13.5f));
		googleMap.getUiSettings().setMapToolbarEnabled(false);

		googleMap.addMarker(new MarkerOptions().position(centerLocation)
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));

		craneMarker = googleMap.addMarker(new MarkerOptions().position(craneLocation)
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.crane)));

		googleMap.setOnMapLoadedCallback(() -> handler.postDelayed(craneMovingRunnable, DELAY));
	}

	private void showHelpArrivedDialog() {

		MaterialDialog dialog = new MaterialDialog.Builder(this).title(R.string.help_arrived)
			.content(R.string.inform)
			.positiveText(android.R.string.ok)
			.onPositive((dialog1, which) -> {
				launchLoginActivity();
			})
			.cancelable(false)
			.build();

		dialog.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		craneMarker = null;

		handler.removeCallbacks(craneMovingRunnable);
	}

	@Override
	public void onBackPressed() {
		launchLoginActivity();
		super.onBackPressed();
	}

	private void launchLoginActivity() {
		Intent intent = new Intent(this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}
}
