package com.liferay.myapplication.viewsets; /**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel;
import com.liferay.mobile.screens.ddl.model.Field;
import java.util.List;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class DDLFormPagerViewAdapterCustom extends PagerAdapter {

	private final List<List<Field>> fields;
	private final Map<Field.EditorType, Integer> layoutIds;
	private final Context context;

	public DDLFormPagerViewAdapterCustom(List<List<Field>> fields,
		Map<Field.EditorType, Integer> layoutIds, Context context) {

		this.fields = fields;
		this.layoutIds = layoutIds;
		this.context = context;
	}

	public List<List<Field>> getFields() {
		return fields;
	}

	@Override
	public int getCount() {
		return fields.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Context context = container.getContext();

		List<Field> fieldsInPage = fields.get(position);

		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setLayoutParams(
			new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		ScrollView scrollView = new ScrollView(context);
		scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.MATCH_PARENT));

		for (int i = 0, size = fieldsInPage.size(); i < size; i++) {
			Field field = fieldsInPage.get(i);
			Field.EditorType type = field.getEditorType();
			int layoutId = layoutIds.get(type);

			LayoutInflater inflater = LayoutInflater.from(context);
			View view = inflater.inflate(layoutId, container, false);
			view.setTag(position + ":" + i);

			DDLFieldViewModel viewModel = (DDLFieldViewModel) view;
			viewModel.setField(field);
			viewModel.setParentView((View) container.getParent().getParent());

			linearLayout.addView(view);
		}

		scrollView.addView(linearLayout);
		container.addView(scrollView);

		return scrollView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}
}