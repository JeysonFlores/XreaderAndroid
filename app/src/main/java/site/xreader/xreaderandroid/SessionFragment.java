package site.xreader.xreaderandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import site.xreader.xreaderandroid.services.BackendProxy;

public class SessionFragment extends Fragment {
    EditText cajaUser, cajacontra;
    Button btnConsultar;
    BackendProxy backend;

    public SessionFragment(BackendProxy backend) {
        this.backend = backend;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_session, container, false);
        View vista = inflater.inflate(R.layout.fragment_session,container,false);
        cajaUser=(EditText) vista.findViewById(R.id.txtUsuario);
        cajacontra=(EditText) vista.findViewById(R.id.txtContraseña);
        btnConsultar = (Button) vista.findViewById(R.id.btnSesion);


        btnConsultar.setOnClickListener((v) -> {
            try {
                String user = cajaUser.getText().toString();
                String pass = cajacontra.getText().toString();

                backend.login(user, pass, () -> {
                    Toast.makeText(getContext(), "Token: " + backend.getToken(), Toast.LENGTH_SHORT).show();
                }, (error) -> {
                    Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                });

            } catch (Exception e) {
                Toast.makeText(getContext(), "Hubo un error en el inicio de sesion" + e.toString(), Toast.LENGTH_SHORT).show();
            }

        });


        return vista;
    }

}