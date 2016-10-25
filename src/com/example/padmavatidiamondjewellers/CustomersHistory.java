package com.example.padmavatidiamondjewellers;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CustomersHistory extends Activity {
	ListView l;
	String name,bill_nos;
	SQLiteDatabase mydatabase;
	int count;
	double partial=0,d=0;;
	EditText partially_paid;
	double amt_pending1,amt_paid1,total1,amt_pending,amt_paid,amt_total;
	String names[],bill_no[],date[],item_no[],item_total[],statuss[],total_amt[],at_paid[];
	double pending;
	double dp=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customers_history);
		l=(ListView)findViewById(R.id.listView_ch);
		Intent in=getIntent();
		
		ActionBar actionBar=this.getActionBar();
		actionBar.setIcon(R.drawable.pdj);
		
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.menubar));
		
		name=in.getStringExtra("c_name");
		
		actionBar.setTitle(name.toUpperCase() );
		
		DBHelper mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		Cursor c_count= mydatabase.rawQuery("select distinct "+Database.BD_BILL_NO+" from "+Database.BD_TABLE_NAME+" where "+Database.BD_NAME+" = '"+name+"'",null);
		count=c_count.getCount();
		
		amt_pending1=0;
		amt_paid1=0;
		total1=0;
		amt_pending=0;
		amt_paid=0;
		amt_total=0;
		bill_no=new String[count];
		date=new String[count];
		names=new String[count];
		item_no=new String[count];
		item_total =new String[count];
		statuss =new String[count];
		total_amt =new String[count];
		at_paid =new String[count];
		String col1[]=new String[]{Database.BI_BILL_NAME,
				Database.BI_BILL_DATE,Database.BI_BILL_NO,Database.BI_BILL_ITEM_COUNT,Database.BI_BILL_TOTAL,Database.BI_AMT_PAID,Database.BI_AMT_PENDING,Database.BI_PAYMENT_STATUS};
		Cursor c1=mydatabase.query(Database.BI_TABLE_NAME, col1, null, null, null, null, null);
		int i=0;
		for(c1.moveToLast();!c1.isBeforeFirst();c1.moveToPrevious()){
			if(name.equals(c1.getString(0))){
				names[i]=c1.getString(0);
					date[i]=c1.getString(1);
			bill_no[i]=c1.getString(2);
			item_no[i]=c1.getString(3);
			item_total[i]=c1.getString(4);
			at_paid[i]=c1.getString(5);
			total_amt[i]=c1.getString(6);
			statuss[i]=c1.getString(7);
			i++;
			
			}
		}
		l.addHeaderView(new View(this));
	    l.addFooterView(new View(this));
		l.setAdapter(new TransactionAdapter(this,names,date,bill_no,item_no,item_total,at_paid,total_amt,statuss));
		registerForContextMenu(l);
		OnItemClickListener o=new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				
				Intent i=new Intent(CustomersHistory.this,BillSummary.class);
				i.putExtra("bill_no", bill_no[pos-1]);
				i.putExtra("bill_total", item_total[pos-1]);
				startActivity(i);
				
			}
			
		};
		l.setOnItemClickListener(o);
	}
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)  
    {  
            super.onCreateContextMenu(menu, v, menuInfo); 
            menu.setHeaderTitle("Select The Action");    
            menu.add(0, v.getId(), 0, "Paid/Unpaid");  
            menu.add(0, v.getId(), 0, "Partially Paid");   
            
    }   
    @Override    
    public boolean onContextItemSelected(MenuItem item){ 
    	String status="";
    	AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = info.position;/*what item was selected is ListView*/
        String col[]=new String[]{Database.BAL_AMT};
		Cursor c1=mydatabase.query(Database.BAL_TABLE_NAME, col, null, null, null, null, null);
		
		c1.moveToLast();
		d=0;
		while(!c1.isAfterLast()){
			

		d=Double.parseDouble(c1.getString(0));
		c1.moveToNext();
		}
    	status=statuss[id-1];
		bill_nos=bill_no[id-1];
		amt_paid=Double.parseDouble(at_paid[id-1]);
		amt_pending=Double.parseDouble(total_amt[id-1]);
		amt_total=Double.parseDouble(item_total[id-1]);
		amt_pending1=amt_pending;
		amt_paid1=amt_paid;
		total1=amt_total;
		String col3[]=new String[]{Database.C_NAME,Database.C_ADV};
		Cursor c3=mydatabase.query(Database.C_TABLE_NAME, col3, null, null, null, null, null);
		c3.moveToFirst();
		dp=0;
		while(!c3.isAfterLast()){
			if(c3.getString(0).equals(name)){
				dp = Double.parseDouble(c3.getString(1));
			}
			c3.moveToNext();
		}
    	if(item.getTitle()=="Paid/Unpaid"){  
    		

	    		
    		if(status.equals("Paid")){
    			
				dp+=amt_total;
    			
    			status="Unpaid";
    			amt_pending=amt_paid;
    			amt_paid=0;
    		}else if(status.equals("Unpaid")){
    			if(dp>=amt_pending){
					dp-=amt_pending;
    			}else{
    				amt_pending-=dp;
    				dp=0;
    			}
    			status="Paid";
    			amt_paid=amt_pending;
    			amt_pending=0;
    			
    		}else{
    			if(dp>=amt_pending){
					dp-=amt_pending;
    			}else{
    				amt_pending-=dp;
    				dp=0;
    			}
    			status="Paid";
    			amt_paid=amt_total;
    			amt_pending=0;
    		}
	    		ContentValues cxs=new ContentValues();
	    		cxs.put(Database.C_ADV,dp+"");
	    		mydatabase.update(Database.C_TABLE_NAME, cxs,Database.C_NAME+" = '"+name+"'",null);	
	    			ContentValues cs=new ContentValues();
    		cs.put(Database.BI_PAYMENT_STATUS,status);
    		cs.put(Database.BI_AMT_PAID,amt_paid);
    		cs.put(Database.BI_AMT_PENDING,amt_pending);
    		
    		mydatabase.update(Database.BI_TABLE_NAME,cs,Database.BI_BILL_NO+"='"+bill_nos+"'",null);
    		onCreate(null);
    		
            }    
            else if(item.getTitle()=="Partially Paid"){  
                if(status.equals("Unpaid") || status.equals("Partially Paid")){
                	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CustomersHistory.this);
            		alertDialogBuilder.setTitle("Payment Details") ; 
            		LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            		View view=layoutInflater.inflate(R.layout.payment_details,null);
            		alertDialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            			@Override
            	        public void onClick(DialogInterface arg0, int arg1) {
            				Dialog f=(Dialog)arg0;
            				partially_paid=(EditText)f.findViewById(R.id.bd_partially_paid_amt);
            				partial=Double.parseDouble(partially_paid.getText().toString());
            				double p=partial;
                        	
            				
            					if(dp>=p){
            						dp-=p;
            	    			}	
                        		ContentValues cxs=new ContentValues();
                	    		cxs.put(Database.C_ADV,dp+"");
                	    		mydatabase.update(Database.C_TABLE_NAME, cxs,Database.C_NAME+" = '"+name+"'",null);	
                            amt_pending1-=p;
                            
                        	amt_paid1+=p;
                        	String statu="Partially Paid";
                                                 	
                            if(amt_pending1<0){
                            	Toast.makeText(CustomersHistory.this,"Cannot Proceed",Toast.LENGTH_SHORT).show();
                            }else{
                        		if(amt_pending1==0){
                            		statu="Paid";
                            		amt_paid1=total1;
                            		amt_pending1=0;
                        		}
                        		ContentValues cs=new ContentValues();
                        		cs.put(Database.BI_PAYMENT_STATUS,statu);
                        		cs.put(Database.BI_AMT_PAID,amt_paid1+"");
                        		cs.put(Database.BI_AMT_PENDING,amt_pending1+"");

                        		mydatabase.update(Database.BI_TABLE_NAME,cs,Database.BI_BILL_NO+"='"+bill_nos+"'",null);
                        		
                            }
                            
                    		onCreate(null);
            				
                    		
            			}
            	    });
            	    alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            			@Override
            	        public void onClick(DialogInterface arg0, int arg1) {
            				partial=0;
            				amt_pending1=0;

                        	amt_paid1=0;
            			}
            	    });
            	    AlertDialog alertDialog = alertDialogBuilder.create();
            	    alertDialog.setView(view);
            	    alertDialog.show(); 
                
                
                }else if(status.equals("Paid")){
        			Toast.makeText(this, "Amount already Paid", Toast.LENGTH_SHORT).show();

                }
                
            }else{
            		
            		return false;  
            }
    	 	
          return true;    
      }
	
}
