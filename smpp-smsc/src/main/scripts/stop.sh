#!/bin/sh
kill -9 `ps -ef | grep -v grep | grep smpp-smsc | awk '{print $2}'`