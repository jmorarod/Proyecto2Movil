package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Announcement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnunciosActivity extends AppCompatActivity {
    private int subcategoria; //Para hacer el query de anuncios por subcategoria
    private GridView gridViewAnuncios;
    private ArrayList<AnuncioItem> anuncios;
    private Retrofit retrofit;
    AnuncioAdapter anuncioAdapter;
    FreembeService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);
        final Intent intent = getIntent();
        subcategoria = intent.getIntExtra("subcategoria", 0);
        gridViewAnuncios = findViewById(R.id.gridAnuncios);
        anuncios = new ArrayList<>();
        Toast.makeText(this, String.valueOf(subcategoria), Toast.LENGTH_SHORT).show();
        //TODO: Cargar anuncios por subcategoria del backend
        //TODO: QUITARLO CUANDO SE CARGUE DEL BACKEND

        retrofit = new Retrofit.Builder()
                .baseUrl("https://freembe.herokuapp.com/api/")  // Este es el url base del api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FreembeService.class);


        //anuncios.add(new AnuncioItem("Descripcion","$20",R.drawable.ic_computer));
        //anuncios.add(new AnuncioItem("Descripcion","$40",R.drawable.ic_launcher_background));
        //anuncios.add(new AnuncioItem("Descripcion","$40",R.drawable.ic_launcher_background));
        anuncioAdapter = new AnuncioAdapter(this,R.layout.list_item_anuncios,anuncios);
        obtenerAnunciosPorSubcategoria();
        /*gridViewAnuncios.setAdapter(anuncioAdapter);
        anuncioAdapter.notifyDataSetChanged();
        gridViewAnuncios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


            Intent intent1 = new Intent(getApplicationContext(),DetalleAnuncioActivity.class);
            intent1.putExtra("idAnuncio",anuncios.get(position).getId());
            startActivity(intent1);
            }
        });*/

    }


    public void obtenerAnunciosPorSubcategoria() {
        Call<List<Announcement>> announcementCall = service.obtenerAnunciosPorSubcategoria("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo0LCJleHAiOjE1MjkyMTQ3MTF9.Lx8ZpWWYVw1iSqScgL0ncyYPYU8VnknxtY-0BY3Vpj8", subcategoria);
        announcementCall.enqueue(new Callback<List<Announcement>>() {
            @Override
            public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {
                for(Announcement a: response.body()) {
                    AnuncioItem an = new AnuncioItem(a.getTitle(), a.getDescription(), a.getPrice(), a.getPhoto(), a.getUser().getId(), a.getLatitude(), a.getLongitude(), a.getSubcategory().getId(), a.getPlace());
                    anuncios.add(an);
                }

                gridViewAnuncios.setAdapter(anuncioAdapter);
                anuncioAdapter.notifyDataSetChanged();
                gridViewAnuncios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


                        Intent intent1 = new Intent(getApplicationContext(),DetalleAnuncioActivity.class);
                        intent1.putExtra("idAnuncio",anuncios.get(position).getId());
                        startActivity(intent1);
                    }
                });


            }

            @Override
            public void onFailure(Call<List<Announcement>> call, Throwable t) {

            }
        });
    }
}

