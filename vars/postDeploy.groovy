#!/usr/bin/groovy
def call(ArtifactoryRepo,repoName,targetArtifactoryRepo) {
	echo "In PostDeploy method"
	if (EnvSelect == 'none'){
            echo EnvSelect
            echo 'Not Deploying'
        }else {
		    updatePropertyflag(ArtifactoryRepo,repoName)
		    status = "${EnvSelect}".toString() 
		    statusMessage="Deployed to "+status 
		    updateArtifactoryStatus(statusMessage,ArtifactoryRepo,targetArtifactoryRepo)
        }
}