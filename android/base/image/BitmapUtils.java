
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Bitmap utils
 *
 * @author von
 * @version 1.0.0
 * @date 2021-01-19 13:50:45
 */
public class BitmapUtils {

    /**
     * bitmap to base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }

        ByteArrayOutputStream byteArrayOutputStream;

        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            return encoded;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * base64 to bitmap
     *
     * @param base64
     * @return
     */
    public static Bitmap base64ToBitmap(String base64) {
        try {
            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return decodedByte;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

     /**
     * 将图片读取为Bitmap
     *
     * @param path 图片地址
     * @return 图片
     */
    public static Bitmap getBitmapFromPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        File image = new File(path);
        if (!image.exists()) {
            return null;
        }
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        return BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
    }

}
