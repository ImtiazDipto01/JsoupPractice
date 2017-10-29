package com.dipto.jsouppractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.my_btn)
    Button myBtn;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.values)
    TextView values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.my_btn)
    public void onViewClicked() {

        progressbar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {

            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {

                    Log.d("++Connection++", "yup iam here") ;
                    Document doc = Jsoup.connect("http://www.ssaurel.com/blog").get();
                    Elements elements = doc.select("span.menu-decsription") ;

                    for(Element e : elements){
                        Log.d("span values :", String.valueOf(e.text())) ;
                        builder.append("Menu Name : ").append(e.text()).append("\n");

                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressbar.setVisibility(View.GONE);
                        values.setText(builder.toString());
                        values.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }
}
