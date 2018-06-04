package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AnunciosActivity extends AppCompatActivity {
    private String subcategoria; //Para hacer el query de anuncios por subcategoria
    private GridView gridViewAnuncios;
    private ArrayList<AnuncioItem> anuncios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);
        Intent intent = getIntent();
        subcategoria = intent.getStringExtra("subcategoria");
        gridViewAnuncios = findViewById(R.id.gridAnuncios);
        anuncios = new ArrayList<>();
        //TODO: Cargar anuncios por subcategoria del backend
        //TODO: QUITARLO CUANDO SE CARGUE DEL BACKEND

        anuncios.add(new AnuncioItem("Descripcion","$20",R.drawable.ic_computer));
        anuncios.add(new AnuncioItem("Descripcion","$40",R.drawable.ic_launcher_background));
        anuncios.add(new AnuncioItem("Descripcion","$40",R.drawable.ic_launcher_background));
        AnuncioAdapter anuncioAdapter = new AnuncioAdapter(this,R.layout.list_item_anuncios,anuncios);
        gridViewAnuncios.setAdapter(anuncioAdapter);
        anuncioAdapter.notifyDataSetChanged();
    }
}
