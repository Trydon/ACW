package mobile.labs.acw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 424574 on 19/03/2017.
 */
public class AdapterClass extends BaseAdapter {

    Context context;
    pictureHolder s1 = null;
    pictureHolder s2 = null;
    private int screenHeight;
    private int screenWidth;
    private int rownumber;
    int score;
    private long mLastClickTime = 0;


    ArrayList<pictureHolder> pictureHolders;
    LayoutInflater inflter;

    public AdapterClass(Context applicationContext, ArrayList<pictureHolder> pictureHolders, int rowN, int screenH, int screenW) {
        this.context = applicationContext;
        this.pictureHolders = pictureHolders;
        inflter = (LayoutInflater.from(applicationContext));
        screenHeight=screenH;
        rownumber=rowN;
        screenWidth = screenW;

    }

    @Override
    public int getCount() {

        return pictureHolders.size();
    }

    @Override
    public ImageView getItem(int position) {
        //ImageView icon = (ImageView) ((Activity)context).findViewById(R.id.icon);
        return pictureHolders.get(position).imageView;

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_gridview, null);
        //view.setMinimumHeight(screenHeight/rownumber);


        ImageView icon = pictureHolders.get(position).imageView;







        return icon;
    }
}