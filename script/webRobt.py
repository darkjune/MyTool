import re
from robobrowser import RoboBrowser

# Browse to Rap Genius
browser = RoboBrowser(history=True)
browser.open('http://news.dbanotes.net/')
txt = browser.find(class_=re.compile(r'.15.'))
txt.text
