package site.xreader.xreaderandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {
    private EditText usernameTxt;
    private EditText passwordTxt;
    private Button loginBtn;
    private Button signupBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main_view = inflater.inflate(R.layout.fragment_login,container,false);

        usernameTxt = (EditText) main_view.findViewById(R.id.usernameTxt);
        passwordTxt = (EditText) main_view.findViewById(R.id.passwordTxt);
        loginBtn = (Button) main_view.findViewById(R.id.loginBtn);
        signupBtn = (Button) main_view.findViewById(R.id.signinBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Login", Toast.LENGTH_SHORT).show();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Signup", Toast.LENGTH_SHORT).show();
            }
        });

        return main_view;
    }


}