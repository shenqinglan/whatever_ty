#!/bin/sh
kill -9 `ps -ef | grep -v grep | grep euicc-dp-container | awk '{print $2}'`