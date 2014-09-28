package com.example.admin.webviewerlist;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.webviewerlist.Site;

public class SiteAdapter extends ArrayAdapter<Site> {

	private Context context;
	private List<Site> siteList;

	public SiteAdapter(Context context, int resource, List<Site> objects) {
		super(context, resource, objects);
		this.context = context;
		this.siteList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = 
				(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_site, parent, false);

		//Display flower name in the TextView widget
		Site site = siteList.get(position);
		TextView tv1 = (TextView) view.findViewById(R.id.textView1);
		tv1.setText(site.getUrl());

        TextView tv2 = (TextView) view.findViewById(R.id.textView2);
        tv2.setText(site.getDescription());
		
		return view;
	}

}
