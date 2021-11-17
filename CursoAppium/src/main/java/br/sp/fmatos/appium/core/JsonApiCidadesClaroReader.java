package br.sp.fmatos.appium.core;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static br.sp.fmatos.appium.core.Utils.*;

public class JsonApiCidadesClaroReader {

    public static void main(String[] args) {
        long startTime = getSystemCurrentTimeInMilliseconds();
        String linhaDeImpressao;
        String textosParaConverter =
                "1509129695986,1509129727047,1509129698706,1509129648628,1509129728017,1509129730198,1509129703887,1509129705856,1509129744837,1509129709172,1509129709481,1509129719721,1509129636701,1509129707464,1509129729550";
        List<String> id_cidade = quebrarTextosDeUmaStringEArmazanarEmUmArrayList(textosParaConverter, ",");
        String dddsEsperado = "95";
        consolePrinter("LISTA DE CIDADES DDD [" + dddsEsperado+"]");
        int totalDeCidades = id_cidade.size();
        int contadorDeCidades = 0;
        String jsonString = callURL("https://preprod.claro.com.br/sites/api/cidades");
//        System.out.println("\n\njsonString: " + jsonString);
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            int count = jsonArray.length(); // get totalCount of all jsonObjects
            for (int i = 0; i < count; i++) {   // iterate through jsonArray
                JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position
                for (String s : id_cidade) {
                    if (jsonObject.get("id_cidade").equals(s)) {
                        contadorDeCidades++;
                        linhaDeImpressao = "[" + contadorDeCidades + "]" + jsonObject.toString();
                        if (!dddsEsperado.equals("")) {
                            String ddd = jsonObject.get("ddd").toString();
                            if (!dddsEsperado.contains(ddd)) {
                                linhaDeImpressao = linhaDeImpressao.concat(" <<< [DDD NÃO ESPERADO]");
                            }
                        }
                        consolePrinter(linhaDeImpressao);
//                        consolePrinter(true, "Estado: " + jsonObject.get("estado").toString());
//                        consolePrinter(true, "Id_cidade: " + jsonObject.get("id_cidade").toString());
//                        consolePrinter(true, "DDD: " + jsonObject.get("ddd").toString());
//                        consolePrinter(true, "Nome: " + jsonObject.get("nome").toString());
                    }
                }
            }
            consolePrinter("TOTAL DE CIDADES OBTIDAS[" + totalDeCidades + "]");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        long endTime = getSystemCurrentTimeInMilliseconds();
        long totalTime = endTime - startTime;
        consolePrinter("TEMPO TOTAL DE EXECUÇÃO (milisegundos) [" + totalTime + "]");
        consolePrinter(
                "_________________________________________________________________________________________________________________________________");
    }

    private static String callURL(String myURL) {
        consolePrinter("Json URL address:" + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                int cp;
                while ((cp = bufferedReader.read()) != -1) {
                    sb.append((char) cp);
                }
                bufferedReader.close();
//                if (bufferedReader != null) {
//                    int cp;
//                    while ((cp = bufferedReader.read()) != -1) {
//                        sb.append((char) cp);
//                    }
//                    bufferedReader.close();
//                }
            }
            try {
                assert in != null;
                in.close();
            }catch (NullPointerException npe){
                npe.printStackTrace();
            }

        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:" + myURL, e);
        }
        return sb.toString();
    }
}