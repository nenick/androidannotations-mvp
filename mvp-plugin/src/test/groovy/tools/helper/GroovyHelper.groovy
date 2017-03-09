package tools.helper

import com.google.common.base.StandardSystemProperty
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.util.GradleVersion

trait GroovyHelper {

    GradleRunner runner(String gradleVersion, String... args) {
        return GradleRunner.create()
                .withProjectDir(dir.root)
                .withDebug(true) // always run inline to save memory, especially on CI
                .forwardOutput()
                .withTestKitDir(getTestKitDir())
                .withArguments(args.toList())
                .withGradleVersion(gradleVersion ?: GradleVersion.current().version)
    }

    BuildResult runWithVersion(String gradleVersion, String... args) {
        runner(gradleVersion, args).build()
    }

    BuildResult run(String... args) {
        runner(null, args).build()
    }

    File getTestKitDir() {
        def gradleUserHome = System.getenv('GRADLE_USER_HOME')
        if (!gradleUserHome) {
            gradleUserHome = new File(System.getProperty('user.home'), '.gradle').absolutePath
        }
        return new File(gradleUserHome, 'testkit')
    }

    File getLocalRepo() {
        def rootRelative = new File('build/localrepo')
        return rootRelative.directory ? rootRelative : new File(new File(StandardSystemProperty.USER_DIR.value()).parentFile, 'build/localrepo')
    }
}