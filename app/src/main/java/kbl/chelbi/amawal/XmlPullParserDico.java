package kbl.chelbi.amawal;

import android.content.Context;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;



import org.xmlpull.v1.XmlPullParser;

public class XmlPullParserDico {

    String text ;
    String mot;

    DicHandler dicHandler ;

    public XmlPullParserDico(Context context){
        dicHandler = new DicHandler(context) ;
        dicHandler.open();

    }



    public void parse(InputStream is){
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null ;
        try {
            factory= XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            parser = factory.newPullParser();
            parser.setInput(is,null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagname = parser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("item")) {
                            mot = parser.getAttributeValue(0);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text =parser.getText();

                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("item")) {
                            //dicHandler.inserer(mot, text, "");
                            mot = "";
                            text = "";
                        }else if (tagname.equalsIgnoreCase("resources")){
                           // dicHandler.close();
                        }
                        break;
                    default: break;
                }

                eventType=parser.next();
            }



        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
