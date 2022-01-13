package com.shivam.androidtask.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.shivam.androidtask.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class AddNotesActivity extends AppCompatActivity {

    private static final String IMAGE_DIRECTORY = "/YourDirectName";
    private Context mContext;
    private ImageView ivImage;  // imageview
    private int GALLERY = 1, CAMERA = 2;
    int noteId;
    LinearLayout llAddImage;
    RecyclerView rvImageList;
    EditText etTitle, etDescription;
    Button btnSaveMote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        requestMultiplePermissions(); // check permission

        llAddImage = findViewById(R.id.ll_add_image);
        ivImage = findViewById(R.id.profile_image);
        rvImageList = findViewById(R.id.rv_image_list);
        llAddImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showPictureDialog();
            }
        });


        btnSaveMote = findViewById(R.id.btn_save_note);
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);

        btnSaveMote.setOnClickListener(view -> {

            String title = etTitle.getText().toString();
            String description = etDescription.getText().toString();
            Intent intent = new Intent(AddNotesActivity.this, HomeActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("description", description);
//            intent.putExtra("image", bs.toByteArray());
            startActivity(intent);
        });
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//        Intent intent = new Intent(AddNotesActivity.this, HomeActivity.class);
//        intent.putExtra("title", etTitle.getText().toString());
//        intent.putExtra("description", etDescription.getText().toString());
////        intent.putExtra("image", image);
//    }


    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    ivImage.setImageBitmap(bitmap);

                    btnSaveMote.setOnClickListener(view -> {

                        Intent intent = new Intent(AddNotesActivity.this, HomeActivity.class);
                        intent.putExtra("title", etTitle.getText().toString());
                        intent.putExtra("description", etDescription.getText().toString());

                        ByteArrayOutputStream bs = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);

                        intent.putExtra("image", bs.toByteArray());
                        startActivity(intent);
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ivImage.setImageBitmap(thumbnail);
            saveImage(thumbnail);
        }
    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {  // have the object build the directory structure, if needed.
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        if (report.areAllPermissionsGranted()) {  // check if all permissions are granted
//                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
//                        }

                        if (report.isAnyPermissionPermanentlyDenied()) { // check for permanent denial of any permission
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

//    public void sendNoteToHome(){
//        btnSaveMote.setOnClickListener(view -> {
//
//            String title = etTitle.getText().toString();
//            String description = etDescription.getText().toString();
//            Intent intent = new Intent(AddNotesActivity.this, HomeActivity.class);
//
//            intent.putExtra("title", title);
//            intent.putExtra("description", description);
////            intent.putExtra("image", bs.toByteArray());
//            startActivity(intent);
//        });
//    }
}