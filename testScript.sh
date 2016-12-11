#!/bin/bash 

#call this script with 1 argument: the number of items

iterateThreads () {
	echo "DATASTRUCTURE $1    WORKTIME $2    ITEMS $3";
	./bin/test_data_structures $1 1 $3 $2 | grep time &&
	./bin/test_data_structures $1 2 $3 $2 | grep time &&
	./bin/test_data_structures $1 4 $3 $2 | grep time &&
	./bin/test_data_structures $1 10 $3 $2 | grep time &&
	./bin/test_data_structures $1 20 $3 $2 | grep time &&
	echo;
}

iterateWorkTimeAndThreads () {
	echo;
	echo "DATASTRUCTURE $1";
	iterateThreads $1 0 $2;
	iterateThreads $1 1 $2;
	iterateThreads $1 2 $2;
	iterateThreads $1 5 $2;
	iterateThreads $1 10 $2;
	echo;
}

./bin/build.sh && 
echo && {
(
	# iterateWorkTimeAndThreads cgl $1;
	# iterateWorkTimeAndThreads cgt $1;
	# iterateWorkTimeAndThreads fgl $1;
	iterateWorkTimeAndThreads fgt $1;
)	| tee testing.txt
}