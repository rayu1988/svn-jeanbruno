#!/bin/bash
mkindex.sh  #generate index/files.html files
sumcheck.sh #generate checksums for above files
mkindex.sh  #generate index files to include any new checksums
archive.sh  #generate download packages

mkindex.sh  #generate index files to include download packages
sumcheck.sh #generate checksums for download packages
mkindex.sh  #generate index files to include new checksums for download packages
sumcheck.sh #generate checksums for download package indexes