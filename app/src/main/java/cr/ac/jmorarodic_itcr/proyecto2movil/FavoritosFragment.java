package cr.ac.jmorarodic_itcr.proyecto2movil;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
 */
public class FavoritosFragment extends Fragment {

    ArrayList<AnuncioItem> anuncioItems = new ArrayList<>();

    public FavoritosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View RootView = inflater.inflate(R.layout.fragment_favoritos, container, false);


        for(int i=0; i<10; i++)
        {
            Drawable img = getResources().getDrawable(R.drawable.photo);
            Bitmap photo = ((BitmapDrawable) img).getBitmap();
            AnuncioItem e = new AnuncioItem("Hola soy un anuncio",photo, "Anuncio");
        }

        ListView listView = (ListView) RootView.findViewById(R.id.listFavoritos);
        AnuncioNewAdapter adapter = new AnuncioNewAdapter(getActivity().getApplicationContext(), R.layout.list_anuncios,anuncioItems);
        listView.setAdapter(adapter);

        return RootView;
    }

}
