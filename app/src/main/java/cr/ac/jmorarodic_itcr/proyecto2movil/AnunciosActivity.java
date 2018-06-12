package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Announcement;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Category;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.SubcategoryJson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnunciosActivity extends AppCompatActivity {
    private int subcategoria; //Para hacer el query de anuncios por subcategoria
    private GridView gridViewAnuncios;
    //private ArrayList<AnuncioItem> anuncios;
    AnuncioAdapter anuncioAdapter;
    private Retrofit retrofit;
    FreembeService service;
    Spinner spinner;

    SharedPreferences sharedPreferences;
    String tok;
    int idU;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);
        final Intent intent = getIntent();
        subcategoria = intent.getIntExtra("subcategoria", 0);
        gridViewAnuncios = findViewById(R.id.gridAnuncios);
        //anuncios = new ArrayList<>();
        //TODO: Cargar anuncios por subcategoria del backend
        //TODO: QUITARLO CUANDO SE CARGUE DEL BACKEND

        retrofit = new Retrofit.Builder()
                .baseUrl("https://freembe.herokuapp.com/api/")  // Este es el url base del api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FreembeService.class);

        sharedPreferences = getSharedPreferences("Freembe", MODE_PRIVATE);
        //SharedPreferences sp = getPreferences(context.MODE_PRIVATE);
        tok = sharedPreferences.getString("Token", "No token");
        idU = sharedPreferences.getInt("Id", 0);

        progressBar = findViewById(R.id.progressBarAnuncios);




        //anuncios = new ArrayList<>();



        spinner = findViewById(R.id.spinner4);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "FECHA", Toast.LENGTH_LONG).show();
                    ArrayList<AnuncioItem>  an = new ArrayList<>();
                    obtenerAnunciosPorSubcategoria(an);

                }
                else if(position == 1) {
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "PRECIO", Toast.LENGTH_LONG).show();
                    ArrayList<AnuncioItem>  an = new ArrayList<>();
                    obtenerAnunciosPorSubcategoriaP(an);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //LISTENER BUSCADOR//////////
        SearchView simpleSearchView = findViewById(R.id.searchView2); // inititate a search view

        // perform set on query text listener event
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                /*Log.d("Query",query);
                if(query.equals(" ")){
                    CategoriaAdapter adapter = new CategoriaAdapter(getActivity().getApplicationContext(),R.layout.list_item_categorias,categorias);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    return true;
                }
                Toast.makeText(getActivity().getApplicationContext(), query, Toast.LENGTH_LONG).show();
                ArrayList<CategoriaItem> categoriasChange = new ArrayList<>();
                for(CategoriaItem categoriaItem : categorias){
                    query = query.toLowerCase();
                    String nombre = categoriaItem.getNombre();

                    String descripcion = categoriaItem.getDescripcion();
                    if(nombre.toLowerCase().contains(query) || descripcion.toLowerCase().contains(query)){
                        categoriasChange.add(new CategoriaItem(nombre, descripcion, categoriaItem.getImageResource()));
                    }
                }
                CategoriaAdapter adapter = new CategoriaAdapter(getActivity().getApplicationContext(),R.layout.list_item_categorias,categoriasChange);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();*/
                //Toast.makeText(getActivity().getApplicationContext(), query, Toast.LENGTH_LONG).show();
                ArrayList<AnuncioItem> anunci = new ArrayList<>();
                obtenerAnuncioNombre(query, anunci);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
        //obtenerAnunciosPorSubcategoria();


    }


    public void obtenerAnunciosPorSubcategoria(final ArrayList<AnuncioItem> anuncios) {
        Call<List<Announcement>> announcementCall = service.obtenerAnunciosPorSubcategoria(tok, subcategoria);
        announcementCall.enqueue(new Callback<List<Announcement>>() {
            @Override
            public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {
                //anuncios = new ArrayList<>();
                for(Announcement a: response.body()) {
                    AnuncioItem an = new AnuncioItem(a.getId(), a.getTitle(), a.getDescription(), a.getPrice(), a.getPhoto(), a.getUser().getId(), a.getLatitude(), a.getLongitude(), a.getSubcategory().getId(), a.getPlace(), a.getUser().getId());
                    anuncios.add(an);
                }


                anuncioAdapter = new AnuncioAdapter(getApplicationContext(),R.layout.list_item_anuncios,anuncios);
                gridViewAnuncios.setAdapter(anuncioAdapter);
                anuncioAdapter.notifyDataSetChanged();
                gridViewAnuncios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


                        Intent intent1 = new Intent(getApplicationContext(),DetalleAnuncioActivity.class);
                        intent1.putExtra("idAnuncio",anuncios.get(position).getId());
                        intent1.putExtra("idAutor", anuncios.get(position).getAutor());
                        startActivity(intent1);
                    }
                });

                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<List<Announcement>> call, Throwable t) {

            }
        });
    }

    // se obtiene todos los anuncios para ser mostrados
    // es un request tipo get

    public void obtenerAnunciosPorSubcategoriaP(final ArrayList<AnuncioItem> anuncios) {
        Call<List<Announcement>> announcementCall = service.obtenerAnunciosPorSubcategoriaP(tok, subcategoria);
        announcementCall.enqueue(new Callback<List<Announcement>>() {
            @Override
            public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {
                //anuncios = new ArrayList<>();
                for(Announcement a: response.body()) {
                    AnuncioItem an = new AnuncioItem(a.getId(), a.getTitle(), a.getDescription(), a.getPrice(), a.getPhoto(), a.getUser().getId(), a.getLatitude(), a.getLongitude(), a.getSubcategory().getId(), a.getPlace(), a.getUser().getId());
                    anuncios.add(an);
                }

                anuncioAdapter = new AnuncioAdapter(getApplicationContext(),R.layout.list_item_anuncios,anuncios);
                gridViewAnuncios.setAdapter(anuncioAdapter);
                anuncioAdapter.notifyDataSetChanged();
                gridViewAnuncios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


                        Intent intent1 = new Intent(getApplicationContext(),DetalleAnuncioActivity.class);
                        intent1.putExtra("idAnuncio",anuncios.get(position).getId());
                        intent1.putExtra("idAutor", anuncios.get(position).getAutor());
                        startActivity(intent1);
                    }
                });

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<Announcement>> call, Throwable t) {

            }
        });
    }

    // se obtiene los anuncios por nombre, para el buscador
    // es un request tipo get


    public void obtenerAnuncioNombre(String nombre, final ArrayList<AnuncioItem> anunci) {
        Call<List<Announcement>> categoryCall = service.obtenerAnuncioPorNombre(tok, nombre);
        categoryCall.enqueue(new Callback<List<Announcement>>() {
            @Override
            public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {

                for(Announcement a: response.body()) {
                    if(a.getSubcategory().getId() == subcategoria) {
                        AnuncioItem an = new AnuncioItem(a.getId(), a.getTitle(), a.getDescription(), a.getPrice(), a.getPhoto(), a.getUser().getId(), a.getLatitude(), a.getLongitude(), a.getSubcategory().getId(), a.getPlace(), a.getUser().getId());
                        anunci.add(an);
                    }

                }

                anuncioAdapter = new AnuncioAdapter(getApplicationContext(),R.layout.list_item_anuncios,anunci);
                gridViewAnuncios.setAdapter(anuncioAdapter);
                anuncioAdapter.notifyDataSetChanged();
                gridViewAnuncios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


                        Intent intent1 = new Intent(getApplicationContext(),DetalleAnuncioActivity.class);
                        intent1.putExtra("idAnuncio",anunci.get(position).getId());
                        intent1.putExtra("idAutor", anunci.get(position).getAutor());
                        startActivity(intent1);
                    }
                });

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Announcement>> call, Throwable t) {

            }
        });
    }
}

