package testtool.custom

import testtool.FileHelper

trait AndroidFileHelper implements FileHelper, TestProperties {

    void createGradleSettings() {
        file("settings.gradle") << "rootProject.name = '$androidProjectName'"
    }

    void createSimpleAndroidManifest() {
        file('src/main/AndroidManifest.xml') << """

            <?xml version="1.0" encoding="utf-8"?>
            <manifest xmlns:android="http://schemas.android.com/apk/res/android"
                    package="de.nenick.test.simple" >
            </manifest>

        """.trim()
    }

    void createAndroidManifestWithMainActivity(String projectId) {
        file('src/main/AndroidManifest.xml') << """

            <?xml version="1.0" encoding="utf-8"?>
            <manifest xmlns:android="http://schemas.android.com/apk/res/android"
                    package="${projectId}">
                <application
                        android:allowBackup="true"
                        android:label="Test Lib">
                    <activity
                            android:name="${projectId}.MainActivity_"
                            android:label="Test Lib">
                        <intent-filter>
                            <action android:name="android.intent.action.MAIN"/>
                            <category android:name="android.intent.category.LAUNCHER"/>
                        </intent-filter>
                    </activity>
                </application>
            </manifest>

       """.trim()
    }

    void createBuildScript(String androidPlugin, String projectId, File localRepo) {
        def optionalApplicationId = (androidPlugin == "application") ? "applicationId '${projectId}'" : ""
        file('build.gradle') << """

            buildscript {
                repositories {
                    jcenter()
                }
                dependencies {
                    //classpath 'com.android.tools.build:gradle:$androidPluginVersion'
                    classpath 'com.android.tools.build:gradle:2.1.0'
                    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
                }
            }

            apply plugin: 'com.android.$androidPlugin'
            apply plugin: 'android-apt'

            repositories {
                maven { url "${localRepo.toURI()}" }
                jcenter()
            }

            android {
                compileSdkVersion $compileSdkVersion
                buildToolsVersion '$buildToolsVersion'

                defaultConfig {
                    ${optionalApplicationId}
                    minSdkVersion 16
                    targetSdkVersion $compileSdkVersion
                    versionCode 1
                    versionName '1.0.0'
                    testInstrumentationRunner 'android.support.test.runner.AndroidJUnitRunner'
                }
            }

            dependencies {
                apt "org.androidannotations:androidannotations:4.2.0"
                compile "org.androidannotations:androidannotations-api:4.2.0"

                apt 'de.nenick:androidannotations-mvp:$pluginVersion'
                compile 'de.nenick:androidannotations-mvp-api:$pluginVersion'
            }

        """.trim()
    }

    void createActivityLayout() {
        file('src/main/res/layout/activity_main.xml') << """

            <?xml version="1.0" encoding="utf-8"?>
            <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent">
                <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Hello Groovy!"
                        android:gravity="center"
                        android:textAppearance="?android:textAppearanceLarge" />
            </FrameLayout>

        """.trim()
    }

    void createActivityClass(String projectId) {
        fileFromProject("src/test/groovy/integration/MainActivity.java")
        file("src/main/java/${projectId.replace(".", "/")}/MainActivity.java") << """

            package ${projectId};

            import android.app.Activity;
            import android.os.Bundle;
            import ${projectId}.R;
            import org.androidannotations.annotations.EActivity;
            import de.nenick.androidannotations.plugin.mvp.EMvpPresenter;

            @EMvpPresenter
            @EActivity
            public class MainActivity extends Activity {

                @Override
                public void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_main);
                }
            }

        """.trim()
        //file("src/main/java/${projectId.replace(".", "/")}/MainActivity2.java") << file("src/main/java/${projectId.replace(".", "/")}/MainActivity.java")
    }

    void createInstrumentationTest(String projectId) {
        file("src/androidTest/java/${projectId.replace(".", "/")}/AndroidTest.java") << """

            package ${projectId};

            import android.support.test.runner.AndroidJUnit4;
            import org.junit.Before;
            import org.junit.Test;
            import org.junit.runner.RunWith;
            import org.junit.Assert;

            @RunWith(AndroidJUnit4.class)
            public class AndroidTest {

                @Test
                public void shouldCompile() {
                    Assert.assertTrue(true);
                }
            }

        """.trim()
    }

    abstract File file(String name)
    abstract File fileFromProject(String name)
}