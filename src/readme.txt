启动kafka集群:
1、切换到kafka安装目录:
    cd /usr/local/kafka_2.13-3.1.0/
2、启动zookeeper:
    nohup bin/zookeeper-server-start.sh config/zookeeper.properties &
3、启动kafka集群:
    nohup bin/kafka-server-start.sh config/server0.properties &
    nohup bin/kafka-server-start.sh config/server1.properties &
    nohup bin/kafka-server-start.sh config/server2.properties &