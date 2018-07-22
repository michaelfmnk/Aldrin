#!/bin/bash

pushd `dirname $0` > /dev/null;
ROOT_DIR=`pwd`'/..';
popd > /dev/null;
mkdir ${ROOT_DIR}/dist/lang