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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class ViewDiamondInventory extends Activity {
	DBHelper mydbhelper;
	SQLiteDatabase mydatabase;
	int count;
	ListView l;
	double partial=0;
	EditText partially_paid;
	double amt_pending1,amt_paid1,total1;
	String d_bill_no;
	double d=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_diamond_inventory);
		Intent in = getIntent();
		
		ActionBar actionBar=this.getActionBar();
		actionBar.setIcon(R.drawable.pdj);
		actionBar.setTitle("DIAMOND INVENTORY");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.menubar));
		
		view_buy();
		
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_add:
            
        	Intent i=new Intent(this,DiamondInventory.class);
    		startActivity(i);
            return true;
        
              
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
		
		private void view_buy() {
			// TODO Auto-generated method stub
			mydbhelper=new DBHelper(ViewDiamondInventory.this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
			mydatabase=mydbhelper.getWritableDatabase();
			Cursor c_count= mydatabase.rawQuery("select * from "+Database.D_TABLE_NAME+" where "+Database.D_OPERATION +" = 'Buy'",null);
			
			count=c_count.getCount();
			String op[]=new String[count];
			String date[]=new String[count];
			String qty[]=new String[count];
			String rate[]=new String[count];
			String total[]=new String[count];
			String status[]=new String[count];
			String bought_from[]=new String[count];
			String amt_paid[]=new String[count];
			String amt_pending[]=new String[count];
			
			int i=0;
			String col1[]=new String[]{Database.D_DATE,Database.D_OPERATION,
					Database.D_QTY,Database.D_RATE,
					Database.D_TOTAL,Database.D_STATUS,
					Database.D_BOUGHT_FROM,Database.D_AMT_PAID,Database.D_AMT_PENDING};
			Cursor c1=mydatabase.query(Database.D_TABLE_NAME, col1, null, null, null, null, null);
			for(c1.moveToLast();!c1.isBeforeFirst();c1.moveToPrevious()){
				if(c1.getString(1).equals("Buy")){
				date[i]=c1.getString(0);
				op[i]=c1.getString(1);
				qty[i]=c1.getString(2);
				rate[i]=c1.getString(3);
				total[i]=c1.getString(4);
				status[i]=c1.getString(5);
				bought_from[i]=c1.getString(6);
				amt_paid[i]=c1.getString(7);
				amt_pending[i]=c1.getString(8);
				i++;
				}
			}
			l=(ListView)findViewById(R.id.diamond_listView);
			l.setAdapter(new DiamondAdapter(ViewDiamondInventory.this,op,date,qty,rate,total,status,count,bought_from,amt_paid,amt_pending));
			registerForContextMenu(l);

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
			String cols[]=new String[]{Database.D_STATUS,Database.D_TOTAL,
					Database.D_AMT_PAID,Database.D_AMT_PENDING,Database.D_KEY};
			Cursor c=mydatabase.query(Database.D_TABLE_NAME, cols, null, null, null, null, null);
			int co=c.getCount();
			c.moveToPosition(co-id-1);
			status=c.getString(0);
			d_bill_no=c.getString(4);
			double amt_paid=Double.parseDouble(c.getString(2));
			double amt_pending=Double.parseDouble(c.getString(3));
			double amt_total=Double.parseDouble(c.getString(1));
			amt_pending1=amt_pending;
			amt_paid1=amt_paid;
			total1=amt_total;
			String col[]=new String[]{Database.BAL_AMT};
			Cursor c1=mydatabase.query(Database.BAL_TABLE_NAME, col, null, null, null, null, null);
			
			c1.moveToLast();
			d=0;
			while(!c1.isAfterLast()){
				

			d=Double.parseDouble(c1.getString(0));
			c1.moveToNext();
			}
			
			
	    	if(item.getTitle()=="Paid/Unpaid"){  
	    		
	    		if(status.equals("Paid")){
	    			status="Unpaid";
	    			amt_pending=amt_paid;
	    			amt_paid=0;
	    		}else if(status.equals("Unpaid")&&(d>=amt_pending)){
	    			
	    			status="Paid";
	    			amt_paid=amt_pending;
	    			amt_pending=0;
	    			
	    		}else if(d>=amt_pending){
	    			status="Paid";
	    			amt_paid=amt_total;
	    			amt_pending=0;
	    		}else{
                	Toast.makeText(ViewDiamondInventory.this,"Not enough Capital",Toast.LENGTH_LONG).show();
                	onBackPressed();

	    		}
	    		ContentValues cs=new ContentValues();
	    		cs.put(Database.D_STATUS,status);
	    		cs.put(Database.D_AMT_PAID,amt_paid);
	    		cs.put(Database.D_AMT_PENDING,amt_pending);
	    		
	    		mydatabase.update(Database.D_TABLE_NAME,cs,Database.D_KEY+"='"+d_bill_no+"'",null);
	    		onCreate(null);
	    		
	            }    
	            else if(item.getTitle()=="Partially Paid"){  
	                if(status.equals("Unpaid") || status.equals("Partially Paid")){
	                	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewDiamondInventory.this);
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
	                        	if(d>=p){

	                            amt_pending1-=p;
	                            

	                        	amt_paid1+=p;
	                        	String statu="Partially Paid";
	                                                 	
	                            if(amt_pending1<0){
	                            	Toast.makeText(ViewDiamondInventory.this,"Cannot Proceed",Toast.LENGTH_SHORT).show();
	                            }else{
	                        		if(amt_pending1==0){
	                            		statu="Paid";
	                            		amt_paid1=total1;
	                            		amt_pending1=0;
	                        		}
	                        		ContentValues cs=new ContentValues();
	                	    		cs.put(Database.D_STATUS,statu);
	                	    		cs.put(Database.D_AMT_PAID,amt_paid1);
	                	    		cs.put(Database.D_AMT_PENDING,amt_pending1);
	                	    		
	                	    		mydatabase.update(Database.D_TABLE_NAME,cs,Database.D_KEY+"='"+d_bill_no+"'",null);
	                            }
	                            
	                    		onCreate(null);
	                        	}else{
	                            	Toast.makeText(ViewDiamondInventory.this,"Not enough Capital",Toast.LENGTH_LONG).show();
	                            	onBackPressed();
	                        	}
	                    		
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
    	    		mydatabase.delete(Database.D_TABLE_NAME,Database.D_KEY+"='"+d_bill_no+"'",null);

	            	
	        		onCreate(null);
	        		
	            }else{
	            		
	            		return false;  
	            }
	    	 	
	          return true;    
	      }
		

	
		}
