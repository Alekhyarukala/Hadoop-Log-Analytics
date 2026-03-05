# Hadoop Log Analytics using MapReduce

This project analyzes web server log files using Hadoop MapReduce.

## Technologies
- Hadoop
- MapReduce
- Java
- HDFS

## Features
1. Page Visit Analysis
   Counts how many times each webpage is accessed.

2. Error Code Analysis
   Identifies HTTP status codes such as 200, 404, and 500.

3. Most Active IP Address
   Detects which IP address generates the most traffic.

## Dataset
Sample web server log file used for analysis.

Example log format:
192.168.1.1 - - [12/Mar/2025:10:00:00] "GET /home HTTP/1.1" 200 1024

## Output Examples

Page Visits:
