#!/usr/bin/env ruby

########################################################################
# Generate template yaml file for local, gae test and gae production
# I do not publish my yaml files. 
# Copy the file to the 'private' directory, update the parameters
# Then use the appropriate ant task to generate the Java files.
# muquit@muquit.com Jul-01-2014 
########################################################################

require 'yaml'
require 'pp'
require_relative 'oauth_params.rb'

class GenerateYamlTemplate
  def initialize
  end

  def doit
    dir = File.expand_path(File.dirname(__FILE__))
    base_dir = File.dirname(dir)
    yaml_file = base_dir + "/private/local.yaml"
    oauth = YAML::load_file(yaml_file)
    providers = oauth.providers
    puts "--- !ruby/object:OAuthParams"
    puts <<EOF
########################################################################
# If you want to use this file to test OAuth in your local machine:
#  - Copy this file to local.yaml
#  - Come up with a fictitious domain name that does not exist
#  - Use the domain and URL to register the application with OAuth
#    providers e.g. facebook, google etc.
#  - update the redirect_url with your domain
#  - Update API Key and API secret.
#  - If you are on Linux/Unix/Mac, edit /etc/hosts file:
# 127.0.0.1 yourdomain.com
#  - If you on Windows, update c:\\Windows\\System32\\drivers\\etc\\hosts
#    as Asministrator
#
#  - run the ant task prepare-dev-local (refresh if in eclipse)
#  - run compile-and-run-local
# Note: the above ant tasks require ruby 1.9x
#  - point your browser to http://your_domain:8888/GWTOAuthLoginDemo.html
########################################################################
redirect_url: 'http://your_domain:8888/GWTOAuthLoginDemo.html'
EOF
    puts "providers:"
    providers.each do |provider,params|
      puts "  #{provider}:"
      params.each do |k,v|
        puts "    #{k}: 'Your #{k}'"
      end
    end
  end

end

if __FILE__ == $0
  GenerateYamlTemplate.new.doit
end
