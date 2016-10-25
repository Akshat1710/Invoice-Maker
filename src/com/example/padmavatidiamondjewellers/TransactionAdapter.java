package com.example.padmavatidiamondjewellers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TransactionAdapter extends BaseAdapter {
	String []name,date,bill_no,item_no,item_total,total_amt,amt_paid,status;
	LayoutInflater inflater;
	private Context c;
	TransactionAdapter(Context c, String[] name, String[] date, String[] bill_no, String[] item_no, String[] item_total,
			String[] amt_paid,String[] total_amt,String[] status){
		this.c=c;
		this.name=name;
		this.date=date;
		this.bill_no=bill_no;
		this.item_no=item_no;
		this.item_total=item_total;
		this.total_amt=total_amt;
		this.amt_paid=amt_paid;
		this.status=status;
		inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bill_no.length;
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
		TextView trname,trdate,trbillno,trbilltotal,trbillitem,trtotalamt,tramtpaid,trstatus;
		View r;       
        r = inflater.inflate(R.layout.transactions_inflate, null);
		trname=(TextView)r.findViewById(R.id.name_tr);
		trdate=(TextView)r.findViewById(R.id.date_tr);
		trbillno=(TextView)r.findViewById(R.id.bill_no_tr);
		trbilltotal=(TextView)r.findViewById(R.id.bill_total_tr);
		trbillitem=(TextView)r.findViewById(R.id.no_of_items_tr);
		trtotalamt=(TextView)r.findViewById(R.id.total_amt_tr);
		tramtpaid=(TextView)r.findViewById(R.id.amt_paid_tr);
		trstatus=(TextView)r.findViewById(R.id.status_tr);
		
		trname.setText(name[a]);
		trdate.setText(date[a]);
		trbillno.setText(bill_no[a]); 
		trbilltotal.setText(total_amt[a]);
		trbillitem.setText(item_no[a]);
		trtotalamt.setText(item_total[a]);
		tramtpaid.setText(amt_paid[a]);
		trstatus.setTextColor(Color.RED);
		if(status[a].equals("Paid"))
			trstatus.setTextColor(Color.GREEN);
		
		trstatus.setText(status[a]);
		
		
		return r;
	}

}
