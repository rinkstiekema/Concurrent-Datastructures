#!/bin/bash 

iterateThreads () {
	echo "DATASTRUCTURE $1    WORKTIME $2";
	./bin/test_data_structures $1 1 2000 $2 | grep time &&
	./bin/test_data_structures $1 10 2000 $2 | grep time &&
	./bin/test_data_structures $1 100 2000 $2 | grep time &&
	./bin/test_data_structures $1 1000 2000 $2 | grep time
	echo;
}

iterateWorkTimeAndThreads () {
	echo;
	echo "DATASTRUCTURE $1";
	iterateThreads $1 0;
	iterateThreads $1 1;
	iterateThreads $1 2;
	iterateThreads $1 5;
	iterateThreads $1 10;
	echo;
}

./bin/build.sh && 
echo && {
iterateWorkTimeAndThreads cgl;
iterateWorkTimeAndThreads cgt;
iterateWorkTimeAndThreads fgl;
# iterateWorkTimeAndThreads fgt;
}