# 绘制透明背景PNG图片出现黑色背景

项目中曾遇到过多次透明图片经过OpenGL渲染之后或者图片颜色偏黑出现色差或者透明区域呈现黑色，不是透明状态。这种情况的出现多是由于glBlendFunc设置的有问题造成的。

其默认的设置是glBlendFunc(GL_ONE, GL_ZERO);

通过glBlendFunc可以设置纹理的Blend方式，其第一个参数代表源因子，第二个参数代表目标因子，这里把将要画上去的颜色称为源颜色，把原来的颜色称为目标颜色，混合出的颜色结果为：源颜色 * 源因子 + 目标颜色 * 目标因子，其可能的值为一下几种：

GL_ZERO：     表示使用0.0作为因子，实际上相当于不使用这种颜色参与混合运算。
GL_ONE：      表示使用1.0作为因子，实际上相当于完全的使用了这种颜色参与混合运算。
GL_SRC_ALPHA：表示使用源颜色的alpha值来作为因子。
GL_DST_ALPHA：表示使用目标颜色的alpha值来作为因子。
GL_ONE_MINUS_SRC_ALPHA：表示用1.0减去源颜色的alpha值来作为因子。

GL_ONE_MINUS_DST_ALPHA：表示用1.0减去目标颜色的alpha值来作为因子。

而我们想要正常渲染出透明纹理的效果则需要设置glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

~~~ java
GLES30.glEnable(GLES30.GL_BLEND);
GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
~~~
