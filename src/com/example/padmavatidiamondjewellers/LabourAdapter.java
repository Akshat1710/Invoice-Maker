package com.example.padmavatidiamondjewellers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LabourAdapter extends BaseAdapter {
	String [] ldate,lto,lamt;
	Context con;
	LayoutInflater inflater;
	LabourAdapter(Context con,String [] ldate,String [] lto,String [] lamt){
		this.lamt=lamt;
		this.ldate=ldate;
		this.lto=lto;
		this.con=con;
		inflater=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ldate.length;
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
	public View getView(int p, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView ld,lt,la;
		View r;       
        r = inflater.inflate(R.layout.labour_inflate, null);
        lt=(TextView)r.findViewById(R.id.li_to);
        ld=(TextView)r.findViewById(R.id.li_date);
        la=(TextView)r.findViewById(R.id.li_amt);
        
        lt.setText(lto[p]);
        la.setText("Rs." +lamt[p]);
        ld.setText("Date-"+ldate[p]);
        
		return r;
		
	}

}
