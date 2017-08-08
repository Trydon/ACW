package mobile.labs.acw;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    // used to ignore the initial selection the first time the activity begins
    public int num1 = 0;
    public int num2 = 1;
    public int num3 = 0;
    public int num4 = 1;
    public int num5 = 0;
    public int num6 = 1;
    public int num7 = 0;
    public int num8 = 1;
    String parts[];
    String parts2[];
    int values;
    ArrayList<String> names1 = new ArrayList<>();
    ArrayList<String> puzzleNames = new ArrayList<>();
    int amountOfPuzzles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSONDownloader task = new JSONDownloader(getApplicationContext());
        JSONObject result = null;
        Bundle extras = getIntent().getExtras();
        values = extras.getInt("Network");
        setButtons();

        Spinner sp3 = (Spinner) findViewById(R.id.spinner1);
        sp3.setVisibility(View.GONE);
        Spinner sp4 = (Spinner) findViewById(R.id.spinner2);
        sp4.setVisibility(View.GONE);
        Spinner sp5 = (Spinner) findViewById(R.id.spinner);
        sp5.setVisibility(View.GONE);
        if (values == 0) {
            Button b5 = (Button) findViewById(R.id.button5);
            b5.setVisibility(View.GONE);
            Button b6 = (Button) findViewById(R.id.button6);
            b6.setVisibility(View.GONE);
            Button b7 = (Button) findViewById(R.id.button7);
            b7.setVisibility(View.GONE);
        }

        try {
            result = task.execute("index.json").get();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }

        indexLoader();
        JSONObject puzzlenames = result;
        spinnerMethod(puzzlenames);


    }

    public void setButtons(){
        Button mButton =  (Button)findViewById(R.id.button5);
        mButton.setText(getString(R.string.small));
        Button pButton = (Button)findViewById(R.id.button6);
        pButton.setText(getString(R.string.medium));
        Button gButton = (Button)findViewById(R.id.button7);
        gButton.setText(getString(R.string.large));

    }


    public void largePuzzles(View view) {
        ArrayList<Integer> list = new ArrayList<>();

        for (String name : names1) {
            int a = Character.getNumericValue(name.charAt(6));
            int b = Character.getNumericValue(name.charAt(7));
            list.add(a * b);

        }

        final ArrayList<String> filteredNames = new ArrayList<>();

        filteredNames.add(getString(R.string.selection));
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) >= 27) {
                filteredNames.add(names1.get(i));


            }
        }

        Spinner sp1 = (Spinner) findViewById(R.id.spinner2);
        

        sp1.setVisibility(View.VISIBLE);

        final ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, filteredNames);


        sp1.setAdapter(adapter4);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this,puzzleNames.get(position), Toast.LENGTH_SHORT).show();
                if (num3 < num4) {
                    num3++;
                } else {

                    Intent myIntent = new Intent(MainActivity.this, puzzleActivity.class);


                    downloader(filteredNames.get(position));
                    myIntent.putExtra("info", filteredNames.get(position));


                    startActivity(myIntent);


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void mediumPuzzle(View view) {
        ArrayList<Integer> list = new ArrayList<>();

        for (String name : names1) {
            int a = Character.getNumericValue(name.charAt(6));
            int b = Character.getNumericValue(name.charAt(7));
            list.add(a * b);

        }

        final ArrayList<String> filteredNames = new ArrayList<>();
        filteredNames.add(getString(R.string.selection));
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > 12) {
                if (list.get(i) <= 27) {
                    filteredNames.add(names1.get(i));

                }
            }
        }

        Spinner sp5 = (Spinner) findViewById(R.id.spinner);

        sp5.setVisibility(View.VISIBLE);

        final ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, filteredNames);


        sp5.setAdapter(adapter4);
        sp5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this,puzzleNames.get(position), Toast.LENGTH_SHORT).show();
                if (num5 < num6) {
                    num5++;
                } else {

                    Intent myIntent = new Intent(MainActivity.this, puzzleActivity.class);

                    downloader(filteredNames.get(position));
                    myIntent.putExtra("info", filteredNames.get(position));


                    startActivity(myIntent);


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void smallPuzzles(View view) {
        ArrayList<Integer> list = new ArrayList<>();

        for (String name : names1) {
            int a = Character.getNumericValue(name.charAt(6));
            int b = Character.getNumericValue(name.charAt(7));
            list.add(a * b);

        }

        final ArrayList<String> filteredNames = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) <= 12) {
                filteredNames.add(names1.get(i));


            }
        }

        Spinner sp1 = (Spinner) findViewById(R.id.spinner1);

        sp1.setVisibility(View.VISIBLE);

        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, filteredNames);


        sp1.setAdapter(adapter3);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this,puzzleNames.get(position), Toast.LENGTH_SHORT).show();
                if (num7 < num8) {
                    num7++;
                } else {

                    Intent myIntent = new Intent(MainActivity.this, puzzleActivity.class);

                    downloader(filteredNames.get(position));
                    myIntent.putExtra("info", filteredNames.get(position));


                    startActivity(myIntent);


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void indexLoader() {
        File localIndex = new File(getApplicationContext().getFilesDir(), "LocalIndex");
        try {
            FileInputStream inputStream = new FileInputStream(localIndex);
            String result = "";
            int data = 0;
            try {
                data = inputStream.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (data != -1) {
                char current = (char) data;
                result += current;
                try {
                    data = inputStream.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            parts = result.split("'");
            names1.add(getString(R.string.selection));
            for (String IndexEntry : parts) {
                if (IndexEntry.length() > 1) {
                    parts2 = IndexEntry.split(",");

                    names1.add(parts2[0]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void downloader(String name) {

        JSONObject result1, result2;
        JSONDownloader task = new JSONDownloader(getApplicationContext());
        JSONDownloader task2 = new JSONDownloader(getApplicationContext());


        try {
            result1 = task.execute("puzzles/" + name).get();
            Log.i("test", result1.toString());

            JSONObject jsonObject = result1.getJSONObject("Puzzle");

            //use these numbers to populate grid
            JSONArray jsonArray = jsonObject.getJSONArray("Layout");
            //**

            String pictureSet = jsonObject.getString("PictureSet");

            result2 = task2.execute("picturesets/" + pictureSet).get();

            JSONArray pictureFiles = result2.getJSONArray("PictureFiles");

            amountOfPuzzles = pictureFiles.length();


            final ArrayList<String> fruit = new ArrayList<>();

            File localIndex = new File(getApplicationContext().getFilesDir(), "LocalIndex");

                //Make file here

                FileInputStream inputStream = new FileInputStream(localIndex);
                String result = "";
                int data = 0;
                try {
                    data = inputStream.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    try {
                        data = inputStream.read();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            if (!result.contains(name)){




                String puzzleInfo = (name + "," + pictureFiles.length() + "," + pictureSet + "'");

                FileWriter fileWriter = new FileWriter(localIndex, true);
                fileWriter.append(puzzleInfo.toString()).flush();

                fileWriter.close();
            }


            for (int i = 0; i < pictureFiles.length(); i++) {
                fruit.add((String) pictureFiles.get(i));
            }


            for (String element : fruit) {

                imgDownloader task1 = new imgDownloader(getApplicationContext());

                Bitmap downloadedImage = null;

                try {


                    task1.execute(element).get();


                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void spinnerMethod(final JSONObject names) {


        try {

            //JSONObject jsonObject = names.getJSONObject("PuzzleIndex");

            JSONArray jsonArray = names.getJSONArray("PuzzleIndex");


            puzzleNames.add(getString(R.string.selection));


            for (int i = 0; i < jsonArray.length(); i++) {
                puzzleNames.add((String) jsonArray.get(i));
            }


            Spinner sp = (Spinner) findViewById(R.id.puzzleSpinner);


//HEEEEEEEEEEEEEEEEEEEEEEREEEEEE

            final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, names1);

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, puzzleNames);


            if (values == 0) {
                sp.setAdapter(adapter);
            } else {
                sp.setAdapter(adapter2);
            }
            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(MainActivity.this,puzzleNames.get(position), Toast.LENGTH_SHORT).show();
                    if (num1 < num2) {
                        num1++;
                    } else {

                        Intent myIntent = new Intent(MainActivity.this, puzzleActivity.class);

                        if (values == 0) {
                            downloader(puzzleNames.get(position));
                            myIntent.putExtra("info", puzzleNames.get(position));
                        } else {
                            myIntent.putExtra("info", names1.get(position));
                        }


                        startActivity(myIntent);


                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }


        //      JSONObject jsonObject = result.getJSONObject("Puzzle");
        //      String pictureSet = jsonObject.getString("PictureSet");


        catch (JSONException e) {
            e.printStackTrace();
        }


    }
}

