package androidboardgame.syslink.com.korokorobear;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import java.util.List;
import java.util.Random;

/**
 * メインアクティビティクラス
 */
public class MainActivity extends Activity implements Runnable, SensorEventListener {
    /** センサーマネージャ */
    SensorManager manager;
    /** マップ */
    Map map = null;
    /** クマクラス */
    Bear bear = null;
    /** ブロッククラス */
    Block block = null;
    /** ハンドラ */
    Handler handler = null;
    /** 幅 */
    int width = 0;
    /** 高さ */
    int height = 0;
    /** 処理時間 */
    int time = 0;
    /** 加速度センサー取得値 X */
    float gx = 0;
    /** 加速度センサー取得値 Y */
    float gy = 0;
    /** 画面解像度 */
    float dpi = 0;
    /** フレームレイアウト */
    FrameLayout framelayout = null;
    /** 経過時間 */
	Time elapsedTime = new Time();
    /** パラメタ-経過時間 */
    long paramTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* フルスクリーン */
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* タイトルなし */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* フレーム設定 */
        framelayout = new FrameLayout(this);
        framelayout.setBackgroundColor(Color.GREEN);
        setContentView(framelayout);

        time = 10;
        handler = new Handler();
        handler.postDelayed(this, 3000);

        Point size = getDisplaySize();
        width = size.x;
        height = size.y;
        dpi = getResources().getDisplayMetrics().densityDpi;

        /* マップのインスタンスを作成 */
        map = new Map(this);

        /* クマクラスのインスタンスを作成 */
        bear = new Bear(this);
        bear.x = width / 2;
        bear.y = height / 2;

        /* 障害物クラスのインスタンスを作成 */
        block = new Block(this);
        Random rnd = new Random();
        /* 障害物の出現位置のX座標は固定 */
        block.x = width;
        /* 障害物の出現位置のY座標をランダムで設定 */
        block.y = rnd.nextInt(height - (block.pSize * 2));
        /* ビューに追加 */
        framelayout.addView(map);
        framelayout.addView(block);
        framelayout.addView(bear);

        /* 経過時間の計測開始 */
        elapsedTime.start();
    }

    @Override
    public void run() {
        /* クマの移動座標を計算 */
        bear.vx += gx * time / 1000;
        bear.vy += gy * time / 1000;
        /* クマの移動座標を設定 */
        bear.x += dpi * bear.vx * time / 25.4;
        bear.y += dpi * bear.vy * time / 25.4;

       /* ブロックの移動座標を設定 X */
       if (0 < block.x) {
           /* ブロックのX座標を移動 */
           block.x -= 25;
       } else {
           /* 左端までいったら右端に移動 */
           block.x = width;
           /* 左端まで行ったら高さをランダムで変更 */
           Random rnd = new Random();
           block.y = rnd.nextInt(height - (block.pSize * 2));
       }

        /* X座標が0以下の場合 */
        if (bear.x <= 0) {
            /* X座標を画面をはみ出さない位置に設定 */
            bear.x = bear.pSize;
            bear.vx = -bear.vx / 2;
        } else if (bear.x >= (width - (bear.pSize * 2))) {
            /* X座標が画面の幅 - 画像の大きさ 以上の場合 */

            /* X座標を画面をはみ出さない位置に設定 */
            bear.x = bear.x - bear.pSize;
            bear.vx = -bear.vx / 2;
        }

        /* Y座標が0以下の場合 */
        if (bear.y <= 0) {
            /* Y座標を画面をはみ出さない位置に設定 */
            bear.y = bear.pSize;
            bear.vy = -bear.vy / 2;
        } else if (bear.y >= height - (bear.pSize * 2)) {
            /* Y座標が画面の高さ - 画像の大きさ 以上の場合 */

            /* Y座標を画面をはみ出さない位置に設定 */
            bear.y = bear.y - bear.pSize;
            bear.vy = -bear.vy / 2;
        }

        /* 衝突判定 */
        if ((block.x - block.pSize < bear.x &&
                bear.x < block.x + block.pSize) &&
                (block.y - block.pSize < bear.y &&
                        bear.y < block.y + block.pSize)) {

            /* X,Y座標を固定 */
            bear.x = block.x;
            bear.y = block.y;
            bear.vx = 0;
            bear.vy = 0;
            bear.invalidate();
            
            /* 経過時間の取得 */
            paramTime = elapsedTime.getElipseTime();

            /* クマを回転させる */
            RotateAnimation rotateAnimation = new RotateAnimation(0, 360, bear.x, bear.y);
            rotateAnimation.setDuration(1000);
            bear.startAnimation(rotateAnimation);

            /* アニメーションリスナの登録 */
            rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // アニメーションの開始時に呼ばれます
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // アニメーションの繰り返し時に呼ばれます。
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // アニメーションの終了時に呼ばれます

                    /* 画面外にクマを移動させる。 */
                    TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, height);
                    translateAnimation.setDuration(2000);
                    translateAnimation.setFillAfter(true);
                    bear.startAnimation(translateAnimation);

                    /* アニメーションリスナの登録 */
                    translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            // アニメーションの開始時に呼ばれます
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // アニメーションの繰り返し時に呼ばれます
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // アニメーションの終了時に呼ばれます
                            finish();
                            /* GameOver画面の呼び出し */
                            gameOver(paramTime);
                        }
                    });
                }
            });

        } else {
            /* 処理を停止する。 */
            bear.invalidate();
            block.invalidate();
            /* 処理を再開する。 */
            handler.postDelayed(this, time);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager = (SensorManager)getSystemService(
                SENSOR_SERVICE);
        List<Sensor> sensors =
                manager.getSensorList(
                        Sensor.TYPE_ACCELEROMETER);
        if (0 < sensors.size()) {
            manager.registerListener(
                    this, sensors.get(0),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        /* 加速度センサーより取得した値を調整 */
        gy = event.values[0] / 10;
        gx = event.values[1] / 10;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * GameOver画面に遷移します。
     * @param elapsedTime 経過時間
     */
    public void gameOver(long elapsedTime){
        Intent intent = new Intent();
        intent.setClassName("androidboardgame.syslink.com.korokorobear", "androidboardgame.syslink.com.korokorobear.GameOverActivity");
        intent.putExtra("elapsedTime", elapsedTime);
        startActivity(intent);
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
}