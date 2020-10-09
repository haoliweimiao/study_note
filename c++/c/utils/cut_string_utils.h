#ifndef cut_string_utils_h
#define cut_string_utils_h

#ifdef __cplusplus
extern "C"
{
#endif

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

    /**
 * 截取字符串(从左往右开始)
 * @params dest: 返回的字符串
 * @params src : 截取的字符串
 * @params n   : 截取的字符长度
 */
    static char *cutStringFromLeft(char *dest, const char *src, int n)
    {
        int len = strlen(src);
        char copySrc[len];
        memset(copySrc, '\0', 1);
        strcat(copySrc, src);
        char *p = dest;
        char *q = copySrc;

        if (n > len)
        {
            n = len;
        }
        while (n--)
            *(p++) = *(q++);
        *(p++) = '\0';

        return dest;
    }

    /**
 * 截取字符串(从右往左开始)
 * @params dest: 返回的字符串
 * @params src : 截取的字符串
 * @params n   : 截取的字符长度
 */
    static char *cutStringFromRight(char *dest, const char *src, int n)
    {
        int len = strlen(src);
        char copySrc[len];
        memset(copySrc, '\0', 1);
        strcat(copySrc, src);
        char *p = dest;
        char *q = copySrc;

        if (n > len)
        {
            n = len;
        }
        //int start=len-n;
        //q=q+start;
        q += len - n;
        while (n--)
            *(p++) = *(q++);
        *(p++) = '\0';
        return dest;
    }

    /**
 * 截取字符串(从左往右开始)
 * @params dest : 返回的字符串
 * @params src  : 截取的字符串
 * @params start: 开始位置
 * @params n    : 截取的字符长度
 */
    static char *cutString(char *dest, const char *src, char start, int n)
    {
        int len = strlen(src);
        char copySrc[len];
        memset(copySrc, '\0', 1);
        strcat(copySrc, src);
        char *p = dest;
        char *q = copySrc;

        char *temp = NULL;

        if (start >= len || start < 0)
        {
            return NULL;
        }
        temp = q + start;
        if (n > strlen(temp))
        { //注意这里，截取长度如果超过了src剩余的长度则只截取到src的最后，以避免内存越界；
            n = strlen(temp);
        }
        q += start;
        while (n--)
            *(p++) = *(q++);
        *(p++) = '\0';
        return dest;
    }

#ifdef __cplusplus
}
#endif

#endif /* cut_string_utils_h */