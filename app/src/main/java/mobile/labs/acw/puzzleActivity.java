package mobile.labs.acw;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class puzzleActivity extends AppCompatActivity {


    ArrayList<pictureHolder> pictureHolders = new ArrayList<>();


    GridView simpleGrid;
    pictureHolder s1 = null;
    pictureHolder s2 = null;
    int score = 0;
    private long mLastClickTime = 0;
    long time;
    String puzzleName;
    boolean gameO = false;
    int turntover;
    int pos1=0;
    int pos2=0;


    public void gameOver(){
        Toast.makeText(getApplicationContext(),getText(R.string.gameover).toString() +" "+ score,Toast.LENGTH_SHORT).show();
        Intent gameOver = new Intent(puzzleActivity.this, HighScores.class );
        /*gameOver.putExtra("score", score);
        gameOver.putExtra("name", puzzleName );*/
        SharedPreferences sharedPreferences = getSharedPreferences("prefs",Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(puzzleName, score).commit();

        gameO = true;

        //gameOver.putExtra("time",time);
        startActivity(gameOver);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_puzzle2);

        JSONObject result, result2;
        JSONDownloader task = new JSONDownloader(getApplicationContext());
        JSONDownloader task2 = new JSONDownloader(getApplicationContext());


        Bundle extras = getIntent().getExtras();
        String value = extras.getString("info");
        puzzleName = value;


        try {
            result = task.execute("puzzles/" + value).get();
            Log.i("test", result.toString());

            JSONObject jsonObject = result.getJSONObject("Puzzle");

            //use these numbers to populate grid
            JSONArray jsonArray = jsonObject.getJSONArray("Layout");
            //**

            String pictureSet = jsonObject.getString("PictureSet");

            result2 = task2.execute("picturesets/" + pictureSet).get();

            JSONArray pictureFiles = result2.getJSONArray("PictureFiles");

            final ArrayList<String> fruit = new ArrayList<>();


            for (int i = 0; i < pictureFiles.length(); i++) {
                fruit.add((String) pictureFiles.get(i));
            }


            for (String element : fruit) {

                imgDownloader task1 = new imgDownloader(getApplicationContext());

                Bitmap downloadedImage = null;

                try {

                    //simpleGrid = (GridView) findViewById(R.id.simpleGridView);
                    task1.execute(element).get();


                    // pictureHolder p = new pictureHolder(downloadedImage, result4);

                    /// TODO: 20/03/2017 Write logic for getting specific pictures from layout

                    // pictureHolders.add(p);
                    //AdapterClass adapterClass = new AdapterClass(getApplicationContext(), pictureHolders);
                    //simpleGrid.setAdapter(adapterClass);

                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    e1.printStackTrace();
                }
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                int imageId = jsonArray.optInt(i) - 1;
                String imgName = pictureFiles.optString(imageId);
                imgDownloader task3 = new imgDownloader(getApplicationContext());

                Bitmap tempImg = task3.execute(imgName).get();

                pictureHolder p = new pictureHolder(tempImg, imageId, this);
                pictureHolders.add(p);





            }






            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int width = metrics.widthPixels;
            int height = metrics.heightPixels;


            simpleGrid = (GridView) findViewById(R.id.simpleGridView);

            Integer rows = jsonObject.getInt("Rows");

            /*GridView.LayoutParams lp = new GridView.LayoutParams(width,height/rows);
            simpleGrid.setLayoutParams(lp);*/

            simpleGrid.setNumColumns(pictureHolders.size() / rows);

            AdapterClass adapterClass = new AdapterClass(this, pictureHolders, rows, height, width);


            simpleGrid.setNumColumns(pictureHolders.size() / rows);
            simpleGrid.setAdapter(adapterClass);

            for (pictureHolder ph :
                    pictureHolders) {
                ph.imageView.setImageBitmap(ph.GetBitmap());
            }

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (pictureHolder ph :
                                    pictureHolders) {
                                ph.imageView.setImageResource(R.drawable.simon);
                            }
                        }
                    });

                    return null;
                }
            }.execute();



            /*

            for (pictureHolder ph :
                    pictureHolders) {
                ph.imageView.setImageBitmap(ph.GetBitmap());
            }


            Thread.sleep(3000);
            for (pictureHolder ph :
                    pictureHolders) {
                ph.imageView.setImageResource(R.drawable.simon);
            }
*/



        /*    for (int i=0; i<pictureHolders.size(); i++){
                pictureHolders.add(new pictureHolder());
                pictureHolders.get(i);

            }*/

            simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*    if (SystemClock.elapsedRealtime() - mLastClickTime < 600) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();*/
                    if (!pictureHolders.get(position).isMatched) {

                        if (s1 == null) {
                            pos1 = position;
                            s1 = pictureHolders.get(position);
                            s1.setImageView((ImageView) simpleGrid.getAdapter().getItem(position));
                            s1.imageView.setAlpha(0.5f);
                        } else if (s2 == null) {
                            pos2 = position;
                            if (pos1 != pos2) {
                                s2 = pictureHolders.get(position);
                                s2.setImageView((ImageView) simpleGrid.getAdapter().getItem(position));
                                if (s1 != null && s2 != null && s1 != s2) {
                                    if (s1.layoutID.equals(s2.layoutID)) {
                                        s1.imageView.setImageBitmap(s1.GetBitmap());
                                        s1.imageView.setAlpha(1f);
                                        //s1.imageView.setMinimumHeight(LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                                        //s1.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                        s2.imageView.setImageBitmap(s2.GetBitmap());
                                        //s2.imageView.setMinimumHeight(LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                                        //s2.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                        score++;
                                        turntover++;
                                        pictureHolders.get(pos1).isMatched = true;
                                        pictureHolders.get(pos2).isMatched = true;
                                        if (turntover == pictureHolders.size() / 2) {
                                            gameOver();
                                        }

                                        s1 = null;
                                        s2 = null;
                                    } else {

                                        new AsyncTask<Void, Void, Void>() {
                                            @Override
                                            protected Void doInBackground(Void... params) {
                                                try {
                                                    Thread.sleep(1000);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        score--;
                                                        s1.imageView.setAlpha(1f);
                                                        s1.imageView.setImageResource(R.drawable.simon);
                                                        s2.imageView.setImageResource(R.drawable.simon);
                                                        s1 = null;
                                                        s2 = null;
                                                    }
                                                });

                                                return null;
                                            }
                                        }.execute();

                                        s1.imageView.setImageBitmap(s1.GetBitmap());
                                       // s1.imageView.setMinimumHeight(LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                                       // s1.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                        s1.imageView.invalidate();
                                        s2.imageView.setImageBitmap(s2.GetBitmap());
                                        //s2.imageView.setMinimumHeight(LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                                       // s2.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                        s2.imageView.invalidate();


                                        Toast.makeText(getApplicationContext(), R.string.nomatch, Toast.LENGTH_SHORT).show();


                                    }


                                    //icon.setImageBitmap(pictureHolders.get(position).GetBitmap());
                                }
                            } else {
                                s1.imageView.setAlpha(1f);
                                s1 = null;
                            }
                        }

                    }
                    //icon.setImageBitmap(pictureHolders.get(position).GetBitmap());
                    // int pid = pictureHolders.get(position).layoutID;


                    //icon.setImageBitmap(pictureHolders.get(position).GetBitmap());
                    //int pid = pictureHolders.get(position).layoutID;
                    //icon.setImageBitmap(pictureHolders.get(position).GetBitmap());
                }




            });



            new CountDownTimer(pictureHolders.size()*2500, 1000) {

                public void onTick(long millisUntilFinished) {
                    Log.i("time",String.valueOf(millisUntilFinished * 1000) );
                    time = (millisUntilFinished / 60000)+(millisUntilFinished % 60000 / 1000);
                }

                public void onFinish() {
                  if (gameO = false)
                  {
                    gameOver();}
                }
            }.start();


            Log.i("tester1", pictureSet);
            Log.i("tester", jsonArray.toString());
            Log.i("Tester3", result2.toString());
            Log.i("Tester4", pictureFiles.toString());
            Log.i("Tester5", fruit.toString());






        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
    /*private  void dbCreate (){
        try {
            SQLiteDatabase myDb = this.openOrCreateDatabase("Puzzles", MODE_PRIVATE, null);
            myDb.execSQL("CREATE TABLE IF NOT EXISTS puzzles(puzzlename VARCHAR, jsondata VARCHAR)");
            myDb.execSQL("INSERT INTO puzzles(puzzlename, jsondata) VALUES ('"+name+"', '"+jsdata+"')");

            Cursor c =  myDb.rawQuery("SELECT * FROM puzzles", null);

            int nameIndex = c.getColumnIndex("puzzlename");
            int jsinfoIndex = c.getColumnIndex("jsondata");

            c.moveToFirst();

            while (c != null){

                Log.i("Name", c.getString(nameIndex));
                Log.i("Data", c.getString(jsinfoIndex));
                c.moveToNext();
            }


        } catch (Exception e){

            e.printStackTrace();
        }
    }

    private void jsdownloader() {
        JSONDownloader1 task = new JSONDownloader1();
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("info");
        name = value;

        task.execute("http://www.hull.ac.uk/php/349628/08027/acw/puzzles/" + value);
    }

    private void imgdownloader() {
        imgDownloader dl = new imgDownloader();
        Bitmap image;
        try {
            dl.execute("http://www.hull.ac.uk/php/349628/08027/acw/images/apple.jpg").get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int num1 = 0;
    public int num2 = 1;
    String name;
    String jsdata;














        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            Log.i("stuff", result.toString());
            jsdata = result.toString();

            try {
                FileOutputStream fileout=openFileOutput("puzzledata.txt", MODE_PRIVATE);
                OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
                outputWriter.write(result.toString());
                outputWriter.close();


            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONObject jsonObject = result.getJSONObject("Puzzle");






                JSONArray jsonArray = jsonObject.getJSONArray("Layout");

                for (int i = 0; i < jsonArray.length(); i++){

                }







        } catch (JSONException e) {
                e.printStackTrace();
            }


            //imgdownloader();






            /*try {

                     JSONObject jsonObject = result.getJSONObject("PuzzleIndex");



            }



            //      JSONObject jsonObject = result.getJSONObject("Puzzle");
            //      String pictureSet = jsonObject.getString("PictureSet");



            catch (JSONException e){
                e.printStackTrace();
            }


        }
    }*/




