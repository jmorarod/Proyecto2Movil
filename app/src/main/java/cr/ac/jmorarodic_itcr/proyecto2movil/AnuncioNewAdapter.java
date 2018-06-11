package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Karina on 10/06/18.
 */

public class AnuncioNewAdapter extends ArrayAdapter<AnuncioItem>
{
    ArrayList<AnuncioItem> anuncioItems = new ArrayList<>();
    Context context;

    SharedPreferences sharedPreferences;
    String tok;
    int idU;


    public AnuncioNewAdapter(Context context, int listViewId, ArrayList<AnuncioItem> anuncioItems)
    {
        super(context, listViewId, anuncioItems);
        this.anuncioItems = anuncioItems;
        this.context = context;
    }

    public View getView(int pos, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View v = convertView;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_anuncios, null);

        ImageView imageView = v.findViewById(R.id.imgAnuncio);
        TextView title = v.findViewById(R.id.txtTitleAnuncio);
        TextView description =  v.findViewById(R.id.txtDescriptionAnuncio);

        if(anuncioItems.get(pos)!=null)
        {

            Glide.with(context)
                    .load(anuncioItems.get(pos).getImagenS())
                    .into(imageView);

            //imageView.setImageBitmap(anuncioItems.get(pos).getImagen());
            title.setText(anuncioItems.get(pos).getTitulo());
            description.setText(anuncioItems.get(pos).getDescripcion());
        }

        return v;
    }
}
