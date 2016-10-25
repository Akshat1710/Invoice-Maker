package com.example.padmavatidiamondjewellers;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class LabourPay extends Activity {
	RadioGroup radiogroup;
	String type="Labour";
	EditText to,amt,date;
	private static final int DEPOSIT_DATE_DIALOG = 1;
	int dyear, dmonth, dday;
	DBHelper mydbhelper;
	SQLiteDatabase mydatabase;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_labour_pay);
		Intent in=getIntent();
		
		ActionBar actionBar=this.getActionBar();
		actionBar.setIcon(R.drawable.pdj);
		actionBar.setTitle(" LABOUR/EXPENSE");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.menubar));
		
		radiogroup=(RadioGroup)findViewById(R.id.l_rg);
		to=(EditText)findViewById(R.id.l_to);
		amt=(EditText)findViewById(R.id.l_amt);
		date=(EditText)findViewById(R.id.l_date);
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == R.id.l_rb_labour){
					type="Labour";
					to.setHint("Labour Paid to");
					amt.setHint("Labour Amount");
				}else{
					type="Expense";
					to.setHint("Paid to/for");
					amt.setHint("Amount");
				}
			}
		
			});
		final Calendar c = Calendar.getInstance();
		dyear = c.get(Calendar.YEAR);
		dmonth =  c.get(Calendar.MONTH);
		dday =  c.get(Calendar.DAY_OF_MONTH);
		updateDepositDateDisplay();
		 
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
	public void l_done(View v){
		mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		
		String col[]=new String[]{Database.BAL_AMT};
		Cursor c=mydatabase.query(Database.BAL_TABLE_NAME, col, null, null, null, null, null);
		
		c.moveToLast();
		double d=0;
		while(!c.isAfterLast()){
		d=Double.parseDouble(c.getString(0));
		c.moveToNext();
		}
		mydbhelper.close();
		double t=Double.parseDouble(amt.getText().toString());
		if(t<=d){
		mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(Database.L_DATE, date.getText().toString());
		cv.put(Database.L_DAY, dday);
		cv.put(Database.L_MONTH, dmonth+1);
		cv.put(Database.L_YEAR, dyear);
		cv.put(Database.L_TYPE, type);
		cv.put(Database.L_TO, to.getText().toString());
		cv.put(Database.L_AMT, amt.getText().toString());
		long r=mydatabase.insertOrThrow(Database.L_TABLE_NAME, null, cv);
		mydbhelper.close();
		}else{
			Toast.makeText(this, "Not enough Capital\nAdd Capital First", Toast.LENGTH_LONG).show();
		}
		Labour l=new Labour();
		l.onCreate(null);
		onBackPressed();
		
	}
	public void l_cancel(View v){
		onBackPressed();
	}
}
