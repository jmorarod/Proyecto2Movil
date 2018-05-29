package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BuscadorSecundarioActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private ArrayList<String> subcategorias;
    private ListView listSubcategorias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador_secundario);

        listSubcategorias = findViewById(R.id.listSubCategorias);
        subcategorias = new ArrayList<>();

        //TODO: Obtener del intent la categoria de la que se está visualizando
        //TODO: Cargar las subcategorias de la categoria
        adapter = new ArrayAdapter<String>(this,
                R.layout.list_layout, subcategorias);
        listSubcategorias.setAdapter(adapter);
    }
}
