# YUV图像相关功能

## YUV数据转Bitmap

~~~ java
	// 不可用，会出现以下异常
	// android.renderscript.RSIllegalArgumentException: Cannot update allocation from bitmap, sizes mismatch
    private static RenderScript rs = RenderScript.create(ZKApplication.getContext());
    private static ScriptIntrinsicYuvToRGB yuvToRgbIntrinsic = ScriptIntrinsicYuvToRGB.create(rs, Element.U8_4(rs));
    private static Type.Builder yuvType, rgbaType;
    private static Allocation in, out;

    /**
     * nv21 to bitmap
     *
     * @param data   nv21 data
     * @param width  nv21 image width
     * @param height nv21 image height
     * @return bitmap
     */
    public static Bitmap nv21ToBitmap(byte[] data, int width, int height) {
        if (yuvType == null) {
            yuvType = new Type.Builder(rs, Element.U8(rs)).setX(data.length);
            in = Allocation.createTyped(rs, yuvType.create(), Allocation.USAGE_SCRIPT);

            rgbaType = new Type.Builder(rs, Element.RGBA_8888(rs)).setX(width).setY(height);
            out = Allocation.createTyped(rs, rgbaType.create(), Allocation.USAGE_SCRIPT);
        }

        in.copyFrom(data);

        yuvToRgbIntrinsic.setInput(in);
        yuvToRgbIntrinsic.forEach(out);

        Bitmap bitmapOutput = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        out.copyTo(bitmapOutput);
        return bitmapOutput;
    }
~~~

~~~ java
    /**
     * nv21 to bitmap
     *
     * @param data   nv21 data
     * @param width  nv21 image width
     * @param height nv21 image height
     * @return bitmap
     */
    public static Bitmap nv21ToBitmap(byte[] data, int width, int height, @Nullable int[] strides) {
        YuvImage image = new YuvImage(data, ImageFormat.NV21, width, height, strides);
        ByteArrayOutputStream outputSteam = new ByteArrayOutputStream();
        // 将NV21格式图片，以质量70压缩成Jpeg，并得到JPEG数据流
        image.compressToJpeg(new Rect(0, 0, image.getWidth(), image.getHeight()), 70, outputSteam);
        //从outputSteam得到byte数据
        byte[] jpegData = outputSteam.toByteArray();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap bmp = BitmapFactory.decodeByteArray(jpegData, 0, jpegData.length);

        return bmp;
    }
~~~