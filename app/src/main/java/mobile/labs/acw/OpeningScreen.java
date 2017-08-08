package mobile.labs.acw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpeningScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_screen);
        setButtons();
    }




public void setButtons(){
    Button mButton =  (Button)findViewById(R.id.button);
    mButton.setText(getString(R.string.Online));
    Button pButton = (Button)findViewById(R.id.button2);
    pButton.setText(getString(R.string.Local));
    Button fButton =(Button)findViewById(R.id.button8);
    fButton.setText(R.string.highscores);
}


    public void highscore(View view){
        Intent myIntent = new Intent(OpeningScreen.this, HighScores.class);
        startActivity(myIntent);
    }

    public void online(View view) {
        Intent myIntent = new Intent(OpeningScreen.this, MainActivity.class);
        myIntent.putExtra("Network", 0);
        startActivity(myIntent);
    }


    public void local(View view) {
        Intent myIntent = new Intent(OpeningScreen.this, MainActivity.class);
        myIntent.putExtra("Network", 1);
        startActivity(myIntent);
    }
}
