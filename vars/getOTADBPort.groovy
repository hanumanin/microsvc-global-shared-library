#!/usr/bin/groovy
def call() {
	echo "In GetDBPort method"
	if (EnvSelect == 'sit1'){
        return 6009
    }else if(EnvSelect == 'sita'){
        return 6010
    }else if(EnvSelect == 'sitb'){
        return 6008
    }else if(EnvSelect == 'sitci'){
        return 6007
    }else if(EnvSelect == 'sitd'){
        return 3014
    }else if(EnvSelect == 'site'){
        return 7761
    }else if(EnvSelect == 'sitf'){
        return 6029
    }else if(EnvSelect == 'sitg'){
        return 6030
    }else if(EnvSelect == 'sitz'){
        return 6017
    }else {
        echo "Not Valid"
    }
}