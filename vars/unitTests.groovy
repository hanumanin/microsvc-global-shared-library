#!/usr/bin/groovy
def call() {
	echo "In UnitTests method"
	def dir=''
    res = sh(script: "ls | grep -q  'rest-web-services' && echo '1' || echo '0' ", returnStdout: true).trim()
    if(res=='1'){
        dir='rest-web-services/'
    } else {
        dir=''
    }

	junit "${dir}${ServiceToBuild}/target/surefire-reports/*.xml"
}