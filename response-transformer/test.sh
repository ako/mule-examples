#!/bin/bash
echo "<hello>World</echo>" | curl -X POST -d @- http://localhost:11110/response-transformer-example
