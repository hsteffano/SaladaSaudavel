package com.project.saladaSaudavel.DAOs;

import android.util.Log;

import com.project.saladaSaudavel.Entidades.Pedido;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class PedidoDAO {

    private static final String URL = "http://192.168.241.187:8080/SaladaS/services/PedidoDAO?wsdl";
    private static final String NAMESPACE = "http://DAOs.saladaSaudavel.project.com";

    private static final String INSERIR= "inserirPedido";
    private static final String INSERIR_PEDIDO_OPCIONAIS= "inserirPedidoOpcionais";
    private static final String RECUPERA_MAIOR_ID= "recuperaMaiorId";
    private static final String RECUPERA_POR_ID= "recuperaPorId";
    private static final String RECUPERA_POR_CLIENTE= "recuperaPorCliente";

    public boolean inserirPedido(Pedido pedido){

        SoapObject inserirPedido = new SoapObject(NAMESPACE,INSERIR);
        SoapObject order = new SoapObject(NAMESPACE,"pedido");

        order.addProperty("idPedido", pedido.getIdPedido());
        order.addProperty("descrAdic", pedido.getDescr());
        order.addProperty("idPrato", pedido.getIdPrato());
        order.addProperty("idCliente", pedido.getIdCliente());
        order.addProperty("data", pedido.getData());

        inserirPedido.addSoapObject(order);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(inserirPedido);
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
    public boolean inserirPedidoOpcionais(int idPedido, int[] idOpcionais){

        SoapObject inserirPedido = new SoapObject(NAMESPACE,INSERIR_PEDIDO_OPCIONAIS);
        inserirPedido.addProperty("idPedido", idPedido);
        inserirPedido.addProperty("idOpcionais", idOpcionais);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(inserirPedido);
        envelope.implicitTypes = true;
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + INSERIR_PEDIDO_OPCIONAIS, envelope);
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
    public int recuperaMaiorId(int id){

        int idPedido = 0;

        SoapObject recuperaPorId = new SoapObject(NAMESPACE,RECUPERA_MAIOR_ID);
        recuperaPorId.addProperty("id", id);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(recuperaPorId);
        envelope.implicitTypes = true;
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + RECUPERA_MAIOR_ID, envelope);
            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
            idPedido = Integer.parseInt(resposta.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return idPedido;
    }

    public Pedido recuperaPorId(int id){

        Pedido pedido = null;

        SoapObject recuperaPorId = new SoapObject(NAMESPACE,RECUPERA_POR_ID);
        recuperaPorId.addProperty("id",id);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(recuperaPorId);
        envelope.implicitTypes = true;
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + RECUPERA_POR_ID, envelope);
            SoapObject resposta = (SoapObject) envelope.getResponse();
            pedido = new Pedido();
            pedido.setIdPedido(Integer.parseInt(resposta.getProperty("idPedido").toString()));
            pedido.setIdPrato(Integer.parseInt(resposta.getProperty("idPrato").toString()));
            pedido.setIdCliente(Integer.parseInt(resposta.getProperty("idCliente").toString()));
            pedido.setDescr(resposta.getProperty("descrAdic").toString());
            pedido.setData(resposta.getProperty("data").toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return pedido;
    }
    public static ArrayList<Pedido> recuperaPorCliente(int id){

        ArrayList<Pedido> lista = new ArrayList<Pedido>();

        SoapObject recuperaPorCliente = new SoapObject(NAMESPACE,RECUPERA_POR_CLIENTE);
        recuperaPorCliente.addProperty("idCliente",id);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(recuperaPorCliente);
        envelope.implicitTypes = true;
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + RECUPERA_POR_CLIENTE, envelope);
            if (envelope.bodyIn instanceof SoapFault) {
                String str = ((SoapFault) envelope.bodyIn).faultstring;
                Log.i("erro pedido", str);
            } else{
                SoapObject resposta = (SoapObject) envelope.bodyIn;
                Pedido[] respostaVetor = new Pedido[resposta.getPropertyCount()];
                int count=respostaVetor.length;
                for(int i = 0; i <count; ++i){
                    SoapObject object = (SoapObject)resposta.getProperty(i);
                    Pedido pedido = new Pedido();
                    pedido.setIdPedido(Integer.parseInt(object.getProperty(0).toString()));
                    pedido.setIdPrato(Integer.parseInt(object.getProperty(1).toString()));
                    pedido.setIdCliente(Integer.parseInt(object.getProperty(2).toString()));
                    pedido.setDescr(object.getProperty(3).toString());
                    pedido.setData(object.getProperty(4).toString());

                    lista.add(pedido);
                }
            }

        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
