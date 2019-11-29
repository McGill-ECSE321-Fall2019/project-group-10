#!/bin/bash

if grep -q 'person' ./prediction.txt; then
    echo found > prediction_results.txt
else
    echo not found > prediction_results.txt
fi


