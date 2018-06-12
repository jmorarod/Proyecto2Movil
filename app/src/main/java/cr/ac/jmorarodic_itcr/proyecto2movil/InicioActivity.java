package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.AuthUser;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.CreatedUser;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.EmailPost;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.User;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InicioActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    Button login;
    SignInButton google;
    SharedPreferences sharedPreferences;
    private Context context;

    private Retrofit retrofit;
    FreembeService service;
    SharedPreferences.Editor editor;

    String nombre;
    String correo;
    String url;

    Uri uri;

    Map map = null;




    //private SignInButton login = findViewById(R.id.gmailLogin);
    public void onClickRegistrarse(View view){
        Intent intent = new Intent(this, RegistrarActivity.class);
        startActivity(intent);
    }
    public void onClickIngresar(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_inicio);

        login = findViewById(R.id.button);
        google = findViewById(R.id.google_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        context = this;

        retrofit = new Retrofit.Builder()
                .baseUrl("https://freembe.herokuapp.com/api/")  // Este es el url base del api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FreembeService.class);


        sharedPreferences = getSharedPreferences("Freembe", MODE_PRIVATE);
        editor  = sharedPreferences.edit();

        //SharedPreferences sp = getPreferences(context.MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", "No token");

        if (!token.equals("No token")) {

            Intent intent = new Intent(this, PantallaPrincipalActivity.class);
            startActivity(intent);


        }
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, 777);
            }
        });



    }





    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 777) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);

        }
    }

    private void handleResult(GoogleSignInResult result) {
        if(result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();

            nombre = account.getDisplayName();
            correo = account.getEmail();
            uri = account.getPhotoUrl();

            iniciarSesion();

        }
    }


    // crea un nuevo usuario al llamar api/users
    // post
    public void crearUsuario() {
        Call<CreatedUser> createdUserCall = service.crearUsuario(nombre, correo, "Googleinvitado123456", url, "User");
        createdUserCall.enqueue(new Callback<CreatedUser>() {
            @Override
            public void onResponse(Call<CreatedUser> call, Response<CreatedUser> response) {
                editor.clear();
                editor.putString("Token", response.body().getAuth_token());
                editor.putInt("Id", response.body().getUser().getId());
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), PantallaPrincipalActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<CreatedUser> call, Throwable t) {

            }
        });
    }


    // inicia sesión al llamar a api/authenticate
    // post
    public void iniciarSesion() {
        Call<AuthUser> login = service.iniciarSesion(correo, "Googleinvitado123456");
        final AuthUser[] authUser = {new AuthUser()};


        login.enqueue(new Callback<AuthUser>() {
            @Override
            public void onResponse(Call<AuthUser> call, Response<AuthUser> response) {
                if (response.isSuccessful()) {
                    editor.clear();
                    editor.putString("Token", response.body().getAuth_token());
                    editor.putInt("Id", response.body().getId());
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), PantallaPrincipalActivity.class);
                    startActivity(intent);
                }
                else {

                    //crearUsuario();

                    Intent intent = new Intent(getApplicationContext(), TelefonoCorreo.class);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("correo", correo);
                    intent.putExtra("password", "Googleinvitado123456");
                    intent.putExtra("foto", "https://res.cloudinary.com/poppycloud/image/upload/v1528709364/8a7qtzfGAUuuZJltqJD3XQ.jpg");
                    startActivity(intent);

                    //ImageUploadTask imageUploadTask = new ImageUploadTask();
                    //imageUploadTask.execute(uri);


                }

            }

            @Override
            public void onFailure(Call<AuthUser> call, Throwable t) {

            }
        });


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


                Intent intent = new Intent(getApplicationContext(), TelefonoCorreo.class);
                intent.putExtra("nombre", nombre);
                intent.putExtra("correo", correo);
                intent.putExtra("password", "Googleinvitado123456");
                intent.putExtra("foto", "https://res.cloudinary.com/poppycloud/image/upload/v1528709364/8a7qtzfGAUuuZJltqJD3XQ.jpg");
                startActivity(intent);




            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }





}
