def call(){
    if (fileExists("selenium/src")){
        try {
    		dir('selenium') {
    	    	//editLastGitlabComment('+ Starting Navigation Tests')
    			sh 'bash test_launcher.sh'
    			allure includeProperties: false, jdk: '', results: [[path: 'selenium/src/allure-results']]
    			//junit 'src/allure-results/test_result.xml' TODO check this
    			if (currentBuild.currentResult == Result.UNSTABLE.toString()) {
    				echo 'WARNING: Build is unstable'
					//editLastGitlabComment('+ Build is unstable')
    	    		//updateGitlabStatus('failed')
    			} else {
    		       	//editLastGitlabComment('+ Navigation Tests passed')
    			}
    		}
    	} catch (error) {
    		//editLastGitlabComment('+ Exception at Navigation Tests')
    	}
    } else {
    	echo 'INFO: Directory selenium not exist, the test will not execute'
    	//editLastGitlabComment('+ Directory selenium does not exist, omitting selenium tests')
    }
}