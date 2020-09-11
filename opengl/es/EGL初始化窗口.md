# EGL初始化窗口

~~~ c
GLboolean ESUTIL_API
esCreateWindow(ESContext *esContext, const char *title, GLint width, GLint height, GLuint flags) {
#ifndef __APPLE__
    EGLConfig config;
    EGLint majorVersion;
    EGLint minorVersion;
    EGLint contextAttribs[] = {EGL_CONTEXT_CLIENT_VERSION, 3, EGL_NONE};

    if (esContext == NULL) {
        return GL_FALSE;
    }

#ifdef ANDROID
    // For Android, get the width/height from the window rather than what the
    // application requested.
    esContext->width = ANativeWindow_getWidth(esContext->eglNativeWindow);
    esContext->height = ANativeWindow_getHeight(esContext->eglNativeWindow);
#else
    esContext->width = width;
    esContext->height = height;
#endif

    if (!WinCreate(esContext, title)) {
        return GL_FALSE;
    }

    esContext->eglDisplay = eglGetDisplay(esContext->eglNativeDisplay);
    if (esContext->eglDisplay == EGL_NO_DISPLAY) {
        // unable to open connection to local windowing system
        return GL_FALSE;
    }

    // Initialize EGL
    if (!eglInitialize(esContext->eglDisplay, &majorVersion, &minorVersion)) {
        EGLint error = eglGetError();
        if (error == EGL_BAD_DISPLAY) {
            // display 没有指定有效的EGLDisplay
        } else if (error == EGL_NOT_INITIALIZED) {
            // 如果EGL不能初始化
        }
        // unable to initialize EGL; handle and recover
        return GL_FALSE;
    }

    {
        EGLint numConfigs = 0;
        EGLint attribList[] =
                {
//                        EGL_RED_SIZE, 5,
//                        EGL_GREEN_SIZE, 6,
//                        EGL_BLUE_SIZE, 5,
                        EGL_RED_SIZE, 8,
                        EGL_GREEN_SIZE, 8,
                        EGL_BLUE_SIZE, 8,
                        EGL_ALPHA_SIZE, (flags & ES_WINDOW_ALPHA) ? 8 : EGL_DONT_CARE,
						// 请求深度缓冲区，不开启 glEnable(GL_DEPTH_TEST); 无效
                        EGL_DEPTH_SIZE, (flags & ES_WINDOW_DEPTH) ? 8 : EGL_DONT_CARE,
						// 请求模版缓冲区
                        EGL_STENCIL_SIZE, (flags & ES_WINDOW_STENCIL) ? 8 : EGL_DONT_CARE,
                        EGL_SAMPLE_BUFFERS, (flags & ES_WINDOW_MULTISAMPLE) ? 1 : 0,
                        // if EGL_KHR_create_context extension is supported, then we will use
                        // EGL_OPENGL_ES3_BIT_KHR instead of EGL_OPENGL_ES2_BIT in the attribute list
                        EGL_RENDERABLE_TYPE, GetContextRenderableType(esContext->eglDisplay),
                        EGL_NONE
                };

        // Choose config
        if (!eglChooseConfig(esContext->eglDisplay, attribList, &config, 1, &numConfigs)) {
            return GL_FALSE;
        }

        if (numConfigs < 1) {
            return GL_FALSE;
        }
    }


#ifdef ANDROID
    // For Android, need to get the EGL_NATIVE_VISUAL_ID and set it using ANativeWindow_setBuffersGeometry
    {
        EGLint format = 0;
        eglGetConfigAttrib(esContext->eglDisplay, config, EGL_NATIVE_VISUAL_ID, &format);
        ANativeWindow_setBuffersGeometry(esContext->eglNativeWindow, 0, 0, format);
    }
#endif // ANDROID

    // Create a surface
    esContext->eglSurface = eglCreateWindowSurface(esContext->eglDisplay, config,
                                                   esContext->eglNativeWindow, NULL);

    if (esContext->eglSurface == EGL_NO_SURFACE) {
        return GL_FALSE;
    }

    // Create a GL context
    esContext->eglContext = eglCreateContext(esContext->eglDisplay, config,
                                             EGL_NO_CONTEXT, contextAttribs);

    if (esContext->eglContext == EGL_NO_CONTEXT) {
        return GL_FALSE;
    }

    // Make the context current
    if (!eglMakeCurrent(esContext->eglDisplay, esContext->eglSurface,
                        esContext->eglSurface, esContext->eglContext)) {
        return GL_FALSE;
    }

#endif // #ifndef __APPLE__

    return GL_TRUE;
}
~~~


## EGL Config 属性



