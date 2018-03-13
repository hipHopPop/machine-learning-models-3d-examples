To enable 3d grid visuals, 
- clone https://github.com/jzy3d/jzy3d-api.git
- build 
-- mvn clean compile package -Dmaven.test.skip=true
- install to your local repository using below commands
-- replace ~ with your local path where https://github.com/jzy3d/jzy3d-api.git is cloned

mvn deploy:deploy-file -DgroupId=org.jzy3d -DartifactId=jzy3d-jdt-core -Dversion=1.0.2-SNAPSHOT -Dpackaging=jar -Dfile="~\jzy3d-api\jzy3d-jdt-core\target\jzy3d-jdt-core-1.0.2-SNAPSHOT.jar" -DgeneratePom=false
		
mvn deploy:deploy-file -DgroupId=org.jzy3d -DartifactId=jzy3d-api -Dversion=1.0.2-SNAPSHOT -Dpackaging=jar -Dfile="~\jzy3d-api\jzy3d-api\target\jzy3d-api-1.0.2-SNAPSHOT.jar" -DgeneratePom=false

mvn deploy:deploy-file -DgroupId=org.jzy3d -DartifactId=jzy3d-tester -Dversion=1.0.2-SNAPSHOT -Dpackaging=jar -Dfile="~\jzy3d-api\jzy3d-tester\target\jzy3d-tester-1.0.2-SNAPSHOT.jar" -DgeneratePom=false
 
mvn deploy:deploy-file -DgroupId=org.jzy3d -DartifactId=jzy3d-depthpeeling -Dversion=1.0.2-SNAPSHOT -Dpackaging=jar -Dfile="~\jzy3d-api\jzy3d-depthpeeling\target\jzy3d-depthpeeling-1.0.2-SNAPSHOT.jar" -DgeneratePom=false
 
mvn deploy:deploy-file -DgroupId=org.jzy3d -DartifactId=jzy3d-javafx -Dversion=1.0.2-SNAPSHOT -Dpackaging=jar -Dfile="~\jzy3d-api\jzy3d-javafx\target\jzy3d-javafx-1.0.2-SNAPSHOT.jar" -DgeneratePom=false
 
mvn deploy:deploy-file -DgroupId=org.jzy3d -DartifactId=jzy3d-tools-libsvm -Dversion=1.0.2-SNAPSHOT -Dpackaging=jar -Dfile="~\jzy3d-api\jzy3d-tools-libsvm\target\jzy3d-tools-libsvm-1.0.2-SNAPSHOT.jar" -DgeneratePom=false

mvn deploy:deploy-file -DgroupId=org.jzy3d -DartifactId=jzy3d-svm-mapper -Dversion=1.0.2-SNAPSHOT -Dpackaging=jar -Dfile="~\jzy3d-api\jzy3d-svm-mapper\target\jzy3d-svm-mapper-1.0.2-SNAPSHOT.jar" -DgeneratePom=false

mvn deploy:deploy-file -DgroupId=org.jzy3d -DartifactId=jzy3d-swt -Dversion=1.0.2-SNAPSHOT -Dpackaging=jar -Dfile="~\jzy3d-api\jzy3d-swt\target\jzy3d-swt-1.0.2-SNAPSHOT.jar" -DgeneratePom=false
 
mvn deploy:deploy-file -DgroupId=org.jzy3d -DartifactId=jzy3d-tutorials -Dversion=1.0.2-SNAPSHOT -Dpackaging=jar -Dfile="~\jzy3d-api\jzy3d-tutorials\target\jzy3d-tutorials-1.0.1.jar" -DgeneratePom=false

//mvn deploy:deploy-file -DgroupId=org.jzy3d -DartifactId=jzy3d-api -Dversion=1.0.2-SNAPSHOT -Dpackaging=jar -Dfile="~\jzy3d-api\jzy3d-api\target\jzy3d-api-1.0.2-SNAPSHOT-maths-io.jar" -DgeneratePom=false
