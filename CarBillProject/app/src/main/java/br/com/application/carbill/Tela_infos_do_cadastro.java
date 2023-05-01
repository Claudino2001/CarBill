package br.com.application.carbill;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Tela_infos_do_cadastro extends AppCompatActivity {

    public TextView txtTitulo;
    public EditText editTextNome, editTextSobrenome, editTextApelido, editTextTelefone, editTextRua, editTextBairro, editTextNumero, editTextValorPorCorrida;
    public Button buttonExcluir, buttonAtualizar;

    int id;

    //BANCO
    private SQLiteDatabase banco;
    private static final String DATABASE_NAME = "banco_de_dados_carbill";

    ClassPessoa pessoa = new ClassPessoa();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos_do_cadastro);

        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextSobrenome = (EditText) findViewById(R.id.editTextSobrenome);
        editTextApelido = (EditText) findViewById(R.id.editTextApelido);
        editTextTelefone = (EditText) findViewById(R.id.editTextTelefone);
        editTextRua = (EditText) findViewById(R.id.editTextRua);
        editTextBairro = (EditText) findViewById(R.id.editTextBairro);
        editTextNumero = (EditText) findViewById(R.id.editTextNumero);
        editTextValorPorCorrida = (EditText) findViewById(R.id.editTextValorPorCorrida);
        buttonExcluir = (Button) findViewById(R.id.buttonExcluir);
        buttonAtualizar = (Button) findViewById(R.id.ButtonAtualizar);

        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnExcluir();
            }
        });

        buttonAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePessoa();
                Toast.makeText(Tela_infos_do_cadastro.this, "Atualizado com sucesso.", Toast.LENGTH_SHORT).show();
            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id_pessoa");
        }

        pessoa.setId(id);

        acessoBanco();

        listoInfos();

    }

    public void acessoBanco(){
        try{
            banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            Cursor cursor = banco.rawQuery("SELECT id_pessoa, nome, sobrenome, apelido, telefone, rua, bairro, numero, valor_por_corrida FROM TB_PESSOA WHERE id_pessoa = " + pessoa.getId() + ";",null);

            if(cursor.moveToFirst()){
                do{
                    String nome = cursor.getString((int) cursor.getColumnIndex("nome"));
                    String sobrenome = cursor.getString((int) cursor.getColumnIndex("sobrenome"));
                    String apelido = cursor.getString((int) cursor.getColumnIndex("apelido"));
                    String telefone = cursor.getString((int) cursor.getColumnIndex("telefone"));
                    String rua = cursor.getString((int) cursor.getColumnIndex("rua"));
                    String bairro = cursor.getString((int) cursor.getColumnIndex("bairro"));
                    int numero = cursor.getInt((int) cursor.getColumnIndex("numero"));
                    double valor_por_corrida = cursor.getDouble((int) cursor.getColumnIndex("valor_por_corrida"));

                    pessoa.setNome(nome);
                    pessoa.setSobrenome(sobrenome);
                    pessoa.setApelido(apelido);
                    pessoa.setTelefone(telefone);
                    pessoa.setRua(rua);
                    pessoa.setBairro(bairro);
                    pessoa.setNumero(numero);
                    pessoa.setValor_por_corrida(valor_por_corrida);

                }while (cursor.moveToNext());
            }

            banco.close();
        }catch (Exception e){
            Toast.makeText(this, "erro: acessobanco()", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void listoInfos(){
        txtTitulo.setText(pessoa.getNome() + " " + pessoa.getSobrenome());
        editTextNome.setText(pessoa.getNome());
        editTextSobrenome.setText(pessoa.getSobrenome());
        editTextApelido.setText(pessoa.getApelido());
        editTextTelefone.setText(pessoa.getTelefone());
        editTextRua.setText(pessoa.getRua());
        editTextBairro.setText(pessoa.getBairro());
        editTextNumero.setText(pessoa.getNumero() +"");
        editTextValorPorCorrida.setText(String.valueOf(pessoa.getValor_por_corrida()));

    }

    public void btnExcluir(){
        if(temDivida()){
            String valorFormatado_DividaTotal = NumberFormat.getCurrencyInstance().format(pessoa.getDivida_total());
            Toast.makeText(this, "Impossível excluir. Esse usuário ainda possui uma dívida de " + valorFormatado_DividaTotal, Toast.LENGTH_SHORT).show();
        }else{
            AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
            msgBox.setTitle("EXCLUIR CADASTRO");
            msgBox.setIcon(R.drawable.ic_lixeira);
            msgBox.setMessage("Tem certeza que deseja excluir permanentemente esse cadastro?\nEssa ação não pode ser desfeita.");
            msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    deletarPessoa();
                    finish();
                    Toast.makeText(Tela_infos_do_cadastro.this, pessoa.getNome() + " " + pessoa.getSobrenome() + " foi excluido com sucesso.", Toast.LENGTH_SHORT).show();
                }
            });
            msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(Tela_infos_do_cadastro.this, "Operação cancelada.", Toast.LENGTH_SHORT).show();
                }
            });
            msgBox.show();
        }
    }

    public boolean temDivida(){
        boolean temDivida = false;
        double deve = 0.0f;
        try{
            banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            Cursor cursor = banco.rawQuery("SELECT SUM(valor) Total FROM TB_PESSOA INNER JOIN tb_viagem ON tb_pessoa.id_pessoa = tb_viagem.id_pessoa INNER JOIN tb_tipo ON tb_viagem.id_tipo = tb_tipo.id_tipo where tb_pessoa.id_pessoa = "+ pessoa.getId() + ";", null);

            if(cursor.moveToFirst()){
                do{
                    deve = cursor.getDouble((int) cursor.getColumnIndex("Total"));
                    pessoa.setDivida_total(deve);
                }while (cursor.moveToNext());
            }

            if(deve > 0)
                temDivida = true;

            banco.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return temDivida;
    }

    public void deletarPessoa(){
        try{
            banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            banco.execSQL("DELETE FROM TB_PESSOA WHERE id_pessoa = " + pessoa.getId());

            banco.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updatePessoa(){
        try{
            banco = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

            String sql = "UPDATE TB_PESSOA SET nome = ? WHERE id_pessoa = ?;";
            SQLiteStatement stmt = banco.compileStatement(sql);
            stmt.bindString(1, editTextNome.getText().toString());
            stmt.bindLong(2, pessoa.getId());
            stmt.executeUpdateDelete();

            sql = "UPDATE TB_PESSOA SET sobrenome = ? WHERE id_pessoa = ?;";
            stmt = banco.compileStatement(sql);
            stmt.bindString(1, editTextSobrenome.getText().toString());
            stmt.bindLong(2, pessoa.getId());
            stmt.executeUpdateDelete();

            sql = "UPDATE TB_PESSOA SET apelido = ? WHERE id_pessoa = ?;";
            stmt = banco.compileStatement(sql);
            stmt.bindString(1, editTextApelido.getText().toString());
            stmt.bindLong(2, pessoa.getId());
            stmt.executeUpdateDelete();

            sql = "UPDATE TB_PESSOA SET telefone = ? WHERE id_pessoa = ?;";
            stmt = banco.compileStatement(sql);
            stmt.bindString(1, editTextTelefone.getText().toString());
            stmt.bindLong(2, pessoa.getId());
            stmt.executeUpdateDelete();

            sql = "UPDATE TB_PESSOA SET rua = ? WHERE id_pessoa = ?;";
            stmt = banco.compileStatement(sql);
            stmt.bindString(1, editTextRua.getText().toString());
            stmt.bindLong(2, pessoa.getId());
            stmt.executeUpdateDelete();

            sql = "UPDATE TB_PESSOA SET bairro = ? WHERE id_pessoa = ?;";
            stmt = banco.compileStatement(sql);
            stmt.bindString(1, editTextBairro.getText().toString());
            stmt.bindLong(2, pessoa.getId());
            stmt.executeUpdateDelete();

            sql = "UPDATE TB_PESSOA SET numero = ? WHERE id_pessoa = ?;";
            stmt = banco.compileStatement(sql);
            stmt.bindString(1, editTextNumero.getText().toString());
            stmt.bindLong(2, pessoa.getId());
            stmt.executeUpdateDelete();

            sql = "UPDATE TB_PESSOA SET valor_por_corrida = ? WHERE id_pessoa = ?;";
            stmt = banco.compileStatement(sql);
            stmt.bindString(1, editTextValorPorCorrida.getText().toString());
            stmt.bindLong(2, pessoa.getId());
            stmt.executeUpdateDelete();

            banco.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}