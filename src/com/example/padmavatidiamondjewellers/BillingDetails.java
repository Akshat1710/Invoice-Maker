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
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class BillingDetails extends Activity {
	int dyear, dmonth, dday;
	DBHelper mydbhelper;
	CheckBox c_gold,c_diamond,c_silver,c_colour_stone;
	SQLiteDatabase mydatabase;
	EditText labour_details,particulars;
	EditText gold_wt,gold_rate;
	EditText diamond_wt,diamond_rate;
	EditText silver_wt,silver_rate;
	EditText colour_stone,partially_paid,desc;
	TextView date,name,gold_total,diamond_total,silver_total,total,bill_no_bd;
	Button gold_button,add,generate,discard,diamond_button,silver_button;
	private static final int DEPOSIT_DATE_DIALOG = 1;
	int item_id;
	int bill_no;
	double gt,dt,st;
	String gw,gr,dw,dr,sw,sr;
	double item_total=0,t=0;
	boolean g_status=false;
	double g_qty=0;
	RadioGroup radiogroup;
	String payment_status,partial;
	String name_person,phone_number;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_billing_details);
		Intent i=getIntent();
		
		ActionBar actionBar=this.getActionBar();
		actionBar.setIcon(R.drawable.pdj);
		actionBar.setTitle(" INVOICE");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.menubar));
		
        
        
		date=(TextView)findViewById(R.id.date1);
		
		final Calendar c = Calendar.getInstance();
		dyear = c.get(Calendar.YEAR);
		dmonth =  c.get(Calendar.MONTH);
		dday =  c.get(Calendar.DAY_OF_MONTH);
		updateDepositDateDisplay();
		
		gold_button=(Button)findViewById(R.id.button2);
		diamond_button=(Button)findViewById(R.id.button3);
		silver_button=(Button)findViewById(R.id.button4);
		add=(Button)findViewById(R.id.add_bd);
		//calculate=(Button)findViewById(R.id.calculate_bd);
		generate=(Button)findViewById(R.id.generate_bill_bd);
		discard=(Button)findViewById(R.id.discard_bill_bd);
		
		radiogroup = (RadioGroup) findViewById(R.id.payment_bd);
		
		gold_total=(TextView)findViewById(R.id.gold_total_amount);
		diamond_total=(TextView)findViewById(R.id.diamond_total_amount);
		silver_total=(TextView)findViewById(R.id.silver_total_amount);
		total=(TextView)findViewById(R.id.total);
		name=(TextView)findViewById(R.id.name_bd);
		bill_no_bd=(TextView)findViewById(R.id.bill_no_bd);
		desc=(EditText)findViewById(R.id.desc_bd);
		
		labour_details=(EditText)findViewById(R.id.labour_details);
		particulars=(EditText)findViewById(R.id.particulars_bd);
		colour_stone=(EditText)findViewById(R.id.colour_stone_amt_bd);
		
		gold_total.setText("0");
		diamond_total.setText("0");
		silver_total.setText("0");
		particulars.setText("");
		labour_details.setText("0");
		sw=sr=dw=dr=gw=gr="0";
		st=dt=gt=0;
		item_id=0;
		colour_stone.setText("0");
		
		//To get a bill no 
		mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		
		Cursor c_count= mydatabase.rawQuery("select * from "+Database.BI_TABLE_NAME,null);
		bill_no=c_count.getCount();
		bill_no++;
		bill_no_bd.setText(bill_no+"");
		
		gold_button.setEnabled(false);
		diamond_button.setEnabled(false);
		silver_button.setEnabled(false);
		gold_total.setEnabled(false);
		diamond_total.setEnabled(false);
		silver_total.setEnabled(false);
		colour_stone.setEnabled(false);
		add.setEnabled(true);
		//calculate.setEnabled(false);
		generate.setEnabled(false);
		item_total=0;
		t=0;
		
		c_gold = (CheckBox) findViewById(R.id.checkBox1);   
		c_diamond = (CheckBox) findViewById(R.id.checkBox2);
		c_silver = (CheckBox) findViewById(R.id.checkBox3);
		c_colour_stone= (CheckBox) findViewById(R.id.checkBox4);
		
		c_colour_stone.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
		               
				  	if (((CheckBox) v).isChecked()) {
					  	colour_stone.setEnabled(true);		
					}else{
						colour_stone.setEnabled(false);
						
					}
		 
			  }
			});
		
		c_gold.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
		                //is chkIos checked?
				  if (((CheckBox) v).isChecked()) {
					  gold_button.setEnabled(true);
					  gold_total.setText(gt+"");
								
					}else{
						gold_button.setEnabled(false);
						
						
					}
		 
			  }
			});
		c_diamond.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
		                //is chkIos checked?
				  if (((CheckBox) v).isChecked()) {
					  diamond_button.setEnabled(true);
					  diamond_total.setText(dt+"");
					}else{
						diamond_button.setEnabled(false);
						
						
					}
				  
		 
			  }
			});
		c_silver.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
		                //is chkIos checked?
				  if (((CheckBox) v).isChecked()) {
					  silver_button.setEnabled(true);
					  silver_total.setText(st+"");
					}else{
						silver_button.setEnabled(false);
						
						
					}
		 
			  }
			});
		payment_status="Unpaid";	
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == R.id.rb_unpaid){
					payment_status="Unpaid";
				}else if(checkedId == R.id.rb_paid){
					payment_status="Paid";
				}else{
					partial="";
					payment_status="Partially Paid";
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BillingDetails.this);
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
		
		 try{
			mydbhelper=new DBHelper(BillingDetails.this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
			mydatabase=mydbhelper.getWritableDatabase();
			mydatabase.delete(Database.BI_TABLE_NAME,Database.BI_BILL_NO+"='"+bill_no+"'",null);
         	mydatabase.delete(Database.BD_TABLE_NAME,Database.BD_BILL_NO+"='"+bill_no+"'",null);
         	mydatabase.delete(Database.G_TABLE_NAME,Database.G_STATUS+"='(Bill No : "+bill_no+" )'",null);
         	mydatabase.delete(Database.D_TABLE_NAME,Database.D_STATUS+"='(Bill No : "+bill_no+" )'",null);
         	mydatabase.delete(Database.S_TABLE_NAME,Database.S_STATUS+"='(Bill No : "+bill_no+" )'",null);
		 }catch(Exception e){
			 
		 }
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_billing_details, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_collection:
            // collection
        	//collection();
            return true;
        
        case R.id.action_refresh:
            // refresh
            return true;
        
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	/*private void collection() {
		// TODO Auto-generated method stub
		
	}*/
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
		
	
	}
	public void selectCustomer(View v){
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
                         name.setText(message); }
                		catch(Exception e){}
                      }  
  }

	public void goldDetails(View v){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Add Gold Details") ; 
		LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  View view=layoutInflater.inflate(R.layout.gold_details,null);
		  
		
		
		
	    alertDialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			@Override
	        public void onClick(DialogInterface arg0, int arg1) {
	        	 //deposit_no.setText("");
				Dialog f=(Dialog)arg0;
				gold_wt=(EditText)f.findViewById(R.id.gold_wt_ad);
				gold_rate=(EditText)f.findViewById(R.id.gold_rate_ad);
				gw=gold_wt.getText().toString();
				gr=gold_rate.getText().toString();
				
				mydbhelper=new DBHelper(BillingDetails.this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
				mydatabase=mydbhelper.getWritableDatabase();
				g_qty=0;
				String col1[]=new String[]{Database.G_QTY};
				Cursor c1=mydatabase.query(Database.G_TABLE_NAME, col1, null, null, null, null, null);
				for(c1.moveToLast();!c1.isBeforeFirst();c1.moveToPrevious()){
					g_qty+=Double.parseDouble(c1.getString(0));	
				}
				
				if(g_qty >= Double.parseDouble(gold_wt.getText().toString())){
					gt=Double.parseDouble(gold_wt.getText().toString())*Double.parseDouble(gold_rate.getText().toString());
					gold_total.setText(gt+"");
					g_status=true;
				}else{
					g_status=false;
					Toast.makeText(BillingDetails.this,"Not Enough Gold in the Inventory",Toast.LENGTH_LONG).show();
					gold_total.setText(0.0+"");
				}
				if(g_status){
			    	ContentValues cv=new ContentValues();
					cv.put(Database.G_DATE, date.getText().toString());
					cv.put(Database.G_QTY, gold_wt.getText().toString());
					cv.put(Database.G_RATE, gold_rate.getText().toString());
					cv.put(Database.G_TOTAL, gt);
					cv.put(Database.G_STATUS, "(Bill No : "+bill_no+" )");
					cv.put(Database.G_OPERATION, "Sell");
					
					long r=mydatabase.insertOrThrow(Database.G_TABLE_NAME, null, cv);
					if(r>0){
						Toast.makeText(BillingDetails.this,"Gold used from the Inventory",Toast.LENGTH_LONG).show();
						g_qty=0;
					}
			    }
				
			}
	    });
	    
	    mydatabase.close();
	    alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			@Override
	        public void onClick(DialogInterface arg0, int arg1) {
				gold_total.setText("0.0");
				g_qty=0;
			}
	    });
	    AlertDialog alertDialog = alertDialogBuilder.create();
	    alertDialog.setView(view);
	    alertDialog.show();
	}
	public void diamondDetails(View v){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Add Diamond Details") ; 
		LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  View view=layoutInflater.inflate(R.layout.diamond_details,null);
		  
			
			
			
		//alertDialogBuilder.setMessage("The Record for the given Deposit Number exists");
	    alertDialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			@Override
	        public void onClick(DialogInterface arg0, int arg1) {
				Dialog f=(Dialog)arg0;
				EditText diamond_wt=(EditText)f.findViewById(R.id.diamond_wt_ad);
				EditText diamond_rate=(EditText)f.findViewById(R.id.diamond_rate_ad);
				
				dw=diamond_wt.getText().toString();
				dr=diamond_rate.getText().toString();
				dt=Double.parseDouble(diamond_wt.getText().toString())*Double.parseDouble(diamond_rate.getText().toString());
				diamond_total.setText(""+dt);
				mydbhelper=new DBHelper(BillingDetails.this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
				mydatabase=mydbhelper.getWritableDatabase();
				g_qty=0;
				String col1[]=new String[]{Database.D_QTY};
				Cursor c1=mydatabase.query(Database.D_TABLE_NAME, col1, null, null, null, null, null);
				for(c1.moveToLast();!c1.isBeforeFirst();c1.moveToPrevious()){
					g_qty+=Double.parseDouble(c1.getString(0));	
				}
				
				if(g_qty >= Double.parseDouble(diamond_wt.getText().toString())){
					dt=Double.parseDouble(diamond_wt.getText().toString())*Double.parseDouble(diamond_rate.getText().toString());
					diamond_total.setText(dt+"");
					g_status=true;
				}else{
					g_status=false;
					Toast.makeText(BillingDetails.this,"Not Enough Diamond in the Inventory",Toast.LENGTH_LONG).show();
					diamond_total.setText(0.0+"");
				}
				if(g_status){
			    	ContentValues cv=new ContentValues();
					cv.put(Database.D_DATE, date.getText().toString());
					cv.put(Database.D_QTY, diamond_wt.getText().toString());
					cv.put(Database.D_RATE, diamond_rate.getText().toString());
					cv.put(Database.D_TOTAL, dt);
					cv.put(Database.D_STATUS, "(Bill No : "+bill_no+" )");
					cv.put(Database.D_OPERATION, "Sell");
					
					long r=mydatabase.insertOrThrow(Database.D_TABLE_NAME, null, cv);
					if(r>0){
						Toast.makeText(BillingDetails.this,"Diamond used from the Inventory",Toast.LENGTH_LONG).show();
						g_qty=0;
					}
			    }
			}
	    });
	    alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			@Override
	        public void onClick(DialogInterface arg0, int arg1) {
	        	g_qty=0;
				diamond_total.setText("0.0");
			}
	    });
	    AlertDialog alertDialog = alertDialogBuilder.create();
	    alertDialog.setView(view);
	    alertDialog.show();
	}
	public void silverDetails(View v){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Add Silver Details") ; 
		LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  View view=layoutInflater.inflate(R.layout.silver_details,null);
		//alertDialogBuilder.setMessage("The Record for the given Deposit Number exists");
	    alertDialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			@Override
	        public void onClick(DialogInterface arg0, int arg1) {
				Dialog f=(Dialog)arg0;
				silver_wt=(EditText)f.findViewById(R.id.silver_wt_ad);
				silver_rate=(EditText)f.findViewById(R.id.silver_rate_ad);
				sw=silver_wt.getText().toString();
				sr=silver_rate.getText().toString();
				st=Double.parseDouble(silver_wt.getText().toString())*Double.parseDouble(silver_rate.getText().toString());
				silver_total.setText(st+"");
				mydbhelper=new DBHelper(BillingDetails.this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
				mydatabase=mydbhelper.getWritableDatabase();
				g_qty=0;
				String col1[]=new String[]{Database.S_QTY};
				Cursor c1=mydatabase.query(Database.S_TABLE_NAME, col1, null, null, null, null, null);
				for(c1.moveToLast();!c1.isBeforeFirst();c1.moveToPrevious()){
					g_qty+=Double.parseDouble(c1.getString(0));	
				}
				
				if(g_qty >= Double.parseDouble(silver_wt.getText().toString())){
					st=Double.parseDouble(silver_wt.getText().toString())*Double.parseDouble(silver_rate.getText().toString());
					silver_total.setText(st+"");
					g_status=true;
				}else{
					g_status=false;
					Toast.makeText(BillingDetails.this,"Not Enough Silver in the Inventory",Toast.LENGTH_LONG).show();
					silver_total.setText(0.0+"");

				}
				if(g_status){
			    	ContentValues cv=new ContentValues();
					cv.put(Database.S_DATE, date.getText().toString());
					cv.put(Database.S_QTY, silver_wt.getText().toString());
					cv.put(Database.S_RATE, silver_rate.getText().toString());
					cv.put(Database.S_TOTAL, st);
					cv.put(Database.S_STATUS, "(Bill No : "+bill_no+" )");
					cv.put(Database.S_OPERATION, "Sell");
					
					long r=mydatabase.insertOrThrow(Database.S_TABLE_NAME, null, cv);
					if(r>0){
						Toast.makeText(BillingDetails.this,"Silver used from the Inventory",Toast.LENGTH_LONG).show();
						g_qty=0;
					}
			    }
			}
	    });
	    alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			@Override
	        public void onClick(DialogInterface arg0, int arg1) {
				silver_total.setText("0.0");
			}
	    });
	    AlertDialog alertDialog = alertDialogBuilder.create();
	    alertDialog.setView(view);
	    alertDialog.show();
	}
	public void generateBill(View v){
		
		t+=Double.parseDouble(colour_stone.getText().toString());
		t=Math.ceil(t);
		total.setText(t+""); 

		mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		
		String col[]=new String[]{Database.C_NAME,Database.C_ADV};
		Cursor c=mydatabase.query(Database.C_TABLE_NAME, col, null, null, null, null, null);
		c.moveToFirst();
		double d=0;
		while(!c.isAfterLast()){
			if(c.getString(0).equals(name.getText().toString())){
				d = Double.parseDouble(c.getString(1));
			}
			c.moveToNext();
		}
		
		String a_paid="",a_pending="";
		if(payment_status.equals("Unpaid")){
			
			a_paid="0";
			a_pending=t+"";
			
		}else if(payment_status.equals("Paid")){
			if(d>=t){
					d-=t;
			}
			a_paid=t+"";
			a_pending="0";
		
		}else{
			if(d>=Double.parseDouble(partial)){
				d-=Double.parseDouble(partial);
			}
			a_paid=partial;
			a_pending=(t-Double.parseDouble(partial))+"";
		
		}
		
		
		
		ContentValues cs=new ContentValues();
		cs.put(Database.C_ADV,d+"");
		mydatabase.update(Database.C_TABLE_NAME, cs,Database.C_NAME+" = '"+name.getText().toString()+"'",null);

		ContentValues cv=new ContentValues();
		cv.put(Database.BI_BILL_NO, bill_no+"");
		cv.put(Database.BI_BILL_ITEM_COUNT, item_id+"");
		cv.put(Database.BI_BILL_TOTAL, t+"");
		cv.put(Database.BI_BILL_NAME, name.getText().toString());
		cv.put(Database.BI_BILL_DATE, date.getText().toString());
		cv.put(Database.BI_COLOUR_STONE_AMT, colour_stone.getText().toString());
		cv.put(Database.BI_DESC, desc.getText().toString());
		cv.put(Database.BI_PAYMENT_STATUS, payment_status);
		cv.put(Database.BI_AMT_PAID, a_paid);
		cv.put(Database.BI_AMT_PENDING,a_pending);
		
		
		long r=mydatabase.insertOrThrow(Database.BI_TABLE_NAME, null, cv);
		mydbhelper.close();
		if(r>0){
			
			item_id=0;
			Intent i=new Intent(this,BillSummary.class);
			i.putExtra("bill_no", bill_no_bd.getText().toString());
			i.putExtra("bill_total", t+"");
			startActivity(i);
		}
		
		
	}
	public void addItem(View v){
		if(name.getText().toString().equals("")){
			Toast.makeText(this, "Please Enter a name", Toast.LENGTH_SHORT).show();
		}else if(particulars.getText().toString().equals("")){
			Toast.makeText(this, "Enter Particulars Details", Toast.LENGTH_SHORT).show();
		}else{
		item_id+=1;
		mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		
		ContentValues cv=new ContentValues();
		cv.put(Database.BD_BILL_NO, bill_no);
		cv.put(Database.BD_NAME, name.getText().toString());
		cv.put(Database.BD_DATE, date.getText().toString());
		cv.put(Database.BD_ITEM_ID, item_id+"");
		cv.put(Database.BD_PARTICULARS, particulars.getText().toString());
		cv.put(Database.BD_GOLD_WT, gw);
		cv.put(Database.BD_GOLD_RATE, gr);
		cv.put(Database.BD_GOLD_TOTAL, gt);
		cv.put(Database.BD_DIAMOND_WT, dw);
		cv.put(Database.BD_DIAMOND_RATE, dr);
		cv.put(Database.BD_DIAMOND_TOTAL, dt);
		cv.put(Database.BD_SILVER_WT, sw);
		cv.put(Database.BD_SILVER_RATE, sr);
		cv.put(Database.BD_SILVER_TOTAL, st);
		cv.put(Database.BD_LABOUR_CHARGES,labour_details.getText().toString());
		double x=Double.parseDouble(labour_details.getText().toString());
		item_total=gt+dt+st+x;
		t+=item_total;
		cv.put(Database.BD_ITEM_TOTAL,item_total);
		long r=mydatabase.insertOrThrow(Database.BD_TABLE_NAME, null, cv);
		mydbhelper.close();
		if(r>0){
			
			gold_total.setText("0");
			diamond_total.setText("0");
			silver_total.setText("0");
			total.setText("0");
			particulars.setText("");
			labour_details.setText("0");
			colour_stone.setText("0");
			sw=sr=dw=dr=gw=gr="0";
			st=dt=gt=0;
			
			//name.setEnabled(false);
			add.setEnabled(true);
			//calculate.setEnabled(true);
			generate.setEnabled(true);
			//t+=Double.parseDouble(colour_stone.getText().toString());
			t=Math.ceil(t);
			total.setText(t+""); 
			
			
		}
		}
	}
	public void discardBill(View v){
		
		onBackPressed();
	}
	/*public void calculateBill(View v){
		
		
		add.setEnabled(false);
		calculate.setEnabled(false);
		generate.setEnabled(true);
		t+=Double.parseDouble(colour_stone.getText().toString());
		t=Math.ceil(t);
		total.setText(t+"");
		item_id=0;
	}*/
	
	
}
