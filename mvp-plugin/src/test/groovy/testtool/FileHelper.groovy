package testtool

import org.gradle.testfixtures.ProjectBuilder
import org.junit.rules.TemporaryFolder

trait FileHelper {

    File makeFile(String path) {
        def f = file(path)
        if (!f.exists()) {
            def parts = path.split("/")
            if (parts.size() > 1) {
                dir.newFolder(*parts[0..-2])
            }
            dir.newFile(path)
        }
        return f
    }

    File file(String path) {
        def file = new File(dir.root, path)
        assert file.parentFile.mkdirs() || file.parentFile.exists()
        return file
    }

    File fileFromProject(String path) {
        def file = new File("../", path)
        assert file.parentFile.mkdirs() || file.parentFile.exists()
        return file
    }

    /**
     * Helper method for copy a test project into home directory for debugging.
     */
    void copyTestDir() {
        def project = ProjectBuilder.builder().withProjectDir(dir.root).build()
        project.copy {
            from dir.root
            into new File(System.getProperty("user.home"), "testoutput-${new Date().format('yyyyMMddHHmm')}")
        }
    }

     abstract TemporaryFolder getDir()
}