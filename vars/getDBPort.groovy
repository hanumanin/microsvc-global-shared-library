#!/usr/bin/groovy
def call() {
	echo "In GetDBPort method"
	if (EnvSelect == 'sit1'){
        return 3011
    }else if(EnvSelect == 'sita'){
        return 3015
    }else if(EnvSelect == 'sitb'){
        return 3013
    }else if(EnvSelect == 'sitci'){
        return 3012
    }else if(EnvSelect == 'sitd'){
        return 7731
    }else if(EnvSelect == 'site'){
        return 7741
    }else if(EnvSelect == 'sitf'){
        return 6014
    }else if(EnvSelect == 'sitg'){
        return 6021
    }else if(EnvSelect == 'sitz'){
        return 6016
    }else {
        echo "Not Valid"
    }
}