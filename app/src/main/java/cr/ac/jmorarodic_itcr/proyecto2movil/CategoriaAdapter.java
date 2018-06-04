package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by josem on 3/6/2018.
 */

public class CategoriaAdapter extends ArrayAdapter<CategoriaItem>{
    ArrayList<CategoriaItem> categorias = new ArrayList<>();

    public CategoriaAdapter(Context context, int textViewId, ArrayList<CategoriaItem> categorias){
        super(context,textViewId,categorias);
        this.categorias = categorias;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_item_categorias, null);

        ImageView imageView = v.findViewById(R.id.categoria_image);
        TextView nombreText = v.findViewById(R.id.txtNombre);
        TextView descText = v.findViewById(R.id.txtDescripcion);
        nombreText.setText(categorias.get(position).getNombre());
        descText.setText(categorias.get(position).getDescripcion());
        if(categorias.get(position).getImagen() != null)
            imageView.setImageBitmap(categorias.get(position).getImagen());
        else
            imageView.setImageResource(categorias.get(position).getImageResource());


        return v;

    }
}
