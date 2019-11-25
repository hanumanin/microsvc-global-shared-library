#!/usr/bin/groovy
def call(repoName) {
    echo "In CheckDeployDirectoryExists method"

    sh """
        echo ${EnvSelect}
        ssh -q weblogic@dpvmrsrva${EnvSelect} "[ -d "/home/weblogic/rest-web-services/apps/${repoName}/${ServiceToBuild}/config" ] && echo "Directory /home/weblogic/rest-web-services/apps/${repoName}/${ServiceToBuild}/config exists." || { mkdir -p /home/weblogic/rest-web-services/apps/${repoName}/${ServiceToBuild}/config  && echo "Directory config created"; } "
        ssh -q weblogic@dpvmrsrva${EnvSelect} "[ -d "/home/weblogic/rest-web-services/apps/${repoName}/${ServiceToBuild}/lib" ] && echo "Directory /home/weblogic/rest-web-services/apps/${repoName}/${ServiceToBuild}/lib exists." || { mkdir -p /home/weblogic/rest-web-services/apps/${repoName}/${ServiceToBuild}/lib  && echo "Directory lib created"; } "
        """
}