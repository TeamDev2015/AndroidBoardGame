package androidboardgame.syslink.com.korokorobear;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

/**
 * クマクラス
 */
public class Bear extends View{

    /* X座標 */
    int x = 0;
    /* Y座標 */
    int y = 0;
    /* X座標移動用 */
    float vx = 0;
    /* Y座標移動用 */
    float vy = 0;
    /* 衝突判定用 円 半径 */
    int radius = 0;
    /* クマ画像 */
    private Bitmap korokoroBearBitmap;

    /**
     * コンストラクタ
     * @param context
     */
    public Bear(Context context) {
        super(context);
        /* 各変数の初期値を設定 */
        x = 0;
        y = 0;
        vx = 0;
        vy = 0;
        radius = 60;

        /* 画像を設定 */
        korokoroBearBitmap = BitmapFactory.decodeResource(
                getResources(), R.drawable.imgkorokorobear);
    }

    /**
     * 描画処理
     * @param canvas
     */
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(korokoroBearBitmap, x, y, null);
    }
}
