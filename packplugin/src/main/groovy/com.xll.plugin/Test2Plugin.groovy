package com.xll.plugin;

import org.gradle.api.Plugin
import org.gradle.api.Project

//http://kvh.io/cn/tags/EmbraceAndroidStudio/
//

public class Test2Plugin implements Plugin<Project> {

    void apply(Project project) {

        int DEFAULT_VER_CODE=2;
        String DEFAULT_VER_NAME="2.0"
        String DEFAULT_ENV="TEST_EVN"
        String ONLINE_URL="ONLIN_URL"
        String TEST_URL="TEST_URL"
        String DEV_URL="DEV_URL"



        System.out.println("========================");
        System.out.println("hello gradle plugin! :From Test2Plugin");
        System.out.println("========================");
    }
}