#!/usr/bin/groovy
def call() {
    echo "In MavenBuild method"
	def dir=''
    res = sh(script: "ls | grep -q  'rest-web-services' && echo '1' || echo '0' ", returnStdout: true).trim()
    if(res=='1'){
        dir='rest-web-services/'
    } else {
        dir=''
    }

    withCredentials([usernameColonPassword(credentialsId: 'ArtifactoryCredentials', variable: 'ArtifactoryCredentials')]) {
        def mvnHome = tool 'Maven'
        withEnv( ["PATH+MAVEN=${mvnHome}/bin"] ) {
        echo mvnHome

        sh """
            mvn clean install -Dmaven.test.failure.ignore=true -P ${ServiceToBuild} -f ${dir}pom.xml -Dmaven.repo.remote=http://${ArtifactoryCredentials}@dpvmjartaprd1:8081/artifactory/tf-generic
        """}
    }

    echo "GIT URL: ${env.GIT_URL}"

    saveConfigFiles(dir)
    createBuildtxt(dir)
}