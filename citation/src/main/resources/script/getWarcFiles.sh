#!/bin/bash

progname=$(basename $0)
if [[ $# != 2 ]] ; then
    echo "Uasage: $progname [start index] [end index]"
    exit 1
fi

re='^[0-9]+$'
start=$1
end=$2

if ! [[ $start =~ $re && $end =~ $re ]]; then
    echo "Uasage: $progname [start index] [end index]"
    echo "        Both start index and end index have to be non-negative integers"
    exit 1
fi

if [[ $start -gt $end ]]; then
    echo "Uasage: $progname [start index] [end index]"
    echo "        Start index has to be less than or equal to end index"
    exit 1
fi

server="https://aws-publicdatasets.s3.amazonaws.com"
path="/common-crawl/crawl-data/CC-MAIN-2015-40/segments/1443736672328.14/warc"

for n in $(seq -f "%05g" $start $end)
{
    file="${server}${path}/CC-MAIN-20151001215752-${n}-ip-10-137-6-227.ec2.internal.warc.gz"
    wget --no-check-certificate $file 
}

echo "All Done"
