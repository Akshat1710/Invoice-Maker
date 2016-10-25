package com.example.padmavatidiamondjewellers;






import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Inventory extends Activity {
	DBHelper mydbhelper;
	ListView l;
	SQLiteDatabase mydatabase;
	double g_qty=0,g_rate=0,d_qty=0,s_qty=0;
	
	String [] qty;
	//CardArrayAdapter cardArrayAdapter;
	//List<Card> cd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);
		Intent i = getIntent();
		
		ActionBar actionBar=this.getActionBar();
		actionBar.setIcon(R.drawable.pdj);
		actionBar.setTitle(" INVENTORY");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.menubar));
		
        qty=new String[3];
		
		l=(ListView)findViewById(R.id.listView_inventory);
		fetchData();
		l.setAdapter(new InventoryAdapter(this,qty));
		OnItemClickListener o=new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				switch(pos){
				case 0:
					Intent ig=new Intent(Inventory.this,ViewGoldInventory.class);
					startActivity(ig);
					break;
				case 1:
					Intent id=new Intent(Inventory.this,ViewDiamondInventory.class);
					startActivity(id);
					break;
				case 2:
					Intent is=new Intent(Inventory.this,ViewSilverInventory.class);
					startActivity(is);
					break;
				}
				
			}
			
		};
		l.setOnItemClickListener(o);
	}
	private void fetchData() {
		// TODO Auto-generated method stub
		mydbhelper=new DBHelper(this, Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mydatabase=mydbhelper.getWritableDatabase();
		String col1[]=new String[]{Database.G_QTY,Database.G_RATE,Database.G_OPERATION};
		Cursor c1=mydatabase.query(Database.G_TABLE_NAME, col1, null, null, null, null, null);
		int ij=0;
		for(c1.moveToLast();!c1.isBeforeFirst();c1.moveToPrevious()){
			if(c1.getString(2).equals("Buy")){
				
				g_qty+=Double.parseDouble(c1.getString(0));
				qty[ij]=g_qty+"";
			}else{
				
				g_qty-=Double.parseDouble(c1.getString(0));
				qty[ij]=g_qty+"";
			}
		}
		ij++;
		String col2[]=new String[]{Database.D_QTY,Database.D_RATE,Database.D_OPERATION};
		Cursor c2=mydatabase.query(Database.D_TABLE_NAME, col2, null, null, null, null, null);
		for(c2.moveToLast();!c2.isBeforeFirst();c2.moveToPrevious()){
			if(c2.getString(2).equals("Buy")){
				
				d_qty+=Double.parseDouble(c2.getString(0));
				qty[ij]=d_qty+"";
			}else{
				
				d_qty-=Double.parseDouble(c2.getString(0));
				qty[ij]=d_qty+"";
			}
		}
		ij++;
		String col3[]=new String[]{Database.S_QTY,Database.S_RATE,Database.S_OPERATION};
		Cursor c3=mydatabase.query(Database.S_TABLE_NAME, col3, null, null, null, null, null);
		for(c3.moveToLast();!c3.isBeforeFirst();c3.moveToPrevious()){
			if(c3.getString(2).equals("Buy")){
				
				s_qty+=Double.parseDouble(c3.getString(0));
				qty[ij]=s_qty+"";
			}else{
				
				s_qty-=Double.parseDouble(c3.getString(0));
				qty[ij]=s_qty+"";
			}
		}
		mydatabase.close();

	}
	
}
