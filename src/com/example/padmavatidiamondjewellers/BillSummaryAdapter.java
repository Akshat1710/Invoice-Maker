package com.example.padmavatidiamondjewellers;



import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BillSummaryAdapter extends BaseAdapter {
	String  particulars[],gold_wt[],gold_rate[],gold_total[],diamond_wt[],diamond_rate[],diamond_total[],
	silver_wt[],silver_rate[],silver_total[],labour_charges[],item_no[],item_total[];
	Context con;
	Cursor cur;
	LayoutInflater inflater;
	public BillSummaryAdapter(Context con, Cursor cur, String [] particulars,
			String [] gold_wt,String [] gold_rate,String [] gold_total,
			String [] diamond_wt,String [] diamond_rate,String [] diamond_total,
			String [] silver_wt,String [] silver_rate,String [] silver_total,
			String [] labour_charges,String [] item_no,String [] item_total){
		this.cur=cur;
		this.con=con;
		this.particulars=particulars;
		this.gold_wt=gold_wt;
		this.gold_rate=gold_rate;
		this.gold_total=gold_total;
		this.diamond_wt=diamond_wt;
		this.diamond_rate=diamond_rate;
		this.diamond_total=diamond_total;
		this.silver_wt=silver_wt;
		this.silver_rate=silver_rate;
		this.silver_total=silver_total;
		this.labour_charges=labour_charges;
		this.item_no=item_no;
		this.item_total=item_total;
		
		inflater=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return particulars.length;
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
	public View getView(int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView gw,gr,gt,dw,dr,dt,sw,sr,st,p,l,i,it;
		View r;       
        r = inflater.inflate(R.layout.bill_details_inflate, null);
		gw=(TextView)r.findViewById(R.id.gold_wt_bs);
		gr=(TextView)r.findViewById(R.id.gold_rate_bs);
		gt=(TextView)r.findViewById(R.id.gold_total_bs);
		dw=(TextView)r.findViewById(R.id.diamond_wt_bs);
		dr=(TextView)r.findViewById(R.id.diamond_rate_bs);
		dt=(TextView)r.findViewById(R.id.diamond_total_bs);
		sw=(TextView)r.findViewById(R.id.silver_wt_bs);
		sr=(TextView)r.findViewById(R.id.silver_rate_bs);
		st=(TextView)r.findViewById(R.id.silver_total_bs);
		p=(TextView)r.findViewById(R.id.particulars_bs);
		l=(TextView)r.findViewById(R.id.labour_charges_bs);
		i=(TextView)r.findViewById(R.id.item_no_bs);
		it=(TextView)r.findViewById(R.id.item_total_bs);
		
		gw.setText(gold_wt[position]+" Gm");
		gr.setText(gold_rate[position]+" /Gm");
		gt.setText(gold_total[position]+" Rs");
		dw.setText(diamond_wt[position]+" Ct");
		dr.setText(diamond_rate[position]+" /Ct");
		dt.setText(diamond_total[position]+" Rs");
		sw.setText(silver_wt[position]+" Kg");
		sr.setText(silver_rate[position]+" /Kg");
		st.setText(silver_total[position]+" Rs");
		p.setText(particulars[position]);
		l.setText(labour_charges[position]);
		i.setText(item_no[position]);
		it.setText(item_total[position]);
		
		return r;
	}

}
