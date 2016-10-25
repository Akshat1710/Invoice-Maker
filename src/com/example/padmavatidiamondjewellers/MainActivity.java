package com.example.padmavatidiamondjewellers;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar=this.getActionBar();
		actionBar.setIcon(R.drawable.pdj);
		actionBar.setTitle(" HOME");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.menubar));
		
	}
	public void createABill(View v){
		Intent i=new Intent(this,BillingDetails.class);
		startActivity(i);
	}
	public void viewCustomers(View v){
		Intent i=new Intent(this,CustomersCopy.class);
		startActivity(i);
	}
	
	public void inv_loan(View v){
		Intent i=new Intent(this,InvestmentsLoans.class);
		startActivity(i);
	}
	public void viewTransactions(View v){
		Intent i=new Intent(this,Transactions.class);
		startActivity(i);
	}
	
	public void viewGoldInventory(View v){
		Intent i=new Intent(this,Inventory.class);
		startActivity(i);
	}
	public void accounts(View v){
		Intent i=new Intent(this,Accounts.class);
		startActivity(i);
	}
	public void labour(View v){
		Intent i=new Intent(this,Labour.class);
		startActivity(i);
	}
}
