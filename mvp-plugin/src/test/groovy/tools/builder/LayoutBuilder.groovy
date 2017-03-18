package tools.builder

import tools.builder.base.Builder

class LayoutBuilder implements Builder {

    String name

    @Override
    String build(String projectId) {
        return """

<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    <TextView
            android:id="@+id/textView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Hello Groovy!"
            android:gravity="center"
            android:textAppearance="?android:textAppearanceLarge" />
</FrameLayout>

        """.trim()
    }
}
