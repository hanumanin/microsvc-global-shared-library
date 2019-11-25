#!/usr/bin/groovy
def call(ArtifactoryRepo,repoName) {
    echo "In GenerateConfigFiles method"
    echo EnvSelect
    
    if (EnvSelect == 'none'){
        echo 'Not Deploying'
    } else {
        echo EnvSelect
        def DBCap = ""
        if (EnvSelect == 'sitci'){
            echo "in if"
            DBCap = "SCI"
        }else{
            echo "in else"
            DBCap = EnvSelect.toUpperCase()
        }
        
        def CapEnv = EnvSelect.toUpperCase()
        def dbpass = "MSVC_DB_CLFY_${CapEnv}"
        def otaDbPass = "otadb_pswd_${EnvSelect}"
        echo "otaDbPass : ${otaDbPass}"

        withCredentials([string(credentialsId: 'MSVC_Auris_NT', variable: 'auris_net10'), 
            string(credentialsId: 'MSVC_Auris_SM', variable: 'auris_simplemobile'), 
            string(credentialsId: 'MSVC_Auris_TF', variable: 'auris_tracfone'), 
            string(credentialsId: 'MSVC_Nuleef', variable: 'nuleef'),
            string(credentialsId: 'ofs_db_pwd', variable: 'ofsDb_pwd'),
            string(credentialsId: 'hyla_param_password', variable: 'hyla_param_password'),
            string(credentialsId: 'hyla_token_password', variable: 'hyla_token_password'),
            string(credentialsId: 'avalara_apiKey', variable: 'avalara_apiKey'),
            string(credentialsId: 'MSVC_3ci_user', variable: 'ci_user'),
            string(credentialsId: 'MSVC_3ci_pwd', variable: 'ci_pwd'),
            string(credentialsId: "${dbpass}", variable: 'db_clfy_pass'),
            string(credentialsId: "${otaDbPass}", variable: 'ota_db_pass'),
            string(credentialsId: 'MSVC_IGN', variable: 'intergate')]){
            db_port = getDBPort()
            otaDb_port = getOTADBPort()
            sh "echo ${db_port}"
        
            sh """ 

                sed -i 's/{{redis.evn.master}}/${EnvSelect}master/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{redis.evn.nodes}}/dpvm${EnvSelect}redis.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{redis.env.master}}/${EnvSelect}master/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{redis.env.nodes}}/dpvm${EnvSelect}redis.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{redis.master}}/${EnvSelect}master/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{redis.nodes}}/dpvm${EnvSelect}redis.tracfone.com:26379,dpvm${EnvSelect}redis.tracfone.com:26380/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{db.host}}/devdb.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{db.port}}/${db_port}/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{db.name}}/CLFY${DBCap}/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{db.user}}/soacbo/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{esbgateway}}/${EnvSelect}esbgateway/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{esbout}}/sit1esbout.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{jms.tm.host}}/dp-${EnvSelect}-jms1.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{jms.att.host}}/dp-${EnvSelect}-jms1.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{jms.verizon.host}}/dp-${EnvSelect}-jms1.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{jms.sprint.host}}/dp-${EnvSelect}-jms1.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{jms.claro.host}}/dp-${EnvSelect}-jms1.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{jms.pcrf.host}}/dp-${EnvSelect}-jms1.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{jms.pcrf.response.host}}/dp-${EnvSelect}-jms1.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{jms.ppe.host}}/dp-${EnvSelect}-jms1.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{jms.ubi.sms.host}}/dp-${EnvSelect}-jms1.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{avalara.clientId}}/956/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{avalara.clientProfileId}}/2/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{hyla.grant.type}}/password/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{hyla.param.user}}/tracfone_uat/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{hyla.token.username}}/tracfone/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{otadb.host}}/devdbapps.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{otadb.port}}/${otaDb_port}/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{otadb.name}}/OTA${DBCap}/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{otadb.user}}/rtc/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{jms.qualtrics-host}}/dp-${EnvSelect}-jms2.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{jms.qualtrics-port}}/16100/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{jms.qualtrics-threadcount}}/25/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{jms.qualtrics-retrymax}}/3/g' ./config/${ServiceToBuild}/application.yaml
                
                sed -i 's,{{jms.ubi.sms.url}},t3://dp-${EnvSelect}-jms1.tracfone.com:15100,g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's,{{event-mgmt-customer-action-pega-listener.pega.service.url}},http://sit1esbout.tracfone.com/stream/RTEEvents,g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{jms.tradein-host}}/dp-${EnvSelect}-jms2.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                
                sed -i 's/{{ofs-db.host}}/dsvmofs.tracfone.com/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{ofs-db.port}}/1526/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{ofs-db.name}}/OFSDEV/g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's/{{ofs-db.user}}/apps/g' ./config/${ServiceToBuild}/application.yaml
                
                sed -i 's,{{otadb.pswd}},${ota_db_pass},g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's,{{hyla.param.password}},${hyla_param_password},g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's,{{hyla.token.password}},${hyla_token_password},g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's,{{ofs-db.pswd}},${ofsDb_pwd},g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's,{{intergate.network.pswd}},${intergate},g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's,{{db.pswd}},${db_clfy_pass},g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's,{{ild.nuleef.pswd}},${nuleef},g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's,{{ild.auris.net10.pswd}},${auris_net10},g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's,{{ild.auris.tracfone.pswd}},${auris_tracfone},g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's,{{ild.auris.simple_mobile.pswd}},${auris_simplemobile},g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's,{{avalara.apiKey}},${avalara_apiKey},g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's,{{3ci.user}},${ci_user},g' ./config/${ServiceToBuild}/application.yaml
                sed -i 's,{{3ci.pswd}},${ci_pwd},g' ./config/${ServiceToBuild}/application.yaml
               
            """
            }  
            downloadArtifact(ArtifactoryRepo,repoName)
    }
}
