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
import android.widget.Toast;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.screens.LoginFragment;
import site.xreader.xreaderandroid.utils.BackendProxy;
import site.xreader.xreaderandroid.widgets.StatusDialog;

public class SignupFragment extends Fragment {

    private TextView titleLbl;
    private TextView subtitleLbl;
    private ImageView imageImg;
    private EditText userTxt;
    private EditText usernameTxt;
    private EditText passwordTxt;
    private ConstraintLayout userIcon;
    private ConstraintLayout usernameIcon;
    private ConstraintLayout passwordIcon;
    private Button signupBtn;
    private Button loginBtn;
    private BackendProxy backend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_signup,container,false);
        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        titleLbl = (TextView) mainView.findViewById(R.id.signupTitleLbl);
        subtitleLbl = (TextView) mainView.findViewById(R.id.signupSubtitleLbl);
        imageImg = (ImageView) mainView.findViewById(R.id.signupImage);
        userTxt = (EditText) mainView.findViewById(R.id.signupUserTxt);
        userIcon = (ConstraintLayout) mainView.findViewById(R.id.signupUserIconBack);
        usernameTxt = (EditText) mainView.findViewById(R.id.signupUsernameText);
        usernameIcon = (ConstraintLayout) mainView.findViewById(R.id.signupUsernameIconBack);
        passwordTxt = (EditText) mainView.findViewById(R.id.signupPasswordTxt);
        passwordIcon = (ConstraintLayout) mainView.findViewById(R.id.signupPasswordIconBack);
        signupBtn = (Button) mainView.findViewById(R.id.signupBtn);
        loginBtn = (Button) mainView.findViewById(R.id.toLoginBtn);
        backend = new BackendProxy();

        titleLbl.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
        subtitleLbl.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
        imageImg.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
        userTxt.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        userIcon.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        usernameTxt.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        usernameIcon.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        passwordTxt.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        passwordIcon.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        signupBtn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        loginBtn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));

        signupBtn.setOnClickListener((v) -> {
            String user = userTxt.getText().toString();
            String username = usernameTxt.getText().toString();
            String password = passwordTxt.getText().toString();

            if(validateText(user, username, password)) {
                signupBtn.setEnabled(false);

                backend.signup(username, user, password, () -> {
                    StatusDialog.createSuccess(getContext(), "Cuenta creada!",
                            () -> {
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                fm.beginTransaction().replace(R.id.scenario, new LoginFragment()).commit();
                            }).show();
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

                    signupBtn.setEnabled(true);
                    StatusDialog.createError(getContext(), errorMsg).show();
                });
            } else {
                Toast.makeText(getContext(), "Los campos no están completos. Verifíquelos.", Toast.LENGTH_SHORT).show();
            }
        });

        loginBtn.setOnClickListener((v) -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.scenario, new LoginFragment()).commit();
        });

        return mainView;
    }

    public boolean validateText(String name, String username, String password) {
        return (name.replace(" ", "") != "" && username.replace(" ", "")
                != "" && password.replace(" ", "") != "");
    }
}