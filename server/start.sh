#!/bin/sh

#===========================================================================================
# init
#===========================================================================================
export SERVER="server"
export JAVA="$JAVA_HOME/bin/java"
# 获取当前目录
export BASE_DIR=`cd $(dirname $0)/.; pwd`
# 默认加载路径
export DEFAULT_SEARCH_LOCATIONS="classpath:/,classpath:/config/,file:./,file:./config/"
# 自定义默认加载配置文件路径
export CUSTOM_SEARCH_LOCATIONS=${DEFAULT_SEARCH_LOCATIONS},file:${BASE_DIR}/conf/

#===========================================================================================
# JVM Configuration
#===========================================================================================
JAVA_OPT="${JAVA_OPT} -server -Xms512m -Xmx512m -Xmn256 -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"
JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${BASE_DIR}/logs/java_heapdump.hprof"
JAVA_OPT="${JAVA_OPT} -XX:-UseLargePages"
JAVA_OPT="${JAVA_OPT} -jar ${BASE_DIR}/${SERVER}-*.jar"
JAVA_OPT="${JAVA_OPT} --spring.config.location=${CUSTOM_SEARCH_LOCATIONS}"
# 创建日志文件目录
if [ ! -d "${BASE_DIR}/logs" ]; then
  mkdir ${BASE_DIR}/logs
fi

# 输出变量
echo "$JAVA ${JAVA_OPT}"
# 检查start.out日志输出文件
if [ ! -f "${BASE_DIR}/logs/${SERVER}.log" ]; then
  touch "${BASE_DIR}/logs/${SERVER}.log"
fi
#===========================================================================================
# 启动服务
#===========================================================================================
# 启动服务
echo "$JAVA ${JAVA_OPT}" > ${BASE_DIR}/logs/${SERVER}.log 2>&1 &
nohup $JAVA ${JAVA_OPT} hero_web.hero_web >> ${BASE_DIR}/logs/${SERVER}.log 2>&1 &
echo "server is starting，you can check the ${BASE_DIR}/logs/${SERVER}.log"
