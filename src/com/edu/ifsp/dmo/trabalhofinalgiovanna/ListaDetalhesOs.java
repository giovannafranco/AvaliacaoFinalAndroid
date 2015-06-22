package com.edu.ifsp.dmo.trabalhofinalgiovanna;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.edu.ifsp.dmo.model.OrdemServico;

public class ListaDetalhesOs extends ActionBarActivity {
	private TextView descricao;
	private TextView valor;
	private TextView data;
	private TextView cliente;
	private TextView telefone;
	private ToggleButton pago;
	
	private OrdemServico osSelecionada;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_detalhes_os);
		
		descricao = (TextView) findViewById(R.id.textDescricao);
		valor = (TextView) findViewById(R.id.textValor);
		data = (TextView) findViewById(R.id.textData);
		telefone = (TextView) findViewById(R.id.textTelefone);
		cliente = (TextView) findViewById(R.id.textCliente);
		pago = (ToggleButton) findViewById(R.id.btnPago);
		pago.setClickable(false);
		
		try {			
			Bundle parametros = getIntent().getExtras();
			osSelecionada = (OrdemServico) parametros.getSerializable(ListarOS.PARAM_OS);
			preencherFormulario();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void preencherFormulario() {
		NumberFormat format = NumberFormat.getInstance();
		
		descricao.setText(osSelecionada.getDescricao());
		valor.setText(format.format(osSelecionada.getValor()));
		data.setText(new SimpleDateFormat("dd-MM-yyyy").format(osSelecionada.getData()));
		telefone.setText(osSelecionada.getTelefone());
		cliente.setText(osSelecionada.getCliente());
		pago.setChecked(osSelecionada.isPago());
	}
}
