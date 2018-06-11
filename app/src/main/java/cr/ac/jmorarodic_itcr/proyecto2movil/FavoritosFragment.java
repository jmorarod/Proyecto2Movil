package cr.ac.jmorarodic_itcr.proyecto2movil;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Announcement;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.FavoriteJson;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritosFragment extends Fragment {

    private Retrofit retrofit;
    FreembeService service;

    ListView listView;
    AnuncioNewAdapter adapter;

    private ProgressBar progressBar;


    ArrayList<AnuncioItem> anuncioItems;
    ArrayList<Announcement> announcements;
    SharedPreferences sharedPreferences;
    String tok;
    int idU;

    public FavoritosFragment() {
        // Required empty public constructor

        retrofit = new Retrofit.Builder()
                .baseUrl("https://freembe.herokuapp.com/api/")  // Este es el url base del api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FreembeService.class);

        announcements = new ArrayList<>();



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View RootView = inflater.inflate(R.layout.fragment_favoritos, container, false);

        anuncioItems = new ArrayList<>();
        progressBar  = RootView.findViewById(R.id.progressBarFavoritos);


        //ListView listView = (ListView) RootView.findViewById(R.id.listFavoritos);
        //AnuncioNewAdapter adapter = new AnuncioNewAdapter(getActivity().getApplicationContext(), R.layout.list_anuncios, anuncioItems);

        listView = (ListView) RootView.findViewById(R.id.listFavoritos);
        adapter = new AnuncioNewAdapter(getActivity().getApplicationContext(), R.layout.list_anuncios,anuncioItems);

        progressBar.setVisibility(View.VISIBLE);

        obtenerUsuarioId();
        sharedPreferences = getActivity().getSharedPreferences("Freembe", Context.MODE_PRIVATE);
        tok = sharedPreferences.getString("Token", "No token");
        idU = sharedPreferences.getInt("Id", 0);

        //listView.setAdapter(adapter);

        return RootView;
    }


    public void obtenerUsuarioId() {
        Call<User> obtenerUsuarioId = service.obtenerUsuarioId(tok, idU);
        obtenerUsuarioId.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, final Response<User> response) {
                if(response.isSuccessful()) {

                    for(FavoriteJson a: response.body().getFavorites()) {
                        AnuncioItem ai = new AnuncioItem(a.getAnnouncement().getDescription(), a.getAnnouncement().getPhoto(), a.getAnnouncement().getTitle());
                        anuncioItems.add(ai);
                        announcements.add(a.getAnnouncement());
                    }
                    listView.setAdapter(adapter);

                    progressBar.setVisibility(View.GONE);


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

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}
