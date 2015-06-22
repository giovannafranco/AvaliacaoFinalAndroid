package com.edu.ifsp.dmo.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.edu.ifsp.dmo.database.ContratoOrdemServico;
import com.edu.ifsp.dmo.model.OrdemServico;

public class OrdemServicoDao {
	private OrdemServicoDbHelper pDbHelper;

	public OrdemServicoDao(Context context) {
		pDbHelper = new OrdemServicoDbHelper(context);
	}

	public long save(OrdemServico os) {
		SQLiteDatabase db = null;
		long newRowId = -1;

		db = pDbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ContratoOrdemServico.COLUMN_NAME_ENTRY_DESCRICAO, os.getDescricao());
		values.put(ContratoOrdemServico.COLUMN_NAME_ENTRY_VALOR, os.getValor());
		values.put(ContratoOrdemServico.COLUMN_NAME_ENTRY_TELEFONE, os.getTelefone());
		values.put(ContratoOrdemServico.COLUMN_NAME_ENTRY_CLIENTE, os.getCliente());
		values.put(ContratoOrdemServico.COLUMN_NAME_ENTRY_DATA,new SimpleDateFormat("dd-MM-yyyy").format(os.getData()));
		values.put(ContratoOrdemServico.COLUMN_NAME_ENTRY_PAGO, os.isPago() ? "S" : "N");

		if (os.getId() != null) {
			newRowId = db.update(ContratoOrdemServico.TABLE_NAME, values,
					ContratoOrdemServico.COLUMN_NAME_ENTRY_ID + "=?",
					new String[] {os.getId().toString() });
		} else {
			newRowId = db.insert(ContratoOrdemServico.TABLE_NAME, null, values);
		}
		
		return newRowId;
	}

	public List<OrdemServico> listarTodos() {
		List<OrdemServico> list = new ArrayList<OrdemServico>();
		OrdemServico os;
		String[] projection = { ContratoOrdemServico.COLUMN_NAME_ENTRY_ID,
				ContratoOrdemServico.COLUMN_NAME_ENTRY_DESCRICAO,
				ContratoOrdemServico.COLUMN_NAME_ENTRY_VALOR,
				ContratoOrdemServico.COLUMN_NAME_ENTRY_TELEFONE,
				ContratoOrdemServico.COLUMN_NAME_ENTRY_DATA,
				ContratoOrdemServico.COLUMN_NAME_ENTRY_CLIENTE,
				ContratoOrdemServico.COLUMN_NAME_ENTRY_PAGO };

		String sortOrder = ContratoOrdemServico.COLUMN_NAME_ENTRY_DESCRICAO + " DESC";

		SQLiteDatabase db = pDbHelper.getReadableDatabase();
		Cursor cursor = db.query(ContratoOrdemServico.TABLE_NAME, projection, null, null, null, null, sortOrder);

		while (cursor.moveToNext()) {
			os = new OrdemServico();
			
			os.setId(cursor.getInt(cursor
					.getColumnIndexOrThrow(ContratoOrdemServico.COLUMN_NAME_ENTRY_ID)));
			os.setDescricao(cursor.getString(cursor
					.getColumnIndexOrThrow(ContratoOrdemServico.COLUMN_NAME_ENTRY_DESCRICAO)));
			os.setCliente(cursor.getString(cursor
					.getColumnIndexOrThrow(ContratoOrdemServico.COLUMN_NAME_ENTRY_CLIENTE)));
			try {
				os.setData(new SimpleDateFormat("dd-MM-yyyy").parse(cursor.getString(cursor
						.getColumnIndexOrThrow(ContratoOrdemServico.COLUMN_NAME_ENTRY_DATA))));
			} catch (IllegalArgumentException | ParseException e) {
				e.printStackTrace();
			}
			os.setPago(cursor.getString(cursor
					.getColumnIndexOrThrow(ContratoOrdemServico.COLUMN_NAME_ENTRY_PAGO)).equalsIgnoreCase("S"));
			os.setValor(Double.parseDouble(cursor.getString(cursor
					.getColumnIndexOrThrow(ContratoOrdemServico.COLUMN_NAME_ENTRY_VALOR))));
			os.setTelefone(cursor.getString(cursor
					.getColumnIndexOrThrow(ContratoOrdemServico.COLUMN_NAME_ENTRY_TELEFONE)));

			list.add(os);
			
		}
		return list;
	}

	public int excluir(Integer id) {
		SQLiteDatabase db = pDbHelper.getWritableDatabase();
		return db.delete(ContratoOrdemServico.TABLE_NAME,
				ContratoOrdemServico.COLUMN_NAME_ENTRY_ID + "=?",
				new String[] { id.toString() });
	}
	
}
