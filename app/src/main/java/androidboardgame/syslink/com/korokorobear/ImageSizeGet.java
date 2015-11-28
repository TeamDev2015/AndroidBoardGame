package androidboardgame.syslink.com.korokorobear;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * リソースの画像サイズを取得します。.
 */
public class ImageSizeGet {
    /** ビットマップ */
    Bitmap image = null;

    /**
     * コンストラクタ
     * @param context
     * @param resourceName
     */
    public ImageSizeGet(Context context, int resourceName){
        /* リソースからbitmapを作成 */
        image = BitmapFactory.decodeResource(context.getResources(), resourceName);
    }

    /**
     * 画像のHeightを取得して返却します。
     */
    public int getHeight(){
        /* 画像のHeightを返却 */
        return image.getHeight();
    }

    /**
     * 画像のWidthを取得して返却します。
     */
    public int getWidth(){
        /* 画像のWidthを返却 */
        return image.getWidth();
    }

}
