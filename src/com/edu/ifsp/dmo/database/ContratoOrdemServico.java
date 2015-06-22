package com.edu.ifsp.dmo.database;

import android.provider.BaseColumns;

public class ContratoOrdemServico implements BaseColumns {
	
	private ContratoOrdemServico(){
	}
	
	public static final String TABLE_NAME = "ordem_servico";
	public static final String COLUMN_NAME_ENTRY_ID = "id";
	public static final String COLUMN_NAME_ENTRY_DESCRICAO = "descricao";
	public static final String COLUMN_NAME_ENTRY_VALOR = "valor";
	public static final String COLUMN_NAME_ENTRY_DATA = "data";
	public static final String COLUMN_NAME_ENTRY_PAGO = "pago";
	public static final String COLUMN_NAME_ENTRY_CLIENTE = "cliente";
	public static final String COLUMN_NAME_ENTRY_TELEFONE = "telefone";

	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String REAL_TYPE = " REAL";
	private static final String DATE_TYPE = " DATE";
	
	private static final String COMMA_SEP = ",";
	
	public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ TABLE_NAME + " (" + COLUMN_NAME_ENTRY_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP
			+ COLUMN_NAME_ENTRY_DESCRICAO + TEXT_TYPE + COMMA_SEP
			+ COLUMN_NAME_ENTRY_VALOR + REAL_TYPE + COMMA_SEP
			+ COLUMN_NAME_ENTRY_DATA + DATE_TYPE + COMMA_SEP
			+ COLUMN_NAME_ENTRY_PAGO + TEXT_TYPE + COMMA_SEP
			+ COLUMN_NAME_ENTRY_CLIENTE + TEXT_TYPE +COMMA_SEP
			+ COLUMN_NAME_ENTRY_TELEFONE +TEXT_TYPE + ")";
	
	public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ ContratoOrdemServico.TABLE_NAME;

}
