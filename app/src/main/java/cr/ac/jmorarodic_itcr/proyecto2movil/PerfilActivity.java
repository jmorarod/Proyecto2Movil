package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilActivity extends AppCompatActivity {
    private String modo;
    private ImageView editarImageView;
    private ImageView uploadImageView;
    private Button buttonGuardar;
    private EditText editTextTelefono1;
    private EditText editTextTelefono2;
    private EditText editTextCorreo1;
    private EditText editTextCorreo2;

    private TextView txtTelefono1;
    private TextView txtTelefono2;
    private TextView txtCorreo1;
    private TextView txtCorreo2;

    private TextView txtUsername;
    private ImageView imageAnuncio;


    private int autor;


    private Retrofit retrofit;
    FreembeService service;


    public void onClickGuardar(View view){
        uploadImageView.setVisibility(View.INVISIBLE);
        editarImageView.setVisibility(View.VISIBLE);

        txtTelefono1.setVisibility(View.VISIBLE);
        txtTelefono2.setVisibility(View.VISIBLE);
        txtCorreo1.setVisibility(View.VISIBLE);
        txtCorreo2.setVisibility(View.VISIBLE);

        editTextTelefono1.setVisibility(View.INVISIBLE);
        editTextTelefono2.setVisibility(View.INVISIBLE);
        editTextCorreo1.setVisibility(View.INVISIBLE);
        editTextCorreo2.setVisibility(View.INVISIBLE);
        view.setVisibility(View.INVISIBLE);
    }
    public void onClickEditar(View view){
        uploadImageView.setVisibility(View.VISIBLE);
        editarImageView.setVisibility(View.INVISIBLE);

        txtTelefono1.setVisibility(View.INVISIBLE);
        txtTelefono2.setVisibility(View.INVISIBLE);
        txtCorreo1.setVisibility(View.INVISIBLE);
        txtCorreo2.setVisibility(View.INVISIBLE);

        editTextTelefono1.setVisibility(View.VISIBLE);
        editTextTelefono2.setVisibility(View.VISIBLE);
        editTextCorreo1.setVisibility(View.VISIBLE);
        editTextCorreo2.setVisibility(View.VISIBLE);
        buttonGuardar.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Intent intent = getIntent();
        modo = intent.getStringExtra("modo");
        autor = intent.getIntExtra("autor", 0);
        buttonGuardar = findViewById(R.id.btnGuardarCambios);
        editarImageView = findViewById(R.id.imageViewEdit);
        uploadImageView = findViewById(R.id.imgUpload);

        editTextTelefono1 = findViewById(R.id.EditTelefono1);
        editTextTelefono2 = findViewById(R.id.EditTelefono2);
        editTextCorreo1 = findViewById(R.id.EditCorreo1);
        editTextCorreo2 = findViewById(R.id.EditCorreo2);

        txtTelefono1 = findViewById(R.id.txtTelefono1);
        txtTelefono2 = findViewById(R.id.txtTelefono2);
        txtCorreo1 = findViewById(R.id.txtCorreo1);
        txtCorreo2 = findViewById(R.id.txtCorreo2);

        imageAnuncio = findViewById(R.id.imageAnuncio);
        txtUsername = findViewById(R.id.txtUsername);


        retrofit = new Retrofit.Builder()
                .baseUrl("https://freembe.herokuapp.com/api/")  // Este es el url base del api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FreembeService.class);

        obtenerUsuarioId();

        switch (modo){
            case "contacto":
                editarImageView.setVisibility(View.INVISIBLE);
                break;
            case "perfil":
                editarImageView.setVisibility(View.VISIBLE);
        }
    }

    public void obtenerUsuarioId() {
        Call<User> obtenerUsuarioId = service.obtenerUsuarioId("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE1MjkxMDI1OTZ9.Ch3I8ScU927ZayFJK3jUCg0OZCJBB9VZvheCarHacjY", autor);
        obtenerUsuarioId.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {

                    Glide.with(getApplicationContext())
                            .load(response.body().getPhoto())
                            .into(imageAnuncio);

                    txtUsername.setText(response.body().getEmail());

                    txtTelefono1.setText(response.body().getTelephones().get(0).getTelephone());
                    txtTelefono2.setText(response.body().getTelephones().get(1).getTelephone());
                    txtCorreo1.setText(response.body().getEmails().get(0).getEmail());
                    txtCorreo2.setText(response.body().getEmails().get(1).getEmail());


                }
                else{
                    Log.e("user: ", response.errorBody().toString());

                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
}
