package androidboardgame.syslink.com.korokorobear;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

/**
 * ゲームオーバーアクティビティクラス
 */
public class GameOverActivity extends Activity {
    /** 経過時間 */
    private long elapsedTime = 0;
    /** 画面サイズ */
    private Point size;
    /** ハンドラ */
    private final Handler handler = new Handler();
    /** コンテキスト */
    private final Context context = this;

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

        /* 1秒後にダイアログを表示 */
        handler.postDelayed(showDialogTask, 1000);
    }

    /**
     *  ダイアログを表示
     */
    private final Runnable showDialogTask = new Runnable() {
        @Override
        public void run() {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            /* アラートダイアログのタイトルを設定 */
            alertDialogBuilder.setTitle("確認");
            /* アラートダイアログのメッセージを設定 */
            alertDialogBuilder.setMessage("リトライしますか？");
            /* アラートダイアログのはいボタンがクリックされた時に呼び出されるコールバックリスナー */
            alertDialogBuilder.setPositiveButton("はい",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            /* メイン画面に戻る */
                            moveMainActivity();
                        }
                    });
            /* アラートダイアログのいいえボタンがクリックされた時に呼び出されるコールバックリスナー */
            alertDialogBuilder.setNegativeButton("いいえ",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            /* アラートダイアログのキャンセルが可能かどうかを設定 */
            alertDialogBuilder.setCancelable(true);
            AlertDialog alertDialog = alertDialogBuilder.create();
            /* アラートダイアログを表示 */
            alertDialog.show();
        }
    };

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
     * ゲームオーバー画面クラス
     */
    class MyOverView extends View {
        private Context mContext = null;

        /**
         * コンストラクタ
         * @param c
         */
        public MyOverView(Context c) {
            super(c);
            mContext = c;
            setFocusable(true);
            Resources res = this.getContext().getResources();
        }

        /**
         * 描画処理
         * @param canvas
         */
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            /* canvasの背景色 */
            canvas.drawColor(Color.BLACK);

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

    /**
     * メイン画面に遷移します。
     */
    public void moveMainActivity(){
        Intent intent = new Intent();
        intent.setClassName("androidboardgame.syslink.com.korokorobear", "androidboardgame.syslink.com.korokorobear.MainActivity");
        startActivity(intent);
    }
}
