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

import java.util.ArrayList;


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
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_buscador, container, false);
        final ListView listView = RootView.findViewById(R.id.listCategoriasBuscador);
        categorias = new ArrayList<>();
        categorias.add(new CategoriaItem("Diseño Gráficoo","¿Tienes una idea? ¿Porqué no diseñarle un logo o hacerle un prototipo?",R.drawable.ic_graphic_design));
        categorias.add(new CategoriaItem("Marketing digital","Marketing digital para hacer crecer tu startup, marca o empresa.",R.drawable.ic_marketing));
        categorias.add(new CategoriaItem("Animación y video","Animaciones y videos a la medida, cuenta tu historia de forma distinta.",R.drawable.ic_video));
        categorias.add(new CategoriaItem("Música y audio","Transmite tu mensaje con los servicios de música y audio.",R.drawable.ic_music));
        categorias.add(new CategoriaItem("Escritura y Traducción","¿Necesitas hacer un documento? También puedes encontrar servicios de traducción.",R.drawable.ic_escritura));
        categorias.add(new CategoriaItem("Programación y Tecnología","No te quedes atrás con el crecimiento tecnológico en el mercado.",R.drawable.ic_computer));
        categorias.add(new CategoriaItem("Negocios","Servicios de outsourcing para hacer crecer tu empresa.",R.drawable.ic_negocios));
        categorias.add(new CategoriaItem("Diversión y estilo de vida","Disfruta de las mejores actividades recreativas, tours, fiestas, actividades deportivas o lo que consideres diversión está aquí.",R.drawable.ic_fun));
        categorias.add(new CategoriaItem("Otros","¿No encuentras lo que buscas? En esta sección de seguro que sí.",R.drawable.ic_otros));
        categoriasOriginal = (ArrayList<CategoriaItem>) categorias.clone();
        CategoriaAdapter categoriaAdapter = new CategoriaAdapter(getActivity().getApplicationContext(),R.layout.list_item_categorias,categorias);
        listView.setAdapter(categoriaAdapter);
        categoriaAdapter.notifyDataSetChanged();
        //LISTENER LISTVIEW///////////
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
        });
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
