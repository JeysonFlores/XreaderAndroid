package site.xreader.xreaderandroid.screens;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.services.BackendProxy;
import site.xreader.xreaderandroid.widgets.StatusDialog;

public class SignupFragment extends Fragment {

    private TextView titleLbl;
    private TextView subtitleLbl;
    private EditText userTxt;
    private EditText usernameTxt;
    private EditText passwordTxt;
    private Button signupBtn;
    private Button loginBtn;
    private BackendProxy backend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View initializing
        View mainView = inflater.inflate(R.layout.fragment_signup,container,false);
        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        // Setting up the UI elements
        titleLbl = (TextView) mainView.findViewById(R.id.signupTitleLbl);
        subtitleLbl = (TextView) mainView.findViewById(R.id.signupDescriptionLbl);
        userTxt = (EditText) mainView.findViewById(R.id.signupUserTxt);
        usernameTxt = (EditText) mainView.findViewById(R.id.signupNameTxt);
        passwordTxt = (EditText) mainView.findViewById(R.id.signupPasswordTxt);
        signupBtn = (Button) mainView.findViewById(R.id.signupSignupBtn);
        loginBtn = (Button) mainView.findViewById(R.id.signupToLoginBtn);

        // Triggering UI elements' animations
        titleLbl.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
        subtitleLbl.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
        userTxt.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        usernameTxt.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        passwordTxt.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        signupBtn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        loginBtn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));

        // API connection proxy initializing
        backend = new BackendProxy();

        // Events handling
        signupBtn.setOnClickListener((v) -> {
            String user = userTxt.getText().toString();
            String username = usernameTxt.getText().toString();
            String password = passwordTxt.getText().toString();
            boolean validated = true;

            if(user.replace(" ", "").contentEquals("")){
                validated = false;
            }

            if(username.replace(" ", "").contentEquals("")){
                validated = false;
            }

            if(password.replace(" ", "").contentEquals("")){
                validated = false;
            }

            // Validating form data
            if(validated) {
                signupBtn.setEnabled(false);

                // API signup request
                backend.signup(username, user, password, () -> {
                    // On success: display success message and Screen change to Login
                    StatusDialog.createSuccess(getContext(), getString(R.string.account_created),
                            () -> {
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                fm.beginTransaction().replace(R.id.scenario, new LoginFragment()).commit();
                            }).show();
                }, (error) -> {
                    // On error: display error
                    String errorMsg = null;

                    switch (error) {
                        case "UserExists":
                            errorMsg = getString(R.string.user_exists_error);
                            break;
                        case "RequestError":
                            break;
                        case "ResponseError":
                            break;
                        default:
                            errorMsg = getString(R.string.signup_error);
                    }

                    signupBtn.setEnabled(true);
                    StatusDialog.createError(getContext(), errorMsg).show();
                });
            } else {
                StatusDialog.createError(getContext(), getString(R.string.validation_error)).show();
            }
        });

        loginBtn.setOnClickListener((v) -> {
            // Screen change to Login
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.scenario, new LoginFragment()).commit();
        });

        return mainView;
    }
}