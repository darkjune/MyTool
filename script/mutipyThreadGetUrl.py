import urllib2  
import time  
from threading import Thread  
class GetUrlThread(Thread):  
  def __init__(self, url):  
      self.url = url   
      super(GetUrlThread, self).__init__()  
  def run(self): 
      proxy = urllib2.ProxyHandler({'http':'127.0.0.1:3128'}) 
      opener = urllib2.build_opener(proxy)
      urllib2.install_opener(opener)
      resp = urllib2.urlopen(self.url)  
      print self.url, resp.getcode(), self.name  
def get_responses():  
  urls = [  
      'http://google.com/', 
      'https://10.158.171.142/', 
      'https://10.158.171.137',   
      'http://10.158.171.131'  
  ]  
  start =  time.time()

  threads = []  
  for url in urls:  
      t = GetUrlThread(url)  
      threads.append(t)  
      t.start()  
  for t in threads:  
      t.join()  
  print "Elapsed time: %s" % (time.time()-start)  
get_responses()  