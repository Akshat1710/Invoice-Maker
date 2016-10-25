package com.example.padmavatidiamondjewellers;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	DBHelper mydbhelper;
	SQLiteDatabase mydatabase;
	Context mycontext;
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		
		super(context,Database.PDJ_DATABASE_NAME, null, Database.PDJ_DATABASE_VERSION);
		mycontext=context;
		// TODO Auto-generated constructor stub
	}

	public DBHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
				
		String bd_table_sql="Create table "+Database.BD_TABLE_NAME+" ( "+
				Database.BD_BILL_NO + " TEXT, "+
				Database.BD_ITEM_ID + " TEXT, "+
				Database.BD_NAME + " TEXT, "+
				Database.BD_PARTICULARS + " TEXT, "+
				Database.BD_DATE + " TEXT, "+
				Database.BD_GOLD_WT + " TEXT, "+
				Database.BD_GOLD_RATE + " TEXT, "+
				Database.BD_GOLD_TOTAL + " TEXT, "+
				Database.BD_DIAMOND_WT + " TEXT, "+
				Database.BD_DIAMOND_RATE + " TEXT, "+
				Database.BD_DIAMOND_TOTAL + " TEXT, "+
				Database.BD_SILVER_WT + " TEXT, "+
				Database.BD_SILVER_RATE + " TEXT, "+
				Database.BD_SILVER_TOTAL + " TEXT, "+
				Database.BD_LABOUR_CHARGES + " TEXT,"+
				Database.BD_ITEM_TOTAL + " TEXT );";
				
				db.execSQL(bd_table_sql);
		
				String cap_table_sql = "create table " + Database.CAP_TABLE_NAME + " ( " + 
						Database.CAP_AMT + " TEXT ,"+
						Database.CAP_DATE + " TEXT ,"+
						Database.CAP_TYPE + " TEXT ,"+
						Database.CAP_PUR + " TEXT );";
						db.execSQL(cap_table_sql); 

						String bal_table_sql = "create table " + Database.BAL_TABLE_NAME + " ( " + 
								Database.BAL_AMT + " TEXT );";
								db.execSQL(bal_table_sql); 
						
		String c_table_sql = "create table " + Database.C_TABLE_NAME + " ( " + 
				Database.C_NAME + " TEXT ,"+
				Database.C_ADV + " TEXT ,"+
				Database.C_ADDRESS + " TEXT ,"+
				Database.C_DESC + " TEXT ,"+
				Database.C_MOBILE_NO + " TEXT );";
				db.execSQL(c_table_sql);
				
				String l_table_sql = "create table " + Database.L_TABLE_NAME + " ( " + 
						
						Database.L_DATE + " TEXT ,"+
						Database.L_TYPE + " TEXT ,"+
						Database.L_TO + " TEXT ,"+
						Database.L_AMT + " TEXT ,"+
						Database.L_DAY + " TEXT ,"+
						Database.L_MONTH + " TEXT ,"+
						Database.L_YEAR + " TEXT );";
						db.execSQL(l_table_sql);
				
		String g_table_sql = "create table " + Database.G_TABLE_NAME + " ( " + 
						Database.G_KEY 	+ " integer  primary key autoincrement, " + 
						Database.G_DATE + " TEXT ,"+
						Database.G_QTY + " TEXT ,"+
						Database.G_OPERATION + " TEXT ,"+
						Database.G_TOTAL + " TEXT ," +
						Database.G_STATUS + " TEXT ," +
						Database.G_AMT_PAID + " TEXT ," +
						Database.G_AMT_PENDING + " TEXT ," +
						Database.G_BOUGHT_FROM + " TEXT ," +
						Database.G_RATE + " TEXT );";
						db.execSQL(g_table_sql);

		String d_table_sql = "create table " + Database.D_TABLE_NAME + " ( " + 
								Database.D_KEY 	+ " integer  primary key autoincrement, " + 
								Database.D_DATE + " TEXT ,"+
								Database.D_QTY + " TEXT ,"+
								Database.D_OPERATION + " TEXT ,"+
								Database.D_TOTAL + " TEXT ," +
								Database.D_STATUS + " TEXT ," +
								Database.D_AMT_PAID + " TEXT ," +
								Database.D_AMT_PENDING + " TEXT ," +
								Database.D_BOUGHT_FROM + " TEXT ," +
								Database.D_RATE + " TEXT );";
								db.execSQL(d_table_sql);
								
		String s_table_sql = "create table " + Database.S_TABLE_NAME + " ( " +
										Database.S_KEY 	+ " integer  primary key autoincrement, " + 
										Database.S_DATE + " TEXT ,"+
										Database.S_QTY + " TEXT ,"+
										Database.S_OPERATION + " TEXT ,"+
										Database.S_TOTAL + " TEXT ," +
										Database.S_STATUS + " TEXT ," +
										Database.S_AMT_PAID + " TEXT ," +
										Database.S_AMT_PENDING + " TEXT ," +
										Database.S_BOUGHT_FROM + " TEXT ," +
										Database.S_RATE + " TEXT );";
										db.execSQL(s_table_sql);
				
		String bi_table_sql = "create table " + Database.BI_TABLE_NAME + " ( " +
				Database.BI_BILL_NO 	+ " integer  primary key autoincrement, " + 
				Database.BI_BILL_ITEM_COUNT + " TEXT ,"+
				Database.BI_BILL_TOTAL + " TEXT ,"+
				Database.BI_BILL_NAME + " TEXT ,"+
				Database.BI_COLOUR_STONE_AMT + " TEXT ,"+
				Database.BI_DESC + " TEXT ,"+
				Database.BI_PAYMENT_STATUS + " TEXT ,"+
				Database.BI_AMT_PAID + " TEXT ,"+
				Database.BI_AMT_PENDING + " TEXT ,"+
				Database.BI_BILL_DATE + " TEXT );";
				db.execSQL(bi_table_sql);
				
				
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
		//db.execSQL("DROP TABLE IF EXISTS "+Database.BI_TABLE_NAME);
		onCreate(db);
		
	}


}
