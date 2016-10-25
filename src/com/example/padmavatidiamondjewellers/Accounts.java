package com.example.padmavatidiamondjewellers;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Accounts extends Activity {
	ListView l_sell,l_buy;
	double x=0,gold=0,diamond=0,silver=0,labourobt=0,labourpaid=0,expenses=0,investment=0,loan=0,total=0,
			 profit_loss,dummy=0,cap=0,p=0,cal_gold,cal_diamond,cal_silver;
	DBHelper mydbhelper;
	SQLiteDatabase mydatabase;
	int bcount,scount,slcount,sccount,sc,gcount,dcount;
	EditText add_cap_amt,add_cap_pur,
		calculate_accounts_gold,calculate_accounts_diamond,calculate_accounts_silver;
	TextView profit,amt_inv,current_balance,gold_balance,gold_sell_qty,gold_buy_qty,
		gold_sell_total,gold_buy_total,labour_obtained,labour_paid;
	String []  sdate,sqty,srate,stotal,sstatus,
		bdate,brate,bqty,btotal,bstatus;
	
	TextView summary_cash_on_hand,summary_investments,summary_loan,
		summary_gold,summary_diamond,summary_silver,summary_labour_obtained,
		summary_labour_paid,summary_expenses,summary_total;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accounts);
		
		ActionBar actionBar=this.getActionBar();
		actionBar.setIcon(R.drawable.pdj);
		actionBar.setTitle("ACCOUNTS");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.menubar));
		
		current_balance=(TextView)findViewById(R.id.acc_current_balance);
		profit=(TextView)findViewById(R.id.acc_profit);
		
		x=0;
		
		l_sell=(ListView)findViewById(R.id.l_sell);
		l_buy=(ListView)findViewById(R.id.l_buy);
		
		fetchData();
		calculateBal();
		
		l_sell.setAdapter(new AccountsSellAdapter(this, sdate, sstatus, sqty, srate, stotal));
		l_buy.setAdapter(new AccountsBuyAdapter(this, bdate, bstatus, bqty, brate, btotal));
		
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_accounts, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.item1:
            currentStatus();
        	return true;
        
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	public void currentStatus(){
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Enter current value for") ; 
		LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=layoutInflater.inflate(R.layout.calculate_accounts,null);
		calculate_accounts_gold=(EditText)view.findViewById(R.id.calculate_gold);
		calculate_accounts_diamond=(EditText)view.findViewById(R.id.calculate_diamond);
		calculate_accounts_silver=(EditText)view.findViewById(R.id.calculate_silver);
		calculate_accounts_gold.setText("0");
		calculate_accounts_diamond.setText("0");
		calculate_accounts_silver.setText("0");
		
		alertDialogBuilder.setPositiveButton("Done",new DialogInterface.OnClickListener() {
		
			@Override
	        public void onClick(DialogInterface arg0, int arg1) {
	        	 //deposit_no.setText("");
				cal_gold=Double.parseDouble(calculate_accounts_gold.getText().toString());
				cal_diamond=Double.parseDouble(calculate_accounts_diamond.getText().toString());
				cal_silver=Double.parseDouble(calculate_accounts_silver.getText().toString());
				
				billSummary();
			}

			private void billSummary() {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Accounts.this);
				alertDialogBuilder.setTitle("Account Summary") ; 
				LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view=layoutInflater.inflate(R.layout.accounts_summary,null);
				summary_cash_on_hand=(TextView)view.findViewById(R.id.cal_cash_on_hand);
				summary_investments=(TextView)view.findViewById(R.id.cal_inv);
				summary_loan=(TextView)view.findViewById(R.id.cal_loan);
				summary_gold=(TextView)view.findViewById(R.id.cal_gold_stock);
				summary_diamond=(TextView)view.findViewById(R.id.cal_diamond_stock);
				summary_silver=(TextView)view.findViewById(R.id.cal_silver_stock);
				summary_labour_obtained=(TextView)view.findViewById(R.id.cal_labour_obtained);
				summary_labour_paid=(TextView)view.findViewById(R.id.cal_labour_paid);
				summary_expenses=(TextView)view.findViewById(R.id.cal_expenses);
				summary_total=(TextView)view.findViewById(R.id.cal_total);
				
				calculateBal();
				
				summary_cash_on_hand.setText("Rs. "+cap);
				summary_investments.setText("Rs. "+investment);
				summary_loan.setText("Rs. "+loan);
				summary_gold.setText("Rs. "+gold);
				summary_diamond.setText("Rs. "+diamond);
				summary_silver.setText("Rs. "+silver);
				summary_labour_obtained.setText("Rs. "+labourobt);
				summary_labour_paid.setText("Rs. "+labourpaid);
				summary_expenses.setText("Rs. "+expenses);
				summary_total.setText("Rs. "+total);
				
				alertDialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					@Override
			        public void onClick(DialogInterface arg0, int arg1) {
			        	 
						onCreate(null);
						
					}
			    });
			    
			    AlertDialog alertDialog = alertDialogBuilder.create();
			    alertDialog.setView(view);
			    alertDialog.show();
				}
	    });
	    
	    AlertDialog alertDialog = alertDialogBuilder.create();
	    alertDialog.setView(view);
	    alertDialog.show();
	    
	}
	private void fetchData() {
		// TODO Auto-generated method stub
		mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		Cursor cg_count= mydatabase.rawQuery("select * from "+Database.G_TABLE_NAME+" where "+Database.G_OPERATION +" = 'Sell'",null);
		gcount=cg_count.getCount();	
		Cursor cs_count= mydatabase.rawQuery("select * from "+Database.S_TABLE_NAME+" where "+Database.S_OPERATION +" = 'Sell'",null);
		scount=cs_count.getCount();	
		Cursor cd_count= mydatabase.rawQuery("select * from "+Database.D_TABLE_NAME+" where "+Database.D_OPERATION +" = 'Sell'",null);
		dcount=cd_count.getCount();	
		Cursor cl_count= mydatabase.rawQuery("select * from "+Database.BD_TABLE_NAME+" where "+Database.BD_LABOUR_CHARGES +" <> '0'",null);
		slcount=cl_count.getCount();
		Cursor cc_count= mydatabase.rawQuery("select * from "+Database.BI_TABLE_NAME+" where "+Database.BI_COLOUR_STONE_AMT +" <> '0' ",null);
		sccount=cc_count.getCount();
		scount=scount+gcount+dcount+slcount+sccount;
		//scount+=slcount;
		sdate=new String[scount];
		sqty=new String[scount];
		srate=new String[scount];
		stotal=new String[scount];
		sstatus=new String[scount];
		
		
		Cursor cb_count= mydatabase.rawQuery("select * from "+Database.G_TABLE_NAME+" where "+Database.G_OPERATION +" = 'Buy'",null);
		bcount=cb_count.getCount();
		Cursor cbs_count= mydatabase.rawQuery("select * from "+Database.S_TABLE_NAME+" where "+Database.S_OPERATION +" = 'Buy'",null);
		bcount+=cbs_count.getCount();
		Cursor cbd_count= mydatabase.rawQuery("select * from "+Database.D_TABLE_NAME+" where "+Database.D_OPERATION +" = 'Buy'",null);
		bcount+=cbd_count.getCount();
		bdate=new String[bcount];
		bqty=new String[bcount];
		brate=new String[bcount];
		btotal=new String[bcount];
		bstatus=new String[bcount];
		
		int i=0,j=0;
		String col1[]=new String[]{Database.G_DATE,Database.G_OPERATION,Database.G_QTY,Database.G_RATE,Database.G_TOTAL,Database.G_STATUS};
		Cursor c1=mydatabase.query(Database.G_TABLE_NAME, col1, null, null, null, null, null);
		for(c1.moveToLast();!c1.isBeforeFirst();c1.moveToPrevious()){
			if(c1.getString(1).equals("Sell")){
				sdate[i]=c1.getString(0);
				sqty[i]=c1.getString(2);
				srate[i]=c1.getString(3);
				stotal[i]=c1.getString(4);
				sstatus[i]="Gold";
				i++;
			}else{
				bdate[j]=c1.getString(0);
				bqty[j]=c1.getString(2);
				brate[j]=c1.getString(3);
				btotal[j]=c1.getString(4);
				bstatus[j]=c1.getString(5);
				j++;
			}
		}
		String col2[]=new String[]{Database.D_DATE,Database.D_OPERATION,
				Database.D_QTY,Database.D_RATE,Database.D_TOTAL,Database.D_STATUS};
		Cursor c2=mydatabase.query(Database.D_TABLE_NAME, col2, null, null, null, null, null);
		for(c2.moveToLast();!c2.isBeforeFirst();c2.moveToPrevious()){
			if(c2.getString(1).equals("Sell")){
				sdate[i]=c2.getString(0);
				sqty[i]=c2.getString(2);
				srate[i]=c2.getString(3);
				stotal[i]=c2.getString(4);
				sstatus[i]="Diamond";
				i++;
			}else{
				bdate[j]=c2.getString(0);
				bqty[j]=c2.getString(2);
				brate[j]=c2.getString(3);
				btotal[j]=c2.getString(4);
				bstatus[j]=c2.getString(5);
				j++;
			}
		}
		String col3[]=new String[]{Database.S_DATE,Database.S_OPERATION,Database.S_QTY,
				Database.S_RATE,Database.S_TOTAL,Database.S_STATUS};
		Cursor c3=mydatabase.query(Database.S_TABLE_NAME, col3, null, null, null, null, null);
		for(c3.moveToLast();!c3.isBeforeFirst();c3.moveToPrevious()){
			if(c3.getString(1).equals("Sell")){
				sdate[i]=c3.getString(0);
				sqty[i]=c3.getString(2);
				srate[i]=c3.getString(3);
				stotal[i]=c3.getString(4);
				sstatus[i]="Silver";
				i++;
			}else{
				bdate[j]=c3.getString(0);
				bqty[j]=c3.getString(2);
				brate[j]=c3.getString(3);
				btotal[j]=c3.getString(4);
				bstatus[j]=c3.getString(5);
				j++;
			}
		}
		String col[]=new String[]{Database.BD_DATE,Database.BD_PARTICULARS,
				Database.BD_BILL_NO,Database.BD_LABOUR_CHARGES};
		Cursor c=mydatabase.query(Database.BD_TABLE_NAME, col, null, null, null, null, null);
		for(c.moveToLast();!c.isBeforeFirst();c.moveToPrevious()){
			if(Double.parseDouble(c.getString(3))>0){
				sdate[i]=c.getString(0);
				sqty[i]=c.getString(1);
				srate[i]=c.getString(2);
				stotal[i]=c.getString(3);
				sstatus[i]="Labour";
			//lo+=Double.parseDouble(c.getString(3));
			i++;
			}
		}
		//labour_obtained.setText("Rs. "+lo);
		
		String col4[]=new String[]{Database.BI_BILL_DATE,Database.BI_BILL_NO,Database.BI_COLOUR_STONE_AMT};
		Cursor c4=mydatabase.query(Database.BI_TABLE_NAME, col4, null, null, null, null, null);
		for(c4.moveToLast();!c4.isBeforeFirst();c4.moveToPrevious()){
			if(Double.parseDouble(c4.getString(2))>0){
				sdate[i]=c4.getString(0);
				sqty[i]=c4.getString(1);
				//srate[i]=c4.getString(2);
				stotal[i]=c4.getString(2);
				sstatus[i]="Colour Stones";
				//lo+=Double.parseDouble(c.getString(3));
				i++;
			}
		}
	}
	public void calculateBal(){
		gold=0;diamond=0;silver=0;labourobt=0;labourpaid=0;expenses=0;investment=0;loan=0;total=0;
		dummy=0;cap=0;p=0;
		mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		
		//Fetching Total Investments
		String col1[]=new String[]{Database.CAP_AMT,Database.CAP_TYPE};
		Cursor c1=mydatabase.query(Database.CAP_TABLE_NAME, col1, null, null, null, null, null);
		for(c1.moveToLast();!c1.isBeforeFirst();c1.moveToPrevious()){
			if(c1.getString(1).equals("Investment")){
				investment+=Double.parseDouble(c1.getString(0));
			}
		}
		
		p=investment;
		profit_loss+=investment;		
		
		
		//Fetching Bill Amount
		String col3[]=new String[]{Database.BI_AMT_PAID,Database.BI_BILL_TOTAL};
		Cursor c=mydatabase.query(Database.BI_TABLE_NAME, col3, null, null, null, null, null);
		for(c.moveToLast();!c.isBeforeFirst();c.moveToPrevious()){
			dummy+=Double.parseDouble(c.getString(1));
			cap+=Double.parseDouble(c.getString(0));
		}
		profit_loss+=dummy;
		cap+=p;
		
		dummy=0;
		//Fetching Advance Payments
		String col4[]=new String[]{Database.C_ADV};
		Cursor c4=mydatabase.query(Database.C_TABLE_NAME, col4, null, null, null, null, null);
		for(c4.moveToLast();!c4.isBeforeFirst();c4.moveToPrevious()){
			
			dummy+=Double.parseDouble(c4.getString(0));
		}
		loan=dummy;
		cap+=dummy;
		dummy=0;
		
		
		String col[]=new String[]{Database.G_AMT_PAID,Database.G_STATUS,Database.G_OPERATION};
		Cursor c2=mydatabase.query(Database.G_TABLE_NAME, col, null, null, null, null, null);
		for(c2.moveToLast();!c2.isBeforeFirst();c2.moveToPrevious()){
			if(c2.getString(2).equals("Buy")){
				
					dummy+=Double.parseDouble(c2.getString(0));
				
			}
		}
		
		profit_loss-=dummy;
		cap-=dummy;;
		
		dummy=0;
		//Labour Paid/Expenses
		
		String coll[]=new String[]{Database.L_AMT,Database.L_TYPE};
		Cursor cl=mydatabase.query(Database.L_TABLE_NAME, coll, null, null, null, null, null);
		for(cl.moveToLast();!cl.isBeforeFirst();cl.moveToPrevious()){
			
			dummy+=Double.parseDouble(cl.getString(0));
			if(cl.getString(1).equals("Labour")){
				labourpaid+=Double.parseDouble(cl.getString(0));
			}else{
				expenses+=Double.parseDouble(cl.getString(0));
			}
		}
		profit_loss-=dummy;
		cap-=dummy;
		//Insert Current Balance
	    ContentValues cs=new ContentValues();
		cs.put(Database.BAL_AMT,cap);
		mydatabase.insertOrThrow(Database.BAL_TABLE_NAME, null, cs);
		
		String col11[]=new String[]{Database.BD_LABOUR_CHARGES};
		Cursor cl1=mydatabase.query(Database.BD_TABLE_NAME, col11, null, null, null, null, null);
		for(cl1.moveToLast();!cl1.isBeforeFirst();cl1.moveToPrevious()){
			labourobt+=Double.parseDouble(cl1.getString(0));
		}
		
		String co[]=new String[]{Database.G_QTY,Database.G_RATE,Database.G_OPERATION};
		Cursor cu=mydatabase.query(Database.G_TABLE_NAME, co, null, null, null, null, null);
		for(cu.moveToLast();!cu.isBeforeFirst();cu.moveToPrevious()){
			if(cu.getString(2).equals("Buy")){
				
				gold+=Double.parseDouble(cu.getString(0));
				
			}else{
				
				gold-=Double.parseDouble(cu.getString(0));
				
			}
		}
		gold=gold*cal_gold;
		
		String cod[]=new String[]{Database.D_QTY,Database.D_RATE,Database.D_OPERATION};
		Cursor cud=mydatabase.query(Database.D_TABLE_NAME, cod, null, null, null, null, null);
		for(cud.moveToLast();!cud.isBeforeFirst();cud.moveToPrevious()){
			if(cud.getString(2).equals("Buy")){
				
				diamond+=Double.parseDouble(cud.getString(0));
				
			}else{
				
				diamond-=Double.parseDouble(cud.getString(0));
				
			}
		}
		diamond=diamond*cal_diamond;
		
		String cos[]=new String[]{Database.S_QTY,Database.S_RATE,Database.S_OPERATION};
		Cursor cus=mydatabase.query(Database.S_TABLE_NAME, cos, null, null, null, null, null);
		for(cus.moveToLast();!cus.isBeforeFirst();cus.moveToPrevious()){
			if(cus.getString(2).equals("Buy")){
				
				silver+=Double.parseDouble(cus.getString(0));
				
			}else{
				
				silver-=Double.parseDouble(cus.getString(0));
				
			}
		}
		silver=silver*cal_silver;
		
		p=profit_loss-p;
		profit.setTextColor(Color.GREEN);
		total=p+gold+diamond+silver;
		if(p<0){
			p*=-1;
			profit.setTextColor(Color.RED);

		}
		profit.setText(p+"");
		current_balance.setText(cap+"");
		
		profit_loss=0;
		dummy=0;
		mydatabase.close();
	}
}
