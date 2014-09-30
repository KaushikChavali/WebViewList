package com.example.admin.webviewerlist;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class SiteAdapter extends ArrayAdapter<Site>{

    private MyActivity myActivity;

	private Context context;
	private List<Site> siteList;
    LayoutInflater inflater;

	public SiteAdapter(Context context, int resource, List<Site> objects) {
		super(context, resource, objects);
        this.context = context;
		this.siteList = objects;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {

        inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_site, parent, false);

        //Display flower name in the TextView widget
		Site site = siteList.get(position);
		TextView tv1 = (TextView) view.findViewById(R.id.textView1);
		tv1.setText(site.getUrl());

        TextView tv2 = (TextView) view.findViewById(R.id.textView2);
        tv2.setText(site.getDescription());

        tv1.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MyActivity myActivity1 = new MyActivity();
                Site site = siteList.get(position);
                String urlNames =  site.getUrl();
                Intent intent;
                intent = new Intent(getContext(), WebReaderActivity.class);
                intent.setData(Uri.parse(urlNames));
                context.startActivity(intent);
            }
        });

        tv2.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MyActivity myActivity1 = new MyActivity();
                Site site = siteList.get(position);
                String urlNames =  site.getUrl();
                String urlDescription = site.getDescription();
                Intent intent;
                intent = new Intent(getContext(), WebReaderActivity.class);
                Bundle extras = new Bundle();
                extras.putString("EXTRA_URL",urlNames);
                extras.putString("EXTRA_DESCRIPTION",urlDescription);
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });


        Button btnEdit = (Button) view.findViewById(R.id.editButton);

        btnEdit.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MyActivity myActivity1 = new MyActivity();
                Site site = siteList.get(position);
                String urlNames =  site.getUrl();
                String urlDescription = site.getDescription();
                Integer urlID = site.getId();
                Intent intent;
                intent = new Intent(getContext(), ModifyItemActivity.class);
                Bundle extras = new Bundle();
                extras.putString("EXTRA_URL",urlNames);
                extras.putString("EXTRA_DESCRIPTION",urlDescription);
                extras.putInt("EXTRA_URLID",urlID);
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });

        Button btnDiscard = (Button) view.findViewById(R.id.discardButton);

        btnDiscard.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Site site = siteList.get(position);
                        Integer urlID = site.getId();
                        String urlNames =  site.getUrl();
                        String urlDescription = site.getDescription();
                        Intent intent;
                        intent = new Intent(getContext(), DeleteItemActivity.class);
                        Bundle extras = new Bundle();
                        extras.putString("EXTRA_URL",urlNames);
                        extras.putString("EXTRA_DESCRIPTION",urlDescription);
                        extras.putInt("EXTRA_URLID",urlID);
                        intent.putExtras(extras);
                        context.startActivity(intent);
                    }
                }
        );

		return view;
	}
}