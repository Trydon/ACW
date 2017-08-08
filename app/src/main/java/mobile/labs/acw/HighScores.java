package mobile.labs.acw;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class HighScores extends AppCompatActivity {

    int score;
    String name;
    String parts[];
    String parts2[];
    ArrayList<String> names1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
       /* Bundle extras = getIntent().getExtras();
        int value = extras.getInt("score");
        score = value;
        String value1 = extras.getString("name");
        name = value1;*/
        ImageView sim = (ImageView) findViewById(R.id.imageView2);
        sim.animate().scaleX(0.05f).scaleY(0.05f).rotation(1800f).alpha(0f).setDuration(4500);

        indexLoader();
        listMethod();

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



                    names1.add(parts2[0] +" | "+ getSharedPreferences("prefs",Context.MODE_PRIVATE).getInt(parts2[0],0));
                    //getSharedPreferences(parts2[0],0));

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void listMethod(){
        /*ArrayList<String> scores = new ArrayList<>();



        scores.add((String) "Puzzle name: "+name+" Score: "+score);*/

        ListView lv =(ListView) findViewById(R.id.listView);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(HighScores.this, android.R.layout.simple_list_item_1, names1);
        lv.setAdapter(adapter);



    }
    public void replay(View view){
        Intent myIntent = new Intent(HighScores.this, OpeningScreen.class);

        startActivity(myIntent);

    }
    public void quit(View view){
        finish();
        System.exit(0);

    }
}
