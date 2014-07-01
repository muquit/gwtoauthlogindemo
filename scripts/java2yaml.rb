#!/usr/bin/env ruby

#
# create a yaml file file by parsing Serverutils.java
# just a starter
# muquit@muquit.com Jun-15-2014 
class MakeYamlFromServerUtilsJava
  ME = $0
  def initialize
  end

  def generate_yaml(file)
    lines = File.readlines(file)
    last_provider = ''

    providers = {}
    params = {}

    lines.each do |line|
      line.chomp! if line
      line.strip! if line
      if line =~ /^public static final String (.+)\s+=(.+);$/
        key = $1
        value = $2.strip
        value = value.gsub(/\"/,'')
        a = key.split("_")
        provider = a[0].downcase
        if a.size == 3
          param = "#{a[1]}_#{a[2]}".downcase.strip
        end
        if a.size == 2
          param = "#{a[1]}".downcase.strip
        end
        params[param] = value.strip
        if providers.has_key?(provider)
          params[param] = value
        else
          params = {}
          params[param] = value
          providers[provider] = params
        end 
      end
    end
    puts "--- !ruby/object:OAuthParams"
    puts "providers:"
    providers.each do |provider,params|
    puts "  #{provider}:"
      params.each do |k,v|
        puts "    #{k}: '#{v}'"
      end
    end
  end

  def doit
    if ARGV.length != 1
      puts <<EOF
  Usage: #{ME} <ServerUtils.java>
EOF
      exit 1
    end
    generate_yaml(ARGV[0])
  end
end

if __FILE__ == $0
  MakeYamlFromServerUtilsJava.new.doit
end
