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
import android.widget.TextView;

import com.studioidan.httpagent.U;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.models.User;
import site.xreader.xreaderandroid.services.BackendProxy;
import site.xreader.xreaderandroid.services.InternalDbHelper;
import site.xreader.xreaderandroid.widgets.StatusDialog;

public class LoginFragment extends Fragment {

    private TextView titleLbl;
    private TextView subtitleLbl;
    private EditText usernameTxt;
    private EditText passwordTxt;
    private ConstraintLayout usernameIcon;
    private ConstraintLayout passwordIcon;
    private Button loginBtn;
    private Button signupBtn;
    private BackendProxy backend;
    private InternalDbHelper internalStorage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View initializing
        View mainView = inflater.inflate(R.layout.fragment_login,container,false);
        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        // Setting up the UI elements
        titleLbl = (TextView) mainView.findViewById(R.id.titleLbl);
        subtitleLbl = (TextView) mainView.findViewById(R.id.descriptionLbl);
        usernameTxt = (EditText) mainView.findViewById(R.id.usernameTxt);
        passwordTxt = (EditText) mainView.findViewById(R.id.passwordTxt);
        usernameIcon = (ConstraintLayout) mainView.findViewById(R.id.usernameIconBack);
        passwordIcon = (ConstraintLayout) mainView.findViewById(R.id.passwordIconBack);
        loginBtn = (Button) mainView.findViewById(R.id.loginBtn);
        signupBtn = (Button) mainView.findViewById(R.id.signinBtn);

        // Triggering UI elements' animations
        titleLbl.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
        subtitleLbl.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
        usernameTxt.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        usernameIcon.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        passwordTxt.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        passwordIcon.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        loginBtn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        signupBtn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));

        // API connection proxy initializing
        backend = new BackendProxy();

        // Internal database initialization
        internalStorage = new InternalDbHelper(getContext());

        // Events handling
        loginBtn.setOnClickListener((v) -> {
            String username = usernameTxt.getText().toString();
            String password = passwordTxt.getText().toString();
            boolean validated = true;

            if(username.replace(" ", "").contentEquals("")){
                validated = false;
            }

            if(password.replace(" ", "").contentEquals("")){
                validated = false;
            }

            // Validating form data
            if(validated) {
                loginBtn.setEnabled(false);

                // API login request
                backend.login(username, password, () -> {
                    // On success: Screen change to Home
                    User loggedUser = null;

                    if(internalStorage.userExists(username)) {
                        loggedUser = new User(username, internalStorage.getFavoritesFromUser(username));
                    } else {
                        internalStorage.insertUser(username);
                        loggedUser = new User(username);
                    }

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.scenario, new HomeFragment(backend, loggedUser)).commit();
                }, (error) -> {
                    // On error: display error
                    String errMsg;

                    switch(error) {
                        case "WrongCredentials":
                            errMsg = getString(R.string.wrong_credentials_error);
                            break;
                        default:
                            errMsg = getString(R.string.login_error);
                    }

                    loginBtn.setEnabled(true);
                    StatusDialog.createError(getContext(), errMsg).show();
                });
            } else {
                StatusDialog.createError(getContext(), getString(R.string.validation_error)).show();
            }
        });

        signupBtn.setOnClickListener((v) -> {
            // Screen change to Signup
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.scenario, new SignupFragment()).commit();
        });

        return mainView;
    }
}