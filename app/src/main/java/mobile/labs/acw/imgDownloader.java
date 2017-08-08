package mobile.labs.acw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by 424574 on 13/03/2017.
 */
public class imgDownloader extends AsyncTask<String, String, Bitmap> {

    private Context mcontext = null;

    public imgDownloader(Context pcontext) {
        mcontext = pcontext;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {

        Bitmap myBitmap = null;

        try {
            FileInputStream reader = mcontext.getApplicationContext().openFileInput(urls[0]);
            myBitmap = BitmapFactory.decodeStream(reader);
            return myBitmap;
        } catch (FileNotFoundException e) {
            try {

                String urlstring = "http://www.hull.ac.uk/php/349628/08027/acw/images/" + urls[0];
                URL url = new URL(urlstring);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(inputStream);
                File picture = new File(mcontext.getFilesDir(), urls[0]);
                FileOutputStream writer = new FileOutputStream(picture);
                myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, writer);
                writer.flush();
                writer.close();


                return myBitmap;
            } catch (MalformedURLException g) {
                g.printStackTrace();
            } catch (IOException f) {
                f.printStackTrace();
            }
            e.printStackTrace();
        }


        return myBitmap;
    }
}