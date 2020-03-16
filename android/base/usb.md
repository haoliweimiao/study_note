# USB

+ (获取USB路径)[#获取USB路径]

## 获取USB路径

*需要添加读取权限*

~~~ java
//根路径： /storage/emulated/0 
Log.e("gen", Environment.getExternalStoragePublicDirectory("").getAbsolutePath());
//sdcard路径： /storage/emulated/0 获取路径错误，未连接外部USB报错，无法获取路径
Log.e("sdcard", Environment.getExternalStorageDirectory().getAbsolutePath());
//路径： /storage/emulated/0
Log.e("sdcard", getStoragePath(this,false));
//路径： /storage/46A7-7073 正确
Log.e("sdcard", getStoragePath(this,true));
~~~

~~~ java
    private static String getStoragePath(Context mContext, boolean is_removale) {
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
~~~