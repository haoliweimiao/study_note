/**
 * 刮刮卡
 */
public class ScratchImageView extends View {
 
    private String TAG = "ScratchImageView2";
 
    // 被遮挡的bitmap, 和用于遮挡的bitmap
    private Bitmap mBgBitmap, mFgBitmap;
    // 画笔对象, 主要设置了setXfermode 和透明度
    private Paint mPaint;
    private Canvas mCanvas;
    private Path mPath;
 
    // 控件的宽和高
    private int viewWidth;
    private int viewHeight;
 
    public ScratchImageView2(Context context) {
        super(context);
    }
 
    public ScratchImageView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
 
    public ScratchImageView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
 
 
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 获取控件的宽和高
        viewWidth = getWidth();
        viewHeight = getHeight();
 
        // 遮盖层
        mFgBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        // 底层也就是背景层(被遮盖层)
        mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ace);
 
        // bitmap的宽高和view的宽高也许不是等比例的, 这里计算需要缩放的比例
        float scaleWidth = mFgBitmap.getWidth() * 1.0f / mBgBitmap.getWidth();
        float scaleHeight = mFgBitmap.getHeight() * 1.0f / mBgBitmap.getHeight();
 
        // 为了保证图片能够等比例缩放, 而不是宽/高会被拉伸, 这里要取得相对小的那个值
        float scale = Math.min(scaleWidth, scaleHeight);
 
        // 通过矩阵进行缩放
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // 得到新的图片
        mBgBitmap = Bitmap.createBitmap(mBgBitmap, 0, 0, mBgBitmap.getWidth(), mBgBitmap.getHeight(), matrix,
                true);
        mCanvas = new Canvas(mFgBitmap);
        mCanvas.drawColor(Color.GRAY);
    }
 
    private void init() {
        mPaint = new Paint();
        // 透明度 不设置为255 即可
        mPaint.setAlpha(0);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mPaint.setStyle(Paint.Style.STROKE);
        // 设置结合处的形状为圆角
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(50);
        // 设置结尾处的形状为圆角
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPath = new Path();
    }
 
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(event.getX(), event.getY());
                break;
 
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                break;
        }
        invalidate();
        return true;
    }
 
    @Override
    protected void onDraw(Canvas canvas) {
        // 第一个矩形区域表示要画的bitmap的大小
        Rect bgBtimapRect = new Rect(0, 0, mBgBitmap.getWidth(), mBgBitmap.getHeight());
        // 为了使得bitmap绘制在view的正中心, 这里计算一下起始的x, y的值
        int left = viewWidth / 2 - mBgBitmap.getWidth() / 2;
        int top = viewHeight / 2 - mBgBitmap.getHeight() / 2;
        // 第二个矩形区域表示bitmap要绘制的区域
        Rect bgBitmapStartRect = new Rect(left, top,
                mBgBitmap.getWidth() + left, mBgBitmap.getHeight() + top);
        canvas.drawBitmap(mBgBitmap, bgBtimapRect, bgBitmapStartRect, null);
        canvas.drawBitmap(mFgBitmap, 0, 0, null);
        mCanvas.drawPath(mPath, mPaint);
    }
}
