package com.example.agendacontatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dao.PessoaDao;
import modelo.Pessoa;

public class FormPessoa extends AppCompatActivity {
    EditText editNome, editIdade, editEndereco, editTelefone;
    Button btnVariavel;
    Pessoa pessoa, altpessoa; //*pessoa e obejto que eu vou criar aqui dentro é alt pessoa é quando eu receber alguma informação externa de um obejeto pessoa *//
    PessoaDao pessoaDao;  //** classe que faz conexeão com o banco de dados **//
    long retornoDB; //** vou saber seu deu certo ou não a execução do meu metodo**//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pessoa);
        Intent i = getIntent();
        altpessoa = (Pessoa) i.getSerializableExtra("pessoa-enviada");
        pessoa = new Pessoa();
        pessoaDao = new PessoaDao(FormPessoa.this);


        editNome = (EditText) findViewById(R.id.editNome);
        editIdade = (EditText) findViewById(R.id.editIdade);
        editEndereco = (EditText) findViewById(R.id.editEndereco);
        editTelefone = (EditText) findViewById(R.id.editTelefone);
        btnVariavel = (Button) findViewById(R.id.btnVariavel);

        if(altpessoa != null){
            btnVariavel.setText("Alterar");
            editNome.setText(altpessoa.getNome());
            editIdade.setText(altpessoa.getIdade()+"");
            editEndereco.setText(altpessoa.getEndereco());
            editTelefone.setText(altpessoa.getTelefone());

            pessoa.setId(altpessoa.getId());
        }else{
            btnVariavel.setText("Salvar");
        }
        btnVariavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pessoa.setNome(editNome.getText().toString());
                pessoa.setIdade(Integer.parseInt(editIdade.getText().toString()));
                pessoa.setEndereco(editEndereco.getText().toString());
                pessoa.setTelefone(editTelefone.getText().toString());

                if (btnVariavel.getText().toString().equals("Salvar")){
                    retornoDB = pessoaDao.salvarPessoa(pessoa);
                    pessoaDao.close();
                    if (retornoDB == -1){
                        alert("Erro ao cadastrar"); //** se meu retorno for ==-1 sera exibido erro ao cadastrar se for exibido Cadastro realizado com sucesso **//
                    }else{
                        alert("Cadastro realizado com sucesso");
                    }
                }else{
                    retornoDB = pessoaDao.alterarPessoa(pessoa);
                    pessoaDao.close();
                    if (retornoDB==-1){
                        alert("Erro ao alterar");
                    }else{
                        alert("Atualização realizada com sucesso");
                    }

                }

                finish();

                }


        });

    }
    private  void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}