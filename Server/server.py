__author__ = 'T90'
__version__ = '1.0.0'

import socket
from threading import Thread

HOST = "192.168.1.2"
PORT = 8000
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((HOST, PORT))
s.listen(1)
conn, addr = s.accept()

print 'connected by ' + repr(addr)
data = conn.recv(1024)
if "123" in str(data):
	conn.sendall("456")
	
def rec():
	while True:
		data = conn.recv(1024)
		print 'Rec : ' + data
		if not data: break
		
def sen():
	while True:
		reply = raw_input('Reply : ')
		conn.sendall(reply)

		
Thread(target=rec).start()
Thread(target=sen).start()
