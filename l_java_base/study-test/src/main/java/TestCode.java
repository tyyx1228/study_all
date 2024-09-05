import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * @author relax tongyu
 * @create 2018-04-23 17:18
 **/
public class TestCode {

    @Test
    public void testGBK() throws UnsupportedEncodingException {
        String s = "ABCDabcd楼上的房间流动性开发商";
        String utf8 = new String(s.getBytes(), "UTF-8");
        System.out.println(utf8);

       /* String gbk = new String(utf8.getBytes(), "GBK");
        System.out.println(gbk);

        String iso = new String(utf8.getBytes(), "ISO-8859-1");
        System.out.println(iso);*/

        String gb2312 = new String(utf8.getBytes(), "GB2312");
        System.out.println(gb2312);

        String s1 = new String(gb2312.getBytes("GB2312"), "GB2312");
        System.out.println(s1);

    }

}
