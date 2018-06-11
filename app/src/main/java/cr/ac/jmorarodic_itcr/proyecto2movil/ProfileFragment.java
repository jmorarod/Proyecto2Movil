package cr.ac.jmorarodic_itcr.proyecto2movil;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cr.ac.jmorarodic_itcr.proyecto2movil.Models.Announcement;
import cr.ac.jmorarodic_itcr.proyecto2movil.Models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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

    private ImageView imageAnuncio;

    private Retrofit retrofit;
    FreembeService service;



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

        txtTelefono1.setVisibility(View.INVISIBLE);
        txtTelefono2.setVisibility(View.INVISIBLE);
        txtCorreo1.setVisibility(View.INVISIBLE);
        txtCorreo2.setVisibility(View.INVISIBLE);

        editTextTelefono1.setVisibility(View.VISIBLE);
        editTextTelefono2.setVisibility(View.VISIBLE);
        editTextCorreo1.setVisibility(View.VISIBLE);
        editTextCorreo2.setVisibility(View.VISIBLE);
        buttonGuardar.setVisibility(View.VISIBLE);
    }
    private OnFragmentInteractionListener mListener;

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
        View RootView = inflater.inflate(R.layout.fragment_profile, container, false);
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
        // Inflate the layout for this fragment

        obtenerUsuarioId();



        return RootView;
    }


    public void obtenerUsuarioId() {
        Call<User> obtenerUsuarioId = service.obtenerUsuarioId("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJleHAiOjE1MjkxMDI1OTZ9.Ch3I8ScU927ZayFJK3jUCg0OZCJBB9VZvheCarHacjY", 1);
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
}
