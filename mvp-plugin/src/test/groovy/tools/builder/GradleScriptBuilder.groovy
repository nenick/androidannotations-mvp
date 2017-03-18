package tools.builder

import tools.builder.base.Builder

class GradleScriptBuilder implements Builder {

    String androidPlugin = "application"
    String androidPluginVersion
    String compileSdkVersion
    String buildToolsVersion
    String pluginVersion
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

    dexOptions.preDexLibraries = false
}

dependencies {
    apt "org.androidannotations:androidannotations:4.2.0"
    compile "org.androidannotations:androidannotations-api:4.2.0"

    apt 'de.nenick:androidannotations-mvp:$pluginVersion'
    compile 'de.nenick:androidannotations-mvp-api:$pluginVersion'
}

        """.trim()
    }
}
