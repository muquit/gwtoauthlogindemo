#!/bin/sh

########################################################################
# ALWAYS run this script instead 'git push'
# it will create OurOAuthParams.java without my registered keys and 
# secrets
# muquit@muquit.com Jul-05-2014 
########################################################################
echo ": Generating dummy OAuth parameters"
ant prepare-before-push
git add src/com/example/GWTOAuthLoginDemo/client/OAuth/OurCallbackUrl.java
git add src/com/example/GWTOAuthLoginDemo/server/OAuth/OurOAuthParams.java
git commit -m "push to github. replaced oauth parameters with dummy data"
git push
