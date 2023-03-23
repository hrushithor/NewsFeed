package com.example.newsfeed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {
    String title, desc, content,imageURL, url;
    private TextView titleTV, subDescTV, contentTV;
    private ImageView newIV;
    private Button readnewsbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        title= getIntent().getStringExtra("title");
        desc= getIntent().getStringExtra("description");
        content= getIntent().getStringExtra("content");
        imageURL=getIntent().getStringExtra("image");
        url= getIntent().getStringExtra("url");
        titleTV=findViewById(R.id.idTV_title);
        subDescTV=findViewById(R.id.idTVsubdesc);
        contentTV= findViewById(R.id.idTV_content);
        newIV= findViewById(R.id.idIVnews);
        readnewsbtn= findViewById(R.id.idbtn_read);
        titleTV.setText(title);
        subDescTV.setText(desc);
        contentTV.setText(content);
        Picasso.get().load(imageURL).into(newIV);
        readnewsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity((i));

            }
        });
    }
}