#!/usr/bin/groovy
def call(ArtifactoryRepo,repoName) {
    echo "In DownloadArtifact method"

    def server = Artifactory.server "Artifactory_TF"
    def buildInfo = Artifactory.newBuildInfo()
    buildInfo.env.capture = true
    buildInfo.env.collect()
    
    def downloadFileSpec = """ {
        "files": [
            {
                "pattern": "${ArtifactoryRepo}/TF_Builds/${repoName}/${BranchtoBuild}/Build-${BUILD_NUMBER}/*",
                "target": "/home/svcjenk/stage_artifact/${ServiceToBuild}/",
                "recursive": "false",
                "flat" : "true",
                "build" : "${buildInfo.name}/${buildInfo.number}"
                
            },{
                "pattern": "${ArtifactoryRepo}/TF_Builds/${repoName}/${BranchtoBuild}/Build-${BUILD_NUMBER}/${ServiceToBuild}-config/*",
                "target": "/home/svcjenk/stage_artifact/${ServiceToBuild}/config/",
                "recursive": "false",
                "flat" : "true",
                "build" : "${buildInfo.name}/${buildInfo.number}"
                
            }
            ]
    }"""    
    server.download spec: downloadFileSpec

    echo "${buildInfo.name} ${buildInfo.number}"
}