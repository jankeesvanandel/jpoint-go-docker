# -*- mode: ruby -*-
# vi: set ft=ruby :

# Vagrantfile API/syntax version. Don't touch unless you know what you're doing!
VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  # All Vagrant configuration is done here. The most common configuration
  # options are documented and commented below. For a complete reference,
  # please see the online documentation at vagrantup.com.
  config.vm.hostname = "localhost"

  config.vm.post_up_message =
  " === The following are useful commands within the guest ===

    # SSH into guest (as user vagrant):
        vagrant ssh
    # Switch to 'go' user:
        sudo su go

    # Start go-server and go-agent (already started by default):
        service go-server start && service go-agent start
    # Start docker service (already started by default):
        service docker start

    # Start bash in docker ubuntu image:
        docker run -i -t ubuntu /bin/bash
    # List (running) docker images:
        docker ps -a
    # Build the Dockerfile in /mnt/share/docker (this will take a while)
        docker build --tag my_ubuntu-openjdk-7 /mnt/share/docker
    # Run the docker image you built using the previous command
        docker run -i -t my_ubuntu-openjdk-7
  "

  # Use the ubuntu/trusty64 Vagrant box as a starting point
  # config.vm.box = "ubuntu/trusty64"

  # To prevent overloading of the network, same image, downloaded image locally using prepare.sh
  config.vm.box = "trusty-server-cloudimg-amd64-vagrant-disk1.box"

  # Disable automatic box update checking.
  # Updates to boxes will then only be checked for updates when the user runs `vagrant box outdated`.
  config.vm.box_check_update = false

  # Create a forwarded port mapping which allows access to a specific port
  # within the machine from a port on the host machine.
  # i.e. accessing "localhost:8153" will access port 8153 on the guest machine.
  config.vm.network "forwarded_port", guest: 8153, host: 8153
  config.vm.network "forwarded_port", guest: 8154, host: 8154

  # Create a private network, which allows host-only access to the machine using a specific IP.
  # config.vm.network "private_network", ip: "192.168.33.10"

  # Create a public network, which generally matches to bridged network.
  # Bridged networks make the machine appear as another physical device on your network.
  # config.vm.network "public_network"

  # If true, then any SSH connections made will enable agent forwarding.
  # Default value: false
  # config.ssh.forward_agent = true

  # Share an additional folder to the guest VM.
  # The first argument is the path on the host to the actual folder.
  # The second argument is the path on the guest to mount the folder.
  # And the optional third argument is a set of non-required options.
  # config.vm.synced_folder "../data", "/vagrant_data"
  config.vm.synced_folder "./share", "/mnt/share", owner: "vagrant", group: "vagrant", mount_options: ["dmode=775,fmode=664"]

  config.vm.provider "virtualbox" do |v|
    v.memory = 2048
    v.cpus = 2
  end

  config.vm.provision "shell" do |shell|
    shell.path = "install.sh"
  end
end
