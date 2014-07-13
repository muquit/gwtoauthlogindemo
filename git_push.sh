#!/bin/sh

########################################################################
# ALWAYS run this script instead 'git push'
# it will create OurOAuthParams.java without my registered keys and 
# secrets
# muquit@muquit.com Jul-05-2014 
########################################################################
ARGC=$#
COMMENT="push to github. replaced oauth parameters with dummy data"
if [ $ARGC -eq 1 ]; then
    COMMENT=$1
fi
echo ": Generating dummy OAuth parameters"
echo "Comment: $COMMENT"
ant prepare-before-push
git add src/com/example/GWTOAuthLoginDemo/client/OAuth/OurCallbackUrl.java
git add src/com/example/GWTOAuthLoginDemo/server/OAuth/OurOAuthParams.java
git commit -a -m "$COMMENT"
echo "Sleeping 10 seconds....CTRL+C to interrupt"
sleep 10
git push
