package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.SubcategoryJson;

public class BuscadorSecundarioActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private ArrayList<String> subcategorias;
    private ListView listSubcategorias;
    private String nombreCategoria;
    private String imagenCategoria;
    private ImageView imageView;
    private TextView textView;

    SharedPreferences sharedPreferences;
    String tok;
    int idU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador_secundario);

        listSubcategorias = findViewById(R.id.listSubCategorias);
        subcategorias = new ArrayList<>();
        imageView = findViewById(R.id.imgAnuncio);
        textView = findViewById(R.id.txtCategoria);

        sharedPreferences = getSharedPreferences("Freembe", MODE_PRIVATE);
        //SharedPreferences sp = getPreferences(context.MODE_PRIVATE);
        tok = sharedPreferences.getString("Token", "No token");
        idU = sharedPreferences.getInt("Id", 0);

        //TODO: Obtener del intent la categoria de la que se está visualizando
        Intent intent = getIntent();
        nombreCategoria = intent.getStringExtra("nombre");
        imagenCategoria = intent.getStringExtra("imagen");
        Bundle args = intent.getBundleExtra("BUNDLE");
        final ArrayList<SubcategoryJson> subcategories = (ArrayList<SubcategoryJson>) args.getSerializable("ARRAYLIST");

        textView.setText(nombreCategoria);

        Glide.with(this)
                .load(imagenCategoria)
                .into(imageView);




        //TODO: Cargar las subcategorias de la categoria y borrar lo siguiente

        for(SubcategoryJson s: subcategories) {
            subcategorias.add(s.getSubcategory().getName());
        }

        //subcategorias.add("Subcategoria1");
        //////////////////////////////////////////////////////////////////
        adapter = new ArrayAdapter<String>(this,
                R.layout.list_layout, subcategorias);
        listSubcategorias.setAdapter(adapter);
        listSubcategorias.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {

                Intent intent = new Intent(getApplicationContext(),AnunciosActivity.class);
                intent.putExtra("subcategoria", subcategories.get(position).getSubcategory().getId());

                startActivity(intent);
            }
        });
    }
}
