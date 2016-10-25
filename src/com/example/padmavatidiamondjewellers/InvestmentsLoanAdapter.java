package com.example.padmavatidiamondjewellers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InvestmentsLoanAdapter extends BaseAdapter {
	String [] date, type,name_pur, amount;
	LayoutInflater inflater;
	Context con;
	InvestmentsLoanAdapter(Context con,String [] date,String [] type,
			String [] name_pur,String [] amount){
		this.date=date;
		this.name_pur=name_pur;
		this.type=type;
		this.amount=amount;
		this.con=con;
		inflater=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return date.length;
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
		TextView dt,npt,at,tt;
		View r;       
        r = inflater.inflate(R.layout.investments_loans_inflate, null);
        dt=(TextView)r.findViewById(R.id.date_li);
        at=(TextView)r.findViewById(R.id.amt_li);
        tt=(TextView)r.findViewById(R.id.type_li);
        npt=(TextView)r.findViewById(R.id.name_purpose_li);
       
        
        dt.setText(date[p]);
        at.setText("Rs."+amount[p]);
        if(type[p].equals("Investment"))
        	npt.setText("Purpose - "+name_pur[p]);
        else
        	npt.setText("Advance From - "+name_pur[p]);
        tt.setText(type[p]);
        
		return r;
	}

}
