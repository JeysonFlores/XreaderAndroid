package site.xreader.xreaderandroid.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import site.xreader.xreaderandroid.R;

public class StatusDialog {
    public static Dialog createErrorDialog(Context context, String message) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.error_modal);

        ImageView errorImg = (ImageView) dialog.findViewById(R.id.errorModalImg);
        TextView titleErrorLbl = (TextView) dialog.findViewById(R.id.errorModalTitleLbl);
        TextView msgErrorLbl = (TextView) dialog.findViewById(R.id.errorModalMessageLbl);
        Button closeBtn = (Button) dialog.findViewById(R.id.closeErrorBtn);

        errorImg.startAnimation(AnimationUtils.loadAnimation(context, R.anim.appear));
        titleErrorLbl.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bounce));
        msgErrorLbl.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bounce));
        closeBtn.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bounce));

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        msgErrorLbl.setText(message);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.modal_rounded);

        return dialog;
    }
}
