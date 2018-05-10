package com.liferay.myapplication.viewsets;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.GeoLocation;
import com.liferay.mobile.screens.ddl.model.GeolocationField;
import com.liferay.myapplication.R;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

/**
 * @author Víctor Galán Grande
 */

public class DDLFieldGeoViewDetail extends LinearLayout
	implements DDLFieldViewModel<GeolocationField>, View.OnClickListener {

	private GeolocationField geoField;
	private DecimalFormat df = new DecimalFormat("0.0000");
	private View parentView;
	private TextView locationTextView;
	private TextView labelTextView;

	public DDLFieldGeoViewDetail(Context context) {
		super(context);
	}

	public DDLFieldGeoViewDetail(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public DDLFieldGeoViewDetail(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public DDLFieldGeoViewDetail(Context context, AttributeSet attrs, int defStyleAttr,
		int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		locationTextView = findViewById(R.id.location);

		labelTextView = findViewById(R.id.liferay_ddl_label);

		findViewById(R.id.go_to_maps).setOnClickListener(this);
	}

	@Override
	public GeolocationField getField() {
		return geoField;
	}

	@Override
	public void setField(GeolocationField field) {
		geoField = field;

		if (this.geoField.isShowLabel()) {
			if (labelTextView != null) {
				labelTextView.setText(this.geoField.getLabel());
				labelTextView.setVisibility(VISIBLE);
			}
		}
		setCurrentLocation();
	}

	@Override
	public void refresh() {
		setCurrentLocation();
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
	public void onClick(View v) {
		GeoLocation geoLocation = geoField.getCurrentValue();

		if (geoLocation != null) {
			String uri =
				String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f", geoLocation.getLatitude(),
					geoLocation.getLongitude(), geoLocation.getLatitude(),
					geoLocation.getLongitude());
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			getContext().startActivity(intent);
		}
	}

	private void setCurrentLocation() {

		GeoLocation geoLocation = geoField.getCurrentValue();

		if (geoLocation != null) {
			Geocoder geocoder = new Geocoder(getContext());

			try {
				List<Address> addresses =
					geocoder.getFromLocation(geoLocation.getLatitude(), geoLocation.getLongitude(),
						1);

				locationTextView.setText(buildAddressString(addresses.get(0)));
			} catch (IOException e) {
				String location =
					String.format(Locale.ENGLISH, "%s, %s", df.format(geoLocation.getLatitude()),
						df.format(geoLocation.getLongitude()));

				locationTextView.setText(location);
			}
		}
	}

	private String buildAddressString(Address address) {
		String street = address.getAddressLine(0);
		String locality = address.getLocality();
		String country = address.getCountryName();

		String addressString = "";

		if (street != null) {
			addressString += street + ", ";
		}

		if (locality != null) {
			addressString += locality + ", ";
		}

		if (country != null) {
			addressString += country;
		}

		return addressString;
	}
}
