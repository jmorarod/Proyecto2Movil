package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarActivity extends AppCompatActivity {
    private EditText txtNombre;
    private EditText txtCorreo;
    private EditText txtPassword;
    private EditText txtConfirmPassword;

    public void onClickRegistrar(View view){
        String nombre = txtNombre.getText().toString();
        String correo = txtCorreo.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmPassword = txtConfirmPassword.getText().toString();
        if(nombre.equals("") || correo.equals("") || password.equals("") || confirmPassword.equals("")){
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
        } else{
            if(!password.equals(confirmPassword)){
                Toast.makeText(this,"Los campos de contraseñas deben ser iguales", Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(this, PantallaPrincipalActivity.class);
                startActivity(intent);
            }
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        txtNombre = findViewById(R.id.nombreText);
        txtCorreo = findViewById(R.id.correoText);
        txtPassword = findViewById(R.id.passwordText);
        txtConfirmPassword = findViewById(R.id.confirmPasswordText);
    }
}
