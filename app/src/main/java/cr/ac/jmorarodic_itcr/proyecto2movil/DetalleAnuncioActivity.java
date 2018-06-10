package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Announcement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleAnuncioActivity extends AppCompatActivity {
    private boolean favorite = false; //TODO: CARGAR SI ES UN FAVORITO AQUI
    private ImageView imgFavorite;
    private int idAnuncio;
    private Retrofit retrofit;
    FreembeService service;

    ImageView imageAnuncio;
    ImageView imagePerfil;
    TextView txtUsername;
    TextView txtPrecio;
    TextView txtTitle;
    TextView txtDescription;
    TextView txtLocation;


    public void onContactarClick(View view){
        Intent intent = new Intent(this,PerfilActivity.class);
        intent.putExtra("modo","contacto");
        startActivity(intent);
    }

    public void onClickFavorite(View view){
        if(!favorite){
            imgFavorite.setImageResource(R.drawable.ic_favorite);
            favorite = true;
        }else {
            imgFavorite.setImageResource(R.drawable.ic_favorite_no_border);
            favorite = false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_anuncio);
        imgFavorite = findViewById(R.id.imageViewFavorito);
        Intent intent = getIntent();
        idAnuncio = intent.getIntExtra("idAnuncio",0);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://freembe.herokuapp.com/api/")  // Este es el url base del api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FreembeService.class);

        imageAnuncio = findViewById(R.id.imageAnuncio);
        imagePerfil = findViewById(R.id.profile_image);
        txtUsername = findViewById(R.id.txtUsername);
        txtTitle = findViewById(R.id.txtTitle);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtDescription = findViewById(R.id.txtDescription);
        txtLocation = findViewById(R.id.txtLocation);

        obtenerAnuncioId();



    }


    public void obtenerAnuncioId() {
        Call<Announcement> obtenerAnuncioId = service.obtenerAnuncioId("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo0LCJleHAiOjE1MjkyMTQ3MTF9.Lx8ZpWWYVw1iSqScgL0ncyYPYU8VnknxtY-0BY3Vpj8", idAnuncio);
        obtenerAnuncioId.enqueue(new Callback<Announcement>() {
            @Override
            public void onResponse(Call<Announcement> call, Response<Announcement> response) {
                if(response.isSuccessful()) {

                    Glide.with(getApplicationContext())
                            .load(response.body().getPhoto())
                            .into(imageAnuncio);

                    Glide.with(getApplicationContext())
                            .load(response.body().getUser().getPhoto())
                            .into(imagePerfil);

                    Toast.makeText(getApplicationContext(), response.body().getTitle(), Toast.LENGTH_LONG).show();
                    txtUsername.setText(response.body().getUser().getEmail());
                    txtPrecio.setText(String.valueOf(response.body().getPrice()));
                    txtTitle.setText(response.body().getTitle());
                    txtDescription.setText(response.body().getDescription());
                    txtLocation.setText(response.body().getPlace());




                }
                else{
                    Log.e("user: ", response.errorBody().toString());

                }

            }

            @Override
            public void onFailure(Call<Announcement> call, Throwable t) {

            }
        });

    }
}
