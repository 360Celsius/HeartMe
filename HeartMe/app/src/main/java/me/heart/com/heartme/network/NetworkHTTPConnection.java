package me.heart.com.heartme.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class NetworkHTTPConnection {
    static final int TIMEOUT_SOCKET = 160000;
    static final int TIMEOUT_CONNECTION = 100000;

    private static NetworkHTTPConnection _instance;


    private NetworkHTTPConnection()
    {

    }

    public static NetworkHTTPConnection getInstance()
    {
        if (_instance == null)
        {
            _instance = new NetworkHTTPConnection();
        }
        return _instance;
    }

    public static String getHttp(String apiAction){

        try {
            URL obj = null;
            obj = new URL(apiAction);
            HttpURLConnection HttpConnection = (HttpURLConnection) obj.openConnection();
            HttpConnection.setReadTimeout(TIMEOUT_SOCKET);
            HttpConnection.setConnectTimeout(TIMEOUT_CONNECTION);
            HttpConnection.setRequestMethod("GET");
            HttpConnection.setRequestProperty("User-Agent", "Android");
            HttpConnection.setRequestProperty("accept", "application/json");
            System.setProperty("http.keepAlive", "false");

            int responseCode = HttpConnection.getResponseCode();
            //Log.e("ServiceTest","NetworkHTTPConnection getHttp responseCode"+responseCode);

            if(responseCode==HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(HttpConnection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                String s = new String(response.toString());


                //Log.e("ServiceTest","NetworkHTTPConnection getHttp "+s);
                return s;
            }else{
                //Log.e("ServiceTest","NetworkHTTPConnection getHttp "+"NULL");
                return null;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            //Log.e("ServiceTest","NetworkHTTPConnection getHttp "+"MalformedURLException");
            return null;
        } catch (ProtocolException e) {
            e.printStackTrace();
            //Log.e("ServiceTest","NetworkHTTPConnection getHttp "+"ProtocolException");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            //Log.e("ServiceTest","NetworkHTTPConnection getHttp "+"IOException");
            return null;
        }

    }
}
