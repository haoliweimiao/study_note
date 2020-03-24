# DialogFragment Back Key Press Listener

~~~ java
/**
 * 复写onCreateDialog方法
 */
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
    return new Dialog(getActivity(), getTheme()){
        @Override
        public void onBackPressed() {
            super.onBackPressed();
        }
    };
}
~~~