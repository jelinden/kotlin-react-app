#!/bin/bash

# if the is no symlink to client build directory
if ! [ -L build ] ; then
  ln -s client/build build 
fi

cd server &&
./gradlew build -x test &&
cd .. &&
cp server/build/libs/*.jar . #&&
env=prod java -jar kotlin-react-0.0.1.jar

