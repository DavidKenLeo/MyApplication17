package com.dkl.auser.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;



    public class MainActivity extends AppCompatActivity {
        MyDataHandler dataHandler;
        ListView lv;
        ArrayAdapter<String> adapter;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            dataHandler = new MyDataHandler();
            lv = (ListView) findViewById(R.id.lv);
            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, dataHandler.titles);
            lv.setAdapter(adapter);

            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        URL url = new URL("https://udn.com/rssfeed/news/2/6638?ch=news");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        StringBuilder sb = new StringBuilder();
                        String str;
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        while((str = br.readLine()) != null)
                        {
                            sb.append(str);
                        }
                        br.close();
                        isr.close();
                        is.close();
                        String result = sb.toString();


                        SAXParserFactory spf = SAXParserFactory.newInstance();
                        SAXParser sp = spf.newSAXParser();
                        XMLReader xr = sp.getXMLReader();
                        xr.setContentHandler(dataHandler);
                        xr.parse(new InputSource(new StringReader(result)));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
    }