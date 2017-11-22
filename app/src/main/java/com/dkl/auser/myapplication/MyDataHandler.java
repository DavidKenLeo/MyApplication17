package com.dkl.auser.myapplication;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by auser on 2017/10/30.
 */

public class MyDataHandler extends DefaultHandler {
    boolean isTitle = false;
    boolean isItem = false;
    public ArrayList<String> titles = new ArrayList();
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equals("title"))
        {
            isTitle = true;
        }
        if (qName.equals("item"))
        {
            isItem = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals("title"))
        {
            isTitle = false;
        }
        if (qName.equals("item"))
        {
            isItem = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (isTitle && isItem)
        {
            String data = new String(ch, start, length);
            Log.d("MyTitle", data);
            titles.add(data);
        }
    }
//    @Override
//    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        super.startElement(uri, localName, qName, attributes);
//        if (qName.equals("title"))
//        {
//            isTitle = true;
//        }
//    }
//
//    @Override
//    public void endElement(String uri, String localName, String qName) throws SAXException {
//        super.endElement(uri, localName, qName);
//        if (qName.equals("title"))
//        {
//            isTitle = false;
//        }
//    }
//
//    @Override
//    public void characters(char[] ch, int start, int length) throws SAXException {
//        super.characters(ch, start, length);
//        if (isTitle)
//        {
//            String data = new String(ch, start, length);
//            Log.d("MyTitle",data);
//        }
//    }
}