package androidboardgame.syslink.com.korokorobear;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


public class GameOverActivity extends Activity {
    private long elapsedTime = 0;
    private Point size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        /* フルスクリーン */
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        MyOverView mView = new MyOverView(this);
        setContentView(mView);

        Intent intent = getIntent();
        elapsedTime = intent.getLongExtra("elapsedTime", 0);

        /*画面サイズの取得*/
        size = getDisplaySize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game_over, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 画面サイズを取得します。
     *
     * @return 画面サイズ
     */
    public Point getDisplaySize() {

        WindowManager windowManager =
                (WindowManager) getSystemService(WINDOW_SERVICE);
        /* 画面関係の値を取得 */
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }


    /**
     * �`��p�̃N���X
     */
    class MyOverView extends View {
        private Context mContext;

        /**
         * �R���X�g���N�^
         *
         * @param c
         */
        public MyOverView(Context c) {
            super(c);
            mContext = c;
            setFocusable(true);
            // Resource�C���X�^���X�̐���
            Resources res = this.getContext().getResources();
        }

        /**
         * �`�揈��
         */
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            /* canvasの背景色 */
            canvas.drawColor(Color.BLACK);

            System.out.println(elapsedTime);

            /* ゲームオーバー文字の表示 */
            Paint lblGameOver = new Paint();
            lblGameOver.setColor(Color.WHITE);
            lblGameOver.setTextAlign(Paint.Align.CENTER);
            lblGameOver.setTextSize(100);
            int xPos = size.x / 2;
            int yPos = (int) ((canvas.getHeight() / 2) - ((lblGameOver.descent() + lblGameOver.ascent()) / 2));
            canvas.drawText("Game Over", xPos, yPos, lblGameOver);

            /* スコアの表示 */
            Paint lblScore = new Paint();
            lblScore.setColor(Color.WHITE);
            lblScore.setTextAlign(Paint.Align.CENTER);
            lblScore.setTextSize(100);
            xPos = size.x / 2;
            yPos = (int) ((canvas.getHeight() / 2 + 200) - ((lblScore.descent() + lblScore.ascent()) / 2));
            canvas.drawText("生き残った時間:" + String.valueOf(elapsedTime) + "秒", xPos, yPos, lblScore);
        }
    }
}
