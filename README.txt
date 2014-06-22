WARNING:The information in this file could be stale, please use the web page for the latest information.
Web page: https://code.google.com/p/gwtoauthlogindemo/

Register the App URL with OAuth Providers (Facebook, Google, Twitter etc.) first. Most accept accept
127.0.0.1 in the URL, some dont. Therefore it's a good idea to a domain in your URL and add the domain
in /etc/hosts file in Unix and c:\Windows\System32\drivers\etc\hosts in Windows. Microsoft even wants
a domain which no one is using, so be creative in naming. I am just using localhost.com.

How to run
==========
 
 * In Linux/Unix/Mac, edit /etc/hosts file, add the following:
 
 127.0.0.1 localhost.com

   In windows, edit c:\Windows\System32\drivers\etc\hosts file as Administrator.
 
 * verify: $ ping localhost.com
PING localhost.com (127.0.0.1): 56 data bytes
64 bytes from 127.0.0.1: icmp_seq=0 ttl=64 time=0.045 ms
64 bytes from 127.0.0.1: icmp_seq=1 ttl=64 time=0.080 ms
...
 
 * GWT App must be compiled first, it can not be run as Code server.
   Easiest way is 'compile-and-run' ant task.
 
 * This is an important step. Start your web broswer and enter the 
   App URL as follows:

    http://localhost.com:8888/GWTOAuthLoginDemo.html
   
How to add a new provider
=========================
 * Check if scribe supports it, if not implement it yourself.   
   Look at server/OAuth/GithubApi.java, server/OAuth/InstragramApi.java
   for example.
   
 * Modify ClientUtils.java
     - Add protected resource URL
     - add provider in getAuthProvider()
     - add provider in getAuthProviderName()
     - add provider in getCredential() depending on how it returns verifier.
     
 * Modify ServerUtils.java
 
 * add icon in 32px directory
 
 * update MyImages.java with the new icon image
 
 * Modify LoginScreen.java
    - add the image
    
 * Modify GWTOAuthLoginDemo.java
    - add click handler for the image
     
 * Modify OAuthLoignServiceImpl.java
     - implement servcie
     - look at verifySocialUser() 
     - add case for json parsing
   
-- 
muquit@muquit.com 
Nov-27-2012
