package com.liferay.myapplication.viewsets;

import android.content.Context;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.GeoLocation;
import com.liferay.mobile.screens.ddl.model.GeolocationField;
import com.liferay.myapplication.R;

/**
 * @author Víctor Galán Grande
 */

public class DDLFieldGeoView extends LinearLayout
	implements DDLFieldViewModel<GeolocationField>, GoogleMap.OnMapLongClickListener,
	OnMapReadyCallback {

	private GeolocationField geoField;
	private MapView mapView;
	private LocationManager locationManager;
	private GoogleMap googleMap;
	private Marker marker;
	private View parentView;

	public DDLFieldGeoView(Context context) {
		super(context);
	}

	public DDLFieldGeoView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public DDLFieldGeoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public DDLFieldGeoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		mapView = (MapView) findViewById(R.id.map);

		mapView.onCreate(null);

		mapView.getMapAsync(this);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		mapView.onResume();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		mapView.onDestroy();
	}

	@Override
	public GeolocationField getField() {
		return geoField;
	}

	@Override
	public void setField(GeolocationField field) {
		geoField = field;
	}

	@Override
	public void refresh() {

	}

	@Override
	public void onPostValidation(boolean valid) {

	}

	@Override
	public View getParentView() {
		return parentView;
	}

	@Override
	public void setParentView(View view) {
		parentView = view;
	}



	@Override
	public void setUpdateMode(boolean enabled) {

	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		this.googleMap = googleMap;

		MapsInitializer.initialize(getContext());

		float latitude = Float.parseFloat(getContext().getString(R.string.latitude));
		float longitude = Float.parseFloat(getContext().getString(R.string.longitude));

		geoField.setCurrentValue(new GeoLocation(latitude, longitude));

		LatLng location = new LatLng(latitude, longitude);

		googleMap.moveCamera(
			CameraUpdateFactory.newLatLngZoom(location, 15));

		googleMap.setOnMapLongClickListener(this);

		marker = googleMap.addMarker(new MarkerOptions().position(location));
	}

	@Override
	public void onMapLongClick(LatLng latLng) {
		marker.setPosition(latLng);

		geoField.setCurrentValue(new GeoLocation(latLng.latitude, latLng.longitude));
	}
}
