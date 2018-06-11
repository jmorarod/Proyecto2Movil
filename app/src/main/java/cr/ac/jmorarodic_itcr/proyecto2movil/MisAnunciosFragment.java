package cr.ac.jmorarodic_itcr.proyecto2movil;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Announcement;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class MisAnunciosFragment extends Fragment {

    ArrayList<AnuncioItem> anuncioItems;
    private Retrofit retrofit;
    FreembeService service;

    ListView listView;
    AnuncioNewAdapter adapter;
    FloatingActionButton boton;


    public MisAnunciosFragment() {
        // Required empty public constructor

        retrofit = new Retrofit.Builder()
                .baseUrl("https://freembe.herokuapp.com/api/")  // Este es el url base del api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FreembeService.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_mis_anuncios, container, false);


        anuncioItems  = new ArrayList<>();
        boton =  RootView.findViewById(R.id.btnAddAnuncio);

        listView = (ListView) RootView.findViewById(R.id.listMisAnuncios);
        adapter = new AnuncioNewAdapter(getActivity().getApplicationContext(), R.layout.list_anuncios,anuncioItems);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AgregarAnuncioActivity.class);
                startActivity(intent);
            }
        });


        obtenerUsuarioId();

        return RootView;
    }


    public void obtenerUsuarioId() {
        Call<User> obtenerUsuarioId = service.obtenerUsuarioId("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE1MjkxMDI1OTZ9.Ch3I8ScU927ZayFJK3jUCg0OZCJBB9VZvheCarHacjY", 1);
        obtenerUsuarioId.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {

                    for(Announcement a: response.body().getAnnouncements()) {
                        AnuncioItem ai = new AnuncioItem(a.getDescription(), a.getPhoto(), a.getTitle());
                        anuncioItems.add(ai);
                    }
                    listView.setAdapter(adapter);

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
