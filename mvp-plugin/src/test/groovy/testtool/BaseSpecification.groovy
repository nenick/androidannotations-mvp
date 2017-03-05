package testtool

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

abstract class BaseSpecification extends Specification {

    @Rule
    TemporaryFolder dir
}