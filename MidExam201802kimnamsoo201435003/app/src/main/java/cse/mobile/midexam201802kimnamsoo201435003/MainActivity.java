package cse.mobile.midexam201802kimnamsoo201435003;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean pwcheck=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btlogin = findViewById(R.id.btlogin);
        final EditText etid = findViewById(R.id.etid);
        Button btcancel = findViewById(R.id.btcancel);
        final EditText etpw = findViewById(R.id.etpw);
        RadioGroup rg = findViewById(R.id.rg);
        final TextView tvpw = findViewById(R.id.tvpw);
        Switch sw = findViewById(R.id.switch1);

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etid.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "ID should be typed.", Toast.LENGTH_SHORT).show();
                else if(pwcheck) {
                    if (etpw.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Password should be typed.", Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getApplicationContext(), "ID: " + etid.getText() + ", Password: " + etpw.getText(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "ID:"+etid.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb1:
                        etid.setText("");
                        etpw.setText("");
                        pwcheck=false;
                        tvpw.setVisibility(View.GONE);
                        etpw.setVisibility(View.GONE);
                        break;
                    case  R.id.rb2:
                        etid.setText("");
                        etpw.setText("");
                        pwcheck=true;
                        tvpw.setVisibility(View.VISIBLE);
                        etpw.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });


        final LinearLayout ll = (LinearLayout) View.inflate(getApplicationContext(), R.layout.toast_cancel,null);

        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(ll);
                toast.show();
            }
        });

        etid.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE)
                {
                    if (etid.getText().toString().equals(""))
                        Toast.makeText(getApplicationContext(), "ID should be typed.", Toast.LENGTH_SHORT).show();
                    else if(pwcheck) {
                        if (etpw.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "Password should be typed.", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(getApplicationContext(), "ID: " + etid.getText() + ", Password: " + etpw.getText(), Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "ID:"+etid.getText(), Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

        etpw.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId== EditorInfo.IME_ACTION_DONE)
                {
                    if (etid.getText().toString().equals(""))
                        Toast.makeText(getApplicationContext(), "ID should be typed.", Toast.LENGTH_SHORT).show();
                    else if(pwcheck) {
                        if (etpw.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "Password should be typed.", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(getApplicationContext(), "ID: " + etid.getText() + ", Password: " + etpw.getText(), Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "ID:"+etid.getText(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        final ImageView inflateiv = (ImageView) View.inflate(getApplicationContext(),R.layout.myimageview,null);
        final TextView inflatetv = (TextView) View.inflate(getApplicationContext(),R.layout.mytextview,null);
        final FrameLayout fl = findViewById(R.id.fl);
        fl.addView(inflatetv);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    fl.removeView(inflatetv);
                    if(fl.indexOfChild(inflateiv)<0)
                        fl.addView(inflateiv);
                }else{
                    fl.removeView(inflateiv);
                    if(fl.indexOfChild(inflatetv)<0)
                         fl.addView(inflatetv);
                }
            }
        });

    }
}
