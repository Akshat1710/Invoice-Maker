package com.example.padmavatidiamondjewellers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AccountsSellAdapter extends BaseAdapter {
	String [] date,ref,qty,rate,total;
	LayoutInflater inflater;
	Context con;
	AccountsSellAdapter(Context con,String date[],
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
        r = inflater.inflate(R.layout.accounts_sell, null);
        dt=(TextView)r.findViewById(R.id.acc_sell_date);
        rat=(TextView)r.findViewById(R.id.acc_sell_rate);
        qt=(TextView)r.findViewById(R.id.acc_sell_qty);
        rt=(TextView)r.findViewById(R.id.acc_sell_ref);
        tt=(TextView)r.findViewById(R.id.acc_sell_total);
        
        
        dt.setText(date[p]);
        
        if(ref[p].equals("Gold")){
        	rt.setTextColor(Color.YELLOW);
        }else if(ref[p].equals("Diamond")){
        	rt.setTextColor(Color.BLUE);
        }else if(ref[p].equals("Silver")){
           	rt.setTextColor(Color.DKGRAY);
        }
        
        tt.setText("Rs."+total[p]);
        rat.setText("Rs."+rate[p]+"/Gm");
    	qt.setText(""+qty[p]+" Gm");
        try{
        	if(ref[p].equals("Labour")){
        		rat.setText("Bill No : "+rate[p]);
        		qt.setText("Details : "+qty[p]+"");
        	}
        }catch(Exception e){
        	
        }
        rt.setText(ref[p]);
		return r;
	}

}
