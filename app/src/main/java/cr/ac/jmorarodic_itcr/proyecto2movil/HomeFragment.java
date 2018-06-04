package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_home, container, false);
        ListView listView = RootView.findViewById(R.id.listCategorias);
        ArrayList<CategoriaItem> categorias = new ArrayList<>();
        categorias.add(new CategoriaItem("Diseño Gráfico","¿Tienes una idea? ¿Porqué no diseñarle un logo o hacerle un prototipo?",R.drawable.ic_graphic_design));
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
