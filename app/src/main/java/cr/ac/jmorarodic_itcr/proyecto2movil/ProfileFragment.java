package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Announcement;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.EmailPost;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.TelephonePost;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView editarImageView;
    private ImageView uploadImageView;
    private Button buttonGuardar;
    private EditText editTextTelefono1;
    private EditText editTextTelefono2;
    private EditText editTextCorreo1;
    private EditText editTextCorreo2;

    private TextView txtUsername;

    private TextView txtTelefono1;
    private TextView txtTelefono2;
    private TextView txtCorreo1;
    private TextView txtCorreo2;

    Map map = null;
    private View RootView;


    private ImageView imgExit;

    private ImageView imageAnuncio;

    private Retrofit retrofit;
    FreembeService service;

    SharedPreferences sharedPreferences;
    private Context context;
    String tok;
    int idU;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    int id1;
    int id2;
    int eid1;
    int eid2;

    private Uri uri = null;
    private static final int MY_PERMISSION_REQUEST = 1;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;



    private ProgressBar progressBar;



    public void guardar(){
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
        buttonGuardar.setVisibility(View.INVISIBLE);
    }
    public void editarVisibilty(){
        uploadImageView.setVisibility(View.VISIBLE);
        editarImageView.setVisibility(View.INVISIBLE);

        /*txtTelefono1.setVisibility(View.INVISIBLE);
        txtTelefono2.setVisibility(View.INVISIBLE);
        txtCorreo1.setVisibility(View.INVISIBLE);
        txtCorreo2.setVisibility(View.INVISIBLE);

        editTextTelefono1.setVisibility(View.VISIBLE);
        editTextTelefono2.setVisibility(View.VISIBLE);
        editTextCorreo1.setVisibility(View.VISIBLE);
        editTextCorreo2.setVisibility(View.VISIBLE);

        editTextTelefono1.setText(txtTelefono1.getText().toString());
        editTextTelefono2.setText(txtTelefono2.getText().toString());
        editTextCorreo1.setText(txtCorreo1.getText().toString());
        editTextCorreo2.setText(txtCorreo2.getText().toString());*/
        buttonGuardar.setVisibility(View.VISIBLE);
    }
    private BuscadorFragment.OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor

        retrofit = new Retrofit.Builder()
                .baseUrl("https://freembe.herokuapp.com/api/")  // Este es el url base del api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FreembeService.class);


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.fragment_profile, container, false);
        buttonGuardar = RootView.findViewById(R.id.btnGuardarCambios);
        editarImageView =RootView. findViewById(R.id.imageViewEdit);
        uploadImageView = RootView.findViewById(R.id.imgUpload);


        editTextTelefono1 = RootView.findViewById(R.id.EditTelefono1);
        editTextTelefono2 = RootView.findViewById(R.id.EditTelefono2);
        editTextCorreo1 = RootView.findViewById(R.id.EditCorreo1);
        editTextCorreo2 = RootView.findViewById(R.id.EditCorreo2);

        txtTelefono1 = RootView.findViewById(R.id.txtTelefono1);
        txtTelefono2 = RootView.findViewById(R.id.txtTelefono2);
        txtCorreo1 = RootView.findViewById(R.id.txtCorreo1);
        txtCorreo2 = RootView.findViewById(R.id.txtCorreo2);

        imageAnuncio = RootView.findViewById(R.id.imageAnuncio);
        txtUsername = RootView.findViewById(R.id.txtUsername);

        context = getActivity().getApplicationContext();

        progressBar = RootView.findViewById(R.id.progressBarPerfil);

        progressBar.setVisibility(View.VISIBLE);

        sharedPreferences = getActivity().getSharedPreferences("Freembe", Context.MODE_PRIVATE);
        tok = sharedPreferences.getString("Token", "No token");
        idU = sharedPreferences.getInt("Id", 0);


        //sharedPreferences = getActivity().getSharedPreferences("Freembe", MODE_PRIVATE);
        //final SharedPreferences sp = getActivity().getPreferences(MODE_PRIVATE);


        uploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                } else {
                    cargarImagen();
                }
            }
        });

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUploadTask imageUploadTask = new ImageUploadTask();
                imageUploadTask.execute(uri);
            }
        });

        imgExit = RootView.findViewById(R.id.imgExit);

        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("Token");
                editor.remove("Id");
                editor.commit();
                Intent intent = new Intent(getActivity(), InicioActivity.class);
                startActivity(intent);

            }
        });
        // Inflate the layout for this fragment

        obtenerUsuarioId();




        return RootView;
    }

    public void cargarImagen()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {

        }else{
            if (resultCode == RESULT_OK){
                uri = data.getData();
                Bitmap bitmap;
                try {
                    bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                    imageAnuncio.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cargarImagen();
            } else {
                // Permission Denied
                Toast.makeText(getActivity().getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void obtenerUsuarioId() {
        Call<User> obtenerUsuarioId = service.obtenerUsuarioId(tok, idU);
        obtenerUsuarioId.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {

                    Glide.with(getActivity().getApplicationContext())
                            .load(response.body().getPhoto())
                            .into(imageAnuncio);

                    txtUsername.setText(response.body().getEmail());

                    txtTelefono1.setText(response.body().getTelephones().get(0).getTelephone());
                    txtTelefono2.setText(response.body().getTelephones().get(1).getTelephone());
                    txtCorreo1.setText(response.body().getEmails().get(0).getEmail());
                    txtCorreo2.setText(response.body().getEmails().get(1).getEmail());
                    id1 = response.body().getTelephones().get(0).getUser_id();
                    id2 = response.body().getTelephones().get(1).getUser_id();
                    eid1 = response.body().getEmails().get(0).getUser_id();
                    eid2 = response.body().getEmails().get(1).getUser_id();

                    progressBar.setVisibility(View.GONE);


                }
                else{
                    Log.e("user: ", response.errorBody().toString());

                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void editarTelefonoUsuario(String telefono, int id) {
        Call<TelephonePost> telephonePostCall = service.editarTelefonoUsuario(tok, id, telefono);
        telephonePostCall.enqueue(new Callback<TelephonePost>() {
            @Override
            public void onResponse(Call<TelephonePost> call, Response<TelephonePost> response) {

            }

            @Override
            public void onFailure(Call<TelephonePost> call, Throwable t) {

            }
        });
    }

    public void editarEmailUsuario(String email, int id){
        //Log.d("añslsa: ", email);
        Call<EmailPost> emailPostCall = service.editarEmailUsuario(tok, id, email);
        emailPostCall.enqueue(new Callback<EmailPost>() {
            @Override
            public void onResponse(Call<EmailPost> call, Response<EmailPost> response) {

            }

            @Override
            public void onFailure(Call<EmailPost> call, Throwable t) {

            }
        });

    }




    class ImageUploadTask extends AsyncTask<Uri,Void,Bitmap> {
        File file;
        FileInputStream fileInputStream;
        @Override
        protected Bitmap doInBackground(Uri... urls) {

            Map config = new HashMap();
            config.put("cloud_name", "poppycloud");
            config.put("api_key", "328358331617938");
            config.put("api_secret", "z-7k70XpvP1dl1ZdiqVF0olXp7A");

            Cloudinary cloudinary = new Cloudinary(config);

            //file = new File(String.valueOf(urls[0]));
            //try {
            //    fileInputStream = new FileInputStream(file);
            //} catch (FileNotFoundException e) {
            //    e.printStackTrace();
            //}
            try {
                Log.i("URI",urls[0].getPath());
                String path = getRealPathFromUri(getActivity().getApplicationContext(),urls[0]);
                Log.i("Path",path);
                map = cloudinary.uploader().upload(path, ObjectUtils.emptyMap() );

                //editarEmailUsuario(txtCorreo1.getText().toString(), eid1);
                Call<EmailPost> emailPostCall = service.editarEmailUsuario(tok, 13, "kujijijs");
                emailPostCall.enqueue(new Callback<EmailPost>() {
                    @Override
                    public void onResponse(Call<EmailPost> call, Response<EmailPost> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(RootView.getContext(),"Cambios guardados exitosamente", Toast.LENGTH_SHORT).show();
                            guardar();
                            obtenerUsuarioId();
                        }
                        else{

                        }
                    }

                    @Override
                    public void onFailure(Call<EmailPost> call, Throwable t) {


                    }
                });
                //editarEmailUsuario(txtCorreo2.getText().toString(), eid2);
                //editarTelefonoUsuario(txtTelefono1.getText().toString(), id1);
                //editarTelefonoUsuario(txtTelefono2.getText().toString(), id2);

                //String title = txtTitle.getText().toString();
                //String description = txtDescription.getText().toString();
                //float price = Float.parseFloat(txtPrice.getText().toString());
                //String location = txtLocation.getText().toString();
                //int subcategory = subcategorias.get(spinner.getSelectedItemPosition()).getId();

                //Toast.makeText(getApplicationContext(), String.valueOf(subcategory), Toast.LENGTH_LONG).show();

                Call<User> call = service.editarFotoUsuario(tok, idU, (String)map.get("secure_url"));
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Toast.makeText(RootView.getContext(),"Cambios guardados exitosamente", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void editarFotoUsuario() {
        Call<User> call = service.editarFotoUsuario(tok, idU, "");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }


        // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
