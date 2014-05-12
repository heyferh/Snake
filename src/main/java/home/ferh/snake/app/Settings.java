package home.ferh.snake.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

/**
 * Created by ferh on 09.05.14.
 */
public class Settings extends Activity implements View.OnClickListener {
    RadioGroup radiogroup_mode;
    RadioGroup radiogroup_speed;
    Button bttn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        radiogroup_mode = (RadioGroup) findViewById(R.id.Rg_mode);
        radiogroup_speed = (RadioGroup) findViewById(R.id.Rg_speed);
        bttn = (Button) findViewById(R.id.button);
        bttn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("Game mode", radiogroup_mode.getCheckedRadioButtonId());
        intent.putExtra("Game speed", radiogroup_speed.getCheckedRadioButtonId());
        setResult(RESULT_OK, intent);
        finish();
    }
}
