package com.project.saladaSaudavel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.saladaSaudavel.DAOs.OpcionaisDAO;
import com.project.saladaSaudavel.DAOs.PedidoDAO;
import com.project.saladaSaudavel.DAOs.PratoDAO;
import com.project.saladaSaudavel.Entidades.Opcionais;
import com.project.saladaSaudavel.Entidades.Pedido;
import com.project.saladaSaudavel.Entidades.Prato;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PedidosActivity extends Activity{

  public static final String PREFS_NAME = "Preferences";
  String coment,identificador;
  private Button pratoBT,opcionaisBT,enviarBT,limparBT;
  private TextView pratoTV;
  private EditText comentET;
  private Spinner spinner;
  int idPrato,position;
  Set<String> set;
  public static HashMap<String, String> hashMap = null;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pedidos);

    if (Build.VERSION.SDK_INT > 9)
    {
      StrictMode.ThreadPolicy.Builder localBuilder = new StrictMode.ThreadPolicy.Builder();
      StrictMode.setThreadPolicy(localBuilder.permitAll().build());
    }
    pratoBT = (Button)findViewById(R.id.bt_prato);
    enviarBT = (Button)findViewById(R.id.bt_enviar);
    opcionaisBT = (Button)findViewById(R.id.bt_opcionais);
    limparBT = (Button)findViewById(R.id.bt_limpar);
    pratoTV = (TextView)findViewById(R.id.tv_prato);
    comentET = (EditText)findViewById(R.id.et_coment);
    spinner = (Spinner)findViewById(R.id.spinner);

    set = new HashSet<String>();
    Intent it = getIntent();
    position = it.getIntExtra("itemPosition", 0);
    identificador = it.getStringExtra("tipo");
    if (identificador != null){
      if (identificador.equals("pratos")){
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putString("prato", Integer.toString(position));
        editor.commit();

        idPrato = Integer.parseInt(getSharedPreferences(PREFS_NAME, 0).getString("prato", ""));
        Prato prato = PratoDAO.recuperaPorId(idPrato);
        pratoTV.setText(prato.toString());
      }
      if (identificador.equals("opcionais")){
        hashMap.put(Integer.toString(position), OpcionaisDAO.recuperaPorId(position).toString());
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putString(Integer.toString(position), hashMap.get(Integer.toString(position)));
        editor.commit();

        if(hashMap == null) {
          hashMap = new HashMap<String, String>();
        }
        List<String> lista = new ArrayList<String>(hashMap.values());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lista);
        spinner.setAdapter(arrayAdapter);
      }
    }
    final Intent listIt = new Intent(this, ListViewLoader.class);
    final Intent homeIt = new Intent(this, HomeActivity.class);
    pratoBT.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listIt.putExtra("identificador", "pratos");
        startActivity(listIt);
      }
    });
    opcionaisBT.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listIt.putExtra("identificador", "opcionais");
        startActivity(listIt);
      }
    });
    enviarBT.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        coment = comentET.getText().toString();

        PedidoDAO orderDao = new PedidoDAO();
        String[] vetorIdOpcionais = hashMap.keySet().toArray(new String[hashMap.size()]);
        int[] idOpcionais = new int[vetorIdOpcionais.length];
        for(int i=0;i<vetorIdOpcionais.length;i++){
          idOpcionais[i]=Integer.parseInt(vetorIdOpcionais[i]);
        }
        boolean resultado = orderDao.inserirPedido(new Pedido(0,coment,Integer.parseInt(getSharedPreferences(PREFS_NAME, 0).getString("prato", "")),getSharedPreferences("LoginInfo", 0).getInt("idUsuario", 0),null));
        if (resultado == true) {
          if(orderDao.inserirPedidoOpcionais(orderDao.recuperaMaiorId(getSharedPreferences(PREFS_NAME, 0).getInt("idUsuario", 0)),idOpcionais) == true) {
            startActivity(homeIt);
            finish();
          }else
            Toast.makeText(getApplicationContext(), "Erro ao enviar pedido", Toast.LENGTH_LONG).show();
        }else
          Toast.makeText(getApplicationContext(), "Erro ao enviar pedido", Toast.LENGTH_LONG).show();
      }
    });
    limparBT.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        pratoTV.setText("");
        spinner.setAdapter(null);
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
        editor.clear();
        editor.commit();
      }
    });
  }
  @Override
  protected void onDestroy(){
    super.onDestroy();
    finish();
  }
  @Override
  public void onPause(){
    super.onPause();
    String str = getSharedPreferences(PREFS_NAME, 0).getString("prato", "");
    SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
    editor.putString("prato", str);
    SaveSettings();
    editor.commit();
    finish();
  }

  @Override
  protected void onStop() {
    super.onStop();
    String str = getSharedPreferences(PREFS_NAME, 0).getString("prato", "");
    SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
    editor.putString("prato", str);
    SaveSettings();
    editor.commit();
  }

  @Override
  public void onResume(){
    super.onResume();
    String str = getSharedPreferences(PREFS_NAME, 0).getString("prato", "");
    if(str!="") {
      Prato prato = PratoDAO.recuperaPorId(Integer.parseInt(str));
      pratoTV.setText(prato.toString());
    }else
      pratoTV.setText("");
  }
  public void LoadSettings() {
    SharedPreferences pref = getSharedPreferences(PREFS_NAME, 0);
    hashMap = (HashMap<String, String>) pref.getAll();
    if(hashMap == null) {
      hashMap = new HashMap<String, String>();
    }
  }

  public void SaveSettings() {
    if(hashMap == null) {
      hashMap = new HashMap<String, String>();
    }

    String str = getSharedPreferences(PREFS_NAME, 0).getString(Integer.toString(position), "");
    SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
    editor.putString(Integer.toString(position), str);
    editor.commit();
  }
}