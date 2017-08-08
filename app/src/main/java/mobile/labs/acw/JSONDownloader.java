package mobile.labs.acw;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 424574 on 13/03/2017.
 */

public class JSONDownloader extends AsyncTask<String, Void, JSONObject> {


    String newUrl = null;
    private Context mcontext = null;

    public JSONDownloader(Context pcontext) {
        mcontext = pcontext;
    }

    JSONObject jsonResult = null;


    @Override
    protected JSONObject doInBackground(String... urls) {

        try {
            newUrl = urls[0].substring(urls[0].indexOf('/') + 1);
            FileInputStream reader = mcontext.getApplicationContext().openFileInput(newUrl);
            String result = "";
            int data = reader.read();
            while (data != -1) {
                char current = (char) data;
                result += current;
                data = reader.read();
            }
            jsonResult = new JSONObject(result);
            return jsonResult;
        } catch (Exception e) {
            //e.printStackTrace();
            String result = "";
            String urlstring = "http://www.hull.ac.uk/php/349628/08027/acw/" + urls[0];
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urlstring);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();

                }


                File JSONinfo = new File(mcontext.getFilesDir(), newUrl);
                FileWriter fileWriter = new FileWriter(JSONinfo);
                fileWriter.append(result).flush();
                fileWriter.close();
                jsonResult = new JSONObject(result);
                return jsonResult;
            } catch (Exception f) {
                e.printStackTrace();
                //
            }
        }
        return jsonResult;
    }
}

