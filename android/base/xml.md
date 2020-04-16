# XML相应方法

+ [根据id查询名称](#根据id查询名称)

## 根据id查询名称

~~~ java

// 获取本应用：
CharSequence name=getResources().getResourceEntryName(artistView.getId());



// 获取其它应用：
Context packageContext = mContext.createPackageContext(currentPackageName,
                            Context.CONTEXT_RESTRICTED);
CharSequence name = packageContext.getResources()
                            .getResourceEntryName(tmpView.getId());
~~~