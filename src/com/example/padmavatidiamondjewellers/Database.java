package com.example.padmavatidiamondjewellers;

public class Database {
	public static final String PDJ_DATABASE_NAME = "PDJ";
	public static final int PDJ_DATABASE_VERSION = 1;

	public static final String C_TABLE_NAME = "CUSTOMER_DETAILS";
	public static final String C_ID = "id";
	public static final String C_NAME = "name";
	public static final String C_ADDRESS = "address";
	public static final String C_MOBILE_NO = "mobile_no";
	public static final String C_DESC = "desc";
	public static final String C_ADV = "adv";
	
	public static final String CAP_TABLE_NAME = "CAPITAL";
	public static final String CAP_AMT = "amt";
	public static final String CAP_PUR = "purpose";
	public static final String CAP_DATE = "date";
	public static final String CAP_TYPE = "type";
	
	
	public static final String BAL_TABLE_NAME = "BALANCE";
	public static final String BAL_AMT = "BALANCE";

	public static final String BD_TABLE_NAME = "BILL_DETAILS";
	public static final String BD_BILL_NO = "bill_no";
	public static final String BD_NAME = "name";
	public static final String BD_DATE = "date";
	public static final String BD_ITEM_ID = "item_id";
	public static final String BD_PARTICULARS = "particulars";
	public static final String BD_GOLD_WT = "gold_wt";
	public static final String BD_GOLD_RATE = "gold_rate";
	public static final String BD_GOLD_TOTAL = "gold_total";
	public static final String BD_DIAMOND_WT = "diamond_wt";
	public static final String BD_DIAMOND_RATE = "diamond_rate";
	public static final String BD_DIAMOND_TOTAL = "diamond_total";
	public static final String BD_SILVER_WT = "silver_wt";
	public static final String BD_SILVER_RATE = "silver_rate";
	public static final String BD_SILVER_TOTAL = "silver_total";
	public static final String BD_LABOUR_CHARGES = "labour_charges";
	public static final String BD_ITEM_TOTAL = "item_total";
	
	public static final String BI_TABLE_NAME = "BILL_ITEM";
	public static final String BI_BILL_NO = "bill_no";//Auto Increment
	public static final String BI_BILL_ITEM_COUNT = "bill_item_count";
	public static final String BI_BILL_TOTAL = "bill_total";
	public static final String BI_BILL_NAME = "bill_name";
	public static final String BI_BILL_DATE = "bill_date";
	public static final String BI_COLOUR_STONE_AMT = "colour_stone_amt";
	public static final String BI_DESC = "desc";
	public static final String BI_PAYMENT_STATUS = "payment_status";
	public static final String BI_AMT_PAID = "amt_paid";
	public static final String BI_AMT_PENDING = "amt_pending";
	
	public static final String G_TABLE_NAME = "GOLD_STOCK";
	public static final String G_KEY = "g_key";
	public static final String G_RATE = "g_rate";
	public static final String G_QTY = "g_qty";
	public static final String G_DATE = "g_date";
	public static final String G_OPERATION = "g_op";
	public static final String G_TOTAL = "g_total";
	public static final String G_STATUS = "g_status";
	public static final String G_AMT_PAID = "amt_paid";
	public static final String G_AMT_PENDING = "amt_pending";
	public static final String G_BOUGHT_FROM = "bought_from";
	
	
	public static final String D_TABLE_NAME = "DIAMOND_STOCK";
	public static final String D_RATE = "d_rate";
	public static final String D_QTY = "d_qty";
	public static final String D_DATE = "d_date";
	public static final String D_OPERATION = "d_op";
	public static final String D_TOTAL = "d_total";
	public static final String D_KEY = "d_key";
	public static final String D_STATUS = "d_status";
	public static final String D_AMT_PAID = "amt_paid";
	public static final String D_AMT_PENDING = "amt_pending";
	public static final String D_BOUGHT_FROM = "bought_from";

	
	public static final String S_TABLE_NAME = "SILVER_STOCK";
	public static final String S_RATE = "s_rate";
	public static final String S_QTY = "s_qty";
	public static final String S_DATE = "s_date";
	public static final String S_OPERATION = "s_op";
	public static final String S_TOTAL = "s_total";
	public static final String S_KEY = "s_key";
	public static final String S_STATUS = "s_status";
	public static final String S_AMT_PAID = "amt_paid";
	public static final String S_AMT_PENDING = "amt_pending";
	public static final String S_BOUGHT_FROM = "bought_from";
	
	public static final String L_TABLE_NAME = "LABOUR";
	public static final String L_DATE = "l_date";
	public static final String L_TYPE = "l_type";
	public static final String L_TO = "l_to";
	public static final String L_AMT = "l_amt";
	public static final String L_DAY = "l_day";
	public static final String L_MONTH = "l_month";
	public static final String L_YEAR = "l_year";




	




	
	
}
