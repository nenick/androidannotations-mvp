package integration

import testtool.IntegrationSpecification

class AndroidApplicationSpec extends IntegrationSpecification {

    def "run androidTests with post execution hook"() {
        given:
        setupCompilableApplicationProject()
        blub()

        when:
        run(assembleDebugTask)

        then:
        assert file("build/outputs/androidTest-results/connected").length() > 0
        assert file("build/reports/androidTests/connected/index.html").exists()
        assert file("build/outputs/code-coverage/connected/coverage.ec").exists()
    }
}