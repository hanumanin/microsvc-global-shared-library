#!/usr/bin/groovy
def call() {
	echo "In BuildDesc method"
	try {
		if (BuildDesc=="None") {
	        echo 'No Build Description provided'
	        currentBuild.description = env.ServiceToBuild+":"+env.EnvSelect+":"+env.BranchtoBuild
	    } else {
	        currentBuild.description = env.ServiceToBuild+":"+env.EnvSelect+":"+env.BranchtoBuild+":"+env.BuildDesc
	    } 
	}
	catch (err) {
        echo "Exception: ${err}"
        throw err
    }	
}

def othermethodTest() {
	echo "In other method"
	
}
