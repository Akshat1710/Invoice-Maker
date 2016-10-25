package com.example.padmavatidiamondjewellers;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class Labour extends Activity {
	ListView l_labour,l_expense;
	String [] ldate,lto,lamt;
	String [] edate,eto,eamt;
	int lc,ec;
	DBHelper mydbhelper;
	SQLiteDatabase mydatabase;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_labour);
		Intent in=getIntent();
		
		ActionBar actionBar=this.getActionBar();
		actionBar.setIcon(R.drawable.pdj);
		actionBar.setTitle("LABOUR");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.menubar));
		
		l_labour=(ListView)findViewById(R.id.l_labour);
		l_expense=(ListView)findViewById(R.id.l_expense);
		fetchData();
		l_labour.setAdapter(new LabourAdapter(this,ldate,lto,lamt));
		l_expense.setAdapter(new ExpenseAdapter(this,edate,eto,eamt));
		
	}
	public boolean onCreateOptionsMenu(Menu menu) {  
        
        getMenuInflater().inflate(R.menu.menu_labour, menu);//Menu Resource, Menu  
        return true;  
    }
@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_labour:
            // search action
        	pay();
            return true;
        
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	public void pay(){
		Intent i=new Intent(this,LabourPay.class);
		startActivity(i);
	}
	private void fetchData() {
		// TODO Auto-generated method stub
		mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		Cursor cs_count= mydatabase.rawQuery("select * from "+Database.L_TABLE_NAME+" where "+Database.L_TYPE +" = 'Labour'",null);
		lc=cs_count.getCount();		
		ldate=new String[lc];
		lto=new String[lc];
		lamt=new String[lc];
		
		Cursor c_count= mydatabase.rawQuery("select * from "+Database.L_TABLE_NAME+" where "+Database.L_TYPE +" = 'Expense'",null);
		ec=c_count.getCount();		
		edate=new String[ec];
		eto=new String[ec];
		eamt=new String[ec];
		
		int i=0,j=0;
		String col1[]=new String[]{Database.L_DATE,Database.L_TO,Database.L_AMT,Database.L_TYPE};
		Cursor c1=mydatabase.query(Database.L_TABLE_NAME, col1, null, null, null, null, null);
		for(c1.moveToLast();!c1.isBeforeFirst();c1.moveToPrevious()){
			if(c1.getString(3).equals("Labour")){
				ldate[i]=c1.getString(0);
				lto[i]=c1.getString(1);
				lamt[i]=c1.getString(2);
				i++;
			}else{
				edate[j]=c1.getString(0);
				eto[j]=c1.getString(1);
				eamt[j]=c1.getString(2);
				j++;
			}
		}
		mydatabase.close();
	}
}
