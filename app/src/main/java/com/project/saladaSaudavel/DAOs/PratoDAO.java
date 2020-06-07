package com.project.saladaSaudavel.DAOs;

import com.project.saladaSaudavel.Entidades.Prato;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class PratoDAO {

    private static final String URL = "http://192.168.241.187:8080/SaladaS/services/PratoDAO?wsdl";
    private static final String NAMESPACE = "http://DAOs.saladaSaudavel.project.com";

    private static final String RECUPERA_PRATO= "recuperaPrato";
    private static final String RECUPERA_POR_ID= "recuperaPorId";

    public static ArrayList<Prato> recuperaPrato(){

        ArrayList<Prato> lista = new ArrayList<Prato>();

        SoapObject recuperaPrato = new SoapObject(NAMESPACE,RECUPERA_PRATO);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(recuperaPrato);
        envelope.implicitTypes = true;
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + RECUPERA_PRATO, envelope);
            SoapObject resposta = (SoapObject) envelope.bodyIn;
            Prato[] respostaVetor = new Prato[resposta.getPropertyCount()];
            int count=respostaVetor.length;
            for(int i = 0; i <count; ++i){
                Prato prato = new Prato();
                SoapObject object = (SoapObject)resposta.getProperty(i);

                prato.setId(Integer.parseInt(object.getProperty(0).toString()));
                prato.setNome(object.getProperty(1).toString());
                prato.setIngredientes(object.getProperty(2).toString());
                prato.setValor(object.getProperty(3).toString());

                lista.add(prato);
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

    public static Prato recuperaPorId(int id){

        Prato prato = null;

        SoapObject recuperaPorId = new SoapObject(NAMESPACE,RECUPERA_POR_ID);
        recuperaPorId.addProperty("id",id);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(recuperaPorId);
        envelope.implicitTypes = true;
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + RECUPERA_POR_ID, envelope);
            SoapObject resposta = (SoapObject) envelope.getResponse();
            prato = new Prato();
            prato.setId(Integer.parseInt(resposta.getProperty("id").toString()));
            prato.setNome(resposta.getProperty("nome").toString());
            prato.setValor(resposta.getProperty("valor").toString());
            prato.setIngredientes(resposta.getProperty("ingredientes").toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return prato;
    }
}