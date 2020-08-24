package com.hlw.io.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStreamUtils {

  /**
   * write content to file(path)
   *
   * @param path    write file path(the system-dependent file name)
   * @param content write content
   * @param append  true: append mode; false: override mode
   */
  public static void write(String path, String content, boolean append) {
    if (isEmpty(path) || isEmpty(content)) {
      return;
    }
    FileOutputStream stream = null;
    try {
      stream = new FileOutputStream(path, append);
      stream.write(content.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * read string from path
   *
   * @param path read file path
   * @return string: text content from path
   */
  public static String read(String path) {
    if (isEmpty(path)) {
      throw new NullPointerException("file path is null!");
    }

    FileInputStream stream = null;
    try {
      stream = new FileInputStream(path);
      int len = 0;
      byte[] bys = new byte[1024];
      StringBuilder sb = new StringBuilder();
      while ((len = stream.read(bys)) != -1) {
        sb.append((new String(bys, 0, len)));
      }
      return sb.toString();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return null;
  }

  /**
   * copy src content to dest file
   *
   * @param src  src file
   * @param dest dest file
   */
  public static void copy(String src, String dest) {
    FileInputStream inputStream = null;
    FileOutputStream outputStream = null;
    try {
      inputStream = new FileInputStream(src);
      outputStream = new FileOutputStream(dest, true);
      int len = 0;
      byte[] bytes = new byte[1024];
      while ((len = inputStream.read(bytes)) != -1) {
        outputStream.write(bytes);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (outputStream != null) {
        try {
          outputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
     * copy src content to dest file
     *
     * @param src  src file
     * @param dest dest file
     */
    public static void copyFast(String src, String dest) {
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(src));
            bis = new BufferedInputStream(new FileInputStream(dest));
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

  /**
   * check str is null
   * @param str string
   */
  public static boolean isEmpty(String str) {
    return str == null || str.length() == 0;
  }
}
