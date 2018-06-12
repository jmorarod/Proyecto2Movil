package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.CreatedUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrarActivity extends AppCompatActivity {
    private EditText txtNombre;
    private EditText txtCorreo;
    private EditText txtPassword;
    private EditText txtConfirmPassword;

    String nombre;
    String correo;
    String password;
    String confirmPassword;

    SharedPreferences sharedPreferences;
    String tok;
    int idU;

    private Retrofit retrofit;
    FreembeService service;
    SharedPreferences.Editor editor;

    String token = "2295413ff104b7a24ad6a038bc23ce1c";
    MixpanelAPI mixpanelAPI;





    public void onClickRegistrar(View view){
        nombre = txtNombre.getText().toString();
        correo = txtCorreo.getText().toString();
        password = txtPassword.getText().toString();
        confirmPassword = txtConfirmPassword.getText().toString();
        if(nombre.equals("") || correo.equals("") || password.equals("") || confirmPassword.equals("")){
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
        } else{
            if(!password.equals(confirmPassword)){
                Toast.makeText(this,"Los campos de contraseñas deben ser iguales", Toast.LENGTH_LONG).show();
            }else{

                Intent intent = new Intent(getApplicationContext(), TelefonoCorreo.class);
                intent.putExtra("nombre", nombre);
                intent.putExtra("correo", correo);
                intent.putExtra("password", password);
                intent.putExtra("foto", "https://res.cloudinary.com/poppycloud/image/upload/v1528709364/8a7qtzfGAUuuZJltqJD3XQ.jpg");
                startActivity(intent);
                //crearUsuario();
            }
        }

    }

    // crea un nuevo usuario al llamar api/users
    // post

    public void crearUsuario() {
        Call<CreatedUser> createdUserCall = service.crearUsuario(nombre, correo, password, "https://res.cloudinary.com/poppycloud/image/upload/v1528709364/8a7qtzfGAUuuZJltqJD3XQ.jpg", "User");
        createdUserCall.enqueue(new Callback<CreatedUser>() {
            @Override
            public void onResponse(Call<CreatedUser> call, Response<CreatedUser> response) {
                editor.clear();
                editor.putString("Token", response.body().getAuth_token());
                editor.putInt("Id", response.body().getUser().getId());
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), TelefonoCorreo.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<CreatedUser> call, Throwable t) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        mixpanelAPI = MixpanelAPI.getInstance(this, token);

        mixpanelAPI.track("En pantalla registro", null);

        txtNombre = findViewById(R.id.nombreText);
        txtCorreo = findViewById(R.id.correoText);
        txtPassword = findViewById(R.id.passwordText);
        txtConfirmPassword = findViewById(R.id.confirmPasswordText);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://freembe.herokuapp.com/api/")  // Este es el url base del api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FreembeService.class);

        sharedPreferences = getSharedPreferences("Freembe", MODE_PRIVATE);
        editor  = sharedPreferences.edit();

        //SharedPreferences sp = getPreferences(context.MODE_PRIVATE);
        tok = sharedPreferences.getString("Token", "No token");
        idU = sharedPreferences.getInt("Id", 0);

    }
}
