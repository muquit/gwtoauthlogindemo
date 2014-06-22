#!/usr/bin/env ruby

########################################################################
# A ruby script to kill gwt devmode. It is intended to run from ant.
# It assumes the directory name is the name of the process.
# muquit@muquit.com Nov-10-2012 
########################################################################

class KillGwtDevmode
  ME = File.basename($0)
  def initialize
  end

  def get_project_name
    pwd = Dir.pwd
    name = File.basename(pwd)
    return name
  end

  def log(msg)
    puts "#{ME}: #{msg}"
    STDOUT.flush
  end

  def log_nonl(msg)
    print "#{ME}: #{msg}"
    STDOUT.flush
  end

  def pid_exists(pid)
    log_nonl "Checking if PID #{pid} still exists.."
    begin
      rc = Process.kill(0,pid.to_i)
      log_nonl "yes\n"
      return pid
    rescue Errno::ESRCH
      log "\nOK! PID #{pid} is dead"
      exit 0
    end
  end

  def get_pid(name)
    process = `ps -eaf | grep #{name} | grep com.google.gwt.dev.DevMode |grep -v grep`.strip
    if process.length == 0
      exit 0
    end
    log "Process: '#{process}'"
    pid = nil
    if process =~ /^(\d+)\s+(\d+)\s+.*$/
      pid = $2
    end
    return pid_exists(pid)
  end

  def killpid(pid)
    log "Killing GWT Devmode process #{pid}"
    Process.kill('TERM',pid.to_i)
  end

  def doit
    name = get_project_name
    if name.nil?
      log "Could not find any GWT Devmode process"
      exit 0
    end
    pid = get_pid(name)
    log "Found GWT devmode process. killing it"
    killpid(pid)
    check(pid)
  end

  def check(pid)
    n = 0
    max = 10
    loop do 
      pid_exists(pid)
      sleep 1
      n = n + 1
      if n >= max
        exit 1
      end
    end
  end

end

if __FILE__ == $0
  KillGwtDevmode.new.doit
end

