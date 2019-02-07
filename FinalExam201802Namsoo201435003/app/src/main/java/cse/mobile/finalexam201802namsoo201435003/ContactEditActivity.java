package cse.mobile.finalexam201802namsoo201435003;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactedit);

        final EditText et = findViewById(R.id.editText);
        Button bt_edit = findViewById(R.id.bt_edit);
        Button bt_cancel = findViewById(R.id.bt_cancel);

        et.setHint(getIntent().getStringExtra("mainstring"));

        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("substring", et.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
