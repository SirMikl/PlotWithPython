package com.example.plotwithpython;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {

    EditText editTextX, editTextY;
    String X_Data, Y_Data;
    Button submit;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextX = findViewById(R.id.x_data);
        editTextY=findViewById(R.id.y_data);
        submit = findViewById(R.id.submit);
        iv = findViewById(R.id.image_view);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();
        PyObject pyObject = py.getModule("plot");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                X_Data = editTextX.getText().toString();
                Y_Data = editTextY.getText().toString();

                PyObject pyo = pyObject.callAttr("main", X_Data, Y_Data);

                String str = pyo.toString();
                byte data[] = android.util.Base64.decode(str, Base64.DEFAULT);

                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                iv.setImageBitmap(bmp);
            }
        });

    }
}