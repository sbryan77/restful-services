#!/bin/sh -e

#
# FIXME Later - some of this data could be extrapolated. 
# 							This is for ease of prototyping for now.
#
artifactpath=${artifactpath}
artifactzip=${artifactzip}
zipversion=${zipversion}
dockerimageversion=${dockerimageversion}
# This is used to find the correct context file
target=${target}
contextpath=${contextpath}
workspace=${WORKSPACE}

# Quick verification that we have the data we need.
#
# FIXME Later - Add validation on the input data.


# Create a base empty dir that we are going to use as our docker build context.
# Explicitly copy over just what we need into the image.
# This way we know we're not picking up files when we don't mean to and bloating the image.
#
mkdir ${workspace}/dockerbuild
cp -r ${workspace}/build/* ${workspace}/dockerbuild
cd ${workspace}/dockerbuild/aws/docker/tomcat/monitoring

# If it is a public service, delete the normal tomcat-users.xml file (used for is/iws)
# And rename the public tomcat users file to "tomcat-users.xml"
#
if [[ "${contextpath}" == "default-public" ]]; then
    rm tomcat-users.xml
    rm tomcat-users-pgui.xml
    mv tomcat-users-public.xml tomcat-users.xml
elif [[ "${contextpath}" == "defualt-pgui" ]]; then
    rm tomcat-users.xml
    rm tomcat-users-public.xml
    mv tomcat-users-pgui.xml tomcat-users.xml
else 
    rm tomcat-users-pgui.xml
    rm tomcat-users-public.xml
fi

# Output some of our env values just for debugging
#
pwd
echo ${artifactpath}
echo ${artifactzip}
echo ${zipversion}
echo ${dockerimageversion}
echo ${target}
echo ${contextpath}
echo ${WORKSPACE}

# copy the context file related to our application into the base dir 
# so it is picked up in the docker image.
#
cp ../context-files/${contextpath}/context.xml .

# Dynamically add the service name in the Dockerfile.
#
# We could also just generate a new Dockerfile with each release, but then you can't really see the base contents of it easily.
#
sed -i "s|REPLACEWITHARTIFACTPATH|$artifactpath|g" Dockerfile
sed -i "s|REPLACEWITHARTIFACTZIP|$artifactzip|g" Dockerfile
sed -i "s|REPLACEWITHCONTEXTPATH|$appcontextpath|g" Dockerfile

# FIXME Later 
# Add these later when we're running this from jenkins.
#
# Add some values to the docker image from the jenkins build.
# This way we know who built this image and have a link back to the jenkins job that produced it.
#
sed -i "s|REPLACEWITHBUIILDUSER|$USER|g" Dockerfile
sed -i "s|REPLACEWITHBUIILDURL|$BUILD_URL|g" Dockerfile

# Create the docker image
#
docker build -t ${target} .

# Tag the docker image 
#
docker tag ${target} artifactory.viasat.com:8108/${target}:${zipversion}.${dockerimageversion}

# Push tagged docker image to Artifactory
#
docker push artifactory.viasat.com:8108/${target}:${zipversion}.${dockerimageversion}

