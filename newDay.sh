#! /bin/bash

currentYear=`date +%Y`
currentDay=`date +%d`
mkdir -p src/main/kotlin/y${currentYear}/d${currentDay}
mkdir -p src/test/kotlin/y${currentYear}/d${currentDay}
cp src/test/kotlin/template/*.kt src/test/kotlin/y${currentYear}/d${currentDay}
