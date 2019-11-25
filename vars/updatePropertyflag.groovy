#!/usr/bin/groovy
def call(ArtifactoryRepo,repoName) {
    echo "In UpdatePropertyFlag method"
    def server = Artifactory.server "Artifactory_TF"    
    def setPropsSpec = """ {
            "files" : [
                {
                    "pattern": "${ArtifactoryRepo}/TF_Builds/${repoName}/${BranchtoBuild}/Build-${BUILD_NUMBER}/*",
                    "props"     : "flag=Build_Success"
                    
                }
            ]
                    
        }"""
    server.setProps spec: setPropsSpec, props: "flag=Deployed", failNoOp: true
}