| 属性 | 数据类型 | 默认值 | 排序优先级 | 选择顺序 | 描述 |
| :-: | :-: | :-: | :-: | :-: | :-: |
| EGL_BUFFER_SIZE | int | 0 | 3 | Smaller value | 颜色缓冲区所有颜色分量的位数 |
| EGL_RED_SIZE | int | 0 | 2 | Larger value | 颜色缓冲区红色分量的位数 |
| EGL_GREEN_SIZE | int | 0 | 2 | Larger value | 颜色缓冲区绿色分量的位数 |
| EGL_BLUE_SIZE | int | 0 | 2 | Larger value | 颜色缓冲区蓝色分量的位数 |
| EGL_LUMINANCE_SIZE | int | 0 | ? | ? | 颜色缓冲区亮度位数 |
| EGL_ALPHA_SIZE | int | 0 | 2 | Larger value | 颜色缓冲区Alpha位数 |
| EGL_ALPHA_MASK_SIZE | int | 0 | ? | ? | 颜色缓冲区Alpha掩码位数 |
| EGL_BIND_TO_TEXTURE_RGB | int | EGL_DONT_CARE | ? | ? | 如果可以绑定到RGB纹理，则为真 |
| EGL_BIND_TO_TEXTURE_RGBA | int | EGL_DONT_CARE | ? | ? | 如果可以绑定到RGBA纹理，则为真 |
| EGL_COLOR_BUFFER_TYPE | int | EGL_RGB_BUFFER | ? | ? | 颜色缓冲区类型: EGL_RGB_BUFFER 或 EGL_LUMINANCE_BUFFER |
| EGL_CONFIG_CAVEAT | enum | EGL_DONT_CARE | 1(first) | Exact value | 和配置相关的任何注意事项 |
| EGL_CONFIG_ID | int | EGL_DONT_CARE | 9 | Exact value | 唯一的EGLConfig标识符值 |
| EGL_CONFORMANT | int | - | ? | ? | 如果用这个EGLConfig创建的上下文兼容，则为真 |
| EGL_DEPTH_SIZE | int | 0 | 6 | Smaller value | 深度缓冲区位数 |
| EGL_LEVEL | int | 0 | - | Equal value | 帧缓冲区级别|
| EGL_MAX_PBUFFER_WIDTH | int | - | ? | ? | 用这个EGLConfig创建的PBuffer的最大宽度 |
| EGL_MAX_PBUFFER_HEIGHT | int | - | ? | ? | 用这个EGLConfig创建的PBuffer的最大高度 |
| EGL_MAX_PBUFFER_PIXELS | int | - | ? | ? | 用这个EGLConfig创建的PBuffer的最大尺寸 |
| EGL_MIN_SWAP_INTERVAL | int | EGL_DONT_CARE | ? | ? | 最小缓冲区交换间隔 |
| EGL_MAX_SWAP_INTERVAL | int | EGL_DONT_CARE | ? | ? | 最大缓冲区交换间隔 |
| EGL_NATIVE_RENDERABLE | Boolean | EGL_DONT_CARE | - | Exact value | 如果原生渲染库可以渲染用到EGLConfig创建的表面，则为真|
| EGL_NATIVE_VISUAL_ID | int | EGL_DONT_CARE | ? | ? | 关于应原生窗口系统可视ID句柄 |
| EGL_NATIVE_VISUAL_TYPE | int | EGL_DONT_CARE | 8 | Exact value | 关于应原生窗口系统可视ID类型 |
| EGL_RENDERABLE_TYPE | int | 0 | ?EGL_OPENGL_ES_BIT | ? | 由EGL_OPENGL_ES_BIT、EGL_OPTENFL_ES2_BIT、EGL_OPTENGL_ES3_KRH(需要EGL_KHR_create_context扩展)、EGL_OPTENFL_BIT或EGL_OPENVG_BIT组成的位掩码，代表配置支持的渲染接口 |
| EGL_SAMPLE_BUFFERS | int | 0 | 4 | Smaller value | 可用多重采样缓冲区数量 |
| EGL_SAMPLES | int | 0 | 5 | Smaller value | 每个像素的样本数量 |
| EGL_STENCIL_SIZE | int | 0 | 7 | Smaller value | 模板缓冲区位数 |
| EGL_SURFACE_TYPE | bitmask | EGL_WINDOW_BIT | - | Mask value | 支持的EGL表面类型，可能是 EGL_WINDOW_BIT、EGL_PIXMAP_BIT、EGL_PBUFFER_BIT、EGL_MUTISAMOLE_RESOLVE_BOX_BIT、EGL_SWAP_BEHAVIOR_PERSERVED_BIT、EGL_VG_COLORSPACE_LINEAR_BIT或EGL_VG_ALPHA_FORMAT_PER_BIT |
| EGL_TRANSPARENT_TYPE | enum | EGL_NONE | - | Exact value | 支持的透明度 |
| EGL_TRANSPARENT_RED_VALUE | int | EGL_DONT_CARE | - | Exact value | 解读为透明的红色值 |
| EGL_TRANSPARENT_GREEN_VALUE | int | EGL_DONT_CARE | - | Exact value | 解读为透明的绿色值 |
| EGL_TRANSPARENT_BLUE_VALUE | int | EGL_DONT_CARE | - | Exact value | 解读为透明的蓝色值 |
