package com.liferay.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.myapplication.R;
import com.liferay.myapplication.activities.IncidenceDetailActivity;
import java.util.List;

import static com.liferay.myapplication.activities.IncidenceDetailActivity.EXTRA_RECORD_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncidencesFragment extends Fragment implements BaseListListener<Record> {

	public IncidencesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_incidences, container, false);

		((DDLListScreenlet) view.findViewById(R.id.incidence_list_screenlet)).setListener(this);

		return view;
	}

	@Override
	public void onListPageFailed(int startRow, Exception e) {

	}

	@Override
	public void onListPageReceived(int startRow, int endRow, List<Record> entries, int rowCount) {

	}

	@Override
	public void onListItemSelected(Record record, View view) {
		Intent intent = new Intent(getContext(), IncidenceDetailActivity.class);
		intent.putExtra(EXTRA_RECORD_ID, record.getRecordId());

		startActivity(intent);
	}

	@Override
	public void error(Exception e, String userAction) {

	}
}
