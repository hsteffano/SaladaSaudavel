package com.project.saladaSaudavel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.project.saladaSaudavel.DAOs.UsuarioDAO;
import com.project.saladaSaudavel.Entidades.Usuario;

import org.ksoap2.SoapFault;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends Activity{

    static boolean errored=false;
    private EditText loginET,senhaET;
    private Button loginBT,registroBT;
    String login, senha, loginDigitado, senhaDigitada;
    boolean loginStatus;
    private String SHAHash;
    public static int NO_OPTIONS=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        loginET=(EditText)findViewById(R.id.et_login);
        senhaET=(EditText)findViewById(R.id.et_senha);
        loginBT=(Button)findViewById(R.id.bt_login);
        registroBT=(Button)findViewById(R.id.bt_registro);
        final Intent abreRegistroIt=new Intent(this, RegistroActivity.class);
        final Intent abreHomeIt=new Intent(this, HomeActivity.class);

        Intent registroIt = getIntent();
        loginET.setText(registroIt.getStringExtra("login"));
        senhaET.setText(registroIt.getStringExtra("senha"));


        registroBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(abreRegistroIt);
            }
        });

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginET.getText().toString().equals("") || senhaET.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Campos de login não preenchidos", Toast.LENGTH_LONG).show();
                }else
                    if (loginET.getText().length() <= 1) {
                        Toast.makeText(getApplicationContext(), "Login precisa ter mais de 1 caracteres", Toast.LENGTH_LONG).show();
                    }if (senhaET.getText().length() <= 1) {
                        Toast.makeText(getApplicationContext(), "Senha precisa ter mais de 1 caracteres", Toast.LENGTH_LONG).show();
                    }
                loginDigitado = loginET.getText().toString();
                senhaDigitada = senhaET.getText().toString();
                senha = computeSHAHash(senhaDigitada);
                try {
                    Usuario usuario = UsuarioDAO.login(loginDigitado);
                    if(senha.equals(usuario.getSenha())){
                        SharedPreferences.Editor editor = getSharedPreferences("LoginInfo", 0).edit();
                        editor.putInt("idUsuario", usuario.getId());
                        editor.putBoolean("Logado", true);
                        editor.commit();

                        startActivity(abreHomeIt);
                        loginET.setText("");
                        senhaET.setText("");
                        finish();
                    }else
                        Toast.makeText(getApplicationContext(), "Senha incorreta", Toast.LENGTH_LONG).show();
                } catch (SoapFault soapFault) {
                    soapFault.printStackTrace();
                    finish();
                }
            }
        });

    }

    private static String convertToHex(byte[] data) throws java.io.IOException{


        StringBuffer sb = new StringBuffer();
        String hex=null;

        hex= Base64.encodeToString(data, 0, data.length, NO_OPTIONS);

        sb.append(hex);

        return sb.toString();
    }
    public String computeSHAHash(String password){
        MessageDigest mdSha1 = null;
        try
        {
            mdSha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e1) {
            Log.e("myapp", "Error initializing SHA1 message digest");
        }
        try {
            mdSha1.update(password.getBytes("ASCII"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] data = mdSha1.digest();
        try {
            SHAHash=convertToHex(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SHAHash;
    }

}
