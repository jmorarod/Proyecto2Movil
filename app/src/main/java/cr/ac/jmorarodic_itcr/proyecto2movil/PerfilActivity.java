package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PerfilActivity extends AppCompatActivity {
    private String modo;
    private ImageView editarImageView;
    private ImageView uploadImageView;
    private Button buttonGuardar;
    private EditText editTextTelefono1;
    private EditText editTextTelefono2;
    private EditText editTextCorreo1;
    private EditText editTextCorreo2;

    private TextView txtTelefono1;
    private TextView txtTelefono2;
    private TextView txtCorreo1;
    private TextView txtCorreo2;

    public void onClickGuardar(View view){
        uploadImageView.setVisibility(View.INVISIBLE);
        editarImageView.setVisibility(View.VISIBLE);

        txtTelefono1.setVisibility(View.VISIBLE);
        txtTelefono2.setVisibility(View.VISIBLE);
        txtCorreo1.setVisibility(View.VISIBLE);
        txtCorreo2.setVisibility(View.VISIBLE);

        editTextTelefono1.setVisibility(View.INVISIBLE);
        editTextTelefono2.setVisibility(View.INVISIBLE);
        editTextCorreo1.setVisibility(View.INVISIBLE);
        editTextCorreo2.setVisibility(View.INVISIBLE);

        view.setVisibility(View.INVISIBLE);
    }
    public void onClickEditar(View view){
        uploadImageView.setVisibility(View.VISIBLE);
        editarImageView.setVisibility(View.INVISIBLE);

        txtTelefono1.setVisibility(View.INVISIBLE);
        txtTelefono2.setVisibility(View.INVISIBLE);
        txtCorreo1.setVisibility(View.INVISIBLE);
        txtCorreo2.setVisibility(View.INVISIBLE);

        editTextTelefono1.setVisibility(View.VISIBLE);
        editTextTelefono2.setVisibility(View.VISIBLE);
        editTextCorreo1.setVisibility(View.VISIBLE);
        editTextCorreo2.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Intent intent = getIntent();
        modo = intent.getStringExtra("modo");
        editarImageView = findViewById(R.id.imageViewEdit);
        uploadImageView = findViewById(R.id.imgUpload);

        editTextTelefono1 = findViewById(R.id.EditTelefono1);
        editTextTelefono2 = findViewById(R.id.EditTelefono2);
        editTextCorreo1 = findViewById(R.id.EditCorreo1);
        editTextCorreo2 = findViewById(R.id.EditCorreo2);

        txtTelefono1 = findViewById(R.id.txtTelefono1);
        txtTelefono2 = findViewById(R.id.txtTelefono2);
        txtCorreo1 = findViewById(R.id.txtCorreo1);
        txtCorreo2 = findViewById(R.id.txtCorreo2);

        switch (modo){
            case "contacto":
                editarImageView.setVisibility(View.INVISIBLE);
                break;
            case "perfil":
                editarImageView.setVisibility(View.VISIBLE);
        }
    }
}
