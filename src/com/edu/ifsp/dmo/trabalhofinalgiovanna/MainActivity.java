package com.edu.ifsp.dmo.trabalhofinalgiovanna;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.edu.ifsp.dmo.dao.OrdemServicoDao;
import com.edu.ifsp.dmo.model.OrdemServico;

public class MainActivity extends ActionBarActivity {
	private EditText descricao;
	private EditText valor;
	private EditText data;
	private EditText telefone;
	private EditText cliente;
	private ToggleButton pago;
	
	private Button cadastrar;
	private Button listar;
	
	private OrdemServico osSelecionada;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		descricao = (EditText) findViewById(R.id.textDescricao);
		valor = (EditText) findViewById(R.id.textValor);
		data = (EditText) findViewById(R.id.textData);
		telefone = (EditText) findViewById(R.id.textTelefone);
		cliente = (EditText) findViewById(R.id.textCliente);
		pago = (ToggleButton) findViewById(R.id.toggleButton1);
		
		cadastrar = (Button) findViewById(R.id.btnCadastrar);
		listar = (Button) findViewById(R.id.btnListar);
		
		cadastrar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cadastrar();
			}
		});
		
		listar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), ListarOS.class));
			}
		});
		
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
		valor.setText(String.valueOf(osSelecionada.getValor()));
		data.setText(new SimpleDateFormat("dd-MM-yyyy").format(osSelecionada.getData()));
		telefone.setText(osSelecionada.getTelefone());
		cliente.setText(osSelecionada.getCliente());
	}

	private void cadastrar() {
		OrdemServicoDao dao = new OrdemServicoDao(MainActivity.this);
		OrdemServico os = osSelecionada == null ? new OrdemServico() : osSelecionada;
		
		os.setDescricao(descricao.getText().toString());
		os.setValor(Double.parseDouble(valor.getText().toString()));
		try {
			os.setData(new SimpleDateFormat("dd-MM-yyyy").parse(data.getText().toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		os.setTelefone(telefone.getText().toString());
		os.setCliente(cliente.getText().toString());
		os.setPago(pago.isChecked());
		
		long id = dao.save(os);

		if (id != -1) {
			Toast.makeText(this, "Ordem de serviço salvo com sucesso!",Toast.LENGTH_SHORT).show();
			this.limparFormulario();
		} else {
			Toast.makeText(this, "Ordem de serviço não pode ser inserida!",Toast.LENGTH_SHORT).show();
		}
	}
	
	private void limparFormulario() {
		descricao.setText(null);
		valor.setText(null);
		data.setText(null);
		telefone.setText(null);
		cliente.setText(null);
		
		descricao.requestFocus();
		
		osSelecionada = null;
	}

}

