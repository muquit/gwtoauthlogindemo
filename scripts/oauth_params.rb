#!/usr/bin/env ruby

########################################################################
# A model to hold Oauth parameter values
# It's getting difficult to main multiple versions of parameters for
# production and testing.
# muquit@muquit.com Jun-15-2014 
########################################################################
class OAuthParams
  VERSIN = "1.0.1"
  attr_accessor :redirect_url
  attr_accessor :providers
  attr_accessor :params
  def initialize
    self.redirect_url = ''
    self.providers = {}
    self.params = {}
  end
end
