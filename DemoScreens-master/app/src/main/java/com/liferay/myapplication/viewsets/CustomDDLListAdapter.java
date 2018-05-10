package com.liferay.myapplication.viewsets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.myapplication.R;

/**
 * @author Víctor Galán Grande
 */

public class CustomDDLListAdapter extends BaseListAdapter<Record, CustomDDLListAdapter.ViewHolder> {

	private Context context;

	public CustomDDLListAdapter(int layoutId, int progressLayoutId,
		BaseListAdapterListener listener, Context context) {
		super(layoutId, progressLayoutId, listener);

		this.context = context.getApplicationContext();
	}

	@NonNull
	@Override
	public ViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
		return new ViewHolder(view, listener, context);
	}

	@Override
	protected void fillHolder(Record entry, ViewHolder holder) {

		String date = entry.getServerValue("Datea8dl").toString();
		String registration = entry.getServerValue("CarRegistration").toString();

		holder.bind(date, registration);
	}

	static class ViewHolder extends BaseListAdapter.ViewHolder {

		private TextView insuranceNumberTxt;
		private TextView dateText;
		private TextView registrationText;
		private Context context;

		ViewHolder(View view, BaseListAdapterListener listener, Context context) {
			super(view, listener);

			this.context = context;
			insuranceNumberTxt = view.findViewById(R.id.incidence_number);
			dateText = view.findViewById(R.id.date);
			registrationText = view.findViewById(R.id.registration);
		}

		void bind(String date, String registration) {
			int position = getAdapterPosition() + 1;
			insuranceNumberTxt.setText(context.getString(R.string.incidence_item_text, position));
			dateText.setText(context.getString(R.string.date_item_text, date));
			registrationText.setText(
				context.getString(R.string.registration_item_text, registration));
		}
	}
}
