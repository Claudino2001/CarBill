package br.com.application.carbill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public FloatingActionButton buttonCorridaDiaria, buttonAddNewPerson;
    public ImageButton buttonConfig;
    public Button btnInfoPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCorridaDiaria = (FloatingActionButton) findViewById(R.id.buttonCorridaDiaria);
        buttonAddNewPerson = (FloatingActionButton) findViewById(R.id.buttonAddNewPerson);
        buttonConfig = (ImageButton) findViewById(R.id.buttonConfig);
        btnInfoPessoa = (Button) findViewById(R.id.btnInfoPessoa);

        buttonCorridaDiaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TelaDiaria.class));
            }
        });

        buttonAddNewPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdicionarPessoa.class));
            }
        });

        buttonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TelaConfiguracoes.class));
            }
        });

        btnInfoPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TelaInfoPerson.class));
            }
        });

    }
}