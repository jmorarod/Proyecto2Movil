package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador_secundario);

        listSubcategorias = findViewById(R.id.listSubCategorias);
        subcategorias = new ArrayList<>();
        imageView = findViewById(R.id.imgEvent);
        textView = findViewById(R.id.txtCategoria);
        //TODO: Obtener del intent la categoria de la que se está visualizando
        Intent intent = getIntent();
        nombreCategoria = intent.getStringExtra("nombre");
        imagenCategoria = intent.getStringExtra("imagen");
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<SubcategoryJson> subcategories = (ArrayList<SubcategoryJson>) args.getSerializable("ARRAYLIST");

        Toast.makeText(this, subcategories.get(0).getSubcategory().getDescription(), Toast.LENGTH_LONG).show();
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
                intent.putExtra("subcategoria",subcategorias.get(position));

                startActivity(intent);
            }
        });
    }
}
