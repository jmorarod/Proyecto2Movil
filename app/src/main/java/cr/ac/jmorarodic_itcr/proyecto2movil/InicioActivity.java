package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InicioActivity extends AppCompatActivity {

    public void onClickRegistrarse(View view){
        Intent intent = new Intent(this, RegistrarActivity.class);
        startActivity(intent);
    }
    public void onClickIngresar(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }
}
