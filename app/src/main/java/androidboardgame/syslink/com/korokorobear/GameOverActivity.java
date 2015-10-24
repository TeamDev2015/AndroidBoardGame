package androidboardgame.syslink.com.korokorobear;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class GameOverActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        MyOverView mView = new MyOverView(this);
        setContentView(mView);
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
     *  �`��p�̃N���X
     */
    class MyOverView extends View {
        private Context mContext;
        /**
         * �R���X�g���N�^
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
            Paint paint;
		/* �w�i�F��ݒ� */
            canvas.drawColor(Color.BLACK);

		/* ������`�� */
            paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(100);
            canvas.drawText("Game Over", 250, 250, paint);
        }
    }
}
