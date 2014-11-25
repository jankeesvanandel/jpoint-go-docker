#!/bin/bash

apt-get update

echo "Installing Java7, Git, SVN and Maven"
apt-get install -y openjdk-7-jdk git subversion maven


[ -e /etc/apt/sources.list.d/go-cd.list ] || {
  echo "Installing Go Apt Repository"
  echo "deb http://dl.bintray.com/gocd/gocd-deb /" >> /etc/apt/sources.list.d/go-cd.list
  apt-get update
}

echo "Installing Go Server and Go Agent"
apt-get install -y --force-yes go-server go-agent

echo "Go server operations:"
echo "   service go-server start|status|stop"
echo "Go agent operations:"
echo "   service go-agent start|status|stop"

[ -e /var/lib/go-agent/config/autoregister.properties ] || {
  echo "Go agent auto register enable"

  echo "Stopping go-agent"
  service go-agent stop

  echo "Stopping go-server"
  service go-server stop

  echo "agent.auto.register.key=999b633a88de126531afa41eff9aa69e" >> /var/lib/go-agent/config/autoregister.properties
  echo "agent.auto.register.resources=ant,java" >> /var/lib/go-agent/config/autoregister.properties
  echo "agent.auto.register.environments=QA" >> /var/lib/go-agent/config/autoregister.properties

  echo "Installing xmlstarlet"
  apt-get install -y xmlstarlet

  echo "Use xmlstarlet to add agentAutoRegisterKey attribute"
  cp /etc/go/cruise-config.xml /etc/go/cruise-config.backup.xml
  xmlstarlet ed -L -P --append /cruise/server -t attr -n agentAutoRegisterKey -v 999b633a88de126531afa41eff9aa69e /etc/go/cruise-config.xml
}

echo "Restarting go-server"
service go-server start

echo "Sleeping to allow go-server to finish startup (to prevent connection refused errors in go-agent)"
sleep 30s

echo "Restarting go-agent"
service go-agent start


[ -e /usr/lib/apt/methods/https ] || {
  echo "Docker repository uses SSL"
  apt-get install -y apt-transport-https
}

[ -e /etc/apt/sources.list.d/docker.list ] || {
  echo "Installing docker repository"
  echo "deb https://get.docker.com/ubuntu docker main" >> /etc/apt/sources.list.d/docker.list
  apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 36A1D7869245C8950F966E92D8576A8BA88D21E9
  apt-get update
  echo "Installing docker"
  apt-get install -y --force-yes lxc-docker
}

echo "Add the users go and vagrant to the docker group"
gpasswd -a go docker
gpasswd -a vagrant docker

echo "Add the user go to the vagrant group"
gpasswd -a go vagrant

if ! grep -q "Non-localhost DNS Server" /etc/default/docker; then
  echo "# Non-localhost DNS Server" >> /etc/default/docker
  echo "DOCKER_OPTS=\"--dns 8.8.8.8 --dns 8.8.4.4\"" >> /etc/default/docker
fi

echo "Restart the Docker daemon"
service docker restart
sleep 10s

echo "Verify docker using the following command with user 'go' or 'vagrant'"
echo "   docker run -i -t ubuntu /bin/bash"

echo "Done"
exit 0