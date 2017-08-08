package mobile.labs.acw;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.ImageView;

/**
 * Created by 424574 on 19/03/2017.
 */
public class pictureHolder {

    Bitmap m_Bitmap;
    Integer layoutID;
    ImageView imageView;
    boolean isMatched;



    pictureHolder(Bitmap p_Bitmap, Integer p_int, Context p_context) {
        m_Bitmap = p_Bitmap;
        layoutID = p_int;
        imageView = new ImageView(p_context);
        isMatched = false;

        imageView.setId(layoutID);
        //imageView.setMinimumHeight(LinearLayoutCompat.LayoutParams.MATCH_PARENT);
        imageView.setImageResource(R.drawable.simon);
        //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public void setImageView (ImageView icon){
        imageView = icon;
    }

    Bitmap GetBitmap() {
        return m_Bitmap;
    }


}