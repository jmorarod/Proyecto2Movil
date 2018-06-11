package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.AuthUser;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.CreatedUser;
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
            url = account.getPhotoUrl().getPath();

            iniciarSesion();

        }
    }


    public void crearUsuario() {
        Call<CreatedUser> createdUserCall = service.crearUsuario(nombre, correo, "Googleinvitado123456", "url", "User");
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


    public void iniciarSesion() {
        Call<AuthUser> login = service.iniciarSesion(correo, "Googleinvitado123456");
        final AuthUser[] authUser = {new AuthUser()};


        login.enqueue(new Callback<AuthUser>() {
            @Override
            public void onResponse(Call<AuthUser> call, Response<AuthUser> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), ":D", Toast.LENGTH_LONG).show();
                    editor.clear();
                    editor.putString("Token", response.body().getAuth_token());
                    editor.putInt("Id", response.body().getId());
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), PantallaPrincipalActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), ":V", Toast.LENGTH_LONG).show();

                    crearUsuario();
                }

            }

            @Override
            public void onFailure(Call<AuthUser> call, Throwable t) {

            }
        });


    }





}
