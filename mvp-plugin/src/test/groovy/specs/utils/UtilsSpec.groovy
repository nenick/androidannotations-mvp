package specs.utils

import de.nenick.androidannotations.plugin.mvp.utils.*
import tools.BaseSpecification

import java.lang.reflect.Constructor
import java.lang.reflect.Modifier

class UtilsSpec extends BaseSpecification {

    def "private constructor"() {
        when:
        def classes = [GeneratedClasses, JClasses, JMethods, JTypeArguments, PluginClasses, PluginLists]
        then:
        classes.each {
            assertPrivateConstructor(it)
        }
    }

    private static void assertPrivateConstructor(Class cls) {
        Constructor constructor = cls.getDeclaredConstructor();
        assert Modifier.isPrivate(constructor.getModifiers());
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}

