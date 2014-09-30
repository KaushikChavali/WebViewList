package com.example.admin.webviewerlist;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class SiteAdapter extends ArrayAdapter<Site>{

    private MyActivity myActivity;

	private Context context;
	private List<Site> siteList;
    LayoutInflater inflater;

    private LruCache<Integer, Bitmap> imageCache;

    private RequestQueue queue;


    public SiteAdapter(Context context, int resource, List<Site> objects) {
		super(context, resource, objects);
        this.context = context;
		this.siteList = objects;

        final int maxMemory = (int)(Runtime.getRuntime().maxMemory() /1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<Integer, Bitmap>(cacheSize);

        queue = Volley.newRequestQueue(context);
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

        tv2.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        //Display image in ImageView widget
        Bitmap bitmap = imageCache.get(site.getId());
        final ImageView image = (ImageView) view.findViewById(R.id.imageView1);
        if (bitmap != null) {
           image.setImageBitmap(bitmap);
        }
        else {
            String imageUrl = MyActivity.PHOTOS_BASE_URL;
            ImageRequest request = new ImageRequest(imageUrl,
                    new Response.Listener<Bitmap>() {

                        @Override
                        public void onResponse(Bitmap arg0) {
                            image.setImageBitmap(arg0);
                            //imageCache.put(flower.getProductId(), arg0);
                        }
                    },
                    80, 80,
                    Bitmap.Config.ARGB_8888,

                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError arg0) {
                            Log.d("FlowerAdapter", "Error loading images");
                        }
                    }
            );
            queue.add(request);
        }
		return view;
	}
}