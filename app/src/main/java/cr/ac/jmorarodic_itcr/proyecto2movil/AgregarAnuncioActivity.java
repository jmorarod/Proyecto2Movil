package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Announcement;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.SubcategoriaAnuncio;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Subcategory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


public class AgregarAnuncioActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private Uri uri = null;
    private ImageView imgCell;
    Map map = null;


    ArrayList<String> subcategoriaNombres;
    ArrayList<SubcategoriaAnuncio> subcategorias;
    private Retrofit retrofit;
    FreembeService service;

    ArrayAdapter<String> spinnerAdapter;
    Spinner spinner;

    private EditText txtTitle;
    private EditText txtDescription;
    private EditText txtPrice;
    private EditText txtLocation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_anuncio);
        imgCell = findViewById(R.id.imgAnuncio);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://freembe.herokuapp.com/api/")  // Este es el url base del api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FreembeService.class);

        spinner = findViewById(R.id.spinnerSubcategorias);
        subcategoriaNombres = new ArrayList<>();
        subcategorias = new ArrayList<>();

        spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subcategoriaNombres);

        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);
        txtPrice = findViewById(R.id.txtPrecio);
        txtLocation = findViewById(R.id.txtLocation);


        obtenerSubcategorias();


    }

    public void onClickImagen(View view)
    {
        read();

    }

    public void cargarImagen()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    public void read()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            cargarImagen();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cargarImagen();
            } else {
                // Permission Denied
                Toast.makeText(AgregarAnuncioActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            uri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                imgCell.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void obtenerSubcategorias() {


        Call<List<Subcategory>> subcategoryResultCall = service.obtenerSubcategorias("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE1MjkxMDI1OTZ9.Ch3I8ScU927ZayFJK3jUCg0OZCJBB9VZvheCarHacjY");

        subcategoryResultCall.enqueue(new Callback<List<Subcategory>>() {
            @Override
            public void onResponse(Call<List<Subcategory>> call, Response<List<Subcategory>> response) {
                if(response.isSuccessful()) {
                    for(Subcategory s: response.body()) {
                        SubcategoriaAnuncio sa = new SubcategoriaAnuncio(s.getId(), s.getName());
                        subcategorias.add(sa);
                    }

                    for(int i = subcategorias.size()-1; i>=0; i--){
                        subcategoriaNombres.add(subcategorias.get(i).getNombre());
                    }
                    spinner.setAdapter(spinnerAdapter);
                }
                else {
                    Log.e("categories_error_body: ", response.errorBody().toString());
                }
            }


            @Override
            public void onFailure(Call<List<Subcategory>> call, Throwable t) {
                Log.e("categories_failure: ", t.getMessage());

            }
        });
    }


    public void subirAnuncio(View view) {
        ImageUploadTask imageUploadTask = new ImageUploadTask();
        imageUploadTask.execute(uri);
    }


    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }





    class ImageUploadTask extends AsyncTask<Uri,Void,Bitmap> {
        File file;
        FileInputStream fileInputStream;
        @Override
        protected Bitmap doInBackground(Uri... urls) {

            Map config = new HashMap();
            config.put("cloud_name", "poppycloud");
            config.put("api_key", "328358331617938");
            config.put("api_secret", "z-7k70XpvP1dl1ZdiqVF0olXp7A");

            Cloudinary cloudinary = new Cloudinary(config);

            file = new File(String.valueOf(urls[0]));
            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Log.i("URI",urls[0].getPath());
                String path = getRealPathFromUri(getApplicationContext(),urls[0]);
                Log.i("Path",path);
                map = cloudinary.uploader().upload(path, ObjectUtils.emptyMap() );

                String title = txtTitle.getText().toString();
                String description = txtDescription.getText().toString();
                float price = Float.parseFloat(txtPrice.getText().toString());
                String location = txtLocation.getText().toString();
                int subcategory = subcategorias.get(spinner.getSelectedItemPosition()).getId();


                Call<Announcement> announcementCall = service.crearAnuncio("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE1MjkxMDI1OTZ9.Ch3I8ScU927ZayFJK3jUCg0OZCJBB9VZvheCarHacjY", title, description, price, (String) map.get("secure_url"), 1, 0.0f, 0.0f, 1, location);
                announcementCall.enqueue(new Callback<Announcement>() {
                    @Override
                    public void onResponse(Call<Announcement> call, Response<Announcement> response) {

                    }

                    @Override
                    public void onFailure(Call<Announcement> call, Throwable t) {

                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }




}
