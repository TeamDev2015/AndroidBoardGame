package androidboardgame.syslink.com.korokorobear;

/**
 * Created by toya on 2015/10/24.
 */
public class Time {
    private long m_StartTime = 0;

    // function : Start
    // Abstract : �Q�[�����J�n����
    // Return : �J�n���Ԃ�ێ�����
    public void Start(){
        m_StartTime = System.currentTimeMillis();
    }

    // function : getElipseTime
    // Abstract : �o�ߎ��Ԃ��擾����
    // Return : �o�ߎ���
    public long getElipseTime(){
        return (System.currentTimeMillis() - m_StartTime) / 1000;
    }
}
