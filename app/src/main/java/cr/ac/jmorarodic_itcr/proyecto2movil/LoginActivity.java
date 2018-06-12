package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.AuthUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.READ_CONTACTS;
import static android.view.View.GONE;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    SharedPreferences sharedPreferences;
    private Context context;

    private Retrofit retrofit;
    FreembeService service;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    private ProgressBar progressBar;




    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);


        sharedPreferences = getSharedPreferences("Freembe", MODE_PRIVATE);
        //sp = getPreferences(context.MODE_PRIVATE);
        editor  = sharedPreferences.edit();

        progressBar = findViewById(R.id.login_progress);



        mPasswordView = (EditText) findViewById(R.id.password);

        context = this;

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://freembe.herokuapp.com/api/")  // Este es el url base del api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FreembeService.class);

    }


    public void login(View view) {

        progressBar.setVisibility(View.VISIBLE);
        iniciarSesion();
    }

    // inicia sesión al llamar a api/authenticate
    // post
    public void iniciarSesion() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        Call<AuthUser> login = service.iniciarSesion(email, password);
        final AuthUser[] authUser = {new AuthUser()};


        login.enqueue(new Callback<AuthUser>() {
            @Override
            public void onResponse(Call<AuthUser> call, Response<AuthUser> response) {
                if (response.isSuccessful()) {

                    editor.clear();
                    editor.putString("Token", response.body().getAuth_token());
                    editor.putInt("Id", response.body().getId());
                    editor.commit();

                    progressBar.setVisibility(GONE);


                    Intent intent = new Intent(getApplicationContext(), PantallaPrincipalActivity.class);
                    startActivity(intent);

                }
                else {

                    Toast.makeText(getApplicationContext(), "La contraseña o correo electrónico son incorrectos", Toast.LENGTH_LONG).show();
                    mEmailView.setText("");
                    mPasswordView.setText("");
                }

            }

            @Override
            public void onFailure(Call<AuthUser> call, Throwable t) {

            }
        });


    }


}

