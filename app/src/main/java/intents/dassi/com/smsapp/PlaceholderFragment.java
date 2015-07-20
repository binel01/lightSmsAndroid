package intents.dassi.com.smsapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import intents.dassi.com.util.SendSMS;

/**
 *
 */

@EFragment(R.layout.fragment_placeholder)
public class PlaceholderFragment extends Fragment {

    @ViewById(R.id.phoneNumber)
    EditText textNumber;

    @ViewById(R.id.textSms)
    EditText textSms;

    SendSMS mSender = new SendSMS();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Click
    void sendSms(){
        Context context = this.getActivity().getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        boolean success = false;

        CharSequence text = "Message sent ";
        String exceptionString = "Errors on input data";
        boolean exceptionOccured = false;

        if(correctInputs()) {
            try {
                success = mSender.sendSMSMessage(textNumber.getText().toString(), textSms.getText().toString());

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                exceptionOccured = true;
            }

            if(exceptionOccured)
                text = "Correct Inputs values, " + exceptionString;
            else
                text = "Correct Inputs values, " + text + (success ? "successfully" : "unsuccessfully");
        } else {
            text = "Incorrect Inputs values, " + text + (success ? "successfully" : "unsuccessfully");
        }

        Toast smsToast = Toast.makeText(context, text, duration);
        smsToast.show();
    }

    public boolean correctInputs(){
        if( textNumber == null || textNumber.equals("") )
            return false;
        if (textSms == null)
            return false;

        return true;
    }
}
