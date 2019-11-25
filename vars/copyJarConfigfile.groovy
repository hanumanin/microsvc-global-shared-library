#!/usr/bin/groovy
def call(repoName) {
    echo "In CopyJarConfigfile method"
	sh """
        ssh -q weblogic@dpvmrsrva${EnvSelect} 'rm -rf /home/weblogic/rest-web-services/apps/${repoName}/${ServiceToBuild}/lib/*'
        ssh -q weblogic@dpvmrsrva${EnvSelect} 'rm -rf /home/weblogic/rest-web-services/apps/${repoName}/${ServiceToBuild}/config/application.yaml'
        ssh -q weblogic@dpvmrsrva${EnvSelect} 'rm -rf /home/weblogic/rest-web-services/apps/${repoName}/${ServiceToBuild}/config/version.txt'
        ssh -q weblogic@dpvmrsrva${EnvSelect} 'rm -rf /home/weblogic/rest-web-services/apps/${repoName}/${ServiceToBuild}/config/log4j2.xml'

        scp -q /home/svcjenk/stage_artifact/${ServiceToBuild}/${ServiceToBuild}*.jar weblogic@dpvmrsrva${EnvSelect}:/home/weblogic/rest-web-services/apps/${repoName}/${ServiceToBuild}/lib/ 
        scp -q ./config/${ServiceToBuild}/application.yaml weblogic@dpvmrsrva${EnvSelect}:/home/weblogic/rest-web-services/apps/${repoName}/${ServiceToBuild}/config/
        scp -q /home/svcjenk/stage_artifact/${ServiceToBuild}/config/version.txt weblogic@dpvmrsrva${EnvSelect}:/home/weblogic/rest-web-services/apps/${repoName}/${ServiceToBuild}/config/
        scp -q /home/svcjenk/stage_artifact/${ServiceToBuild}/config/log4j2.xml weblogic@dpvmrsrva${EnvSelect}:/home/weblogic/rest-web-services/apps/${repoName}/${ServiceToBuild}/config/
        scp -q ./build.txt weblogic@dpvmrsrva${EnvSelect}:/home/weblogic/rest-web-services/apps/${repoName}/${ServiceToBuild}/config/
        ssh -q weblogic@dpvmrsrva${EnvSelect} 'cd /home/weblogic/rest-web-services/apps/${repoName}/${ServiceToBuild}/ && chmod -R 744 ./lib'
    """
}