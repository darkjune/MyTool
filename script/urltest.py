import urllib2
from sgmllib import SGMLParser

url = "http://www.cnblogs.com/zhukunrong"

proxy = urllib2.ProxyHandler({'http':'127.0.0.1:3128'}) 
opener = urllib2.build_opener(proxy)
urllib2.install_opener(opener)

class ListName(SGMLParser):
	def __init__(self):
		SGMLParser.__init__(self)
		self.is_div_day = False
		self.get_title_data = False
		self.level = 0
		self.comments = []
		self.postTitle = []
	def start_div(self, attrs):
		for k,v in attrs:
			if k=='class' and v=='day':
				self.is_div_day=True
			if k=='class' and v=='postTitle' and self.is_div_day==True:
				self.get_title_data = True
				self.level = self.level+1

	def end_div(self):
		self.level = self.level-1
		if self.level ==0:
			self.get_title_data = False
	def handle_data(self, text):
		if self.get_title_data ==True and self.is_div_day==True :
			self.postTitle.append(text)

# retrieve the result
#response = urllib2.urlopen(url)
content = urllib2.urlopen(url).read()
#print(content)

listname = ListName()
listname.feed(content)
for item in listname.postTitle:
	print 'item:' + item
	#.decode('gbk').encode('utf8')




