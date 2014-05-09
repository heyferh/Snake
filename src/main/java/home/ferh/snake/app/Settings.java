package home.ferh.snake.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
        Log.d("TAG", "Settings");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        radiogroup_mode = (RadioGroup) findViewById(R.id.Rg_mode);
        radiogroup_speed = (RadioGroup) findViewById(R.id.Rg_speed);
        bttn = (Button) findViewById(R.id.button);
        bttn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    }
}
