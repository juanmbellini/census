cd client/target
tar -xvf census-client-1.0-SNAPSHOT-bin.tar.gz
chmod 777 census-client-1.0-SNAPSHOT/run-client.sh
cd census-client-1.0-SNAPSHOT
./run-client.sh $*
