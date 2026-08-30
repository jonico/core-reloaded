#!/bin/bash
# Install the jars that are not on Maven Central into the local repository.
# These are the proprietary participant SDKs (TeamForge, ScrumWorks, TFS, JIRA, CEE,
# SourceForge) plus openadaptor 3.4 and a few oddly-built or unversioned OSS jars whose
# bytes match nothing on Central. Coordinates are synthetic (ccf.vendored:<name>:0)
# precisely because there is nothing upstream to track.
set -euo pipefail
cd "$(dirname "$0")/.."
: "${JAVA_HOME:?set JAVA_HOME to a JDK 8}"
REPO_ARG=""
[ -n "${MAVEN_REPO_LOCAL:-}" ] && REPO_ARG="-Dmaven.repo.local=$MAVEN_REPO_LOCAL"

echo "==> activation"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/core/lib/openadaptor-3.4/3rdparty/activation.jar" \
    -DgroupId=ccf.vendored -DartifactId=activation -Dversion=0 -Dpackaging=jar
echo "==> commons-vfs-1.0"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/core/lib/openadaptor-3.4/3rdparty/commons-vfs-1.0.jar" \
    -DgroupId=ccf.vendored -DartifactId=commons-vfs-1.0 -Dversion=0 -Dpackaging=jar
echo "==> jaxen-1.1-beta-9"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/core/lib/openadaptor-3.4/3rdparty/jaxen_1.1-beta-9.jar" \
    -DgroupId=ccf.vendored -DartifactId=jaxen-1.1-beta-9 -Dversion=0 -Dpackaging=jar
echo "==> jericho-html-3.1"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/core/lib/openadaptor-3.4/3rdparty/jericho-html-3.1.jar" \
    -DgroupId=ccf.vendored -DartifactId=jericho-html-3.1 -Dversion=0 -Dpackaging=jar
echo "==> jmock-core-1.2.0"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/core/lib/openadaptor-3.4/3rdparty/jmock-core-1.2.0.jar" \
    -DgroupId=ccf.vendored -DartifactId=jmock-core-1.2.0 -Dversion=0 -Dpackaging=jar
echo "==> jmxtools"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/core/lib/openadaptor-3.4/3rdparty/jmxtools.jar" \
    -DgroupId=ccf.vendored -DartifactId=jmxtools -Dversion=0 -Dpackaging=jar
echo "==> mail"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/core/lib/openadaptor-3.4/3rdparty/mail.jar" \
    -DgroupId=ccf.vendored -DartifactId=mail -Dversion=0 -Dpackaging=jar
echo "==> openadaptor-spring"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/core/lib/openadaptor-3.4/openadaptor-spring.jar" \
    -DgroupId=ccf.vendored -DartifactId=openadaptor-spring -Dversion=0 -Dpackaging=jar
echo "==> openadaptor"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/core/lib/openadaptor-3.4/openadaptor.jar" \
    -DgroupId=ccf.vendored -DartifactId=openadaptor -Dversion=0 -Dpackaging=jar
echo "==> cee-5.0"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/plugins/CEE/ProjectTracker/v50/lib/cee-5.0.jar" \
    -DgroupId=ccf.vendored -DartifactId=cee-5.0 -Dversion=0 -Dpackaging=jar
echo "==> jira-rest-java-client-1.0"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/plugins/JIRA/lib/jira-rest-java-client-1.0.jar" \
    -DgroupId=ccf.vendored -DartifactId=jira-rest-java-client-1.0 -Dversion=0 -Dpackaging=jar
echo "==> sf-soap44-sdk"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/plugins/SFEE/v44/lib/sf_soap44_sdk.jar" \
    -DgroupId=ccf.vendored -DartifactId=sf-soap44-sdk -Dversion=0 -Dpackaging=jar
echo "==> scrumworks-soap"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/plugins/SWP/lib/ScrumWorks-soap.jar" \
    -DgroupId=ccf.vendored -DartifactId=scrumworks-soap -Dversion=0 -Dpackaging=jar
echo "==> sf-soap50-sdk"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/plugins/TF/lib/sf_soap50_sdk.jar" \
    -DgroupId=ccf.vendored -DartifactId=sf-soap50-sdk -Dversion=0 -Dpackaging=jar
echo "==> sf-soap60-sdk"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/plugins/TF/lib/sf_soap60_sdk.jar" \
    -DgroupId=ccf.vendored -DartifactId=sf-soap60-sdk -Dversion=0 -Dpackaging=jar
echo "==> tfapi"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/plugins/TF/lib/tfapi.jar" \
    -DgroupId=ccf.vendored -DartifactId=tfapi -Dversion=0 -Dpackaging=jar
echo "==> com.microsoft.tfs.sdk-10.1.0"
mvn -B -q $REPO_ARG install:install-file -Dfile="src/plugins/TFS/lib/com.microsoft.tfs.sdk-10.1.0.jar" \
    -DgroupId=ccf.vendored -DartifactId=com.microsoft.tfs.sdk-10.1.0 -Dversion=0 -Dpackaging=jar
echo "==> done"
