package home.ferh.snake.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class Board extends Activity implements View.OnTouchListener {
    GestureDetector gdt = new GestureDetector(new SwipeListener());
    DrawView drw;
    final int SCREEN_WIDTH = 480;
    final int SCREEN_HEIGHT = 800;
    final int DOT_SIZE = 20;
    final int ALL_DOTS = (SCREEN_WIDTH / DOT_SIZE) * (SCREEN_HEIGHT / DOT_SIZE);
    int dots = 0;
    int x[] = new int[ALL_DOTS];
    int y[] = new int[ALL_DOTS];
    int apple_x = 0;
    int apple_y = 0;
    boolean isUp = false;
    boolean isDown = true;
    boolean isLeft = false;
    boolean isRight = false;
    boolean inGame = false;
    Paint p = new Paint();
    Paint ap = new Paint();

    public void InitGame() {
        p.setColor(Color.WHITE);
        ap.setColor(Color.GREEN);
        dots = 3;
        for (int i = 0; i < dots; i++) {
            y[i] = 0;
            x[i] = i * DOT_SIZE;
        }
        inGame = true;
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (isLeft) {
            x[0] -= DOT_SIZE;
        }
        if (isRight) {
            x[0] += DOT_SIZE;
        }
        if (isUp) {
            y[0] -= DOT_SIZE;
        }
        if (isDown) {
            y[0] += DOT_SIZE;
        }

    }

    public void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                //dots = i - 1;
                inGame = false;
            }
        }
        if (x[0] > SCREEN_WIDTH - DOT_SIZE) {
            x[0] = 0;
        }
        if (x[0] < 0) {
            x[0] = (SCREEN_WIDTH / DOT_SIZE - 1) * DOT_SIZE;
        }
        if (y[0] > SCREEN_HEIGHT - DOT_SIZE) {
            y[0] = 0;
        }
        if (y[0] < 0) {
            y[0] = (SCREEN_HEIGHT / DOT_SIZE - 1) * DOT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == apple_x && y[0] == apple_y) {
            dots++;
            locateApple();
        }
    }

    public void locateApple() {
        int tmp = (int) (Math.random() * (SCREEN_WIDTH / 20));
        apple_x = tmp * DOT_SIZE;
        tmp = (int) (Math.random() * (SCREEN_HEIGHT / 20));
        apple_y = tmp * DOT_SIZE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drw = new DrawView(this);
        drw.setOnTouchListener(this);
        setContentView(drw);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gdt.onTouchEvent(motionEvent);
        return true;
    }

    public void SwipeRight() {
        if (!isLeft && !isRight) {
            isRight = true;
            isUp = false;
            isDown = false;
        }
    }

    public void SwipeLeft() {
        if (!isLeft && !isRight) {
            isLeft = true;
            isUp = false;
            isDown = false;
        }
    }

    public void SwipeUp() {
        if (!isDown && !isUp) {
            isUp = true;
            isLeft = false;
            isRight = false;
        }
    }

    public void SwipeDown() {
        if (!isUp && !isDown) {
            isDown = true;
            isLeft = false;
            isRight = false;
        }
    }

    class SwipeListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY() - e2.getY())) {
                    if (e1.getX() > e2.getX()) {
                        SwipeLeft();
                    } else {
                        SwipeRight();
                    }
                } else if (e1.getY() > e2.getY()) {
                    SwipeUp();
                } else {
                    SwipeDown();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return false;
        }
    }

    class DrawView extends SurfaceView implements SurfaceHolder.Callback {

        private DrawThread drawThread;

        public DrawView(Context context) {
            super(context);
            InitGame();
            getHolder().addCallback(this);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            drawThread = new DrawThread(getHolder());
            drawThread.setRunning(true);
            drawThread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            boolean retry = true;
            drawThread.setRunning(false);
            while (retry) {
                try {
                    drawThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
        }

        public void onDraw(Canvas canvas) {
            canvas.drawRGB(0, 0, 0);
            canvas.drawRect(apple_x, apple_y, apple_x + DOT_SIZE, apple_y + DOT_SIZE, ap);
            for (int i = 0; i < dots; i++) {
                canvas.drawRect(x[i], y[i], x[i] + DOT_SIZE, y[i] + DOT_SIZE, p);
            }
        }

        class DrawThread extends Thread {

            private boolean running = false;
            private SurfaceHolder surfaceHolder;

            public DrawThread(SurfaceHolder surfaceHolder) {
                this.surfaceHolder = surfaceHolder;
            }

            public void setRunning(boolean running) {
                this.running = running;
            }

            @Override
            public void run() {
                Canvas canvas;
                while (running) {
                    canvas = null;
                    try {
                        canvas = surfaceHolder.lockCanvas(null);
                        if (canvas == null)
                            continue;
                        if (inGame) {
                            try {
                                sleep(100, 0);
                            } catch (InterruptedException e) {
                            }
                            checkApple();
                            checkCollision();
                            move();
                            onDraw(canvas);

                        } else
                            setRunning(false);
                    } finally {
                        if (canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        }

    }
}
