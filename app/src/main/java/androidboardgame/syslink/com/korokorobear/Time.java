package androidboardgame.syslink.com.korokorobear;

/**
 * Created by toya on 2015/10/24.
 */
public class Time {
    private long m_StartTime = 0;

    /**
     * 経過時間の計測を開始します。
     */
    public void start() {
        m_StartTime = System.currentTimeMillis();
    }

    /**
     * 経過時間を取得します。
     * @return 経過時間
     */
    public long getElipseTime() {
        return (System.currentTimeMillis() - m_StartTime) / 1000;
    }
}
