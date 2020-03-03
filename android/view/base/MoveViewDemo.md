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

## 动画改变位置

~~~ xml
<?xml version="1.0" encoding="utf-8"?>
<!-- 添加android:fillAfter="true"，动画执行完成，View不会返回原先位置 -->
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:fillAfter="true">
    <translate
        android:duration="1000"
        android:fromXDelta="0"
        android:toXDelta="300" />
</set>
~~~

~~~ java
//View动画并不能改变View的位置参数
view.setAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_move_x));
~~~

~~~ java
//使用属性动画移动
ObjectAnimator.ofFloat(view, "translationX", 0, 300).setDuration(1000).start();
~~~

## scroll

~~~ java
        mBinding.tv.post(()->{
			//View现有位置再次移动
            mBinding.tv.scrollBy(-30, -30);
			//将View移动到10，10
            mBinding.tv.scrollTo(10, 10);
        });
~~~