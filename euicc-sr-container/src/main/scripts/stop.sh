#!/bin/sh
kill -9 `ps -ef | grep -v grep | grep euicc-sr-container | awk '{print $2}'`