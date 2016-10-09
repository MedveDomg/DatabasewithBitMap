package omg.medved.databasewithbitmap;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DBhelper mDBhelper;
    final String TAG = "TAG";
    Button mButton;
    Button mButton2;
    SQLiteDatabase db;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button);
        mImageView = (ImageView) findViewById(R.id.image);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "button get blob is working");
                Cursor c = db.query("mytable", null, null, null, null, null, null);
                if (c.moveToFirst()) {
                    byte[] array = c.getBlob(1);
                    mImageView.setImageBitmap(DbBitmapUtility.getImage(array));
                    Log.d(TAG, "succes");
                }

            }
        });
        mDBhelper = new DBhelper(this);
         db = mDBhelper.getWritableDatabase();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                Log.d(TAG, "button put cv is working");
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", "image1");
                contentValues.put("image", byteArray);
                db.insert("mytable", null, contentValues);
                break;

        }

    }
}
