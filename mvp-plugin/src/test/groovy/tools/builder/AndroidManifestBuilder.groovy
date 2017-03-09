package tools.builder

import tools.builder.base.Builder

class AndroidManifestBuilder implements Builder {

    List<ActivityBuilder> activities = []

    AndroidManifestBuilder with(ActivityBuilder activity) {
        activities.add(activity)
        this
    }

    @Override
    String build(String projectId) {
        def activityEntries = ""
        if (activities[0]) {
            activityEntries += """

        <activity
                android:name="${projectId}.${activities[0].name}_"
                android:label="${activities[0].name}">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>

        """;
        }

        return """

 <?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
        package="${projectId}">
    <application
            android:allowBackup="true"
            android:label="Test Lib">
        $activityEntries
    </application>
</manifest>

        """.trim()
    }
}
