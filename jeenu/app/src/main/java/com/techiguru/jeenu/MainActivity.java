package com.techiguru.jeenu;

import static android.os.Build.VERSION_CODES_FULL.R;
import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

import android.app.ComponentCaller;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ImageView iv_speak;
    int processId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        iv_speak = findViewById(R.id.iv_speak);
        //to speak on the speak image
        iv_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to recognoize voice
                Intent voice = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                //to get all languages
                voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                // to show message for speak
                voice.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now");
                //pass the intent to OS
                startActivityForResult(voice, processId);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    //to get the as output voice to te

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == processId && data != null) {
            //to get the text from intent
            ArrayList<String> list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Toast.makeText(this, list.get(0).toString(), Toast.LENGTH_SHORT).show();
            // to pass number of operation based on statements
            switch (list.get(0).toString()) {
                case "open  camera":
                    Intent camera = new Intent(MediaStore, ACTION_IMAGE_CAPTURE);
                    startActivity(camera);
                    break;

                default:
                    Intent share= new Intent(Intent.ACTION_SEND);
                    //to attach the message with intent
                    share.putExtra(Intent.EXTRA_TEXT,list.get(0).toString());

                    //to define the data type
                    share.setType("text/plain");
                    startActivity(Intent.createChooser(share,"Share via"));

            }

        }
    }
}