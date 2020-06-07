package com.project.saladaSaudavel.DAOs;

import com.project.saladaSaudavel.Entidades.Usuario;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class UsuarioDAO {

    private static final String URL = "http://192.168.241.187:8080/SaladaS/services/UsuarioDAO?wsdl";
    private static final String NAMESPACE = "http://DAOs.saladaSaudavel.project.com";

    private static final String INSERIR = "inserirUsuario";
    private static final String LOGIN= "login";
    private static final String ATUALIZA_SENHA= "atualizarSenha";

    public static boolean inserirUsuario(Usuario usuario){

        SoapObject inserirUsuario = new SoapObject(NAMESPACE,INSERIR);
        SoapObject usr = new SoapObject(NAMESPACE,"usuario");

        usr.addProperty("id",usuario.getId());
        usr.addProperty("nome",usuario.getNome());
        usr.addProperty("sobrenome",usuario.getSobrenome());
        usr.addProperty("endereco",usuario.getEndereco());
        usr.addProperty("cidade",usuario.getCidade());
        usr.addProperty("cep",usuario.getCep());
        usr.addProperty("telefone",usuario.getTelefone());
        usr.addProperty("tipo",usuario.getTipo());
        usr.addProperty("login",usuario.getLogin());
        usr.addProperty("senha", usuario.getSenha());

        inserirUsuario.addSoapObject(usr);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(inserirUsuario);
        envelope.implicitTypes = true;
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + INSERIR, envelope);
            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
            return Boolean.parseBoolean(resposta.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static Usuario login(String login) throws SoapFault {

        Usuario usuario = null;

        SoapObject logar = new SoapObject(NAMESPACE,LOGIN);

        logar.addProperty("login",login);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(logar);
        envelope.implicitTypes = true;
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + LOGIN, envelope);
            SoapObject resposta = (SoapObject) envelope.getResponse();
            usuario = new Usuario();
            usuario.setId(Integer.parseInt(resposta.getProperty("id").toString()));
            usuario.setNome(resposta.getProperty("nome").toString());
            usuario.setSobrenome(resposta.getProperty("sobrenome").toString());
            usuario.setEndereco(resposta.getProperty("endereco").toString());
            usuario.setCidade(resposta.getProperty("cidade").toString());
            usuario.setCep(resposta.getProperty("cep").toString());
            usuario.setTelefone(Integer.parseInt(resposta.getProperty("telefone").toString()));
            usuario.setTipo(resposta.getProperty("tipo").toString());
            usuario.setLogin(resposta.getProperty("login").toString());
            usuario.setSenha(resposta.getProperty("senha").toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        
        return usuario;
    }
    public  boolean atualizarSenha(Usuario usuario){

        SoapObject inserirUsuario = new SoapObject(NAMESPACE,ATUALIZA_SENHA);
        SoapObject usr = new SoapObject(NAMESPACE,"usuario");

        usr.addProperty("id",usuario.getId());
        usr.addProperty("nome",usuario.getNome());
        usr.addProperty("sobrenome",usuario.getSobrenome());
        usr.addProperty("endereco",usuario.getEndereco());
        usr.addProperty("cidade",usuario.getCidade());
        usr.addProperty("cep",usuario.getCep());
        usr.addProperty("telefone",usuario.getTelefone());
        usr.addProperty("tipo",usuario.getTipo());
        usr.addProperty("login",usuario.getLogin());
        usr.addProperty("senha", usuario.getSenha());

        inserirUsuario.addSoapObject(usr);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(inserirUsuario);
        envelope.implicitTypes = true;
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + ATUALIZA_SENHA, envelope);
            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
            return Boolean.parseBoolean(resposta.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return false;
        }
    }
}
