package tools.helper

import tools.builder.ActivityBuilder
import tools.builder.AndroidManifestBuilder
import tools.builder.GradleScriptBuilder
import tools.builder.LayoutBuilder
import tools.builder.base.ClassBuilder

trait AndroidFileHelper implements FileHelper {

    void createAndroidManifest(String projectId, AndroidManifestBuilder androidManifest) {
        file('src/main/AndroidManifest.xml') << androidManifest.build(projectId)
    }

    void createBuildScript(String projectId, GradleScriptBuilder gradleScript) {
        file('build.gradle') << gradleScript.build(projectId)
    }

    void createLayout(String projectId, LayoutBuilder layout) {
        file("src/main/res/layout/${layout.name}.xml") << layout.build(projectId)
    }

    void createActivity(String projectId, ActivityBuilder activity) {
        file("src/main/java/${projectId.replace(".", "/")}/${activity.name}.java") << activity.build(projectId)
    }

    void createClass(String projectId, ClassBuilder clazz) {
        file("src/main/java/${projectId.replace(".", "/")}/${clazz.name}.java") << clazz.build(projectId)
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