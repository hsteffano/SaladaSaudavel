package com.project.saladaSaudavel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.project.saladaSaudavel.DAOs.PedidoDAO;


public class HomeActivity extends ActionBarActivity {

    Button pedirBT,historicoBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Intent intPedir=new Intent(this, PedidosActivity.class);
        final Intent intHist=new Intent(this, ListViewLoader.class);
        pedirBT=(Button)findViewById(R.id.bt_pedir);
        historicoBT=(Button)findViewById(R.id.bt_historico);

        pedirBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intPedir);
            }
        });
        historicoBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intHist.putExtra("identificador","historico");
                intHist.putExtra("idCliente", "");
                startActivity(intHist);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,Menu.FIRST,Menu.FIRST,"Sair");
        menu.add(1,Menu.FIRST+1,Menu.FIRST+1,"Dados");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == 1) {
            final Intent mainIt=new Intent(this, MainActivity.class);
            SharedPreferences.Editor editor = getSharedPreferences("LoginInfo", 0).edit();
            editor.clear();
            editor.commit();
            mainIt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIt);
            finish();
            return true;
        }
        if (id == 2) {
            final Intent dadosIt=new Intent(this, DadosActivity.class);
            startActivity(dadosIt);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
