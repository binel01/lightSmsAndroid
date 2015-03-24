package intents.dassi.com.smsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.method.DigitsKeyListener;
import android.text.method.MetaKeyKeyListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import intents.dassi.com.util.SendSMS;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private EditText textNumber;
        private EditText textSms;
        private Button sendButton;
        SendSMS mSender = new SendSMS();



        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            textNumber = (EditText)rootView.findViewById(R.id.phoneNumber);
            textSms = (EditText)rootView.findViewById(R.id.textSms);
            sendButton = (Button)rootView.findViewById(R.id.sendSms);


            /*
            textNumber.setText("5554");
            textSms.setText(// This is standard lorem-ipsum text, do not bother
                    // trying to wrap it, there's about 500 characters...
                    "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                            "sed do eiusmod tempor incididunt ut labore et dolore " +
                            "magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                            "exercitation ullamco laboris nisi ut aliquip ex ea commodo" +
                            " consequat. Duis aute irure dolor in reprehenderit in voluptate" +
                            " velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint " +
                            "occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit " +
                            "anim id est laborum.");
                            */

            // just permit to type integer number unsigned and not decimal for the destination Number
            DigitsKeyListener digitSms = DigitsKeyListener.getInstance(false, false);
            textNumber.setKeyListener(digitSms);

            //MetaKeyKeyListener digitMetaSms = MetaKeyKeyListener.

            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context = v.getContext();
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

            });


            return rootView;
        }

        public boolean correctInputs(){

            if( textNumber == null || textNumber.equals("") )
                return false;

            if (textSms == null)
                return false;

            return true;
        }
    }
}
