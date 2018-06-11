package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by josem on 3/6/2018.
 */

public class AnuncioAdapter extends ArrayAdapter<AnuncioItem>{
    ArrayList<AnuncioItem> anuncios = new ArrayList<>();
    Context context;

    public AnuncioAdapter(Context context, int textViewId, ArrayList<AnuncioItem> anuncios){
        super(context,textViewId,anuncios);
        this.anuncios = anuncios;
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_item_anuncios, null);

        ImageView imageView = v.findViewById(R.id.imageView3);
        TextView descText = v.findViewById(R.id.textDescripcion);
        TextView precioText = v.findViewById(R.id.textView2);
        descText.setText(anuncios.get(position).getDescripcion());
        precioText.setText(String.valueOf(anuncios.get(position).getPrecioF()));

        Glide.with(context)
                .load(anuncios.get(position).getImagenS())
                .into(imageView);


        return v;

    }
}
