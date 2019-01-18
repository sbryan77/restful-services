FROM artifactory.viasat.com:8131/denverit-mw-docker:mw-tomcat8.0.41c
MAINTAINER "Lisa Henderson <Lisa.Henderson@viasat.com>"

# Label the base image used for patching inventory queries
LABEL viasat.baseimage="mw-tomcat8.0.41c"

# Add Managers
WORKDIR /apps/jee/tomcat/webapps/
RUN curl -k https://artifactory.viasat.com/artifactory/denverit-mw-software/managers.8.0.41.tgz | tar -zxf -

#####################################################################
# MOVE OUT TO A DOCKER BASE LAYER? Or have Middleware review this.
# 
# Just adding things to our base layer for now so that we can deploy and test our apps in docker.
# Change the FROM line above to incorporate changes to the base layer.
#
# We assume that these files being added are in the context path where you are running your docker build
#
ADD ojdbc6.jar-11.2.0.1.0.jar /apps/jee/tomcat/lib/

ADD jssecacerts /apps/jee/tomcat/

# Add and unzip the TCS version that we want.
# FIXME later - parameterize the version of TCS we are using (if we end up leaving it in the docker image)
#
# FIXME - optimize this one one line.
# Instead of the ADD and run command, do one line to grab from artifactory and unzip to save a 9.2 MB layer in the docker image
#ADD InternalService-TomcatContainerStatus-WAR-1.2.0-004-dist.zip /apps/jee/tomcat
#RUN cd /apps/jee/tomcat  && unzip InternalService-TomcatContainerStatus-WAR-1.2.0-004-dist.zip

RUN cd /apps/jee/tomcat && \
wget --quiet --no-cookies https://artifactory.viasat.com/artifactory/DenverITDev-maven/com/viasat/internalservice/tomcatcontainerstatus/InternalService-TomcatContainerStatus-WAR/1.2.0-005/InternalService-TomcatContainerStatus-WAR-1.2.0-005-dist.zip && \
unzip InternalService-TomcatContainerStatus-WAR-1.2.0-005-dist.zip && \
rm InternalService-TomcatContainerStatus-WAR-1.2.0-005-dist.zip

RUN mkdir /apps/jee/tomcat/TomcatContainerStatus
ADD instance.properties /apps/jee/tomcat/TomcatContainerStatus

# FIXME - Do we have different tomcat-users files for private and public services?
#         If so, take that into account here
ADD tomcat-users.xml /apps/jee/tomcat/conf/

#
# We copy the correct context.xml over for this target app first in the BuildDockerImage script
#
ADD context.xml /apps/jee/tomcat/conf/

#####################################################################
# APPLICATION SPECIFIC LAYER
#
# If we had app-specific tomcat files, we would put them here to override the base layer.
#

#
# Change name of the TCS .xml file to change the context path of TCS so it is under the app context path.
# appcontext/TCS
#
RUN cd /apps/jee/tomcat/conf/Catalina/localhost && \
mv InternalService-TomcatContainerStatus#v1##01.02.00-005.xml REPLACEWITHCONTEXTPATH#TCS##01.02.00-005.xml

#
# Change name of the manager folder so the context path of manager is under the app context path.
# appcontext/manager
#

RUN cd /apps/jee/tomcat/webapps && \
mv manager REPLACEWITHCONTEXTPATH#manager

# Add a reference to the renamed manager folder in order to finalize context path changes for manager
RUN cd /apps/jee/tomcat/conf/Catalina/localhost && \
echo '<Context path="/manager" docBase="../webapps/REPLACEWITHCONTEXTPATH#manager" antiResourceLocking="false" privileged="true" />' >> 'REPLACEWITHCONTEXTPATH#manager.xml'

#
# Get our application zip from artifactory, add it, unzip it.
#
# FIXME - Remove it?
#
RUN cd /apps/jee/tomcat && \
wget --quiet --no-cookies https://artifactory.viasat.com/artifactory/DenverITDev-maven/REPLACEWITHARTIFACTPATH/REPLACEWITHARTIFACTZIP && \
unzip REPLACEWITHARTIFACTZIP && \
rm REPLACEWITHARTIFACTZIP

RUN mkdir /apps/jee/tomcat/webapps/ROOT && echo "<HTML> <HEAD> <TITLE> Health Checker </TITLE> </HEAD> </BODY> </HTML>" > /apps/jee/tomcat/webapps/ROOT/index.html

#
# Add some labels from jenkins to link the docker image to the build user and jenkins url that created it.
#
LABEL viasat.build.user="REPLACEWITHBUIILDUSER"
LABEL viasat.build.url="REPLACEWITHBUIILDURL"
