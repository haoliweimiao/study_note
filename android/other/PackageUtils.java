
public class PackageUtils {

    /**
     * 通过包名打开其它应用
     * 
     * @param appPackage 包名 com.tencent.mm
     * @param appName    应用名称
     */
    public static void openOtherApp(String appPackage, String appName) {
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent();
        intent = packageManager.getLaunchIntentForPackage(appPackage);
        if (intent == null) {
            Toast.makeText(ActFsdAndJtj.this, String.format("%s%s", appName, "未安装"), Toast.LENGTH_LONG).show();
        } else {
            startActivity(intent);
        }
    }

    /**
     * 卸载其它app
     * 
     * @param appPackageName 包名
     */
    public static void removeOtherApp(String appPackageName) {
        Uri packageUri = Uri.parse("package:" + appPackageName);// applicationInfo.packageName
        Intent intent = new Intent(Intent.ACTION_DELETE, packageUri);
        startActivity(intent);

    }

    public class MyAppInfo {
        private Drawable image;
        private String appName;
        // get set ...
    }

    /**
     * 获取本机安装的app
     * 
     * @param packageManager
     * @return
     */
    public static List<MyAppInfo> scanLocalInstallAppList(PackageManager packageManager) {
        List<MyAppInfo> myAppInfos = new ArrayList<MyAppInfo>();
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                // 过滤掉系统app
                if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                    continue;
                }
                MyAppInfo myAppInfo = new MyAppInfo();
                myAppInfo.setAppName(packageInfo.packageName);
                if (packageInfo.applicationInfo.loadIcon(packageManager) == null) {
                    continue;
                }
                myAppInfo.setImage(packageInfo.applicationInfo.loadIcon(packageManager));
                myAppInfos.add(myAppInfo);
            }
        } catch (Exception e) {
            Log.e(TAG, "获取应用包信息失败");
        }
        return myAppInfos;
    }

}