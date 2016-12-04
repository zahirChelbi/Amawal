package kbl.chelbi.amawal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

public class DicoDataBase extends SQLiteOpenHelper {

    String text ;
    String mot;
    private Context context ;
    protected final static int VERSION = 3;
    protected final static String NOM = "database.db";

    public static final String TABLE_NAME = "dico";
    public static final String NOM_KEY = "id" ;
    public static final String NOM_KBL = "nomkbl" ;
    public static final String NOM_FR = "nomfr" ;
    public static final String NOM_DESC = "desc" ;
    public static final String DICO_TABLE ="CREATE TABLE " + TABLE_NAME+ " (" +NOM_KEY +
                                           " INTEGER PRIMARY KEY AUTOINCREMENT, "+ NOM_KBL +
                                           " TEXT, "+ NOM_FR + " TEXT, " + NOM_DESC + " TEXT);";
    public static final String DIC_TABLE_DROP = "DROP TABLE IF EXISTS '" +TABLE_NAME+ "';";


    public DicoDataBase(Context context) {
        super(context, NOM,null,VERSION);
        this.context = context ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DICO_TABLE);
        try {
            parse(context.getAssets().open("dico.xml"),db);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DIC_TABLE_DROP);
        context.deleteDatabase(TABLE_NAME);
        onCreate(db);

    }

    public void inserer(String nmkbl,String nmfr , String desc,SQLiteDatabase db){
        ContentValues value = new ContentValues();
        value.put(this.NOM_KBL,nmkbl);
        value.put(this.NOM_FR,nmfr);
        value.put(this.NOM_DESC,desc);
        db.insert(TABLE_NAME, null, value);

    }

    public void parse(InputStream is , SQLiteDatabase db){
        try {
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            XmlPullParser parser = factory.newPullParser();
            parser.setInput(is,null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){

                String balise = parser.getName();

                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if (balise.equalsIgnoreCase("item")) {
                            mot = parser.getAttributeValue(0);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text =parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (balise.equalsIgnoreCase("item")) {
                            inserer(mot, text, "",db);
                            mot = "";text = "";
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
