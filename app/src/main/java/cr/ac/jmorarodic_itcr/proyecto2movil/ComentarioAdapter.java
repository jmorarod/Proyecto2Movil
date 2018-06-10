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

public class ComentarioAdapter extends ArrayAdapter<ComentarioItem> {
    ArrayList<ComentarioItem> comentarios = new ArrayList<>();
    Context context;

    public ComentarioAdapter(Context context, int textViewId, ArrayList<ComentarioItem> comentarios){
        super(context,textViewId,comentarios);
        this.comentarios = comentarios;
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_item_comentario, null);

        ImageView imageView = v.findViewById(R.id.profile_image_comment);
        TextView userText = v.findViewById(R.id.txtUsername_comment);
        TextView commentText = v.findViewById(R.id.txtCommentC);
        userText.setText(comentarios.get(position).getUserName());
        commentText.setText(comentarios.get(position).getComentario());

        Glide.with(context)
                .load(comentarios.get(position).getImage())
                .into(imageView);


        return v;

    }

    }
