#!/bin/bash
time echo "The quick brown fox" | curl -v -X POST -d @- http://localhost:8080/filter-transformer-example
time echo "The quick brown fox" | curl -v -X POST -d @- http://localhost:8081/filter-transformer-example
