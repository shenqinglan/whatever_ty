#!/bin/sh
kill -9 `ps -ef | grep -v grep | grep smpp-esme | awk '{print $2}'`