package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ImageFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Announcement;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.CommentJson;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.FavoriteJson;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;

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
    Float latitud;
    Float longitud;

    private ProgressBar progressBar;

    ComentarioAdapter comentarioAdapter;
    ArrayList<ComentarioItem> comentarios;

    SharedPreferences sharedPreferences;
    String tok;
    int idU;


    int idAutor;



    public void onContactarClick(View view){
        Intent intent = new Intent(this,PerfilActivity.class);
        intent.putExtra("modo","contacto");
        intent.putExtra("autor", idAutor);

        startActivity(intent);
    }

    public void onClickFavorite(View view){
        crearFavorito();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_anuncio);
        imgFavorite = findViewById(R.id.imageViewFavorito);
        Intent intent = getIntent();
        idAnuncio = intent.getIntExtra("idAnuncio",0);
        idAutor = intent.getIntExtra("idAutor", 0);

        progressBar = findViewById(R.id.progressBarAn);

        sharedPreferences = getSharedPreferences("Freembe", MODE_PRIVATE);
        //SharedPreferences sp = getPreferences(context.MODE_PRIVATE);
        tok = sharedPreferences.getString("Token", "No token");
        idU = sharedPreferences.getInt("Id", 0);



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


        progressBar.setVisibility(View.VISIBLE);
        obtenerUsuarioId();
        obtenerAnuncioId();




    }


    public void obtenerAnuncioId() {
        Call<Announcement> obtenerAnuncioId = service.obtenerAnuncioId(tok, idAnuncio);
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

                    progressBar.setVisibility(GONE);

                    txtUsername.setText(response.body().getUser().getEmail());
                    txtPrecio.setText(String.valueOf(response.body().getPrice()));
                    txtTitle.setText(response.body().getTitle());
                    txtDescription.setText(response.body().getDescription());
                    txtLocation.setText(response.body().getPlace());
                    latitud = response.body().getLatitude();
                    longitud = response.body().getLongitude();

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

    public void onMapClick(View view){

        Intent intent = new Intent(this,MapsActivity.class);

        intent.putExtra("LAT", latitud);
        intent.putExtra("LON", longitud);
        startActivity(intent);

    }

    // se crea un comentario al llamar a api/comments
    // es un request tipo post

    public void crearComentario(String comentario) {
        Call<CommentJson> call = service.crearComentario(tok, idU, idAnuncio, comentario);
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

    public boolean estaEnLista(ArrayList<FavoriteJson> announcements) {
        for(FavoriteJson a: announcements) {
            if(a.getAnnouncement().getId() == idAnuncio) return true;
        }
        return false;
    }

    // se obtiene un usuario por id a users/id
    // es un request tipo get

    public void obtenerUsuarioId() {
        Call<User> obtenerUsuarioId = service.obtenerUsuarioId(tok, idU);
        obtenerUsuarioId.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {

                    if(estaEnLista(response.body().getFavorites())) {
                        imgFavorite.setImageResource(R.drawable.ic_favorite);
                        favorite = true;
                        }
                        else{
                            imgFavorite.setImageResource(R.drawable.ic_favorite_no_border);
                            favorite = false;

                        }



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

    // se da like a un anuncio al llamar  a api/favorites
    // es un request tipo post


    public void crearFavorito() {
        Call<FavoriteJson> favoriteCall = service.crearFavorito(tok, idU, idAnuncio);
        favoriteCall.enqueue(new Callback<FavoriteJson>() {
            @Override
            public void onResponse(Call<FavoriteJson> call, Response<FavoriteJson> response) {
                if(response.isSuccessful()) {
                    if(!favorite){
                        imgFavorite.setImageResource(R.drawable.ic_favorite);
                        favorite = true;
                    }else {
                        imgFavorite.setImageResource(R.drawable.ic_favorite_no_border);
                        favorite = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<FavoriteJson> call, Throwable t) {


            }
        });
    }
}
