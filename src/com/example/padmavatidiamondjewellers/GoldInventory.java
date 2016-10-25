package com.example.padmavatidiamondjewellers;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class GoldInventory extends Activity {
	EditText date,qty,rate,partially_paid,seller_info;
	CheckBox payment;
	TextView total,total_t;
	DBHelper mydbhelper;
	SQLiteDatabase mydatabase;
	RadioGroup radiogroup;
	String payment_status,partial;
	int dyear, dmonth, dday;
	double t;
	Button cal;
	private static final int DEPOSIT_DATE_DIALOG = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gold_inventory);
		Intent i=getIntent();
		
		ActionBar actionBar=this.getActionBar();
		actionBar.setIcon(R.drawable.pdj);
		actionBar.setTitle(" GOLD INVENTORY");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.menubar));
		
		final Calendar c = Calendar.getInstance();
		dyear = c.get(Calendar.YEAR);
		dmonth =  c.get(Calendar.MONTH);
		dday =  c.get(Calendar.DAY_OF_MONTH);
		
		cal=(Button)findViewById(R.id.gi_cal);
		date=(EditText)findViewById(R.id.gi_date);
		date.setEnabled(false);
		
		qty=(EditText)findViewById(R.id.gi_qty);
		rate=(EditText)findViewById(R.id.gi_rate);
		total=(TextView)findViewById(R.id.gi_total);
		total_t=(TextView)findViewById(R.id.gi_total_t);
		seller_info=(EditText)findViewById(R.id.seller_g);
		
		seller_info.setText("");
		total.setEnabled(false);
		total_t.setEnabled(false);
		qty.setEnabled(true);
		rate.setEnabled(true);
		
		updateDepositDateDisplay();
		
		
		
		radiogroup = (RadioGroup) findViewById(R.id.payment_g);
		
		payment_status="Unpaid";	
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == R.id.rb_unpaid_g){
					payment_status="Unpaid";
				}else if(checkedId == R.id.rb_paid_g){
					payment_status="Paid";
				}else{
					partial="";
					payment_status="Partially Paid";
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GoldInventory.this);
					alertDialogBuilder.setTitle("Payment Details") ; 
					LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View view=layoutInflater.inflate(R.layout.payment_details,null);
					alertDialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
						@Override
				        public void onClick(DialogInterface arg0, int arg1) {
							Dialog f=(Dialog)arg0;
							partially_paid=(EditText)f.findViewById(R.id.bd_partially_paid_amt);
							partial=partially_paid.getText().toString();
						}
				    });
				    alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
						@Override
				        public void onClick(DialogInterface arg0, int arg1) {
							partial="";
							
						}
				    });
				    AlertDialog alertDialog = alertDialogBuilder.create();
				    alertDialog.setView(view);
				    alertDialog.show();
					}
				}
			
		
			});
		 	
		
	}
	public void calculate(View v){
		cal.setEnabled(false);
		total.setEnabled(true);
		total_t.setEnabled(true);
		qty.setEnabled(false);
		rate.setEnabled(false);
		double q=Double.parseDouble(qty.getText().toString());
        double r=Double.parseDouble(rate.getText().toString());
        total.setText(q*r+"");
        
	}
	public void gi_cancel(View v){
		
		onBackPressed();
	}
	public void gi_done(View v){
		mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		
		Cursor c_count= mydatabase.rawQuery("select * from "+Database.G_TABLE_NAME,null);
		int g_bill_no=0;
		g_bill_no=c_count.getCount();
		g_bill_no++;

		
		
		String col[]=new String[]{Database.BAL_AMT};
		Cursor c=mydatabase.query(Database.BAL_TABLE_NAME, col, null, null, null, null, null);
		
		c.moveToLast();
		double d=0;
		while(!c.isAfterLast()){
			

		d=Double.parseDouble(c.getString(0));
		c.moveToNext();
		}
		mydbhelper.close();
		
		double t=Double.parseDouble(total.getText().toString());
		String a_paid="",a_pending="";
		if(payment_status.equals("Unpaid")){
			a_paid="0";
			a_pending=t+"";
		}else if(payment_status.equals("Paid")){
			a_paid=t+"";
			a_pending="0";
		}else{
			a_paid=partial;
			a_pending=(t-Double.parseDouble(partial))+"";
			

		}
		if((t<=d) && payment_status.equals("Paid")){
			
		mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(Database.G_KEY, g_bill_no+"");
		cv.put(Database.G_DATE, date.getText().toString());
		cv.put(Database.G_QTY, qty.getText().toString());
		cv.put(Database.G_RATE, rate.getText().toString());
		cv.put(Database.G_TOTAL, total.getText().toString());
		cv.put(Database.G_STATUS, payment_status);
		cv.put(Database.G_BOUGHT_FROM, seller_info.getText().toString());
		cv.put(Database.G_AMT_PAID,a_paid );
		cv.put(Database.G_AMT_PENDING, a_pending);
		cv.put(Database.G_OPERATION, "Buy");
		mydatabase.insertOrThrow(Database.G_TABLE_NAME, null, cv);
		mydbhelper.close();
		
		}else if(payment_status.equals("Unpaid")||(payment_status.equals("Partially Paid"))){
			mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
			mydatabase=mydbhelper.getWritableDatabase();
			ContentValues cv=new ContentValues();
			cv.put(Database.G_DATE, date.getText().toString());
			cv.put(Database.G_QTY, qty.getText().toString());
			cv.put(Database.G_RATE, rate.getText().toString());
			cv.put(Database.G_TOTAL, total.getText().toString());
			cv.put(Database.G_STATUS, payment_status);
			cv.put(Database.G_BOUGHT_FROM, seller_info.getText().toString());
			cv.put(Database.G_AMT_PAID,a_paid );
			cv.put(Database.G_AMT_PENDING, a_pending);
			cv.put(Database.G_OPERATION, "Buy");
			mydatabase.insertOrThrow(Database.G_TABLE_NAME, null, cv);
			mydbhelper.close();
		}else{
			
			Toast.makeText(this, "Not Enough Capital\nAdd Capital First", Toast.LENGTH_LONG).show();
		}
		Inventory in=new Inventory();
		in.onCreate(null);
		onBackPressed(); 
	}
	private DatePickerDialog.OnDateSetListener depositDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int pYear, int pMonth, int pDay) {
			dyear = pYear;
			dmonth = pMonth;
			dday = pDay;
			updateDepositDateDisplay();
		}
	};
	public void showDepositDateDialog(View v) {
		showDialog(DEPOSIT_DATE_DIALOG);
	}
	protected Dialog onCreateDialog(int id) {
		super.onCreateDialog(id);

		switch (id) {
		case DEPOSIT_DATE_DIALOG:
			return new DatePickerDialog(this, depositDateSetListener, dyear,
					dmonth, dday);
		}
		return null;
	}
	private void updateDepositDateDisplay() {
		// Month is 0 based so add 1
		date.setText(String
				.format("%02d-%02d-%04d", dday, dmonth + 1, dyear));
		date.setEnabled(false);
	
	}
}
