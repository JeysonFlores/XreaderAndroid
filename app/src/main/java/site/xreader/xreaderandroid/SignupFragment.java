package site.xreader.xreaderandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import site.xreader.xreaderandroid.utils.BackendProxy;

public class SignupFragment extends Fragment {

    private EditText userTxt;
    private EditText usernameTxt;
    private EditText passwordTxt;
    private Button signupBtn;
    private Button loginBtn;
    private BackendProxy backend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main_view = inflater.inflate(R.layout.fragment_signup,container,false);

        userTxt = (EditText) main_view.findViewById(R.id.signupUserTxt);
        usernameTxt = (EditText) main_view.findViewById(R.id.signupUsernameText);
        passwordTxt = (EditText) main_view.findViewById(R.id.signupPasswordTxt);
        signupBtn = (Button) main_view.findViewById(R.id.signupBtn);
        loginBtn = (Button) main_view.findViewById(R.id.toLoginBtn);
        backend = new BackendProxy();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userTxt.getText().toString();
                String username = usernameTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                if(validateText(user, username, password)) {
                    backend.signup(username, user, password, () -> {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.scenario, new LoginFragment()).commit();
                    }, (error) -> {
                        String errorMsg = null;

                        switch (error) {
                            case "UserExists":
                                errorMsg = "Ya existe ese nombre de usuario";
                                break;
                            case "RequestError":
                                break;
                            case "ResponseError":
                                break;
                            default:
                                errorMsg = "Hubo un error";
                        }

                        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
                    });
                } else {
                    Toast.makeText(getContext(), "Los campos no están completos. Verifíquelos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.scenario, new LoginFragment()).commit();
            }
        });

        return main_view;
    }

    public boolean validateText(String name, String username, String password) {
        return (name.replace(" ", "") != "" && username.replace(" ", "")
                != "" && password.replace(" ", "") != "");
    }
}