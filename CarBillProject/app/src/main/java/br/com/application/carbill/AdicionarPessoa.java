package br.com.application.carbill;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AdicionarPessoa extends AppCompatActivity {
    //LEMBRAR DE FAZER O OVERRIDE ONRESUME PARA ATUALIZAR

    public TextView textAddNome, textAddSobrenome, textAddApelido, textAddTelefone, textAddBairro, textAddRua, textAddNumero, textValorPagoPorViagem;
    public Button buttonAddPessoa;
    private SQLiteDatabase banco;
    private static final String DATABASE_NAME = "banco_de_dados_carbill";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_pessoa);

        textAddNome = (TextView) findViewById(R.id.textAddNome);
        textAddSobrenome = (TextView) findViewById(R.id.textAddSobrenome);
        textAddApelido = (TextView) findViewById(R.id.textAddApelido);
        textAddRua = (TextView) findViewById(R.id.textAddRua);
        textAddNumero= (TextView) findViewById(R.id.textAddNumero);
        textAddTelefone= (TextView) findViewById(R.id.textAddTelefone);
        textAddBairro = (TextView) findViewById(R.id.textAddBairro);
        textValorPagoPorViagem = (TextView) findViewById(R.id.textValorPagoPorViagem);
        buttonAddPessoa = (Button) findViewById(R.id.buttonAddPessoa);

        buttonAddPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarNovoUsuario();
                //uma função de limpar os campos
            }
        });
    }

    public void cadastrarNovoUsuario(){

        String strNome = textAddNome.getText().toString();
        String strSobrenome = textAddSobrenome.getText().toString();
        String strApelido = textAddApelido.getText().toString();
        String strTelefone = textAddTelefone.getText().toString();
        String strRua = textAddRua.getText().toString();
        String strBairro = textAddBairro.getText().toString();
        String strNumero = textAddNumero.getText().toString();
        String strValorPagoPorViagem = textValorPagoPorViagem.getText().toString();

        if(TextUtils.isEmpty(strNome) ||TextUtils.isEmpty(strSobrenome) || TextUtils.isEmpty(strApelido) || TextUtils.isEmpty(strTelefone) ||TextUtils.isEmpty(strRua) || TextUtils.isEmpty(strBairro) || TextUtils.isEmpty(strNumero) || TextUtils.isEmpty(strValorPagoPorViagem)){
            Toast.makeText(this, "Falha no cadastro.\nPreencha todos os campos.", Toast.LENGTH_SHORT).show();
        }else{
            try{
                banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
                String sql = "INSERT INTO tb_pessoa (nome, sobrenome, apelido, telefone, rua, bairro, numero, valor_por_corrida) VALUES (?,?,?,?,?,?,?,?);";
                SQLiteStatement stmt = banco.compileStatement(sql);
                stmt.bindString(1, strNome);
                stmt.bindString(2, strSobrenome);
                stmt.bindString(3, strApelido);
                stmt.bindString(4, strTelefone);
                stmt.bindString(5, strRua);
                stmt.bindString(6, strBairro);
                stmt.bindString(7, strNumero);
                stmt.bindString(8, strValorPagoPorViagem);
                stmt.executeInsert();
                banco.close();
                Toast.makeText(this, "Cadastro realizado com sucesso.", Toast.LENGTH_SHORT).show();
                limparCampodeTexto();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void limparCampodeTexto(){
        textAddNome.setText("");
        textAddSobrenome.setText("");
        textAddApelido.setText("");
        textAddRua.setText("");
        textAddNumero.setText("");
        textAddTelefone.setText("");
        textAddBairro.setText("");
        textValorPagoPorViagem.setText("");
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}