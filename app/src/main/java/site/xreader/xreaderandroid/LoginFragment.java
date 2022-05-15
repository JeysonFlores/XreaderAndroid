package site.xreader.xreaderandroid;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import site.xreader.xreaderandroid.utils.BackendProxy;

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

        backend = new BackendProxy();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                if(validateText(username, password)) {
                    backend.login(username, password, () -> {
                        Toast.makeText(getContext(), "Token: " + backend.getToken(), Toast.LENGTH_SHORT).show();
                    }, (error) -> {
                        String errMsg;

                        switch(error) {
                            case "Wrong credentials":
                                errMsg = "Usuario o contraseña incorrectos. Verifique la información ant" +
                                        "antes de continuar.";
                                break;
                            default:
                                errMsg = "Ha habido un error al hacer el inicio de sesión.";
                        }

                        showErrorDialog(errMsg);
                    });
                } else {
                    showErrorDialog("Ingrese datos válidos por favor.");
                }
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.error_modal);

                EditText msgErrorTxt = (EditText) dialog.findViewById(R.id.messageErrorTxt);
                msgErrorTxt.setText("XDDDDDDDDDDDDDD");
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.modal_rounded);
                dialog.show();
            }
        });

        return main_view;
    }

    private boolean validateText(String username, String password) {
        return !(username.replace(" ", "") == "" || password.replace(" ", "") == "");
    }

    private void showErrorDialog(String msg) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.error_modal);

        EditText msgErrorTxt = (EditText) dialog.findViewById(R.id.messageErrorTxt);
        msgErrorTxt.setText(msg);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.modal_rounded);
        dialog.show();
    }


}