package ca.klapstein.nklapste_feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditFeel extends AppCompatActivity {

    private static final String TAG = "EditFeel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_feel);
        final String comment = getIntent().getStringExtra("comment");
        final int position = getIntent().getIntExtra("position", 0);
        final EditText edit_comment_text = (EditText) findViewById(R.id.edit_comment_text);
        edit_comment_text.setText(comment);

        final Button button_save_feel = (Button) findViewById(R.id.button_save_feel);
        button_save_feel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String comment = edit_comment_text.getText().toString();

                Intent data = new Intent();
                data.putExtra("comment", comment);
                data.putExtra("position", position);
                setResult(RESULT_OK, data);
                Log.d(TAG, "Sending save_feel intent: comment: "+comment+" position: "+position);
                finish();
            }
        });
    }
}
