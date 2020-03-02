# MoveViewDemo

~~~ java
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MoveView extends View {

    private int lastX;

    private int lastY;

    public MoveView(Context context) {
        super(context);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算偏移量
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                 //使用layout方法重新放置它的位置
//                layout(
//                        getLeft() + offsetX,
//                        getTop() + offsetY,
//                        getRight() + offsetX,
//                        getBottom() + offsetY
//                );

                //对left right进行偏移
                offsetLeftAndRight(offsetX);
                //对top bottom进行偏移
                offsetTopAndBottom(offsetY);
                break;
            default:
                break;
        }
		//消耗事件
        return true;
    }
}

~~~