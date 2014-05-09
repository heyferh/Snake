package home.ferh.snake.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ferh on 07.05.14.
 */
public class TitleActivity extends Activity implements View.OnTouchListener {
    ImageView newGameButton, settingsButton, scoresButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);

        newGameButton = (ImageView) findViewById(R.id.NewGame);
        settingsButton = (ImageView) findViewById(R.id.Settings);
        scoresButton = (ImageView) findViewById(R.id.Scores);

        newGameButton.setOnTouchListener(this);
        settingsButton.setOnTouchListener(this);
        scoresButton.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Intent intent;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (v.getId() == R.id.NewGame)
                    newGameButton.setBackgroundResource(R.drawable.menu_new_game_pressed);
                else if (v.getId() == R.id.Settings)
                    settingsButton.setBackgroundResource(R.drawable.menu_settings_pressed);
                else {
                    scoresButton.setBackgroundResource(R.drawable.menu_scores_pressed);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (v.getId() == R.id.NewGame) {
                    newGameButton.setBackgroundResource(R.drawable.menu_new_game);
                    intent = new Intent(this, Board.class);
                    startActivity(intent);
                } else if (v.getId() == R.id.Settings) {
                    settingsButton.setBackgroundResource(R.drawable.menu_settings);
                    intent = new Intent(this, Settings.class);
                    startActivity(intent);
                } else {
                    scoresButton.setBackgroundResource(R.drawable.menu_scores);
                    //intent = new Intent("scores");
                    //startActivity(intent);
                }
                break;
        }
        return true;
    }
}