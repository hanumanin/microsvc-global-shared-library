#!/usr/bin/groovy
def call() {
        echo "In CleanUp method"
        sh 'ls -al /home/svcjenk/stage_artifact'

        if (fileExists("/home/svcjenk/stage_artifact/${ServiceToBuild}/")) {
            echo 'stage_artifact directory exists'
            sh 'rm -rf /home/svcjenk/stage_artifact/${ServiceToBuild}/*'
            sh 'mkdir -p /home/svcjenk/stage_artifact/${ServiceToBuild}/config/'
        } else {
            echo 'Config folder does not exist'
            sh 'mkdir -p /home/svcjenk/stage_artifact/${ServiceToBuild}/config/'
        }

        if(!fileExists("./config/${ServiceToBuild}")){
            sh 'mkdir -p config/${ServiceToBuild}'
        }
        sh 'rm -rf ./config/${ServiceToBuild}/*'
        sh 'ls -al ./config'
    
}