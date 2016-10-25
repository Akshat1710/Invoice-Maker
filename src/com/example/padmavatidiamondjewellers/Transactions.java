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
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Transactions extends Activity {
	ListView l;
	SQLiteDatabase mydatabase;
	int count;
	double partial=0,d=0;
	EditText partially_paid;
	double amt_pending1,amt_paid1,total1;
	String bill_no,name;
	double dp=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transactions);
		
		ActionBar actionBar=this.getActionBar();
		actionBar.setIcon(R.drawable.pdj);
		actionBar.setTitle(" TRANSACTIONS");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.menubar));
		
		l=(ListView)findViewById(R.id.listView_tr);
		DBHelper mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		Cursor c_count= mydatabase.rawQuery("select distinct "+Database.BI_BILL_NO+" from "+Database.BI_TABLE_NAME,null);
		count=c_count.getCount();
		amt_pending1=0;
		amt_paid1=0;
		total1=0;
		String bill_no[]=new String[count];
		String date[]=new String[count];
		String name[]=new String[count];
		String item_no[]=new String[count];
		String item_total[]=new String[count];
		String status[]=new String[count];
		String total_amt[]=new String[count];
		String amt_paid[]=new String[count];
		try{
		String col1[]=new String[]{Database.BI_BILL_NAME,
				Database.BI_BILL_DATE,Database.BI_BILL_NO,Database.BI_BILL_ITEM_COUNT,Database.BI_BILL_TOTAL,Database.BI_AMT_PAID,Database.BI_AMT_PENDING,Database.BI_PAYMENT_STATUS};
		Cursor c1=mydatabase.query(Database.BI_TABLE_NAME, col1, null, null, null, null, null);
		int i=0;
		for(c1.moveToLast();!c1.isBeforeFirst();c1.moveToPrevious()){
			//Toast.makeText(this, c1.getString(0)+"\t"+c1.getString(1)+"\t"+c1.getString(2), Toast.LENGTH_SHORT).show();
			name[i]=c1.getString(0);
			date[i]=c1.getString(1);
			bill_no[i]=c1.getString(2);
			item_no[i]=c1.getString(3);
			item_total[i]=c1.getString(4);
			amt_paid[i]=c1.getString(5);
			total_amt[i]=c1.getString(6);
			status[i]=c1.getString(7);
			i++;
			//System.out.println(c1.getString(0)+"\t"+c1.getString(1)+"\t"+c1.getString(2));
		}
		}catch(Exception e){
			
		}
		l.addHeaderView(new View(this));
	    l.addFooterView(new View(this));
		l.setAdapter(new TransactionAdapter(this,name,date,bill_no,item_no,item_total,amt_paid,total_amt,status));
		registerForContextMenu(l);
		OnItemClickListener o=new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				DBHelper mydbhelper=new DBHelper(Transactions.this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
				mydatabase=mydbhelper.getWritableDatabase();
				String cols[]=new String[]{Database.BI_BILL_NO,Database.BI_BILL_TOTAL};
				Cursor c=mydatabase.query(Database.BI_TABLE_NAME, cols, null, null, null, null, null);
				int co=c.getCount();
				c.moveToPosition(co-pos);
				Intent i=new Intent(Transactions.this,BillSummary.class);
				i.putExtra("bill_no", c.getString(0));
				i.putExtra("bill_total", c.getString(1));
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
            menu.add(0, v.getId(), 0, "Delete"); 
    }   
    @Override    
    public boolean onContextItemSelected(MenuItem item){ 
    	String status="";
    	AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = info.position;/*what item was selected is ListView*/
        DBHelper mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		String cols[]=new String[]{Database.BI_PAYMENT_STATUS,Database.BI_BILL_NO,Database.BI_AMT_PAID,
				Database.BI_AMT_PENDING,Database.BI_BILL_TOTAL,Database.BI_BILL_NAME};
		Cursor c=mydatabase.query(Database.BI_TABLE_NAME, cols, null, null, null, null, null);
		int co=c.getCount();
		c.moveToPosition(co-id);
		String col[]=new String[]{Database.BAL_AMT};
		Cursor c1=mydatabase.query(Database.BAL_TABLE_NAME, col, null, null, null, null, null);
		
		c1.moveToLast();
		d=0;
		while(!c1.isAfterLast()){
			

		d=Double.parseDouble(c1.getString(0));
		c1.moveToNext();
		}
		status=c.getString(0);
		bill_no=c.getString(1);
		name=c.getString(5);
		double amt_paid=Double.parseDouble(c.getString(2));
		double amt_pending=Double.parseDouble(c.getString(3));
		double amt_total=Double.parseDouble(c.getString(4));
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
    		
    		mydatabase.update(Database.BI_TABLE_NAME,cs,Database.BI_BILL_NO+"='"+bill_no+"'",null);
    		onCreate(null);
    		
            }    
            else if(item.getTitle()=="Partially Paid"){  
                if(status.equals("Unpaid") || status.equals("Partially Paid")){
                	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Transactions.this);
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
                            	Toast.makeText(Transactions.this,"Cannot Proceed",Toast.LENGTH_SHORT).show();
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

                        		mydatabase.update(Database.BI_TABLE_NAME,cs,Database.BI_BILL_NO+"='"+bill_no+"'",null);
                        		
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
                
            }else if(item.getTitle()=="Delete"){
            	mydatabase.delete(Database.BI_TABLE_NAME,Database.BI_BILL_NO+"='"+bill_no+"'",null);
            	mydatabase.delete(Database.BD_TABLE_NAME,Database.BD_BILL_NO+"='"+bill_no+"'",null);
            	mydatabase.delete(Database.G_TABLE_NAME,Database.G_STATUS+"='(Bill No : "+bill_no+" )'",null);
        		onCreate(null);
        		
            }else{
            		
            		return false;  
            }
    	 	
          return true;    
      }
	
   }

	

