package site.xreader.xreaderandroid;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import site.xreader.xreaderandroid.utils.BackendProxy;
import site.xreader.xreaderandroid.utils.StatusDialog;

public class LoginFragment extends Fragment {

    private EditText usernameTxt;
    private EditText passwordTxt;
    private Button loginBtn;
    private Button signupBtn;
    private BackendProxy backend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main_view = inflater.inflate(R.layout.fragment_login,container,false);

        usernameTxt = (EditText) main_view.findViewById(R.id.usernameTxt);
        passwordTxt = (EditText) main_view.findViewById(R.id.passwordTxt);
        loginBtn = (Button) main_view.findViewById(R.id.loginBtn);
        signupBtn = (Button) main_view.findViewById(R.id.signinBtn);

        loginBtn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));

        backend = new BackendProxy();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                if(validateText(username, password)) {
                    backend.login(username, password, () -> {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.scenario, new HomeFragment(backend)).commit();
                    }, (error) -> {
                        String errMsg;

                        switch(error) {
                            case "WrongCredentials":
                                errMsg = "Usuario o contraseña incorrectos. Verifique la información" +
                                        " antes de continuar.";
                                break;
                            default:
                                errMsg = "Ha habido un error al hacer el inicio de sesión.";
                        }

                        StatusDialog.createErrorDialog(getContext(), errMsg).show();
                    });
                } else {
                    StatusDialog.createErrorDialog(getContext(), "Ingrese datos válidos por favor.").show();
                }
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.scenario, new SignupFragment()).commit();
            }
        });

        return main_view;
    }

    private boolean validateText(String username, String password) {
        return !(username.replace(" ", "") == "" || password.replace(" ", "") == "");
    }
}