JPoint Vagrant Box for GoCD and Docker Testing
==============================================

This Vagrant Box follows steps outlined in the Go CD manual
It makes use of the Vagrant ``ubuntu/trusty64`` image and adds applications on top
so we can simulate a deployment pipeline in which we also make use of Docker containers.


Guest image details
===================

The image is the same as the ubuntu/trusty64 image from [https://vagrantcloud.com/ubuntu/boxes/trusty64]()
modified to have 2GB of RAM and 2 CPUs.

**Port mappings**

* port 8153 to host port 8153
* port 8154 to host port 8154


**Drive mappings**

* ``./share to /mnt/share``


You can use the ``downloadbox.sh`` script to predownload the image. 
If you do that uncomment the line in the Vagrantfile ``config.vm.box = "trusty-server-cloudimg-amd64-vagrant-disk1.box"`` to actually use the image.


Documentation
=============

The Vagrantfile runs the ``install.sh`` script which has followed the installation steps as described on the following pages

* [http://www.go.cd/documentation/user/current/installation/installing_go_agent.html]()
* [http://www.go.cd/documentation/user/current/installation/installing_go_server.html]()
* [http://www.go.cd/documentation/user/current/advanced_usage/agent_auto_register.html]()
* [http://docs.docker.com/installation/ubuntulinux/]()


Useful folder locations
=======================

**go-agent**

* Binaries: ``/var/lib/go-agent/``
* Logs: ``/var/log/go-agent/``
* Scripts: ``/usr/share/go-agent/``
* Environment variables: ``/etc/default/go-agent``

**go-server**

* Binaries: ``/var/lib/go-server/``
* Logs: ``/var/log/go-server/``
* Scripts: ``/usr/share/go-server/``
* Environment variables: ``/etc/default/go-server``
* Server configuration ``/etc/go/``

**Repositories and Workspaces (for use by go-server and go-agent)**

The following locations are mapped to the ``share`` folder in the checkout and are writable by the ``go`` and ``vagrant`` users.

* ``/mnt/share/repos``
* ``/mnt/share/workspaces``


Requirements
============

* Vagrant [https://www.vagrantup.com/]()
* VirtualBox [https://www.virtualbox.org/]()


Running the image
=================

* Clone the repository
* CD to the checkout directory ``cd jpoint-go-docker``
* Bring up the vagrant image (this could take a while) ``vagrant up``
* SSH into the guest as default user vagrant ``vagrant ssh``
* You can change to user ``go`` which runs the Go server ``sudo su go``