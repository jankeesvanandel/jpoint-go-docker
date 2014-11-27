#!/bin/bash

echo "Predownloading Vagrant box"
curl -L https://cloud-images.ubuntu.com/vagrant/trusty/current/trusty-server-cloudimg-amd64-vagrant-disk1.box -o trusty-server-cloudimg-amd64-vagrant-disk1.box
echo "Done"

exit 0
