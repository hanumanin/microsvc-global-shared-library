#!/usr/bin/groovy
def call(dir) {
	echo "In SaveConfigFiles method"
	sh """
        echo "Generating version.txt"
        cat ${dir}${ServiceToBuild}/pom.xml | grep -A 1  "<artifactId>${ServiceToBuild}.*" | sed -n '/version/,/version/p' | sed -e 's/.*<version>//'| sed -e 's,</version>.*,,' > ./config/${ServiceToBuild}/version.txt

        echo "Copying log4j2.xml"
        cp -r ${dir}${ServiceToBuild}/src/main/resources/log4j2.xml config/${ServiceToBuild}/

        echo "Generating application.yaml"
        cp -r ${dir}${ServiceToBuild}/src/main/resources/application.yaml config/${ServiceToBuild}/
        """ 
}