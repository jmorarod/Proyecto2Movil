package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

    private ArrayList<CategoriaItem> categorias;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView listView;

    public HomeFragment() {
        // Required empty public constructor
    }


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

        categorias = new ArrayList<>();







    }

    public void obtenerCategorias() {
        Call<List<Category>> categoryResultCall = service.obtenerCategorias("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE1MjkxMDI1OTZ9.Ch3I8ScU927ZayFJK3jUCg0OZCJBB9VZvheCarHacjY");

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




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_home, container, false);
        //ListView listView = RootView.findViewById(R.id.listCategorias);
        listView = RootView.findViewById(R.id.listCategorias);
        obtenerCategorias();

        //Toast.makeText(this.getContext(), categorias.get(0).getDescripcion(), Toast.LENGTH_LONG).show();

        /*ArrayList<CategoriaItem> categorias = new ArrayList<>();
        categorias.add(new CategoriaItem("Diseño Gráficoo","¿Tienes una idea? ¿Porqué no diseñarle un logo o hacerle un prototipo?",R.drawable.ic_graphic_design));
        categorias.add(new CategoriaItem("Marketing digital","Marketing digital para hacer crecer tu startup, marca o empresa.",R.drawable.ic_marketing));
        categorias.add(new CategoriaItem("Animación y video","Animaciones y videos a la medida, cuenta tu historia de forma distinta.",R.drawable.ic_video));
        categorias.add(new CategoriaItem("Música y audio","Transmite tu mensaje con los servicios de música y audio.",R.drawable.ic_music));
        categorias.add(new CategoriaItem("Escritura y Traducción","¿Necesitas hacer un documento? También puedes encontrar servicios de traducción.",R.drawable.ic_escritura));
        categorias.add(new CategoriaItem("Programación y Tecnología","No te quedes atrás con el crecimiento tecnológico en el mercado.",R.drawable.ic_computer));
        categorias.add(new CategoriaItem("Negocios","Servicios de outsourcing para hacer crecer tu empresa.",R.drawable.ic_negocios));
        categorias.add(new CategoriaItem("Diversión y estilo de vida","Disfruta de las mejores actividades recreativas, tours, fiestas, actividades deportivas o lo que consideres diversión está aquí.",R.drawable.ic_fun));
        categorias.add(new CategoriaItem("Otros","¿No encuentras lo que buscas? En esta sección de seguro que sí.",R.drawable.ic_otros));

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
                int image = categoriaItem.getImageResource();
                Intent intent = new Intent(getActivity().getApplicationContext(),BuscadorSecundarioActivity.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("descripcion",descripcion);
                intent.putExtra("imagen",image);
                startActivity(intent);
            }
        });*/


        // Inflate the layout for this fragment
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
}
