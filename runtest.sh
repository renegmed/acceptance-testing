JAVA=java
JAVA_OPTS="-mx1024M -Xdebug -Xnoagent -Djava.compiler=NONE"

for i in `ls lib/*`
   do
      temp=${temp}":"${i}
      done
      export APP_CLASSPATH=$temp

STATUS=10
echo $JAVA $JAVA_OPTS \
               -classpath $APP_CLASSPATH  \
               com.nature.testing.Main 

$JAVA $JAVA_OPTS \
               -classpath "$APP_CLASSPATH" \
               com.nature.testing.Main 

