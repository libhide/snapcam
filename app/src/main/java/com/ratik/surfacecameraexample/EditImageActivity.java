package com.ratik.surfacecameraexample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Ratik on 25/12/15.
 */
public class EditImageActivity extends AppCompatActivity {

    private static final String TAG = EditImageActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        Intent intent = getIntent();
        final byte[] data = intent.getByteArrayExtra("bitmap");

        ImageView mainImageView = (ImageView) findViewById(R.id.mainImage);
        final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        mainImageView.setImageBitmap(bitmap);

        ImageButton saveButton = (ImageButton) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File pictureFile = FileHelper.getOutputMediaFile();
                if (pictureFile == null) {
                    Toast.makeText(EditImageActivity.this, "Image retrieval failed.", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();

                    // Refresh phone media to show image
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(Uri.fromFile(pictureFile));
                    sendBroadcast(mediaScanIntent);

                    finish();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ImageButton cancelButton = (ImageButton) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
