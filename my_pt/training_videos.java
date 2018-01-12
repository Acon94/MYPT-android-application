package com.example.andrew.my_pt;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.VideoView;

public class training_videos extends AppCompatActivity {

    public ScrollView my_scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_videos);
        VideoView vidView = (VideoView) findViewById(R.id.vidone);
        VideoView vidViewx = (VideoView) findViewById(R.id.vidtwo);
        VideoView vidViewy = (VideoView) findViewById(R.id.videothree);
        my_scroll = (ScrollView) findViewById(R.id.scroll);

        my_scroll.scrollTo(0, my_scroll.getBottom());


        // apply url to a string
        String vidAddress = "http://www.wwwmy-pt.com/ufc.mp4";
        String vidAddressx = "http://www.wwwmy-pt.com/deadandpress.mp4";
        //String vidAddressx = "https://www.dropbox.com/scl/fi/j9pr0qa694mzde1xb7cna/deadandpress.mp4?dl=0&oref=e&r=AAVfe9o8QrLM7o-Th7cSXPDZYeAwwTxJnEEoeIlBB-GCXqD5hyhvFloKcXBWd-sKseJftanljwdV2UIgyv0zPs6fHvmaLc7piWX_gCQ38dFxL_2qUAG1n4OjyiCn-TmVsoZr-e35touDGhPltQKWUIeScA3IDBbeMUSUfJoIy1P_HIU8eC_PeqBbuVhn39SuV_c1guxbq9UuBcQPkdh3K442&sm=1\n";
        String vidAddressy = "http://www.wwwmy-pt.com/promo.mp4";

    //parse the string to a uri
        Uri vidUri = Uri.parse(vidAddress);
        Uri vidUrix = Uri.parse(vidAddressx);
        Uri vidUriy = Uri.parse(vidAddressy);

        //set the uri to the video view
        vidView.setVideoURI(vidUri);
        vidViewx.setVideoURI(vidUrix);
        vidViewy.setVideoURI(vidUriy);

        //declare media controler for the video view
        MediaController vidControl = new MediaController(this);
        MediaController vidControlx = new MediaController(this);
        MediaController vidControly = new MediaController(this);

        vidControl.setAnchorView(vidView);
        vidControl.setAnchorView(vidViewx);
        vidControl.setAnchorView(vidViewy);



        //apply media controller to the video view
        vidView.setMediaController(vidControl);
        vidViewx.setMediaController(vidControlx);
        vidViewy.setMediaController(vidControly);

        vidView.canPause();
        vidViewx.canPause();
        vidViewy.canPause();
    }
}

