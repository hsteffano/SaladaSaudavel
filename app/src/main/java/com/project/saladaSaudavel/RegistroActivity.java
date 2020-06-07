package com.project.saladaSaudavel;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.saladaSaudavel.DAOs.UsuarioDAO;
import com.project.saladaSaudavel.Entidades.Usuario;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegistroActivity extends Activity {

    String nome, sobrenome, endereco, cidade, cep, tipo, login, senha, senhaConf;
    int telefone;
    private EditText nomeET,sobrenomeET, enderecoET, cidadeET, cepET, telefoneET, loginET, senhaET, senhaConfET;
    private Button registroBT;
    private String SHAHash;
    public static int NO_OPTIONS=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        if(Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        registroBT=(Button)findViewById(R.id.bt_registrar);
        nomeET=(EditText)findViewById(R.id.et_nome);
        sobrenomeET=(EditText)findViewById(R.id.et_sobrenome);
        enderecoET=(EditText)findViewById(R.id.et_endereco);
        cidadeET=(EditText)findViewById(R.id.et_cidade);
        cepET=(EditText)findViewById(R.id.et_cep);
        telefoneET=(EditText)findViewById(R.id.et_telefone);
        loginET=(EditText)findViewById(R.id.et_email);
        senhaET=(EditText)findViewById(R.id.et_senha);
        senhaConfET =(EditText)findViewById(R.id.et_senha1);

        final Intent it = new Intent(this,LoginActivity.class);

        registroBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validar(new EditText[]{nomeET,sobrenomeET, enderecoET, cidadeET, cepET, telefoneET, loginET, senhaET, senhaConfET})==true){

                    nome = nomeET.getText().toString();
                    sobrenome = sobrenomeET.getText().toString();
                    endereco = enderecoET.getText().toString();
                    cidade = cidadeET.getText().toString();
                    cep = cepET.getText().toString();
                    telefone = Integer.parseInt(telefoneET.getText().toString());
                    login = loginET.getText().toString();
                    senha = senhaET.getText().toString();
                    senhaConf = senhaConfET.getText().toString();

                    tipo="cliente";

                    if(senha.equals(senhaConf)){
                        UsuarioDAO usrDao = new UsuarioDAO();
                        boolean resultado = usrDao.inserirUsuario(new Usuario(0,nome,sobrenome,endereco,cidade,cep,telefone,tipo,login,computeSHAHash(senha)));
                        Log.d("webSer", resultado + "");
                        if(resultado==true) {
                            Toast.makeText(getApplicationContext(), "Usuário registrado", Toast.LENGTH_LONG).show();
                            it.putExtra("login",login);
                            it.putExtra("senha",senha);
                            startActivity(it);
                        }else
                            Toast.makeText(getApplicationContext(), "Erro ao registrar usuário", Toast.LENGTH_LONG).show();
                    }else
                        Toast.makeText(getApplicationContext(), "Senha e confirmação estão diferentes", Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
            }
        });

    }
    private boolean validar(EditText[] campos){
        for(int i=0; i<campos.length; i++){
            EditText campoAtual=campos[i];
            if(campoAtual.getText().toString().length()<=0){
                return false;
            }
        }
        return true;
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
