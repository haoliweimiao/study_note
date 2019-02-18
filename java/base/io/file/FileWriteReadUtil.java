import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    private static final String TAG = "FileUtil";

    /**
     * 文件写入数据
     *
     * @param fileContents 写入内容
     * @param filePath     文件路径
     * @param isAppend     是否为追加模式
     */
    public static void writeStringAsFile(final String fileContents, String filePath, boolean isAppend) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return;
            }
            FileWriter out = new FileWriter(file, isAppend);
            out.write(fileContents);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件
     *
     * @param filePath 文件路径
     */
    public static String readFileAsString(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(file));
            while ((line = in.readLine()) != null) stringBuilder.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }


    /**
     * 保存二进制文件
     *
     * @param filePath filePath
     * @param data     二进制数据
     */
    public static void writeBinFile(final String filePath, byte[] data) {
        DataOutputStream dos = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
            dos.write(data, 0, data.length);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
