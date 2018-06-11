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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;



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
    private float latitud;
    private float longitud;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    LatLng placeLocation;
    String api_key;
    private static final int MY_PERMISSION_REQUEST = 1;


    //LatLng placeLocation;



    SharedPreferences sharedPreferences;
    String tok;
    int idU;





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


        sharedPreferences = getSharedPreferences("Freembe", MODE_PRIVATE);
        //SharedPreferences sp = getPreferences(context.MODE_PRIVATE);
        tok = sharedPreferences.getString("Token", "No token");
        idU = sharedPreferences.getInt("Id", 0);


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

    /*@Override
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
    }*/

    public void obtenerSubcategorias() {


        Call<List<Subcategory>> subcategoryResultCall = service.obtenerSubcategorias(tok);

        subcategoryResultCall.enqueue(new Callback<List<Subcategory>>() {
            @Override
            public void onResponse(Call<List<Subcategory>> call, Response<List<Subcategory>> response) {
                if(response.isSuccessful()) {
                    for(Subcategory s: response.body()) {
                        SubcategoriaAnuncio sa = new SubcategoriaAnuncio(s.getId(), s.getName());
                        subcategoriaNombres.add(sa.getNombre());
                        subcategorias.add(sa);
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

            //file = new File(String.valueOf(urls[0]));
            //try {
            //    fileInputStream = new FileInputStream(file);
            //} catch (FileNotFoundException e) {
            //    e.printStackTrace();
            //}
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


                Call<Announcement> announcementCall = service.crearAnuncio(tok, title, description, price, (String) map.get("secure_url"), idU, latitud, longitud, subcategory, location);
                announcementCall.enqueue(new Callback<Announcement>() {
                    @Override
                    public void onResponse(Call<Announcement> call, Response<Announcement> response) {

                        Intent intent = new Intent(getApplicationContext(), PantallaPrincipalActivity.class);
                        startActivity(intent);
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


    public void onClickImgLocation(View view){
        try {
            AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(Place.TYPE_COUNTRY)
                    .setCountry("CR")
                    .build();
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(autocompleteFilter)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }
    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                TextView txtLocation = findViewById(R.id.txtLocation);
                txtLocation.setText(place.getName());
                placeLocation = place.getLatLng();
                latitud = (float) placeLocation.latitude;
                longitud = (float) placeLocation.longitude;


                Log.i("PlaceLocation",placeLocation.toString());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }else{
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
    }




}
