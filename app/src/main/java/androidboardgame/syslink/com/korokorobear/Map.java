package androidboardgame.syslink.com.korokorobear;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by toya on 2015/11/07.
 */
public class Map extends View {
    private Bitmap map;
    /* X座標 */
    int x = 0;
    /* Y座標 */
    int y = 0;
    /* サイズ*/
    int size = 100;
    /* X座標移動用 */
    float vx = 0;


    public Map(Context context) {
        super(context);

        /* 画像を設定 */
        map=BitmapFactory.decodeResource(
                getResources(),R.drawable.backgrund);
    }

    /**
     * 描画処理
     * @param canvas
     */
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(map, x, y, null);
    }
}
