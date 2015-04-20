#!/bin/bash

echo -e "def out(x) puts x end\n `cat $1`" | ruby