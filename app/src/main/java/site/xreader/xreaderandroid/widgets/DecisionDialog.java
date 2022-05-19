package site.xreader.xreaderandroid.widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.callbacks.SimpleCallback;

public class DecisionDialog {
    public static Dialog create(Context context, String title, String message, SimpleCallback agreeCb, SimpleCallback cancelCb) {
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.decision_modal);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.modal_rounded);

        ImageView decisionImg = (ImageView) dialog.findViewById(R.id.decisionModalImg);
        TextView titleDecisionLbl = (TextView) dialog.findViewById(R.id.decisionModalTitleLbl);
        TextView msgDecisionLbl = (TextView) dialog.findViewById(R.id.decisionModalMessageLbl);
        Button agreeBtn = (Button) dialog.findViewById(R.id.decisionModalAgreeBtn);
        Button cancelBtn = (Button) dialog.findViewById(R.id.decisionModalCancelBtn);

        decisionImg.startAnimation(AnimationUtils.loadAnimation(context, R.anim.appear));
        titleDecisionLbl.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bounce));
        msgDecisionLbl.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bounce));
        agreeBtn.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bounce));
        cancelBtn.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bounce));

        titleDecisionLbl.setText(title);
        msgDecisionLbl.setText(message);

        agreeBtn.setOnClickListener((v) -> {
            agreeCb.call();
            dialog.hide();
        });

        cancelBtn.setOnClickListener((v) -> {
            cancelCb.call();
            dialog.hide();
        });

        return dialog;
    }
}
