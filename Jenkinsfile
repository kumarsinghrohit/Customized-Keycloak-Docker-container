node("application") {

    checkout scm

    def currentBranchName = env.BRANCH_NAME
    def commitId = sh(returnStdout: true, script: 'git rev-parse HEAD')

    // consider correct branch names in case of open pull requests
    if (env.BRANCH_NAME.startsWith('PR')) {
        currentBranchName = env.CHANGE_BRANCH
        // if there are local changes after a merged master we ned the original branch commitId
        def lastCommitByJenkins = sh(returnStdout: true, script: 'git log -1')
        if(lastCommitByJenkins.contains("Author: Jenkins <nobody@nowhere>")){
            commitId = sh(returnStdout: true, script: 'git rev-parse @~1')
        }
    }

    def keycloakDevImage
    def keycloakImage

    stage("deploy") {
        keycloakDevImage = docker.build("my-application/keycloak-dev", "-f Dockerfile.dev .")
        keycloakImage = docker.build("my-application/keycloak", "-f Dockerfile.prod .")
    }

    stage("deploy") {
        if (currentBranchName == 'master') {
            withCredentials([string(credentialsId: 'DOCKER_REPOSITORY_URL', variable: 'DOCKER_REPOSITORY_URL')]) {
                docker.withRegistry("${DOCKER_REPOSITORY_URL}", 'dockerUser') {
                    echo "tagging docker image as master.${commitId}"
                    keycloakImage.push("master.${commitId}")
                    keycloakDevImage.push("master.${commitId}")
                }
            }
        } else {

            // use md5 checksum of branchname as tagname
            def branchRef = sh ( script: "echo -n ${currentBranchName} | md5sum | cut -c 1-32", returnStdout: true).trim();

            withCredentials([string(credentialsId: 'DOCKER_REPOSITORY_STORE_TASKS_URL', variable: 'DOCKER_REPOSITORY_STORE_TASKS_URL')]) {
                docker.withRegistry("${DOCKER_REPOSITORY_STORE_TASKS_URL}", 'dockerUser') {
                    echo "tagging docker image as '${branchRef}.${commitId}'"
                    keycloakDevImage.push("${branchRef}.${commitId}")
                }
            }
        }
    }

}
