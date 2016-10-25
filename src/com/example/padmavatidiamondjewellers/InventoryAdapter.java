package com.example.padmavatidiamondjewellers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InventoryAdapter extends BaseAdapter {
	String []qty;
	LayoutInflater inflater;
	private Context c;
	InventoryAdapter(Context c, String[] qty){
		this.c=c;
		this.qty=qty;
		inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int a, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView invqty,invtitle;
		View r;       
        r = inflater.inflate(R.layout.activity_inventory_inflate, null);
		invqty=(TextView)r.findViewById(R.id.inv_qty);
		invtitle=(TextView)r.findViewById(R.id.inv_title);
				
		
		switch(a){
			case 0:
				invtitle.setText("GOLD");
				invqty.setText(qty[a]+" Gm(s)");
				break;
			case 1:
				invtitle.setText("DIAMOND");
				invqty.setText(qty[a]+" Ct(s)");
				break;
			case 2:
				invtitle.setText("SILVER");
				invqty.setText(qty[a]+" Gm(s)");
				break;
				
		}
		return r;
	}

}