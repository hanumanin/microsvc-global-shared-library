#!/usr/bin/groovy
def call(ArtifactoryRepo,repoName,targetArtifactoryRepo) {
	echo "In UploadArtifact method"
    def dir=''
    res = sh(script: "ls | grep -q  'rest-web-services' && echo '1' || echo '0' ", returnStdout: true).trim()
    if(res=='1'){
        dir='rest-web-services/'
    } else {
        dir=''
    }

    def server = Artifactory.server "Artifactory_TF"
    def buildInfo = Artifactory.newBuildInfo()
    buildInfo.env.capture = true
    buildInfo.env.collect()
    
    def uploadSpec = """{
        "files": [
            {
            "pattern": "${dir}${ServiceToBuild}/target/${ServiceToBuild}*.jar",
            "target": "${ArtifactoryRepo}/TF_Builds/${repoName}/${BranchtoBuild}/Build-${BUILD_NUMBER}/",
            "props" : "flag=Build_Success"
            }, {
            "pattern": "${dir}${ServiceToBuild}/target/${ServiceToBuild}*.zip",
            "target": "${ArtifactoryRepo}/TF_Builds/${repoName}/${BranchtoBuild}/Build-${BUILD_NUMBER}/",
            "props" : "flag=Build_Success"
            }, {
            "pattern": "${dir}${ServiceToBuild}/target/${ServiceToBuild}*.war",
            "target": "${ArtifactoryRepo}/TF_Builds/${repoName}/${BranchtoBuild}/Build-${BUILD_NUMBER}/",
            "props" : "flag=Build_Success"
            }, {
            "pattern": "./config/${ServiceToBuild}/*.xml",
            "target": "${ArtifactoryRepo}/TF_Builds/${repoName}/${BranchtoBuild}/Build-${BUILD_NUMBER}/${ServiceToBuild}-config/",
            "props" : "flag=Build_Success"
            }, {
            "pattern": "config/${ServiceToBuild}/*.yaml",
            "target": "${ArtifactoryRepo}/TF_Builds/${repoName}/${BranchtoBuild}/Build-${BUILD_NUMBER}/${ServiceToBuild}-config/",
            "props" : "flag=Build_Success"
            }, {
            "pattern": "config/${ServiceToBuild}/*.txt",
            "target": "${ArtifactoryRepo}/TF_Builds/${repoName}/${BranchtoBuild}/Build-${BUILD_NUMBER}/${ServiceToBuild}-config/",
            "props" : "flag=Build_Success"
            }
        ]   
        }"""
    // Upload to Artifactory.
    //server.upload(uploadSpec)
    server.upload spec: uploadSpec, buildInfo: buildInfo
    
    /*buildInfo.retention maxBuilds: 10, maxDays: 7, deleteBuildArtifacts: true*/
    // Publish build info.
    //server.publishBuildInfo(buildInfo)
    server.publishBuildInfo buildInfo

    statusMessage="Build Successful " 
    updateArtifactoryStatus(statusMessage,ArtifactoryRepo,targetArtifactoryRepo)
}