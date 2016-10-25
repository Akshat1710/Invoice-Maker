package com.example.padmavatidiamondjewellers;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BillSummary extends Activity {
	
	
	TextView thank_you_bs,padmavati,tagline,diamond,jewellers,b_no,b_date,b_name,bill_total,colour_stones,desc,amt_total,amt_paid;
	SQLiteDatabase mydatabase;
	int count;
	String bill_no,bill_t;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill_summary);
		Intent in=getIntent();
		
		ActionBar actionBar=this.getActionBar();
		actionBar.setIcon(R.drawable.pdj);
		actionBar.setTitle("INVOICE");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.menubar));
		
		bill_no=in.getStringExtra("bill_no");
		bill_t=in.getStringExtra("bill_total");
		padmavati=(TextView)findViewById(R.id.padmavati);
		tagline=(TextView)findViewById(R.id.tagline);
		diamond=(TextView)findViewById(R.id.diamond);
		jewellers=(TextView)findViewById(R.id.jewellers);
		b_no=(TextView)findViewById(R.id.bill_no_bs);
		b_date=(TextView)findViewById(R.id.date_bs);
		b_name=(TextView)findViewById(R.id.name_bs);
		bill_total=(TextView)findViewById(R.id.total_bs);
		amt_paid=(TextView)findViewById(R.id.amt_paid_bs);
		amt_total=(TextView)findViewById(R.id.total_amt_bs);
		desc=(TextView)findViewById(R.id.desc_bs);
		colour_stones=(TextView)findViewById(R.id.colour_stones_amt_bs);
		thank_you_bs=(TextView)findViewById(R.id.thank_you_bs);
		
		Typeface font1 = Typeface.createFromAsset(getAssets(), "Lucida Handwrit.ttf");
		Typeface font2 = Typeface.createFromAsset(getAssets(), "Lucida Sans Demibold Italic.ttf");
		padmavati.setTypeface(font1);
		tagline.setTypeface(font1);
		diamond.setTypeface(font2);
		jewellers.setTypeface(font2);
		thank_you_bs.setTypeface(font1);
		b_no.setText(bill_no);
		
		DBHelper mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		Cursor c_count= mydatabase.rawQuery("select * from "+Database.BD_TABLE_NAME+" where "+Database.BD_BILL_NO+" = "+bill_no,null);
		count=c_count.getCount();
		
		String particulars[]=new String[count];
		String gold_wt[]=new String[count];
		String gold_rate[]=new String[count];
		String gold_total[]=new String[count];
		String diamond_wt[]=new String[count];
		String diamond_rate[]=new String[count];
		String diamond_total[]=new String[count];
		String silver_wt[]=new String[count];
		String silver_rate[]=new String[count];
		String silver_total[]=new String[count];
		String labour_charges[]=new String[count];
		String item_no[]=new String[count];
		String item_total[]=new String[count];
		//bill_total.setText(bill_t+"" );
		
		String col1[]=new String[]{Database.BD_NAME,
				Database.BD_DATE,Database.BD_BILL_NO};
		Cursor c1=mydatabase.query(Database.BD_TABLE_NAME, col1, null, null, null, null, null);
		for(c1.moveToFirst();!c1.isAfterLast();c1.moveToNext()){
			if(c1.getString(2).equals(bill_no)){
				b_name.setText(c1.getString(0));
				b_date.setText(c1.getString(1));
				break;
				
			}
		}//To set Name and Date 
		SQLiteDatabase mydb;
		mydb=mydbhelper.getWritableDatabase();
		String col2[]=new String[]{Database.BI_BILL_NO,Database.BI_BILL_TOTAL,Database.BI_COLOUR_STONE_AMT,Database.BI_AMT_PAID,Database.BI_AMT_PENDING,Database.BI_DESC};
		Cursor c2=mydb.query(Database.BI_TABLE_NAME, col2, null, null, null, null, null);
		for(c2.moveToFirst();!c2.isAfterLast();c2.moveToNext()){
			if(c2.getString(0).equals(bill_no)){
				desc.setText("Note : "+c2.getString(5) );
				amt_total.setText(c2.getString(1));
				colour_stones.setText(Double.parseDouble(c2.getString(2))+"");
				amt_paid.setText(Double.parseDouble(c2.getString(3))+"");
				bill_total.setText(c2.getString(4));
				
				break;
			}
		}
		String col[]=new String[]{Database.BD_PARTICULARS,
					Database.BD_GOLD_WT,Database.BD_GOLD_RATE,
					Database.BD_GOLD_TOTAL,Database.BD_DIAMOND_WT,
					Database.BD_DIAMOND_RATE,Database.BD_DIAMOND_TOTAL,
					Database.BD_SILVER_WT,Database.BD_SILVER_RATE,Database.BD_SILVER_TOTAL,
					Database.BD_LABOUR_CHARGES,Database.BD_BILL_NO,Database.BD_ITEM_ID,Database.BD_ITEM_TOTAL};
			Cursor c=mydatabase.query(Database.BD_TABLE_NAME, col, null, null, null, null, null);
			int i=0;
			for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
				if(c.getString(11).equals(bill_no)){
				particulars[i]=c.getString(0);
				gold_wt[i]=c.getString(1);
				gold_rate[i]=c.getString(2);
				gold_total[i]=c.getString(3);
				diamond_wt[i]=c.getString(4);
				diamond_rate[i]=c.getString(5);
				diamond_total[i]=c.getString(6);
				silver_wt[i]=c.getString(7);
				silver_rate[i]=c.getString(8);
				silver_total[i]=c.getString(9);
				labour_charges[i]=c.getString(10);
				item_no[i]=c.getString(12);
				item_total[i]=c.getString(13);
				
				i++;
				}
				
			}
		
			
			mydatabase.close();
			ListView l=(ListView)findViewById(R.id.listView1);
			l.setAdapter(new BillSummaryAdapter(this,c ,particulars,gold_wt,gold_rate,gold_total,diamond_wt,diamond_rate,diamond_total,
					silver_wt,silver_rate,silver_total,labour_charges,item_no,item_total));
		
	}
	
}
