package androidboardgame.syslink.com.dropball;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Hole extends View{
    int x, y, r;
    Paint p;

    public Hole(Context context){
        super(context);
        x = y = 0;
        r = 40;
        p = new Paint();
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.FILL);
        p.setAntiAlias(true);
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, r, p);
    }
}
