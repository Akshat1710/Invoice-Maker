package com.example.padmavatidiamondjewellers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GoldInventoryAdapter extends BaseAdapter {
	String [] op, date,qty, rate,total,status,bought_from,amt_paid,amt_pending;
	Context con;
	LayoutInflater inflater;
	int c;
	public GoldInventoryAdapter(Context con,String op[],String date[],String qty[] ,
			String rate[],String [] total,String []status,int c,
			String bought_from[],String [] amt_paid,String []amt_pending){
		this.con=con;
		this.op=op;
		this.date=date;
		this.qty=qty;
		this.rate=rate;
		this.bought_from=bought_from;
		this.amt_paid=amt_paid;
		this.amt_pending=amt_pending;
		this.total=total;
		this.status=status;
		this.c=c;
		inflater=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return c;
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
	public View getView(int p, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView opt,datet,qtyt,ratet,statust,totalt,bft,apt;
		View r;       
        r = inflater.inflate(R.layout.gold_inventory_inflate, null);
		opt=(TextView)r.findViewById(R.id.view_gi_operation);
		datet=(TextView)r.findViewById(R.id.view_gi_date);
		qtyt=(TextView)r.findViewById(R.id.view_gi_qty);
		ratet=(TextView)r.findViewById(R.id.view_gi_rate);
		totalt=(TextView)r.findViewById(R.id.view_gi_total);
		statust=(TextView)r.findViewById(R.id.view_gi_status);
		bft=(TextView)r.findViewById(R.id.view_bought_from_gi);
		apt=(TextView)r.findViewById(R.id.view_gi_amt_pending);
		
		double q=Double.parseDouble(qty[p]);
        double rr=Double.parseDouble(rate[p]);
        opt.setText(op[p]);
		datet.setText(date[p]);
		bft.setText(bought_from[p]);
		apt.setText(amt_pending[p]);
		qtyt.setText(qty[p]);
		ratet.setText(rate[p]);
		statust.setTextColor(Color.RED);
		if(status[p].equals("Paid"))
			statust.setTextColor(Color.GREEN);
		statust.setText(status[p]);
		
		totalt.setText(total[p]);
		return r;
	}

}
