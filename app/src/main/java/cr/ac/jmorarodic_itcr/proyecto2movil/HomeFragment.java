package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Announcement;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.AuthUser;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Category;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.CreatedUser;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.EmailPost;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.FavoriteJson;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Subcategory;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.SubcategoryJson;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.TelephonePost;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.User;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Retrofit retrofit;
    FreembeService service;
    private ImageView anuncio;

    SharedPreferences sharedPreferences;
    String tok;
    int idU;

    private ArrayList<CategoriaItem> categorias;

    private ProgressBar progressBar;





    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView listView;


    public HomeFragment() {
        // Required empty public constructor
    }

    //@SuppressLint("ValidFragment")
    //public HomeFragment(String tok, int idU) {
    //    this.tok = tok;
    //    this.idU = idU;
    //}


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl("https://freembe.herokuapp.com/api/")  // Este es el url base del api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FreembeService.class);

        sharedPreferences = getActivity().getSharedPreferences("Freembe", Context.MODE_PRIVATE);
        tok = sharedPreferences.getString("Token", "No token");
        idU = sharedPreferences.getInt("Id", 0);


        categorias = new ArrayList<>();







    }

    // se obtienen las categorias al llamar a api/categories
    // get
    public void obtenerCategorias() {
        Call<List<Category>> categoryResultCall = service.obtenerCategorias(tok);

        categoryResultCall.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()) {

                    for(Category c: response.body()) {
                        CategoriaItem cItem = new CategoriaItem(c.getName(), c.getDescription(), c.getPhoto(), c.getSubcategories());
                        categorias.add(cItem);

                    }

                    CategoriaAdapter categoriaAdapter = new CategoriaAdapter(getActivity().getApplicationContext(),R.layout.list_item_categorias,categorias);
                    listView.setAdapter(categoriaAdapter);
                    categoriaAdapter.notifyDataSetChanged();


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> adapter, View v, int position,
                                                long arg3)
                        {
                            CategoriaItem categoriaItem = (CategoriaItem)adapter.getItemAtPosition(position);
                            String nombre = categoriaItem.getNombre();
                            String descripcion = categoriaItem.getDescripcion();
                            String image = categoriaItem.getImagenS();
                            ArrayList<SubcategoryJson> subcategories = categoriaItem.getSubcategories();
                            Intent intent = new Intent(getActivity().getApplicationContext(),BuscadorSecundarioActivity.class);
                            intent.putExtra("nombre",nombre);
                            intent.putExtra("descripcion",descripcion);
                            intent.putExtra("imagen",image);
                            Bundle args = new Bundle();
                            args.putSerializable("ARRAYLIST",(Serializable)subcategories);
                            intent.putExtra("BUNDLE",args);
                            startActivity(intent);
                        }
                    });




                }
                else {
                    Log.e("categories_error_body: ", response.errorBody().toString());
                }
            }


            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("categories_failure: ", t.getMessage());

            }
        });
    }

    // obtiene anuncio random
    // get

    public void obtenerAnuncioRandom() {
        Call<Announcement> obtenerRandom = service.obtenerAnuncioRandom(tok);
        obtenerRandom.enqueue(new Callback<Announcement>() {
            @Override
            public void onResponse(Call<Announcement> call, final Response<Announcement> response) {
                if(response.isSuccessful()) {
                    int id = response.body().getId();
                    progressBar.setVisibility(View.GONE);


                    Glide.with(getActivity().getApplicationContext())
                            .load(response.body().getPhoto())
                            .into(anuncio);
                    anuncio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), DetalleAnuncioActivity.class);
                            intent.putExtra("idAnuncio",response.body().getId());
                            intent.putExtra("idAutor", response.body().getUser().getId());

                            startActivity(intent);
                        }
                    });
                }
                else{

                }


            }

            @Override
            public void onFailure(Call<Announcement> call, Throwable t) {

            }
        });
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_home, container, false);
        listView = RootView.findViewById(R.id.listCategorias);
        progressBar = RootView.findViewById(R.id.progressBarCat);

        anuncio = RootView.findViewById(R.id.imageViewAnuncio);
        progressBar.setVisibility(View.VISIBLE);
        obtenerCategorias();
        obtenerAnuncioRandom();

        SearchView simpleSearchView = (SearchView) RootView.findViewById(R.id.searchView); // inititate a search view

        // perform set on query text listener event
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
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
                ArrayList<CategoriaItem> categori = new ArrayList<>();
                obtenerCategoriaNombre(query, categori);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });


        return RootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void obtenerCategoriaNombre(String nombre, final ArrayList<CategoriaItem> categori) {
        Call<List<Category>> categoryCall = service.obtenerCategoriaPorNombre(tok, nombre);
        categoryCall.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                for(Category c: response.body()) {
                    CategoriaItem cItem = new CategoriaItem(c.getName(), c.getDescription(), c.getPhoto(), c.getSubcategories());
                    categori.add(cItem);

                }

                CategoriaAdapter categoriaAdapter = new CategoriaAdapter(getActivity().getApplicationContext(),R.layout.list_item_categorias,categori);
                listView.setAdapter(categoriaAdapter);
                categoriaAdapter.notifyDataSetChanged();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position,
                                            long arg3)
                    {
                        CategoriaItem categoriaItem = (CategoriaItem)adapter.getItemAtPosition(position);
                        String nombre = categoriaItem.getNombre();
                        String descripcion = categoriaItem.getDescripcion();
                        String image = categoriaItem.getImagenS();
                        ArrayList<SubcategoryJson> subcategories = categoriaItem.getSubcategories();
                        Intent intent = new Intent(getActivity().getApplicationContext(),BuscadorSecundarioActivity.class);
                        intent.putExtra("nombre",nombre);
                        intent.putExtra("descripcion",descripcion);
                        intent.putExtra("imagen",image);
                        Bundle args = new Bundle();
                        args.putSerializable("ARRAYLIST",(Serializable)subcategories);
                        intent.putExtra("BUNDLE",args);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }

    //@Override
    //public void onResume() {
    //    super.onResume();
    //    progressBar.setVisibility(View.GONE);
    //}
}
