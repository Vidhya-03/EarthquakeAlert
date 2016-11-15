package com.earthquakealert.helper;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.earthquakealert.EarthquakeActivity;
import com.earthquakealert.model.EarthquakeDetails;
import com.earthquakealert.util.ApplicationGlobalDataManager;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vidhyasri on 10/11/2016.
 */
public class ServerRequestTask extends IntentService{

    URL _url;
    HttpURLConnection _httpURLConnection;
    String _result;
    public static final String RESPONSE_STRING = "response";

    public ServerRequestTask()
    {
        super("ServerRequestTask");
    }

    public ServerRequestTask(String name) {
        super(name);
    }

    public ServerRequestTask(Context context) {
        super(String.valueOf(context));

    }
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            _url = new URL(intent.getExtras().getString("url"));
            System.out.println(_url);
            _httpURLConnection = (HttpURLConnection) _url.openConnection();
            _httpURLConnection.setRequestMethod("GET");
            _httpURLConnection.setDoInput(true);
            int response = _httpURLConnection.getResponseCode();
            InputStream in = new BufferedInputStream(_httpURLConnection.getInputStream());
            _result = readStream(in);
            parseData(_result);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            _httpURLConnection.disconnect();
        }

    }

    private String readStream(InputStream in) throws IOException {
        BufferedReader reader =new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String result = "",data="";
        while ((data = reader.readLine()) != null){
            result += data + "\n";
        }
        return result;
    }

    private void parseData(String response) {
        EarthquakeDetails earthquakeDetails;
        try {
            JSONObject jsonObject = new JSONObject(response);

            if(jsonObject.has("features")) {
                ObjectMapper objectMapper = new ObjectMapper();
                earthquakeDetails = objectMapper.readValue(response, EarthquakeDetails.class);
                System.out.println(earthquakeDetails);
                ApplicationGlobalDataManager.getInstace().set_earthquakeDetails(earthquakeDetails);
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(EarthquakeActivity.ServerRequestReceiver.PROCESS_RESPONSE);
                broadcastIntent.putExtra(RESPONSE_STRING, "datareceived");
                sendBroadcast(broadcastIntent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
