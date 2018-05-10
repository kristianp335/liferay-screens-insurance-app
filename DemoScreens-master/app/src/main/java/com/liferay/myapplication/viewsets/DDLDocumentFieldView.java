package com.liferay.myapplication.viewsets;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity;
import com.liferay.mobile.screens.base.MediaStoreSelectorDialog;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.PicassoScreens;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.DocumentFile;
import com.liferay.mobile.screens.ddl.model.DocumentLocalFile;
import com.liferay.mobile.screens.ddl.model.DocumentRemoteFile;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.DDLFormView;
import com.liferay.myapplication.R;
import com.squareup.picasso.Picasso;
import rx.functions.Action1;

import static com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity.SELECT_IMAGE_FROM_GALLERY;
import static com.liferay.mobile.screens.base.MediaStoreRequestShadowActivity.TAKE_PICTURE_WITH_CAMERA;

/**
 * @author Javier Gamarra
 */
public class DDLDocumentFieldView
	extends com.liferay.mobile.screens.viewsets.material.ddl.form.fields.DDLDocumentFieldView
	implements DDLFieldViewModel<DocumentField>, View.OnClickListener {

	protected ProgressBar progressBar;
	protected AlertDialog choseOriginDialog;
	protected AlertDialog fileDialog;
	private int positionInForm;
	private ImageView previewImage;

	public DDLDocumentFieldView(Context context) {
		super(context);
	}

	public DDLDocumentFieldView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public DDLDocumentFieldView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void onClick(final View view) {
		if (view.getId() == com.liferay.mobile.screens.R.id.liferay_ddl_edit_text) {
			choseOriginDialog = createOriginDialog();
			choseOriginDialog.show();
		}
	}


	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		// Avoid WindowLeak error on orientation changes
		if (choseOriginDialog != null) {
			choseOriginDialog.dismiss();
			choseOriginDialog = null;
		}
	}

	@Override
	public void refresh() {
		super.refresh();
		DocumentFile documentFile = getField().getCurrentValue();

		if (documentFile != null) {

			if (documentFile instanceof DocumentLocalFile) {
				Picasso.with(getContext())
					.load(Uri.parse(documentFile.toString()))
					.into(previewImage);
			}

			if (documentFile instanceof DocumentRemoteFile) {
				PicassoScreens.load(buildRemoteFieldUrl((DocumentRemoteFile) documentFile))
					.into(previewImage);
			}
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		previewImage = findViewById(R.id.preview_image);

		progressBar = findViewById(com.liferay.mobile.screens.R.id.liferay_document_progress);
		getTextEditText().setOnClickListener(this);
	}

	@Override
	protected void onTextChanged(String text) {

	}

	protected AlertDialog createOriginDialog() {
		Activity activity = LiferayScreensContext.getActivityFromContext(getContext());

		return new MediaStoreSelectorDialog().createOriginDialog(activity,
			launchCamera(TAKE_PICTURE_WITH_CAMERA), launchCamera(SELECT_IMAGE_FROM_GALLERY), null);
	}

	protected ProgressBar getProgressBar() {
		return progressBar;
	}

	@NonNull
	private Action1<Boolean> launchCamera(final int mediaStore) {
		return result -> {
			if (result) {
				MediaStoreRequestShadowActivity.show(getContext(), mediaStore,
					DDLDocumentFieldView.this::startUpload);
			}
			choseOriginDialog.dismiss();
		};
	}

	private void startUpload(Uri uri) {
		getField().createLocalFile(uri.toString());
		getTextEditText().setText(uri.getPath());
		((DDLFormView) getParentView()).startUploadField(getField());
	}

	private String buildRemoteFieldUrl(DocumentRemoteFile remoteFile) {
		return remoteFile.getUrl();
	}
}
