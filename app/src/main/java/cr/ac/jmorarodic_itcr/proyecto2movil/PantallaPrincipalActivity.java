package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

public class PantallaPrincipalActivity extends AppCompatActivity {
    private BottomNavigationView mainNav;
    private FrameLayout mainFrame;

    private HomeFragment homeFragment;
    private AnunciosFragment anunciosFragment;
    private BuscadorFragment buscadorFragment;
    private ProfileFragment profileFragment;

    SharedPreferences sharedPreferences;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String token = "2295413ff104b7a24ad6a038bc23ce1c";
    MixpanelAPI mixpanelAPI;

    //LOS SIGUIENTES DOS METODOS SON DEL FRAGMENT PROFILE
    //TODO: EN ESTE METODO SE HACE EL GUARDAR LOS CAMBIOS DEL PERFIL
    public void onClickGuardar(View view){
        profileFragment.guardar();
    }
    public void onClickEditar(View view){
     profileFragment.editarVisibilty();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        Context context = this;

        sharedPreferences = getSharedPreferences("Freembe", MODE_PRIVATE);
        //sp = getPreferences(context.MODE_PRIVATE);
        editor  = sharedPreferences.edit();

        String token = sharedPreferences.getString("Token", "No token");

        mixpanelAPI = MixpanelAPI.getInstance(this, token);

        mixpanelAPI.track("En pantalla principal", null);




        //String token = sharedPreferences.getString("Token", "No token");

        //Intent intent = getIntent();

        //String tokenn = intent.getStringExtra("token");
        //Toast.makeText(getApplicationContext(), tokenn, Toast.LENGTH_LONG).show();
        //int idUsuario = intent.getIntExtra("idUsuario", 0);








        mainNav = (BottomNavigationView) findViewById(R.id.main_menu);
        mainFrame = (FrameLayout) findViewById(R.id.mainFrame);

        homeFragment = new HomeFragment();
        anunciosFragment = new AnunciosFragment();
        buscadorFragment = new BuscadorFragment();
        profileFragment = new ProfileFragment();
        setFragment(homeFragment);

        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.tab_home:

                        setFragment(homeFragment);
                        break;

                    case R.id.tab_anuncios:

                        setFragment(anunciosFragment);
                        break;

                    case R.id.tab_buscar:

                        setFragment(buscadorFragment);
                        break;
                    case R.id.tab_profile:


                        setFragment(profileFragment);


                        break;
                        /*
                        Intent intent = new Intent(getApplicationContext(),PerfilActivity.class);
                        intent.putExtra("modo","perfil");
                        startActivity(intent);
                       /*
                    case R.id.tab_notification:
                        Toast.makeText(IndexActivity.this,"NOTIFICATION",Toast.LENGTH_LONG).show();
                        setFragment(notificationFragment);
                        break;
                    case R.id.tab_profile:
                        Toast.makeText(IndexActivity.this,"PROFILE",Toast.LENGTH_LONG).show();
                        setFragment(profileFragment);
                        break;*/

                }
                return true;
            }
        });

    }
    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();
    }
}
