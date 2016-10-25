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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class InvestmentsLoans extends Activity {
	int dyear, dmonth, dday;
	EditText date,name_purpose,amt;
	TextView il_change;
	private static final int DEPOSIT_DATE_DIALOG = 1;
	String type="";
	RadioGroup radiogroup;
	ImageButton im;
	DBHelper mydbhelper;
	SQLiteDatabase mydatabase;
	int scount;
	String [] sdate,stotal,sname_pur,stype;
	ListView l;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_investments_loans);
		Intent in=getIntent();
		
		ActionBar actionBar=this.getActionBar();
		actionBar.setIcon(R.drawable.pdj);
		actionBar.setTitle(" INVESTMENTS");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.menubar));
		
		il_change=(TextView)findViewById(R.id.il_chnge);
		date=(EditText)findViewById(R.id.il_date);
		name_purpose=(EditText)findViewById(R.id.il_purpose_name);
		amt=(EditText)findViewById(R.id.il_amt);
		im=(ImageButton)findViewById(R.id.il_select);
		
		type="Investment";
		final Calendar c = Calendar.getInstance();
		dyear = c.get(Calendar.YEAR);
		dmonth =  c.get(Calendar.MONTH);
		dday =  c.get(Calendar.DAY_OF_MONTH);
		updateDepositDateDisplay();
		im.setVisibility(4);//Invisible
		im.setEnabled(false);
		il_change.setText("Purpose");
		amt.setText("0");
		radiogroup=(RadioGroup)findViewById(R.id.il_rg);
		l=(ListView)findViewById(R.id.il_listView);
		name_purpose.setText("");
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == R.id.il_rb_investment){
					type="Investment";
					name_purpose.setHint("Eg.Initial Balance");
					amt.setHint("Amount Invested");
					name_purpose.setEnabled(true);
					im.setVisibility(4);//Invisible
					im.setEnabled(false);
					il_change.setText("Purpose");
					amt.setText("0");
					name_purpose.setText("");
				}else{
					type="Loan";
					il_change.setText("Name");
					name_purpose.setHint("Select Name");
					amt.setHint("Amount paid");
					name_purpose.setEnabled(false);
					im.setEnabled(true);
					im.setVisibility(0);//Visible
					amt.setText("0");
					name_purpose.setText("");
				}
			
			}
		
			});
		mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		Cursor cs_count= mydatabase.rawQuery("select * from "+Database.CAP_TABLE_NAME,null);
		scount=cs_count.getCount();		
		
		
		sdate=new String[scount];
		stype=new String[scount];
		stotal=new String[scount];
		sname_pur=new String[scount];
		
		
		
		
		int i=0;
		String col1[]=new String[]{Database.CAP_DATE,Database.CAP_TYPE,
				Database.CAP_PUR,Database.CAP_AMT};
		Cursor c1=mydatabase.query(Database.CAP_TABLE_NAME, col1, null, null, null, null, null);
		for(c1.moveToLast();!c1.isBeforeFirst();c1.moveToPrevious()){
			
				sdate[i]=c1.getString(0);
				stype[i]=c1.getString(1);
				stotal[i]=c1.getString(3);
				sname_pur[i]=c1.getString(2);
				i++;
			
		}
		l.setAdapter(new InvestmentsLoanAdapter(InvestmentsLoans.this,sdate,stype,sname_pur,stotal));
		 
	}
	private void il_cancel(View v) {
		onBackPressed();
	}
	public void il_done(View v) {
		// TODO Auto-generated method stub
		mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		if(amt.getText().toString().equals("0")){
			Toast.makeText(this, "Please Enter Amount", Toast.LENGTH_SHORT).show();
		}else if(name_purpose.getText().toString().equals("")){
			Toast.makeText(this, "Please Enter Name/Purpose", Toast.LENGTH_SHORT).show();
		}else{
		
		ContentValues cv=new ContentValues();
		cv.put(Database.CAP_AMT, amt.getText().toString());
		cv.put(Database.CAP_PUR, name_purpose.getText().toString());
		cv.put(Database.CAP_DATE, date.getText().toString());
		cv.put(Database.CAP_TYPE, type);
		long r=mydatabase.insertOrThrow(Database.CAP_TABLE_NAME, null, cv);
		if(type.equals("Loan")){
			
		String col[]=new String[]{Database.C_NAME,Database.C_ADV};
		Cursor c=mydatabase.query(Database.C_TABLE_NAME, col, null, null, null, null, null);
		c.moveToFirst();
		double d=0;
		while(!c.isAfterLast()){
			if(c.getString(0).equals(name_purpose.getText().toString())){
				d = Double.parseDouble(c.getString(1));
				
				break;
			}
			c.moveToNext();
		}
		d+=Double.parseDouble(amt.getText().toString());
		ContentValues cs=new ContentValues();
		cs.put(Database.C_ADV,d+"");
		mydatabase.update(Database.C_TABLE_NAME, cs,Database.C_NAME+" = '"+name_purpose.getText().toString()+"'",null);
		mydatabase.close();
		amt.setText("0");
		name_purpose.setText("");
		}
		
		onCreate(null);
		}
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
	public void select(View v){
		Intent intent=new Intent(this,Customers.class);  
        startActivityForResult(intent, 2);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {  
              super.onActivityResult(requestCode, resultCode, data);  
               // check if the request code is same as what is passed  here it is 2  
                if(requestCode==2)  
                      {  
                		try{
                         String message=data.getStringExtra("MESSAGE");   
                         name_purpose.setText(message); }
                		catch(Exception e){}
                      }  
  }  
}
