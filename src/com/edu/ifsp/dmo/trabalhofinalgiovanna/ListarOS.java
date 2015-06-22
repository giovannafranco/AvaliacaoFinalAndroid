package com.edu.ifsp.dmo.trabalhofinalgiovanna;

import java.util.List;
import com.edu.ifsp.dmo.dao.OrdemServicoDao;
import com.edu.ifsp.dmo.model.OrdemServico;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class ListarOS extends ActionBarActivity {

	public static final String PARAM_OS = "os";
	private ListView lista;
	private List<OrdemServico> objects;
	private OrdemServico osSelecionada;
	private ArrayAdapter<OrdemServico> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listar_os);
		
		lista = (ListView) findViewById(R.id.listaOs);		
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				osSelecionada = objects.get(position);
				return false;
			}
		});
		
		listarOs();
		
		registerForContextMenu(lista);
	}
	
	private void listarOs() {
		objects = new OrdemServicoDao(this).listarTodos();
		adapter = new ArrayAdapter<OrdemServico>(this, android.R.layout.simple_list_item_1, objects);
		lista.setAdapter(adapter);
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_acoes_contexto, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.acao_editar:
	        	editarOs();
	            return true;
	        case R.id.acao_excluir:
	        	excluirOs();
	        	return true;
	        case R.id.acao_consultar:
	        	consultarOs();
	        	return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}

	private void consultarOs() {
		Intent vaiParaFormulario = new Intent(ListarOS.this, ListaDetalhesOs.class);

		Bundle parametros = new Bundle();
		parametros.putSerializable(PARAM_OS, osSelecionada);

		vaiParaFormulario.putExtras(parametros);

		startActivity(vaiParaFormulario);
	}

	private void editarOs() {
		Intent vaiParaFormulario = new Intent(ListarOS.this, MainActivity.class);
		
		Bundle parametros = new Bundle();
		parametros.putSerializable(PARAM_OS, osSelecionada);
		
		vaiParaFormulario.putExtras(parametros);
		
		startActivity(vaiParaFormulario);
	}
	
	private void excluirOs() {
		new AlertDialog.Builder(ListarOS.this)
				.setTitle("Excluir ordem de serviço")
				.setMessage("Deseja excluir ordem de serviço " + osSelecionada.getDescricao())
				.setPositiveButton(ListarOS.this.getResources().getString(R.string.lbl_sim), new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int teveSucesso = new OrdemServicoDao(ListarOS.this).excluir(osSelecionada.getId());
						Toast.makeText(ListarOS.this,
								teveSucesso != -1 ? "Excluido com sucesso" : "Não foi possível excluir",
								Toast.LENGTH_SHORT).show();
						listarOs();
						adapter.notifyDataSetChanged();
					}
				}).setNegativeButton(ListarOS.this.getResources().getString(R.string.lbl_nao), null).show();
	}
	
	
}
