package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.CreatedUser;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.EmailPost;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.TelephonePost;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TelefonoCorreo extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String tok;
    int idU;

    private Retrofit retrofit;
    FreembeService service;
    SharedPreferences.Editor editor;

    String correo1;
    String correo2;
    String telefono1;
    String telefono2;

    private EditText eCorreo1;
    private EditText eCorreo2;
    private EditText eTelefono1;
    private EditText eTelefono2;

    String nombre;
    String correo;
    String password;
    String foto;

    String token;
    int id;

    private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefono_correo);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://freembe.herokuapp.com/api/")  // Este es el url base del api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FreembeService.class);

        sharedPreferences = getSharedPreferences("Freembe", MODE_PRIVATE);
        editor  = sharedPreferences.edit();

        Intent intent = getIntent();

        correo = intent.getStringExtra("correo");
        nombre = intent.getStringExtra("nombre");
        password = intent.getStringExtra("password");
        foto = intent.getStringExtra("foto");

        progressBar = findViewById(R.id.progressBarRegistro);



        eCorreo1 = findViewById(R.id.txtCorreo1);
        eCorreo2 = findViewById(R.id.txtCorreo2);
        eTelefono1 = findViewById(R.id.txtTelefono1);
        eTelefono2 = findViewById(R.id.txtTelefono2);

    }

    public void onClickRegistrar(View view){

        progressBar.setVisibility(View.VISIBLE);
        crearUsuario();

    }

    // crea un nuevo usuario al llamar api/users
    // post


    public void crearUsuario() {
        Call<CreatedUser> createdUserCall = service.crearUsuario(nombre, correo, password, foto, "User");
        createdUserCall.enqueue(new Callback<CreatedUser>() {
            @Override
            public void onResponse(Call<CreatedUser> call, Response<CreatedUser> response) {

                token = response.body().getAuth_token();
                id = response.body().getUser().getId();
                editor.clear();
                editor.putString("Token", response.body().getAuth_token());
                editor.putInt("Id", response.body().getUser().getId());
                editor.commit();

                crearTelefonoUsuario(eTelefono1.getText().toString());
                crearTelefonoUsuario(eTelefono2.getText().toString());
                crearEmailUsuario(eCorreo1.getText().toString());
                crearEmailUsuario(eCorreo2.getText().toString());

                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Se ha registrado con éxito", Toast.LENGTH_SHORT).show();




                Intent intent = new Intent(getApplicationContext(), PantallaPrincipalActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<CreatedUser> call, Throwable t) {

            }
        });
    }

    public void crearTelefonoUsuario(String telefono) {
        Call<TelephonePost> telephonePostCall = service.crearTelefonoUsuario(token, id, telefono);

        telephonePostCall.enqueue(new Callback<TelephonePost>() {
            @Override
            public void onResponse(Call<TelephonePost> call, Response<TelephonePost> response) {

            }

            @Override
            public void onFailure(Call<TelephonePost> call, Throwable t) {

            }
        });
    }

    public void crearEmailUsuario(String email) {
        Call<EmailPost> emailPostCall = service.crearEmailUsuario(token, id, email);

        emailPostCall.enqueue(new Callback<EmailPost>() {
            @Override
            public void onResponse(Call<EmailPost> call, Response<EmailPost> response) {

            }

            @Override
            public void onFailure(Call<EmailPost> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }


}
