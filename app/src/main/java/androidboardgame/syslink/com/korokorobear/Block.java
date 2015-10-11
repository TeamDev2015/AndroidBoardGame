package androidboardgame.syslink.com.korokorobear;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * 障害物クラス
 */
public class Block extends View{
    /* X座標 */
    int x = 0;
    /* Y座標 */
    int y = 0;
    /* 半径 */
    int r = 0;
    Paint p;
    /* 障害物画像 */
    private Bitmap blockBitmap;

    /**
     * コンストラクタ
     * @param context
     */
    public Block(Context context){
        super(context);
        /* 各変数の初期値を設定 */
        x = 0;
        y = 0;
        r = 60;

        /* 画像を設定 */
        blockBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.block);
    }

    /**
     * 描画処理
     * @param canvas
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(blockBitmap, x, y, null);
    }
}
