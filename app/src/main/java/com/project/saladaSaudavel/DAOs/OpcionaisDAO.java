package com.project.saladaSaudavel.DAOs;

import com.project.saladaSaudavel.Entidades.Opcionais;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class OpcionaisDAO {

    private static final String URL = "http://192.168.241.187:8080/SaladaS/services/OpcionaisDAO?wsdl";
    private static final String NAMESPACE = "http://DAOs.saladaSaudavel.project.com";

    private static final String RECUPERA_OPCIONAIS= "recuperaOpcionais";
    private static final String RECUPERA_POR_ID= "recuperaPorId";

    public static ArrayList<Opcionais> recuperaOpcionais(){

        ArrayList<Opcionais> lista = new ArrayList<Opcionais>();

        SoapObject recuperaOpcionais = new SoapObject(NAMESPACE,RECUPERA_OPCIONAIS);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(recuperaOpcionais);
        envelope.implicitTypes = true;
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + RECUPERA_OPCIONAIS, envelope);
            SoapObject resposta = (SoapObject) envelope.bodyIn;
            Opcionais[] respostaVetor = new Opcionais[resposta.getPropertyCount()];
            int count=respostaVetor.length;
            for(int i = 0; i <count; ++i){
                Opcionais opcional = new Opcionais();
                SoapObject pii = (SoapObject)resposta.getProperty(i);

                opcional.setId(Integer.parseInt(pii.getProperty(0).toString()));
                opcional.setNome(pii.getProperty(1).toString());
                opcional.setValor(pii.getProperty(2).toString());

                lista.add(opcional);
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
    public static Opcionais recuperaPorId(int id){

        Opcionais opcional = null;

        SoapObject recuperaPorId = new SoapObject(NAMESPACE,RECUPERA_POR_ID);
        recuperaPorId.addProperty("id",id);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(recuperaPorId);
        envelope.implicitTypes = true;
        HttpTransportSE http = new HttpTransportSE(URL);

        try {
            http.call("urn:" + RECUPERA_POR_ID, envelope);
            SoapObject resposta = (SoapObject) envelope.getResponse();
            opcional = new Opcionais();
            opcional.setId(Integer.parseInt(resposta.getProperty("id").toString()));
            opcional.setNome(resposta.getProperty("nome").toString());
            opcional.setValor(resposta.getProperty("valor").toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return opcional;
    }
}
