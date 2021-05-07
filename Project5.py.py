import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.base import MIMEBase
from email import encoders
import zipfile
import os
import re
import subprocess

src = "\\home\\asigdel\\CSC344\\"
a1 = open(src + r"Project1.c", 'r')
a2 = open(src + r"Project2.clj", 'r')
a3 = open(src + r"Project3.scala", 'r')
a4 = open(src + r"Project4.pl", 'r')
a5 = open(src + r"Project5.py", 'r')


def getIdentifiers(file):
    files = set()
    comments = re.compile("((#.*)|\".*?\"|\'.*?\'|(//.*)|(--.*)$)|/\*.*\*/")
    code = re.compile("[a-zA-Z_]+\w*")
    for lines in file:
        identifiers = code.findall(comments.sub("", lines))
        files |= set(identifiers)
    return [os.path.basename(sym) for sym in files]


Cidentifer = (getIdentifiers(a1))
Clojureidentifer = (getIdentifiers(a2))
Scalaidentifer = (getIdentifiers(a3))
Prologidentifer = (getIdentifiers(a4))
Pythonidentifer = (getIdentifiers(a5))

clines = sum(1 for line in open(src + r"Project1.c", 'r'))
clojurelines = sum(1 for line in open(src + r"Project2.clj", 'r'))
scalalines = sum(1 for line in open(src + r"Project3.scala", 'r'))
prologlines = sum(1 for line in open(src + r"Project4.pl", 'r'))
pythonlines = sum(1 for line in open(src + r"Project5.py", 'r'))

for file in os.listdir(src):
    if file == 'Project1.c':
        chtml = open('summary_a1.html', 'w')
        webpage = "<html>\
                    <head> \
                    <body> \
                    <link><br><a href=" + src + r"Project1.c" + ">C Project </a></br></link><h3> Line count is: " + str(
            clines) + "</h3><h3>\
                    Identifiers are: </h4>" + str(Cidentifer) + "\
                    </h3></body></html>"
        chtml.write(webpage)
        chtml.close()

    if file == 'Project2.clj':
        clojurehtml = open('summary_a2.html', 'w')
        webpage = "<html>\
                    <head> \
                    <body> \
                    <link><br><a href=" + src + r"Project2.clj" + ">Clojure Project </a></br></link><h3> Line count is: " + str(
            clojurelines) + "</h3><h3>\
                    Identifiers are: </h4>" + str(Clojureidentifer) + "\
                    </h3></body></html>"
        clojurehtml.write(webpage)
        clojurehtml.close()

    if file == 'Project3.scala':
        scalahtml = open('summary_a3.html', 'w')
        webpage = "<html>\
                <head> \
                <body> \
                <link><br><a href=" + src + r"Project3.scala" + ">Scala Project </a></br></link><h3> Line count is: " + str(
            scalalines) + "</h3><h3>\
                Identifiers are: </h4>" + str(Scalaidentifer) + "\
                </h3></body></html>"
        scalahtml.write(webpage)
        scalahtml.close()

    if file == 'Project4.pl':
        prologhtml = open('summary_a4.html', 'w')
        webpage = "<html>\
                <head> \
                <body> \
                <link><br><a href=" + src + r"Project4.pl" + ">Prolog Project </a></br></link><h3> Line count is: " + str(
            prologlines) + "</h3><h3>\
                Identifiers are: </h4>" + str(Prologidentifer) + "\
                </h3></body></html>"
        prologhtml.write(webpage)
        prologhtml.close()

    if file == 'Project5.py':
        pythonhtml = open('summary_a5.html', 'w')
        webpage = "<html>\
                <head> \
                <body> \
                <link><br><a href=" + src + r"Project5.py" + ">Python Project </a></br></link><h3> Line count is: " + str(
            pythonlines) + "</h3><h3>\
                Identifiers are: </h4>" + str(Pythonidentifer) + "\
                </h3></body></html>"
        pythonhtml.write(webpage)
        pythonhtml.close()

html = open('index.html', 'w')
webpage = """<html>
<head>
<body bgcolor="MintCream"> 
<p>Python Project</p>
<ul><li><a href="summary_a1.html">C Project</a>     
<br><li><a href="summary_a2.html">Clojure Project</a>    
<br><li><a href="summary_a3.html">Scala Project</a>   
<br><li><a href="summary_a4.html">Prolog Project</a>    
<br><li><a href="summary_a5.html">Python Project</a>
</head>
</body>
</html>"""
html.write(webpage)
html.close()

zip = zipfile.ZipFile(src + "final.zip", "w")
zip.write("index.html")
zip.write("summary_a1.html")
zip.write("summary_a2.html")
zip.write("summary_a3.html")
zip.write("summary_a4.html")
zip.write("summary_a5.html")
zip.write(src + r"Project1.c")
zip.write(src + r"Project2.clj")
zip.write(src + r"Project3.scala")
zip.write(src + r"Project4.pl")
zip.write(src + r"Project5.py")
zip.close()

From = "asigdel@oswego.edu"
Password = "NOTMYPASSWORDBUTISIT:)?"
Subject = "Python Project - Anubhav Sigdel"
To = input("Enter email: ")

#email = MIMEMultipart()
#email['From'] = From
#email['To'] = To
#email['Subject'] = Subject

emailzip = open(src + "final.zip", 'rb')
# attachment = MIMEBase('application', 'zip')
# attachment.set_payload(emailzip.read())
# encoders.encode_base64(attachment)
# attachment['Content-Disposition'] = 'attachment; filename = outzip.zip'
# email.attach(attachment)
# text = email.as_string()
# server = smtplib.SMTP('smtp.gmail.com', 587)
# server.starttls()
# server.login(From, Password)
# server.sendmail(From, To, text)
# server.quit()
email = subprocess.Popen('mutt -s "Python Project" ' + To + ' -a ' + emailzip + ' ',
                         shell=True)
email.communicate()
