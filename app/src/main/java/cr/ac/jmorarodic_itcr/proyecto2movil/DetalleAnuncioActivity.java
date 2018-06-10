package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Announcement;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.CommentJson;
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
    ListView listView;
    EditText txtCommentC;

    ComentarioAdapter comentarioAdapter;
    ArrayList<ComentarioItem> comentarios;



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

        listView = findViewById(R.id.listViewComentarios);
        imageAnuncio = findViewById(R.id.imageAnuncio);
        imagePerfil = findViewById(R.id.profile_image);
        txtUsername = findViewById(R.id.txtUsername);
        txtTitle = findViewById(R.id.txtTitle);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtDescription = findViewById(R.id.txtDescription);
        txtLocation = findViewById(R.id.txtLocation);
        txtCommentC = findViewById(R.id.txtCommentC);
        comentarios = new ArrayList<>();


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

                    txtUsername.setText(response.body().getUser().getEmail());
                    txtPrecio.setText(String.valueOf(response.body().getPrice()));
                    txtTitle.setText(response.body().getTitle());
                    txtDescription.setText(response.body().getDescription());
                    txtLocation.setText(response.body().getPlace());

                    for(CommentJson c: response.body().getComments()) {
                        ComentarioItem co = new ComentarioItem(c.getUser().getEmail(), c.getDescription(), c.getUser().getPhoto());
                        comentarios.add(co);
                    }
                    comentarioAdapter=new ComentarioAdapter(getApplicationContext(),R.layout.list_item_comentario,comentarios);

                    //Toast.makeText(getApplicationContext(), comentarios.get(1).getComentario(), Toast.LENGTH_LONG).show();
                    listView.setAdapter(comentarioAdapter);
                    comentarioAdapter.notifyDataSetChanged();





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

    public void crearComentario(String comentario) {
        Call<CommentJson> call = service.crearComentario("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE1MjkxMDI1OTZ9.Ch3I8ScU927ZayFJK3jUCg0OZCJBB9VZvheCarHacjY", 1, idAnuncio, comentario);
        call.enqueue(new Callback<CommentJson>() {
            @Override
            public void onResponse(Call<CommentJson> call, Response<CommentJson> response) {
                obtenerAnuncioId();
            }

            @Override
            public void onFailure(Call<CommentJson> call, Throwable t) {

            }
        });
    }

    public void comentar(View view) {
        String comment = txtCommentC.getText().toString();
        comentarios = new ArrayList<>();
        crearComentario(comment);
        txtCommentC.setText("");

    }
}
