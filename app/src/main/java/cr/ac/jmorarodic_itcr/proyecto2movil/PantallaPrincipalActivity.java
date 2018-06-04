package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

public class PantallaPrincipalActivity extends AppCompatActivity {
    private BottomNavigationView mainNav;
    private FrameLayout mainFrame;

    private HomeFragment homeFragment;
    private AnunciosFragment anunciosFragment;
    private BuscadorFragment buscadorFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        mainNav = (BottomNavigationView) findViewById(R.id.main_menu);
        mainFrame = (FrameLayout) findViewById(R.id.mainFrame);

        homeFragment = new HomeFragment();
        anunciosFragment = new AnunciosFragment();
        buscadorFragment = new BuscadorFragment();

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
