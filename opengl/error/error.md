# error


## bingTextureImage：clearing gl error 0x500

根据错误码，是不支持OES类型的纹理导致；报的警告是这个SurfaceTexture.UpdateTexImage()函数处理的，但是并不是这个函数有问题，而是前一次调用opengl的渲染函数（一堆）时产生的，它在这里做了清除（所以叫clearing）。所以查错集中在渲染部分。

~~~ java
    @Override
    public void onDrawFrame(GL10 gl) {
            // 此处导致 clearing gl error 0x500
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                GL10.GL_FASTEST);
    }
~~~

修改方式

``` java
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // todo something
        // 移动到此处，初始化调用一次即可
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                GL10.GL_FASTEST);
    }
```