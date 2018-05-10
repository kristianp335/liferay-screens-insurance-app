package com.liferay.myapplication.viewsets;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.myapplication.R;
import java.util.List;

/**
 * @author Víctor Galán Grande
 */

public class CustomDDLListView
	extends BaseListScreenletView<Record, CustomDDLListAdapter.ViewHolder, CustomDDLListAdapter> {

	private SwipeRefreshLayout swipeRefreshLayout;

	public CustomDDLListView(Context context) {
		super(context);
	}

	public CustomDDLListView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public CustomDDLListView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected CustomDDLListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new CustomDDLListAdapter(itemLayoutId, itemProgressLayoutId, this, getContext());
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.insurance_list_item;
	}

	@Override
	protected RecyclerView.ItemDecoration getDividerDecoration() {
		return null;
	}

	@Override
	public void showStartOperation(String actionName) {
		super.showStartOperation(actionName);
		swipeRefreshLayout.setRefreshing(true);
	}

	@Override
	public void showFinishOperation(int startRow, int endRow, Exception e) {
		super.showFinishOperation(startRow, endRow, e);

		swipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void showFinishOperation(int startRow, int endRow, List<Record> serverEntries,
		int totalRowCount) {
		super.showFinishOperation(startRow, endRow, serverEntries, totalRowCount);

		swipeRefreshLayout.setRefreshing(false);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		swipeRefreshLayout = findViewById(R.id.swipe_refresh);

		swipeRefreshLayout.setOnRefreshListener(
			() -> ((BaseListScreenlet) getScreenlet()).loadPage(0));
	}
}
