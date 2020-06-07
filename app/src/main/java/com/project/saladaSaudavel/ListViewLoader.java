package com.project.saladaSaudavel;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.saladaSaudavel.DAOs.OpcionaisDAO;
import com.project.saladaSaudavel.DAOs.PedidoDAO;
import com.project.saladaSaudavel.DAOs.PratoDAO;
import com.project.saladaSaudavel.Entidades.Opcionais;
import com.project.saladaSaudavel.Entidades.Pedido;
import com.project.saladaSaudavel.Entidades.Prato;

import java.util.ArrayList;
import java.util.List;


public class ListViewLoader extends ListActivity {

    private ListView lv;
    String identificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if(Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Intent it = getIntent();
        identificador = it.getStringExtra("identificador");

        final Intent pedidosIt=new Intent(this, NutricionalActivity.class);
        final Intent homeIt=new Intent(this, HomeActivity.class);

        lv = (ListView)findViewById(android.R.id.list);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(identificador.equals("pratos")||identificador.equals("opcionais")){
                    pedidosIt.putExtra("itemPosition",position+1);
                    pedidosIt.putExtra("tipo", identificador);
                    startActivity(pedidosIt);
                    finish();
                }else
                    startActivity(homeIt);
                    finish();
            }
        });

        if(identificador.equals("pratos")) {
            PratoDAO dishDao = new PratoDAO();
            ArrayList<Prato> lista = (ArrayList<Prato>) dishDao.recuperaPrato();
            List<String> strings = new ArrayList<String>();
            for (Prato pratos : lista) {
                strings.add(pratos != null ? pratos.toString() : null);
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);
            lv.setAdapter(arrayAdapter);
        }if(identificador.equals("opcionais")){
            OpcionaisDAO otherDao = new OpcionaisDAO();
            ArrayList<Opcionais> lista = (ArrayList<Opcionais>) otherDao.recuperaOpcionais();
            List<String> strings = new ArrayList<String>();
            for (Opcionais opcionais : lista) {
                strings.add(opcionais != null ? opcionais.toString() : null);
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);
            lv.setAdapter(arrayAdapter);
        }if(identificador.equals("historico")){
            PedidoDAO requestDao = new PedidoDAO();
            ArrayList<Pedido> lista = (ArrayList<Pedido>) requestDao.recuperaPorCliente(getSharedPreferences("LoginInfo", 0).getInt("idUsuario", 0));
            List<String> strings = new ArrayList<String>();
            for (Pedido pedido : lista) {
                strings.add(pedido != null ? pedido.toString() : null);
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);
            lv.setAdapter(arrayAdapter);
        }
    }

}
