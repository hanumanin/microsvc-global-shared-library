#!/usr/bin/groovy
def call(String buildStatus = 'STARTED',StageName) {
    echo "In EmailNotify method"
	//build status of null = successful
		if("${JOB_NAME}" ==~ /(Sandbox)\/(.+)$/) {
            echo env.BUILD_USER_EMAIL
            emailNotifyList = env.BUILD_USER_EMAIL
        } else {
            emailNotifyList = 'ITCoreLeads@tracfone.com, Services@tracfone.com, DevSystemsSupport@tracfone.com, DevOps@tracfone.com, ProductionSystemsSupport@tracfone.com, PMODistribution@tracfone.com, ProductionControl@tracfone.com'
        }

        def emailNotifyList1 = "${emailNotifyList}"
        echo "emailNotifyList: ${emailNotifyList1}"
        buildStatus =  buildStatus ?: 'SUCCESS'
        def CurrentUser = env.BUILD_USER
        echo CurrentUser

            if(buildStatus == "SUCCESS" || buildStatus == "UNSTABLE") {
                def subject = "${buildStatus} Job Notification: "
                def details = """<p>Service:  ${ServiceToBuild}<BR>
                
                Build #${BUILD_NUMBER} <BR> 
                Deployed to ${EnvSelect} <BR> 
                Branch: ${BranchtoBuild}<BR> 
                JOB: ${JOB_NAME}<BR>
                Executed By: ${CurrentUser} <BR>
                <p>Quick links to the details:
                <ul>
                    <li><a href="${env.JOB_URL}">Job main page</a></li>
                    <li><a href="${env.BUILD_URL}">Build ${env.BUILD_NUMBER} main page</a></li>
                </ul></p>"""

                
                
                emailext (
                    subject: subject,
                    body: details, 
                    to: emailNotifyList1
                    )

            } else {
                def subject = "${buildStatus} Job Notification: "
                def details = """<p>Service:  ${ServiceToBuild}<BR>
                
                Build #${BUILD_NUMBER} ${StageName} Stage FAILED<BR>
                Target env: ${EnvSelect} <BR> 
                Branch: ${BranchtoBuild}<BR> 
                JOB: ${JOB_NAME}<BR>
                Executed By: ${CurrentUser} <BR>
                <p>Quick links to the details:
                <ul>
                    <li><a href="${env.JOB_URL}">Job main page</a></li>
                    <li><a href="${env.BUILD_URL}">Build ${env.BUILD_NUMBER} main page</a></li>
                </ul></p>"""

                
                
                emailext (
                    subject: subject,
                    body: details, 
                    to: emailNotifyList1
                    )

            }
}