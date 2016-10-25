package com.example.padmavatidiamondjewellers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AccountsBuyAdapter extends BaseAdapter {
	String [] date,ref,qty,rate,total;
	LayoutInflater inflater;
	Context con;
	AccountsBuyAdapter(Context con,String date[],
			String ref[],String qty[],
			String rate[],String total[]){
		this.date=date;
		this.qty=qty;
		this.rate=rate;
		this.ref=ref;
		this.total=total;
		this.con=con;
		inflater=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return qty.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int p, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView dt,rt,qt,tt,rat;
		View r;       
        r = inflater.inflate(R.layout.accounts_buy, null);
        dt=(TextView)r.findViewById(R.id.acc_buy_date);
        rat=(TextView)r.findViewById(R.id.acc_buy_rate);
        qt=(TextView)r.findViewById(R.id.acc_buy_qty);
        rt=(TextView)r.findViewById(R.id.acc_buy_status);
        tt=(TextView)r.findViewById(R.id.acc_buy_total);
        
        dt.setText(date[p]);
        rat.setText("Rs."+rate[p]+"/Gm");
        qt.setText(qty[p]+" Gm");
        tt.setText("Rs."+total[p]);
        rt.setTextColor(Color.RED);
        if(ref[p].equals("Paid"))
        	rt.setTextColor(Color.GREEN);
        rt.setText(ref[p]);
		return r;
	}

}
