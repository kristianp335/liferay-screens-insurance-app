package com.liferay.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.myapplication.R;
import com.liferay.myapplication.activities.IncidenceDetailActivity;
import java.util.Map;
import org.json.JSONObject;

import static com.liferay.myapplication.activities.IncidenceDetailActivity.COMING_FROM_INCIDENCE;
import static com.liferay.myapplication.activities.IncidenceDetailActivity.EXTRA_RECORD_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportIncidenceFragment extends Fragment implements DDLFormListener {

	private DDLFormScreenlet screenlet;

	public ReportIncidenceFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_report_incidence, container, false);

		screenlet = view.findViewById(R.id.report_incidence_form);

		screenlet.setListener(this);

		return view;
	}

	@Override
	public void onDDLFormLoaded(Record record) {

	}

	@Override
	public void onDDLFormRecordLoaded(Record record, Map<String, Object> valuesAndAttributes) {

	}

	@Override
	public void onDDLFormRecordAdded(Record record) {
		Intent intent = new Intent(getContext(), IncidenceDetailActivity.class);
		intent.putExtra(EXTRA_RECORD_ID, record.getRecordId());
		intent.putExtra(COMING_FROM_INCIDENCE, true);

		startActivity(intent);
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
