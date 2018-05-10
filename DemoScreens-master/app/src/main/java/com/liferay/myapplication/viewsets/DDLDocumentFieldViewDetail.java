package com.liferay.myapplication.viewsets;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liferay.mobile.screens.context.PicassoScreens;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.DocumentRemoteFile;
import com.liferay.myapplication.R;
import com.liferay.myapplication.activities.ImageDetailActivity;

import static com.liferay.myapplication.activities.ImageDetailActivity.URL_EXTRA;

/**
 * @author Javier Gamarra
 */
public class DDLDocumentFieldViewDetail extends LinearLayout
	implements DDLFieldViewModel<DocumentField> {

	private DocumentField field;
	private ImageView previewImage;
	private View parentView;
	private TextView labelTextView;

	public DDLDocumentFieldViewDetail(@NonNull Context context) {
		super(context);
	}

	public DDLDocumentFieldViewDetail(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public DDLDocumentFieldViewDetail(@NonNull Context context, @Nullable AttributeSet attrs,
		int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public DDLDocumentFieldViewDetail(@NonNull Context context, @Nullable AttributeSet attrs,
		int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public DocumentField getField() {
		return field;
	}

	@Override
	public void setField(DocumentField field) {
		this.field = field;

		if (this.field.isShowLabel()) {
			if (labelTextView != null) {
				labelTextView.setText(this.field.getLabel());
				labelTextView.setVisibility(VISIBLE);
			}
		} else {
			if (labelTextView != null) {
				labelTextView.setVisibility(GONE);
			}
		}
	}

	@Override
	public void refresh() {
		if (field != null) {
			if (field.getCurrentValue() != null) {
				previewImage.setVisibility(VISIBLE);
				PicassoScreens.load(buildRemoteFieldUrl()).into(previewImage);
			} else {
				labelTextView.setVisibility(GONE);
			}
		}
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
		this.parentView = view;
	}



	@Override
	public void setUpdateMode(boolean enabled) {

	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		labelTextView = findViewById(com.liferay.mobile.screens.R.id.liferay_ddl_label);
		previewImage = findViewById(R.id.preview_image);

		previewImage.setOnClickListener(v -> {
			Intent intent = new Intent(getContext(), ImageDetailActivity.class);
			intent.putExtra(URL_EXTRA, buildRemoteFieldUrl());

			getContext().startActivity(intent);
		});
	}

	private String buildRemoteFieldUrl() {
		return ((DocumentRemoteFile) field.getCurrentValue()).getUrl();
	}
}
