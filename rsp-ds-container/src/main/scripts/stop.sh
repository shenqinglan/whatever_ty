#!/bin/sh
kill -9 `ps -ef | grep -v grep | grep euicc-ds-container | awk '{print $2}'`