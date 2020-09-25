package http;

import http.response.HttpResponse;
import util.Constantes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpUsuario {

    public String sendGET(String url, String method, String token) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("User-Agent", Constantes.getUserAgent());
        con.setRequestProperty("Accept-Charset", "UTF-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        con.setRequestProperty("Authorization", token);

        int responseCode = con.getResponseCode();
        System.out.println("Sending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public String sendGET(String url, String method) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("User-Agent", Constantes.getUserAgent());
        con.setRequestProperty("Accept-Charset", "UTF-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        con.setRequestProperty("Authorization", "");

        int responseCode = con.getResponseCode();
        System.out.println("Sending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public String sendPOST(String url, String json, String method) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod(method);
        con.setRequestProperty("User-Agent", Constantes.getUserAgent());
        con.setRequestProperty("Accept-Charset", "ISO-8859-1");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5,pt-br");
        con.setRequestProperty("Content-Type", "application/json;charset=ISO-8859-1");

        con.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
        out.write(json);
        out.flush();
        out.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Submit JSON: " + json);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public Map<Integer, String> autenticated(String url, String json, String method) throws IOException {
        HttpResponse response = new HttpResponse();
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod(method);
        con.setRequestProperty("User-Agent", Constantes.getUserAgent());
        con.setRequestProperty("Accept-Charset", "ISO-8859-1");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5,pt-br");
        con.setRequestProperty("Content-Type", "application/json;charset=ISO-8859-1");

        con.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
        out.write(json);
        out.flush();
        out.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Submit JSON: " + json);
        System.out.println("Response Code : " + responseCode);

        if (responseCode == 200) {
            response.addResponse(responseCode, con.getHeaderField("Authorization"));
            return response.getResponse();
        } else if (responseCode == 401) {
            response.addResponse(responseCode, "Email ou senha inválidos");
            return response.getResponse();
        } else {
            response.addResponse(responseCode, "Algo deu errado, tente novamente mais tarde");
            return response.getResponse();
        }
    }

    public String refreshToken(String url, String method) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod(method);
        con.setRequestProperty("User-Agent", Constantes.getUserAgent());
        con.setRequestProperty("Accept-Charset", "ISO-8859-1");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5,pt-br");
        con.setRequestProperty("Content-Type", "application/json;charset=ISO-8859-1");

        con.setDoOutput(true);
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        return con.getHeaderField("Authorization");
    }
}
