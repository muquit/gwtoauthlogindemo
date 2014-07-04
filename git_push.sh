#!/bin/sh

########################################################################
# ALWAYS run this script instead 'git push'
########################################################################
echo ": Generating dummy OAuth parameters"
ant prepare-before-push
# git push
