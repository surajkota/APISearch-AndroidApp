package com.mobile_computing;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class ResultDisplayActivity extends AppCompatActivity {

    private ImageLoader imgLoad_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
//Receive Data from API_Search activity
        Intent i = getIntent();
        final Datum dene = (Datum) i.getSerializableExtra("PassCard");

        TextView title;
        TextView date;
        NetworkImageView img;
        TextView description;
        ImageView imgview;
        final Context self = this;

//Set the data received on the layout
        title = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.date);
        img = (NetworkImageView) findViewById(R.id.img);
        description = (TextView) findViewById(R.id.textView1);
        description.setMovementMethod(new ScrollingMovementMethod());

        title.setText(dene.title());
        date.setText(dene.date());
        imgLoad_1 = VolleySingleton.getInstance(self).getImageLoader();
        img.setImageUrl(dene.imageUrl(), imgLoad_1);
        imgview = (ImageView) findViewById(R.id.starButton);
        if (dene.isFavourite(self)) {
            imgview.setImageResource(R.drawable.selected_image);
        } else {
            imgview.setImageResource(R.drawable.unselected_image);
        }
        description.setText(dene.text());

    }

//On pressing back button go back to previous state. Imitate the physical back button implementation
    public void goback(View v) {
        try {
            super.onBackPressed();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}