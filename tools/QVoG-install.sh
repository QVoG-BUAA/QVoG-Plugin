#!/bin/bash

# 更新并升级系统
sudo apt-get update && sudo apt-get upgrade -y
apt-get install -y sudo wget unzip gnupg coreutils

# 安装 JDK1.8
echo '===================== Install required JDK1.8 now ==========================='
if java -version 2>&1 | grep -q '1.8'; then
    echo 'JDK 1.8 is already installed.'
else
    echo 'Install required JDK1.8 now'
    sudo apt-get install openjdk-8-jdk -y
fi

# 安装 Neo4j 3.3.9
echo '===================== Install required Neo4j 3.3.9 now ======================'
wget -O - https://debian.neo4j.com/neotechnology.gpg.key | sudo apt-key add -
echo 'deb https://debian.neo4j.com stable legacy' | sudo tee /etc/apt/sources.list.d/neo4j.list
sudo apt-get update
apt list -a neo4j
sudo apt install neo4j=1:3.3.9 -y

# 安装 Gremlin-Server 3.7
echo '===================== Install required Gremlin-Server 3.7 now =============='
wget https://dlcdn.apache.org/tinkerpop/3.7.0/apache-tinkerpop-gremlin-server-3.7.0-bin.zip
GREMLIN_DIR_NAME="apache-tinkerpop-gremlin-server-3.7.0"
unzip ${GREMLIN_DIR_NAME}-bin.zip
cd $GREMLIN_DIR_NAME
bin/gremlin-server.sh install org.apache.tinkerpop neo4j-gremlin 3.7.0

# 配置 Gremlin-Server
echo '==================== Configure Gremlin-Server now =========================='
GREMLIN_SERVER_NEO4J="./conf/gremlin-server-neo4j.yaml"
GREMLIN_SERVER_CONF="./bin/gremlin-server.conf"
sed -i 's/^host: .*/host: 0.0.0.0/' $GREMLIN_SERVER_NEO4J
sed -i 's/^evaluationTimeout: .*/evaluationTimeout: 1000000000/' $GREMLIN_SERVER_NEO4J

cpus=$(($(nproc) * 2))
echo "threadPoolWorker: $cpus" >> $GREMLIN_SERVER_NEO4J

touch $GREMLIN_SERVER_CONF
echo "JAVA_OPTIONS=-Xmx6G" >> $GREMLIN_SERVER_CONF
echo "JAVA_OPTIONS=-Xss16M" >> $GREMLIN_SERVER_CONF

# 嵌入 Neo4j 到 Gremlin-Server
echo '===================== Embed Neo4j into Gremlin-Server now =================='
GREMLIN_SERVER_SH="./bin/gremlin-server.sh"
sed -i 's|GREMLIN_YAML=$GREMLIN_HOME/conf/gremlin-server.yaml|GREMLIN_YAML=$GREMLIN_HOME/conf/gremlin-server-neo4j.yaml|' $GREMLIN_SERVER_SH
