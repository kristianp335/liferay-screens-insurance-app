package com.liferay.myapplication.viewsets;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.myapplication.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class DDLFormPagerCustom
	extends com.liferay.mobile.screens.viewsets.material.ddl.form.DDLFormView {

	private ViewPager pager;
	private int currentFieldIndex = 0;
	private Button backButton;
	private ProgressDialog progressDialog;

	public DDLFormPagerCustom(Context context) {
		super(context);
	}

	public DDLFormPagerCustom(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	@Override
	public void showFormFields(Record record) {
		List<Field.EditorType> editorTypes = Field.EditorType.all();

		Map<Field.EditorType, Integer> layoutIds = new HashMap<>();
		for (Field.EditorType editorType : editorTypes) {
			layoutIds.put(editorType, getFieldLayoutId(editorType));
		}

		List<Field> fields = new ArrayList<>(record.getFieldCount());
		for (int i = 0; i < record.getFieldCount(); ++i) {
			fields.add(record.getField(i));
		}

		List<List<Field>> fieldsPaged = new ArrayList<List<Field>>() {
			{
				add(new ArrayList<Field>() {
					{
						add(fields.get(0));
						add(fields.get(1));
					}
				});

				add(new ArrayList<Field>() {
					{
                        add(fields.get(2));
                        add(fields.get(3));
					}
				});

				add(new ArrayList<Field>() {
					{
						add(fields.get(4));
						add(fields.get(5));
					}
				});

				add(new ArrayList<Field>() {
					{
						add(fields.get(6));
						add(fields.get(7));
					}
				});


				add(new ArrayList<Field>() {
					{

						add(fields.get(8));
						add(fields.get(9));
					}
				});

			}
		};

		pager.setAdapter(new DDLFormPagerViewAdapterCustom(fieldsPaged, layoutIds,
			getContext().getApplicationContext()));
		pager.setOffscreenPageLimit(fields.size());
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		pager = findViewById(R.id.liferay_pager);

		backButton = findViewById(R.id.liferay_form_back);

		backButton.setOnClickListener(this);

		progressDialog = new ProgressDialog(getContext());
		progressDialog.setMessage("Submitting form");
		progressDialog.setCancelable(false);
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.liferay_form_submit
			&& currentFieldIndex == pager.getAdapter().getCount() - 1) {
			super.onClick(view);
			return;
		}

		if (view.getId() == R.id.liferay_form_submit) {
			if (currentFieldIndex == pager.getAdapter().getCount() - 2) {
				submitButton.setText(getContext().getString(R.string.submit));
			}

			currentFieldIndex++;
		} else if (view.getId() == R.id.liferay_form_back) {
			submitButton.setText(getContext().getString(R.string.next));
			if (currentFieldIndex != 0) {
				currentFieldIndex--;
			}
		}

		pager.setCurrentItem(currentFieldIndex, true);
	}

	protected void hideProgressBar(String actionName) {
		if (actionName.equals(DDLFormScreenlet.LOAD_FORM_ACTION)) {
			loadingFormProgressBar.setVisibility(GONE);
		} else {
			progressBar.setVisibility(GONE);
		}
	}

	@Override
	public void showStartOperation(String actionName, Object argument) {
		switch (actionName) {
			case DDLFormScreenlet.UPLOAD_DOCUMENT_ACTION:
				DocumentField documentField = (DocumentField) argument;

				findFieldView(documentField).refresh();
				break;
			case DDLFormScreenlet.LOAD_FORM_ACTION:
				LiferayLogger.i("loading DDLForm");
				loadingFormProgressBar.setVisibility(VISIBLE);
				break;
			case DDLFormScreenlet.ADD_RECORD_ACTION:
				setVisibility(GONE);

				progressDialog.show();

				break;
			default:
				progressBar.setVisibility(VISIBLE);
				break;
		}
	}

	@Override
	public void showFinishOperation(String actionName, Object argument) {
		hideProgressBar(actionName);
		switch (actionName) {
			case DDLFormScreenlet.LOAD_RECORD_ACTION:
				LiferayLogger.i("loaded record");
				showRecordValues();
				break;
			case DDLFormScreenlet.UPLOAD_DOCUMENT_ACTION:
				LiferayLogger.i("uploaded document");
				DocumentField documentField = (DocumentField) argument;

				findFieldView(documentField).refresh();
				break;
			case DDLFormScreenlet.LOAD_FORM_ACTION:
			default:
				LiferayLogger.i("loaded form");
				Record record = (Record) argument;

				showFormFields(record);
				progressDialog.hide();
				break;
		}
	}

	@Override
	public void showFailedOperation(String actionName, Exception e, Object argument) {
		hideProgressBar(actionName);
		if (actionName.equals(DDLFormScreenlet.LOAD_FORM_ACTION)) {
			LiferayLogger.e("error loading DDLForm", e);

			clearFormFields();
		} else if (actionName.equals(DDLFormScreenlet.UPLOAD_DOCUMENT_ACTION)) {
			LiferayLogger.e("error uploading", e);

			DocumentField documentField = (DocumentField) argument;

			findFieldView(documentField).refresh();
		}
	}

	@Override
	public void showValidationResults(Map<Field, Boolean> fieldResults, boolean autoscroll) {
		DDLFormPagerViewAdapterCustom adapter = (DDLFormPagerViewAdapterCustom) pager.getAdapter();
		boolean scrolled = false;
		List<List<Field>> fields = adapter.getFields();

		for (int i = 0; i < fields.size(); i++) {
			for (int j = 0; j < fields.get(i).size(); j++) {
				String tag = i + ":" + j;

				View fieldView = pager.findViewWithTag(tag);
				DDLFieldViewModel fieldViewModel = (DDLFieldViewModel) fieldView;
				boolean isFieldValid = fieldResults.get(fieldViewModel.getField());

				fieldView.clearFocus();

				fieldViewModel.onPostValidation(isFieldValid);

				if (!isFieldValid && autoscroll && !scrolled) {
					fieldView.requestFocus();
					pager.setCurrentItem(i, true);
					currentFieldIndex = i;
					if (currentFieldIndex != pager.getAdapter().getCount() - 1) {
						submitButton.setText(getContext().getString(R.string.next));
					}
					scrolled = true;
				}
			}
		}
	}

	private DDLFieldViewModel findFieldView(Field field) {
		DDLFormPagerViewAdapterCustom adapter = (DDLFormPagerViewAdapterCustom) pager.getAdapter();
		List<List<Field>> fields = adapter.getFields();

		for (int i = 0; i < fields.size(); i++) {
			for (int j = 0; j < fields.get(i).size(); j++) {
				String tag = i + ":" + j;
				View fieldView = pager.findViewWithTag(tag);
				DDLFieldViewModel fieldViewModel = (DDLFieldViewModel) fieldView;

				if (field.equals(fieldViewModel.getField())) {
					return fieldViewModel;
				}
			}
		}
		return null;
	}
}