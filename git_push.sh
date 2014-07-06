#!/bin/sh

########################################################################
# ALWAYS run this script instead 'git push'
# it will create OurOAuthParams.java without my registered keys and 
# secrets
# muquit@muquit.com Jul-05-2014 
########################################################################
echo ": Generating dummy OAuth parameters"
ant prepare-before-push
git commit -a -m "push to github. replaced oauth parameters with dummy data"
git push
