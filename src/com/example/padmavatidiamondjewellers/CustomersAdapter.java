package com.example.padmavatidiamondjewellers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class CustomersAdapter extends BaseAdapter {
	String [] name,mobile_no,adv;
	Context c;
	LayoutInflater inflater;
	double pending[];
	String number;
	public CustomersAdapter(Context c, String[] name,
			 String[] mobile_no,
			 double [] pending,
			 String adv[]) {
		// TODO Auto-generated constructor stub'
		this.c=c;
		this.name=name;
		//this.address=address;
		this.mobile_no=mobile_no;
		//this.desc=desc;
		this.pending=pending;
		this.adv=adv;
		inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return name.length;
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
		TextView cname,cmobileno,cpending,cadv;
		Button call;
		
		View r;       
        r = inflater.inflate(R.layout.customers_inflate, null);
		cname=(TextView)r.findViewById(R.id.name_c);
		//caddress=(TextView)r.findViewById(R.id.address_c);
		cmobileno=(TextView)r.findViewById(R.id.mobile_no_c);
		//cdesc=(TextView)r.findViewById(R.id.desc_c);
		cpending=(TextView)r.findViewById(R.id.c_pending_amt);
		cadv=(TextView)r.findViewById(R.id.c_adv);
		
		
		cname.setText(name[a]);
		//caddress.setText(address[a]);
		cmobileno.setText(mobile_no[a]);
		//cdesc.setText(desc[a]);
		cpending.setTextColor(Color.RED);
		cadv.setText(adv[a]);
		if(pending[a]==0)
			cpending.setTextColor(Color.GREEN);
		cpending.setText("Rs."+pending[a]);
		
		return r;
	}

}
