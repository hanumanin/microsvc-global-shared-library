#!/usr/bin/groovy
def call(statusMessage,ArtifactoryRepo,targetArtifactoryRepo) {
    echo "In UpdateArtifactoryStatus method"
    def server = Artifactory.server "Artifactory_TF"
    def buildInfo = Artifactory.newBuildInfo()
    /*buildInfo.env.capture = true
    buildInfo.env.collect()*/
    
    def promotionConfig = [
        // Mandatory parameters
        'buildName'          : buildInfo.name,
        'buildNumber'        : buildInfo.number,
        'targetRepo'         : targetArtifactoryRepo,
        
        // Optional parameters
        'comment'            : statusMessage, 
        'sourceRepo'         : ArtifactoryRepo,
        'status'             : statusMessage,  
        'includeDependencies': true,
        'copy'               : true,
        // 'failFast' is true by default.
        // Set it to false, if you don't want the promotion to abort upon receiving the first error.
        'failFast'           : true
    ]  
    
    // Promote build
    server.promote promotionConfig
}