import cn.hutool.core.io.FileUtil;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class ListTest {

    @Test
    public void run() {
        String projectPath = System.getProperty("user.dir");
        File parentFile = new File(projectPath).getParentFile();
        // 输入路径
        String inputPath = new File(parentFile, "taideng-generator-demo-projects/acm-template").getAbsolutePath();
        System.out.println(inputPath);
        List<File> files = FileUtil.loopFiles(inputPath);
        for (File file : files) {
            System.out.println(file);
        }
    }
}
