# ContentProvider工作原理

## 定义ContentProvide的App1

定义一个MyContentProvider，并在AndroidManifest.xml中声明。

``` xml
 <application>
    <!-- 省略... -->
    <provider
        android:authorities="von_provider"
        android:name=".MyContentProvider"
        android:exported="true" />
</application>
```

``` java
public class MyContentProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        // 省略代码
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, 
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        // 省略代码
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // 省略代码
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // 省略代码
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, 
                      @Nullable String[] selectionArgs) {
        // 省略代码
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, 
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        // 省略代码
    }
}

```

#### 使用ContentProvide的App2

``` java
public class TestActivity extends Activity {
    ContentResolver contentResolver;
    Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ...
        uri = Uri.parse("content://von_provider/");
        contentResolver = getContentResolver();
    }

    private void delete() {
        int count = contentResolver.delete(uri, "delete_where", null);
        Log.i("test", String.format("delete uri:%s", count));
    }

    private void insert() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("put_key", 1);
        Uri newUri = contentResolver.insert(uri, contentValues);
        Log.i("test", String.format("insert uri:%s", newUri));
    }

    private void update() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("put_key", 1);
        int count = contentResolver.update(uri, contentValues, "update_where", null);
        Log.i("test", String.format("update uri:%s", count));
    }
}

```

首先，我们看一下ContentResolver的增删改查这 4个方法的底层实现，其实都是和AMS 通信，最终调用App1的 ContentProvider的增删改查4个方法，后面我们会讲到这个流程是怎么样的 。
其次，URI是ContentProvider的唯一标识。我们在App1中为ContentProvider声明URI，也就是authorities的值为von_provider,
那么在 App2 中想使用它，就在 ContentResolver的增删改查 4个方法中指定 URI，格式为:
uri = Uri.parse(”content://von_provider/”);
接下来把两个App都进入debug模式，就可以从 App2 调试进入 App1了，比如，query 操作 。
