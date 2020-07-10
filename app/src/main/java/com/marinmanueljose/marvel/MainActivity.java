package com.marinmanueljose.marvel;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marinmanueljose.marvel.characterInfo.CharacterInfo;

import java.net.MalformedURLException;
import java.net.URL;

import cafsoft.foundation.Data;
import cafsoft.foundation.DataTaskCompletionHandler;
import cafsoft.foundation.Error;
import cafsoft.foundation.HTTPURLResponse;
import cafsoft.foundation.URLRequest;
import cafsoft.foundation.URLResponse;
import cafsoft.foundation.URLSession;

public class MainActivity extends AppCompatActivity {

    private EditText entryCharacter = null;
    private Button btnSearch = null;
    private ImageView showImg = null;

    final String host = "https://gateway.marvel.com";
    final String service = "v1/public/characters";
    final String appiKey = "62becf72217686d3244ef8e54fbe84b9";
    final String hash = "c0dfb93686c1052eadf03e61f40b45ee";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initEvents();
        getJsonMarvel();
    }

    public void initViews(){
        entryCharacter = findViewById(R.id.entryCharacter);
        btnSearch = findViewById(R.id.btnSearch);
        showImg = findViewById(R.id.showImg);
    };

    private void initEvents(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJsonMarvel();
            }
        });
    }

    public void getJsonMarvel(){
        URL url = null;
        try {
            url = new URL(makeRequestURl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLRequest request = new URLRequest(url);
        URLSession.getShared().dataTask(request, (data, response, error) -> {
            HTTPURLResponse resp = (HTTPURLResponse)response;
            if (resp.getStatusCode() == 200){
                String text = data.toText();
                //Log.d("json", text);
                Gson json = new Gson();
                CharacterInfo info = json.fromJson(text, CharacterInfo.class);
                getImgCharacter(info);
                //Log.d("resp", info.imgURL());
                }
        }).resume();
    }

    public Bitmap dataToImage(Data data){
        Log.d("joseM", "hola entre a dataToImg");
        return BitmapFactory.decodeByteArray(data.toBytes(),0, data.length());
    }

    public void getImgCharacter(CharacterInfo infoImg){
        String strURL = infoImg.imgURL();
        Log.d("imgURL", strURL);
        URL url = null;
        try {
            url = new URL(strURL);
            //Log.d("URLObj", String.valueOf(url));
        } catch (MalformedURLException ex) {

        }
        URLRequest request = new URLRequest(url);
        //Log.d("reqObj", String.valueOf(request));
        URLSession.getShared().dataTask(request, (data, response, error) -> {
            //Log.d("reqObj", String.valueOf(request));
            HTTPURLResponse resp = (HTTPURLResponse) response;
            if (resp.getStatusCode() == 200){

                final Bitmap image = dataToImage(data);
                showImg(image);
            }else {
                Log.d("Error not found img", String.valueOf(resp.getStatusCode()));
            }
        }).resume();
    }


    public void showImg(Bitmap image){
        runOnUiThread(() -> showImg.setImageBitmap(image));
    }

    public String makeRequestURl(){
        //https://gateway.marvel.com/v1/public/characters?name=thor&apikey=62becf72217686d3244ef8e54fbe84b9&ts=1&hash=c0dfb93686c1052eadf03e61f40b45ee
        String character = getCharacter();
        String request = host + "/" + service + "?name=" + "Thor" + "&apikey="+ appiKey + "&ts=1&hash=" + hash;
        //Log.d("resques", request);
        return request;
    }

    public String getCharacter(){
        String character = entryCharacter.getText().toString();
        return character;
    };

}


