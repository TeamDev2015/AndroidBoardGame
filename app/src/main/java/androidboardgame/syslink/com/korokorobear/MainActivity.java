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
import android.widget.FrameLayout;

import java.util.List;
import java.util.Random;

public class MainActivity extends Activity implements Runnable, SensorEventListener {
    SensorManager manager;
    Bear bear;
    Block block;
    Handler handler;
    int width, height, time;
    float gx, gy, dpi;
    FrameLayout framelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* フルスクリーン */
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* タイトルなし */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* フレーム設定 */
        framelayout = new FrameLayout(this);
        framelayout.setBackgroundColor(Color.GREEN);
        setContentView(framelayout);

        time = 10;
        handler = new Handler();
        handler.postDelayed(this, 3000);

        WindowManager windowManager =
                (WindowManager) getSystemService(WINDOW_SERVICE);

        /* 画面関係の値を取得 */
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        dpi = getResources().getDisplayMetrics().densityDpi;

        /* クマクラスのインスタンスを作成 */
        bear = new Bear(this);
        bear.x = width / 2;
        bear.y = height / 2;

        /* 障害物クラスのインスタンスを作成 */
        block = new Block(this);
        Random rnd = new Random();
        /* 障害物の出現位置をランダムで設定 */
        block.x = rnd.nextInt(
                width - (2 * (block.r + bear.radius)))
                + block.r + bear.radius;
        block.y = rnd.nextInt(
                height - (2 * (block.r + bear.radius)))
                + block.r + bear.radius;

        /* ビューに追加 */
        framelayout.addView(block);
        framelayout.addView(bear);
    }

    @Override
    public void run() {
        /** クマの移動座標を計算 */
        bear.vx += gx * time / 1000;
        bear.vy += gy * time / 1000;
        /** クマの移動座標を設定 */
        bear.x += dpi * bear.vx * time / 25.4;
        bear.y += dpi * bear.vy * time / 25.4;

        // X座標が半径以下の場合
        if (bear.x <= bear.radius) {

            /* X座標を画面をはみ出さない位置に設定 */
            bear.x = bear.radius;
            bear.vx = -bear.vx / 3;
        } else if (bear.x >= width - bear.radius) {
            // X座標が画面の幅 - 半径 以上の場合

            /* X座標を画面をはみ出さない位置に設定 */
            bear.x = width - bear.radius;
            bear.vx = -bear.vx / 3;
        }

        // Y座標が半径以下の場合
        if (bear.y <= bear.radius) {

            /* Y座標を画面をはみ出さない位置に設定 */
            bear.y = bear.radius;
            bear.vy = -bear.vy / 3;
        } else if (bear.y >= height - bear.radius) {
            // Y座標が画面の高さ - 半径 以上の場合

            /* Y座標を画面をはみ出さない位置に設定 */
            bear.y = height - bear.radius;
            bear.vy = -bear.vy / 3;
        }

        /* 衝突判定 */
        if ((block.x - block.r < bear.x &&
                bear.x < block.x + block.r) &&
                (block.y - block.r < bear.y &&
                        bear.y < block.y + block.r)) {

            /* X,Y座標を固定 */
            bear.x = block.x;
            bear.y = block.y;
            bear.vx = 0;
            bear.vy = 0;
            bear.invalidate();
            /* GameOver画面の呼び出し */
            gameOver();
        } else {
            /* 処理を停止する。 */
            bear.invalidate();
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
    public void onAccuracyChanged(
            Sensor sensor, int accuracy) {
    }
    //GameOver画面遷移
    public void gameOver(){
        Intent intent = new Intent();
        intent.setClassName("androidboardgame.syslink.com.korokorobear", "androidboardgame.syslink.com.korokorobear.GameOverActivity");
        startActivity(intent);
    }
}