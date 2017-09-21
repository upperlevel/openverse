#!/bin/sh
java -Dcom.sun.management.jmxremote.port=9005 \
     -Dcom.sun.management.jmxremote.authenticate=false \
     -Dcom.sun.management.jmxremote.ssl=false  \
     -Xverify:none \
     -XX:+DoEscapeAnalysis \
     -jar launcher.jar
pause