#!/usr/bin/groovy
def call(ArtifactoryRepo,repoName,targetArtifactoryRepo) {
	echo "In DeployArtifact method"
	if (EnvSelect == 'none'){
            echo EnvSelect
            echo 'Not Deploying'
        }else {
        	checkDeployDirectoryExists(repoName)
		    copyJarConfigfile(repoName)
		 
		    sh """
		        ssh -q weblogic@dpvmrsrva${EnvSelect} 'cd rest-web-services/apps && ./launcher.sh ${ServiceToBuild} stop'
		        ssh -q weblogic@dpvmrsrva${EnvSelect} 'cd rest-web-services/apps && ./launcher.sh ${ServiceToBuild} status'
		        sleep 5s
		        ssh -q weblogic@dpvmrsrva${EnvSelect} 'cd rest-web-services/apps && ./launcher.sh ${ServiceToBuild} start'
		        ssh -q weblogic@dpvmrsrva${EnvSelect} 'cd rest-web-services/apps && ./launcher.sh ${ServiceToBuild} status'
		    """
        }
}