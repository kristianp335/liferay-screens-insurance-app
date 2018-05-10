package com.liferay.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.myapplication.R;
import java.util.Map;
import org.json.JSONObject;

public class IncidenceDetailActivity extends AppCompatActivity implements DDLFormListener {

	public static final String EXTRA_RECORD_ID = "EXTRA_RECORD_ID";
	public static final String COMING_FROM_INCIDENCE = "COMING_FROM_INCIDENCE";

	private boolean shouldRecreateMainActivity;
	private DDLFormScreenlet screenlet;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_incidence_detail);

		customizeToolbar();

		long recordId = getIntent().getLongExtra(EXTRA_RECORD_ID, 0);
		shouldRecreateMainActivity = getIntent().getBooleanExtra(COMING_FROM_INCIDENCE, false);

		screenlet = findViewById(R.id.ddlform);
		screenlet.setListener(this);
		screenlet.setRecordId(recordId);

		progressBar = findViewById(R.id.progress_bar);
	}

	private void customizeToolbar() {
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Issue");

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (shouldRecreateMainActivity) {
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDDLFormLoaded(Record record) {
		screenlet.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.GONE);
	}

	@Override
	public void onDDLFormRecordLoaded(Record record, Map<String, Object> valuesAndAttributes) {

	}

	@Override
	public void onDDLFormRecordAdded(Record record) {

	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {

	}

	@Override
	public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {

	}

	@Override
	public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {

	}

	@Override
	public void error(Exception e, String userAction) {

	}
}
