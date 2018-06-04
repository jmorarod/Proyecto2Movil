package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DetalleAnuncioActivity extends AppCompatActivity {
    private boolean favorite = false; //TODO: CARGAR SI ES UN FAVORITO AQUI
    private ImageView imgFavorite;
    private int idAnuncio;
    public void onClickFavorite(View view){
        if(!favorite){
            imgFavorite.setImageResource(R.drawable.ic_favorite);
            favorite = true;
        }else {
            imgFavorite.setImageResource(R.drawable.ic_favorite_no_border);
            favorite = false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_anuncio);
        imgFavorite = findViewById(R.id.imageViewFavorito);
        Intent intent = getIntent();
        idAnuncio = intent.getIntExtra("idAnuncio",0);
    }
}
