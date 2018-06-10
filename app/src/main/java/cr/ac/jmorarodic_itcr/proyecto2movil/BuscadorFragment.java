package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.X509TrustManagerExtensions;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Category;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.SubcategoryJson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BuscadorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BuscadorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuscadorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<CategoriaItem> categorias;
    ArrayList<CategoriaItem> categoriasOriginal;

    private Retrofit retrofit;
    FreembeService service;
    private ListView listView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BuscadorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuscadorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuscadorFragment newInstance(String param1, String param2) {
        BuscadorFragment fragment = new BuscadorFragment();
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
        Call<List<Category>> categoryResultCall = service.obtenerCategorias("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo0LCJleHAiOjE1MjkyMTQ3MTF9.Lx8ZpWWYVw1iSqScgL0ncyYPYU8VnknxtY-0BY3Vpj8");

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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_home, container, false);
        listView = RootView.findViewById(R.id.listCategorias);
        obtenerCategorias();
        //////////////////////////////

        //LISTENER BUSCADOR//////////
        SearchView simpleSearchView = (SearchView) RootView.findViewById(R.id.searchView); // inititate a search view

        // perform set on query text listener event
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Query",query);
                if(query.equals(" ")){
                    CategoriaAdapter adapter = new CategoriaAdapter(getActivity().getApplicationContext(),R.layout.list_item_categorias,categorias);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    return true;
                }
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
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
        /////////////////////////////
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
    }
*/
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
