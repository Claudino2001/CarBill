package br.com.application.carbill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TelaInfoPerson extends AppCompatActivity {

    public TextView txtNome;
    String strNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_info_person);

        txtNome = (TextView) findViewById(R.id.txtNome);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            strNome = extras.getString("nome");
        }

        txtNome.setText(strNome);

    }
}