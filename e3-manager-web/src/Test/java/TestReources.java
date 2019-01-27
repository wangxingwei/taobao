import java.io.File;

/**
 * @author www.yanzhongxin.com
 * @date 2018/11/2 16:29
 */
public class TestReources {
    public static void main(String[] args) {
        File file=new File("classpath:java/TestReources.java");
        System.out.println(file.exists());
    }
}
