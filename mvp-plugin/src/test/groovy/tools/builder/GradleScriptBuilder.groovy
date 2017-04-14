package tools.builder

import tools.builder.base.Builder

class GradleScriptBuilder implements Builder {

    String androidPlugin = "application"
    String androidPluginVersion
    String compileSdkVersion
    String buildToolsVersion
    String androidApt
    String pluginVersion
    String androidAnnotations
    String androidAnnotationsApi
    File localRepo

    @Override
    String build(String projectId) {
        String optionalApplicationId = (androidPlugin == "application") ? "applicationId '${projectId}'" : ""
        return """

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:$androidPluginVersion'
        classpath $androidApt
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

    dexOptions.preDexLibraries = false
}

dependencies {
    apt $androidAnnotations
    compile $androidAnnotationsApi

    apt 'de.nenick:androidannotations-mvp:$pluginVersion'
    compile 'de.nenick:androidannotations-mvp-api:$pluginVersion'
}

        """.trim()
    }
}
