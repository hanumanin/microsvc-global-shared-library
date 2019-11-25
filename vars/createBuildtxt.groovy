#!/usr/bin/groovy
def call(dir) {
	echo "In CreateBuildtxt method"
    sh """
        echo ${BranchtoBuild} > build.txt
        echo ${BUILD_NUMBER} >> build.txt
        cat build.txt
        jar uf ${dir}${ServiceToBuild}/target/${ServiceToBuild}*.jar build.txt
    """
}